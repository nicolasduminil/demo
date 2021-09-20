package fr.dsirc.demo.batch.listeners;

import fr.dsirc.demo.model.*;
import lombok.extern.slf4j.*;
import org.springframework.batch.core.*;
import org.springframework.stereotype.*;

@Slf4j
public class JpaRepositoryItemReaderListener implements ItemReadListener<Individual>
{
  @Override
  public void beforeRead()
  {
    log.info("### JpaRepositoryItemReaderListener.beforeRead()");
  }

  @Override
  public void afterRead(Individual individual)
  {
    log.info("### JpaRepositoryItemReaderListener.afterRead(): {}", individual.getRef());
  }

  @Override
  public void onReadError(Exception e)
  {
    log.info("### JpaRepositoryItemReaderListener.onReadError(): {}", e.getMessage());
  }
}
