package fr.dsirc.demo.xa.config;

import org.apache.activemq.*;
import org.apache.camel.component.activemq.*;
import org.apache.camel.spring.spi.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.jms.*;
import org.springframework.boot.autoconfigure.jms.activemq.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.jta.*;

import javax.jms.*;

@Configuration
public class ActivemqConfig
{
  @Value("${spring.activemq.broker-url}")
  private String brokerUrl;

  @Bean
  public ActiveMQComponent activemq(ConnectionFactory connectionFactory, JtaTransactionManager jtaTransactionManager)
  {
    ActiveMQComponent component = new ActiveMQComponent();
    component.setAcknowledgementMode(JmsProperties.AcknowledgeMode.CLIENT.getMode());
    component.setCacheLevelName("CACHE_CONSUMER");
    component.setConnectionFactory(connectionFactory);
    component.setTransacted(true);
    component.setTransactionManager(jtaTransactionManager);
    return component;
  }

  @Bean
  public ActiveMQConnectionFactoryCustomizer activeMQConnectionFactoryCustomizer()
  {
    return factory ->
    {
      RedeliveryPolicy policy = new RedeliveryPolicy();
      policy.setRedeliveryDelay(1000);
      policy.setMaximumRedeliveries(2);
      factory.setRedeliveryPolicy(policy);
    };
  }

  @Bean("policyPropagationRequired")
  public SpringTransactionPolicy transactionPolicyPropagationRequired(@Autowired JtaTransactionManager transactionManager)
  {
    SpringTransactionPolicy policy = new SpringTransactionPolicy(transactionManager);
    policy.setPropagationBehaviorName("PROPAGATION_REQUIRED");
    return policy;
  }
}
