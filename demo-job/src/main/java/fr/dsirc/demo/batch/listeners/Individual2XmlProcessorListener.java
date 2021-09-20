package fr.dsirc.demo.batch.listeners;

import fr.dsirc.demo.model.*;
import lombok.extern.slf4j.*;

import javax.batch.api.chunk.listener.*;

@Slf4j
public class Individual2XmlProcessorListener implements ItemProcessListener
{
  @Override
  public void beforeProcess(Object o) throws Exception
  {
    if (o instanceof Individual)
    {
      Individual individual = (Individual) o;
      log.info("### Individual2XmlProcessorListener.beforeProcess(): {}", individual.getRef());
    }
    else
    {
      log.info("### Individual2XmlProcessorListener.beforeProcess(): {}", o.getClass().getName());
      throw new IllegalStateException(o.toString());
    }
  }

  @Override
  public void afterProcess(Object o, Object o1) throws Exception
  {
    if (o instanceof Individual)
    {
      Individual individual = (Individual) o;
      log.info("### Individual2XmlProcessorListener.afterProcess(): {}", individual.getRef());
    }
    else
    {
      log.info("### Individual2XmlProcessorListener.afterProcess(): {}", o.getClass().getName());
      throw new IllegalStateException(o.toString());
    }
    if (o1 instanceof String)
    {
      String individual = (String) o1;
      log.info("### Individual2XmlProcessorListener.afterProcess(): {}", individual);
    }
    else
    {
      log.info("### Individual2XmlProcessorListener.afterProcess(): {}", o1.getClass().getName());
      throw new IllegalStateException(o1.toString());
    }
  }

  @Override
  public void onProcessError(Object o, Exception e) throws Exception
  {
    log.info("### Individual2XmlProcessorListener.onProcessError(): {} {}", e.getMessage(), o.toString());
  }
}
