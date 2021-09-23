package fr.dsirc.demo.xa.components;

import org.apache.camel.builder.*;
import org.springframework.stereotype.*;

@Component
public class JmsConsumer extends RouteBuilder
{
  @Override
  public void configure() throws Exception
  {
    from("activemq:queue:demoQueue")
      .routeId("demoRoute")
      .transacted("policyPropagationRequired")
      .to("sql:insert into message(contents) values(:#${body})")
      .log(">>> JmsConsumer.configure(): 30s delay to simulate an outage")
      .delay(30000)
      .log(">>> JmsConsumer.configure(): end of 30s delay. Transaction should recover");
  }
}
