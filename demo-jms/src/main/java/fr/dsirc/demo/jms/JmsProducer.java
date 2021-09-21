package fr.dsirc.demo.jms;

import lombok.*;
import lombok.extern.slf4j.*;
import org.apache.activemq.*;
import org.apache.activemq.command.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.*;
import org.springframework.context.annotation.*;
import org.springframework.jms.annotation.*;
import org.springframework.jms.config.*;
import org.springframework.jms.connection.*;
import org.springframework.jms.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import javax.jms.*;
import java.net.*;

@Component
@EnableJms
@EnableTransactionManagement
@Data
@Slf4j
public class JmsProducer
{
  @Value("${jms.destination.name}")
  private String destinationName;
  @Value("${jms.broker.url}")
  private URI jmsBrokerURL;
  private JmsTemplate jmsTemplate;

  @Bean
  public ConnectionFactory jmsConnectionFactory()
  {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(jmsBrokerURL.toString());
    activeMQConnectionFactory.setTrustAllPackages(true);
    RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
    redeliveryPolicy.setMaximumRedeliveries(1);
    activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
    return new ActiveMQConnectionFactory(jmsBrokerURL.toString());
  }

  /*@Bean
  public ActiveMQXAConnectionFactory activeMQXAConnectionFactory()
  {
    ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory();
    activeMQXAConnectionFactory.setBrokerURL(jmsBrokerURL.toString());
    activeMQXAConnectionFactory.setTrustAllPackages(true);
    return activeMQXAConnectionFactory;
  }

  @Bean
  @DependsOn("activeMQXAConnectionFactory")
  public AtomikosConnectionFactoryBean atomikosConnectionFactoryBean(ActiveMQXAConnectionFactory activeMQXAConnectionFactory)
  {
    AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
    atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
    atomikosConnectionFactoryBean.setUniqueResourceName("xa.amq");
    atomikosConnectionFactoryBean.setIgnoreSessionTransactedFlag(false);
    atomikosConnectionFactoryBean.setMinPoolSize(10);
    atomikosConnectionFactoryBean.setMaxPoolSize(20);
    atomikosConnectionFactoryBean.setLocalTransactionMode(false);
    return atomikosConnectionFactoryBean;
  }

  @Bean
  public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory)
  {
    jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setDefaultDestinationName(destinationName);
    jmsTemplate.setPubSubDomain(true);
    jmsTemplate.setSessionTransacted(true);
    jmsTemplate.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.CLIENT.getMode());
    return jmsTemplate;
  }

  @Bean
  public JmsTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
    JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
    jmsTransactionManager.setConnectionFactory(connectionFactory);
    return jmsTransactionManager;
  }*/

  @Bean
  public JmsTemplate jmsTopicTemplate(ConnectionFactory jmsConnectionFactory)
  {
    jmsTemplate = new JmsTemplate(jmsConnectionFactory);
    jmsTemplate.setDefaultDestinationName(destinationName);
    jmsTemplate.setPubSubDomain(true);
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }

  @Bean
  public JmsTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory)
  {
    JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
    jmsTransactionManager.setConnectionFactory(connectionFactory);
    return jmsTransactionManager;
  }

  @Bean
  public Topic demoTopic()
  {
    return new ActiveMQTopic(destinationName);
  }

  /*@Bean
  @DependsOn("atomikosConnectionFactoryBean")
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(AtomikosConnectionFactoryBean atomikosConnectionFactoryBean)
  {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(atomikosConnectionFactoryBean);
    factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
    factory.setConcurrency("1-1");
    factory.setPubSubDomain(true);
    factory.setSessionTransacted(true);
    factory.setCacheLevelName("CACHE_CONSUMER");
    return factory;
  }*/
  @Bean
  public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer)
  {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    return factory;
  }


  //@Transactional
  public void send(String msg)
  {
    log.info ("### JmsProducer.send(): Sending {} to topic {}", msg, jmsTemplate.getDefaultDestinationName());
    jmsTemplate.convertAndSend(destinationName, msg);
  }

  //@JmsListener(destination = "${jms.destination.name}"/*, containerFactory = "jmsListenerContainerFactory"*/)
  @JmsListener(destination = "demoTopic")
  public void onMessage(String msg)
  {
    log.info(">>> JmsListener.onMessage(): Have got {}", msg);
  }

  public String getDestinationName()
  {
    return destinationName;
  }

  public URI getJmsBrokerURL()
  {
    return jmsBrokerURL;
  }

  public JmsTemplate getJmsTemplate()
  {
    return jmsTemplate;
  }
}
