package fr.dsirc.demo.batch.listeners;

import lombok.extern.slf4j.*;
import org.springframework.batch.core.*;

import java.util.*;

@Slf4j
public class JmsItemWriterListener implements ItemWriteListener<String>
{
  @Override
  public void beforeWrite(List<? extends String> list)
  {
    list.forEach(s -> log.info("### JmsItemWriterListener.beforeWrite(): {}", s));
  }

  @Override
  public void afterWrite(List<? extends String> list)
  {
    list.forEach(s -> log.info("### JmsItemWriterListener.afterWrite(): {}", s));
  }

  @Override
  public void onWriteError(Exception e, List<? extends String> list)
  {
    log.info("### JmsItemWriterListener.onWriteError(): {}", e.getMessage());
    list.forEach(s -> log.info("### JmsItemWriterListener.onWriteError(): {}", s));
  }
}
