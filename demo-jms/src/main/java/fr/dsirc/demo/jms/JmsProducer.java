package fr.dsirc.demo.jms;

import lombok.*;
import org.apache.activemq.*;
import org.apache.activemq.command.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jms.annotation.*;
import org.springframework.jms.config.*;
import org.springframework.jms.core.*;
import org.springframework.stereotype.*;

import javax.jms.*;
import java.net.*;

@Component
@EnableJms
@Data
public class JmsProducer
{
  @Value("${jms.destination.name}")
  private String destinationName;
  @Value("${jms.broker.url}")
  private URI jmsBrokerURL;
  private JmsTemplate jmsTemplate;

  @Bean(name = "connectionFactory")
  public ConnectionFactory jmsConnectionFactory()
  {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(jmsBrokerURL.toString());
    activeMQConnectionFactory.setTrustAllPackages(true);
    RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
    redeliveryPolicy.setMaximumRedeliveries(1);
    activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
    return new ActiveMQConnectionFactory(jmsBrokerURL.toString());
  }

  @Bean
  public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory)
  {
    jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setDefaultDestinationName(destinationName);
    jmsTemplate.setPubSubDomain(true);
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }

  @Bean
  public Topic demoTopic()
  {
    return new ActiveMQTopic(destinationName);
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory)
  {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setConcurrency("1-1");
    factory.setPubSubDomain(true);
    return factory;
  }

  public void send(String msg)
  {
    jmsTemplate.convertAndSend(destinationName, msg);
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
