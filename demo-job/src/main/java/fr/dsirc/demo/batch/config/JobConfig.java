package fr.dsirc.demo.batch.config;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jms.*;
import fr.dsirc.demo.jpa.*;
import fr.dsirc.demo.mapper.*;
import fr.dsirc.demo.repo.*;
import lombok.extern.slf4j.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.explore.*;
import org.springframework.batch.core.launch.support.*;
import org.springframework.batch.core.partition.*;
import org.springframework.batch.core.partition.support.*;
import org.springframework.batch.core.repository.*;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.*;
import org.springframework.batch.item.data.builder.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.cloud.deployer.resource.support.*;
import org.springframework.cloud.deployer.spi.task.*;
import org.springframework.cloud.task.batch.partition.*;
import org.springframework.cloud.task.configuration.*;
import org.springframework.cloud.task.repository.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.*;
import org.springframework.core.io.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.jms.support.converter.*;

import javax.xml.bind.*;
import java.io.*;
import java.util.*;

@Configuration
@EnableBatchProcessing
@EnableTask
@EnableJpaRepositories(basePackageClasses = {IndividualRepository.class})
@ComponentScan(basePackages = {"fr.dsirc.demo.jms", "fr.dsirc.demo.repo", "fr.dsirc.demo.jpa", "fr.dsirc.demo.mapper", "fr.dsirc.demo.services"})
@EntityScan("fr.dsirc.demo.jpa")
@Slf4j
public class JobConfig
{
  private static final int GRID_SIZE = 1;
  @Autowired
  public JobBuilderFactory jobBuilderFactory;
  @Autowired
  public StepBuilderFactory stepBuilderFactory;
  @Autowired
  public JobRepository jobRepository;
  @Autowired
  public JobExplorer jobExplorer;
  @Autowired
  private ConfigurableApplicationContext context;
  @Autowired
  private DelegatingResourceLoader resourceLoader;
  @Autowired
  private Environment environment;
  @Autowired
  public TaskExplorer taskExplorer;
  @Autowired
  public TaskLauncher taskLauncher;
  @Autowired
  public TaskRepository taskRepository;
  @Autowired
  private IndividualRepository individualRepository;
  @Autowired
  private JmsProducer jmsProducer;
  @Autowired
  private IndividualTypeMapper individualTypeMapper;
  private final Marshaller marshaller;

  public JobConfig() throws JAXBException
  {
    marshaller = JAXBContext.newInstance(IndividualType.class).createMarshaller();
  }

  @Bean
  @StepScope
  public PartitionHandler partitionHandler(@Value("#{stepExecution}") StepExecution stepExecution)
  {
    String currentStepName = stepExecution.getStepName();
    String workerStepName = stepExecution.getJobExecution().getExecutionContext().getString(currentStepName + "_corresponding_worker_job");
    Resource resource = this.resourceLoader.getResource("file:///demo-job.jar");
    DeployerPartitionHandler partitionHandler =
      new DeployerPartitionHandler(taskLauncher, jobExplorer, resource, workerStepName, taskRepository);
    TaskExecution taskExecution = taskExplorer.getTaskExecution(taskExplorer.getTaskExecutionIdByJobExecutionId(stepExecution.getJobExecutionId()));
    partitionHandler.beforeTask(taskExecution);
    List<String> commandLineArgs = new ArrayList<>(3);
    commandLineArgs.add("--spring.profiles.active=worker");
    commandLineArgs.add("--spring.cloud.task.initialize-enabled=false");
    commandLineArgs.add("--spring.batch.initializer.enabled=false");
    partitionHandler
      .setCommandLineArgsProvider(new PassThroughCommandLineArgsProvider(commandLineArgs));
    partitionHandler
      .setEnvironmentVariablesProvider(new SimpleEnvironmentVariablesProvider(this.environment));
    partitionHandler.setMaxWorkers(GRID_SIZE);
    partitionHandler.setApplicationName("PartitionedBatchJobTask");
    return partitionHandler;
  }

  @Bean
  public Partitioner partitioner()
  {
    return gridSize ->
    {
      Map<String, ExecutionContext> partitions = new HashMap<>(gridSize);
      for (int i = 0; i < GRID_SIZE; i++)
      {
        ExecutionContext context1 = new ExecutionContext();
        context1.put("partitionNumber", i);
        partitions.put("partition" + i, context1);
      }
      return partitions;
    };
  }

  @Bean
  @Profile("worker")
  public DeployerStepExecutionHandler stepExecutionHandler(JobExplorer jobExplorer)
  {
    return new DeployerStepExecutionHandler(this.context, jobExplorer, this.jobRepository);
  }

  @Bean
  public Step step1()
  {
    return this.stepBuilderFactory.get("step1")
      .allowStartIfComplete(true)
      .partitioner(workerStep().getName(), partitioner())
      .step(workerStep())
      .partitionHandler(partitionHandler(null))
      .build();
  }

  @Bean
  @StepScope
  public RepositoryItemReader<IndividualEntity> repositoryItemReader()
  {
    return new RepositoryItemReaderBuilder<IndividualEntity>()
      .name("repositoryItemReader")
      .methodName("findAll")
      .repository(individualRepository)
      .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
      .build();
  }

  @Bean
  public ItemWriter<IndividualEntity> individualItemWriter()
  {
    StringWriter sw = new StringWriter();
    return (items) -> items.forEach(item ->
    {
      try
      {
        marshaller.marshal(individualTypeMapper.toIndividualType(item), sw);
        jmsProducer.send(sw.toString());
       }
      catch (JAXBException e)
      {
        log.error("### JobConfig.individualItemWriter(): Exception {}", e);
        e.printStackTrace();
      }
    });
  }

  @Bean
  public Step workerStep()
  {
    return stepBuilderFactory.get("workerStep")
      .<IndividualEntity, IndividualEntity>chunk(1)
      .reader(repositoryItemReader())
      .writer(individualItemWriter())
      .build();
  }

  @Bean
  public JobExecutionListener jobExecutionListener()
  {
    return new JobExecutionListener()
    {
      @Override
      public void beforeJob(JobExecution jobExecution)
      {
        jobExecution.getExecutionContext().putString("step1_corresponding_worker_job", "workerStep");
      }

      @Override
      public void afterJob(JobExecution jobExecution)
      {
      }
    };
  }

  @Bean
  public MessageConverter jacksonJmsMessageConverter()
  {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

  @Bean
  @Profile("!worker")
  public Job partitionedJob()
  {
    return jobBuilderFactory.get("partitionedJob")
      .incrementer(new RunIdIncrementer())
      .listener(jobExecutionListener())
      .start(step1())
      .build();
  }
}
