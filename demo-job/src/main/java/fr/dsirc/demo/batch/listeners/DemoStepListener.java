package fr.dsirc.demo.batch.listeners;

import lombok.extern.slf4j.*;
import org.springframework.batch.core.*;

@Slf4j
public class DemoStepListener implements StepExecutionListener
{
  @Override
  public void beforeStep(StepExecution stepExecution)
  {
    log.info("### DemoStepListener.beforeStep(): {}", stepExecution.getStepName());
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution)
  {
    log.info("### DemoStepListener.afterStep(): {} {}", stepExecution.getStepName(), stepExecution.getReadCount());
    return null;
  }
}
