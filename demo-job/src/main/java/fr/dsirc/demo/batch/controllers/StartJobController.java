package fr.dsirc.demo.batch.controllers;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/demo/start")
public class StartJobController
{
  @Autowired
  private ApplicationContext applicationContext;
  @Autowired
  private JobLauncher jobLauncher;

  @PostMapping
  public ResponseEntity<ExitStatus> startBatchProcessing(@RequestBody String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException
  {
    Job job = applicationContext.getBean(jobName, Job.class);
    return new ResponseEntity<>(jobLauncher.run(job, new JobParameters()).getExitStatus(), HttpStatus.OK);
  }
}
