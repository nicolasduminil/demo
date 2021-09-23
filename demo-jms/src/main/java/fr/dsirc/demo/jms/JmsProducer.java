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

import javax.jms.*;
import java.net.*;

@Component
@EnableJms
@Data
@Slf4j
public class JmsProducer
{
  @Value("${jms.destination.name}")
  private String destinationName;
  @Value("${spring.activemq.broker-url}")
  private URI jmsBrokerURL;
  private JmsTemplate jmsTemplate;

  @Bean
  public ConnectionFactory jmsConnectionFactory()
  {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(jmsBrokerURL);
    activeMQConnectionFactory.setTrustAllPackages(true);
    RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
    redeliveryPolicy.setMaximumRedeliveries(1);
    activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
    return activeMQConnectionFactory;
  }

  @Bean
  public JmsTemplate jmsTopicTemplate(ConnectionFactory jmsConnectionFactory)
  {
    jmsTemplate = new JmsTemplate(jmsConnectionFactory);
    jmsTemplate.setDefaultDestinationName(destinationName);
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

  @Bean
  public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer)
  {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    return factory;
  }

  public void send(String msg)
  {
    log.debug("### JmsProducer.send(): Sending {} to topic {}", msg, jmsTemplate.getDefaultDestinationName());
    jmsTemplate.convertAndSend(destinationName, msg);
  }

  public String getDestinationName()
  {
    return destinationName;
  }

  public JmsTemplate getJmsTemplate()
  {
    return jmsTemplate;
  }
}
