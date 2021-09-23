package fr.dsirc.demo.xa;

import org.apache.camel.*;
import org.apache.camel.builder.*;
import org.apache.camel.test.spring.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.jdbc.core.*;
import org.springframework.jms.core.*;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

import javax.jms.*;
import javax.sql.*;
import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class TestDemoXa
{
  @Autowired
  private DataSource dataSource;
  @Autowired
  private CamelContext camelContext;
  @Autowired
  @Qualifier("nonXaJmsConnectionFactory")
  private ConnectionFactory jmsConnectionFactory;
  private JmsTemplate jmsTemplate;
  private JdbcTemplate jdbcTemplate;

  @Before
  public void setup()
  {
    jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(jmsConnectionFactory);
    jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.update("delete from message");
  }

  @Test
  public void testFailure()
  {
    NotifyBuilder notify = new NotifyBuilder(camelContext)
      .fromRoute("demoRoute")
      .whenFailed(3)
      .create();
    jmsTemplate.send("demoQueue", session -> session.createTextMessage("testtesttest"));
    assertThat(notify.matches(30, TimeUnit.SECONDS)).isTrue();
    List<String> messages = jdbcTemplate.queryForList("select contents from message", String.class);
    assertThat(messages).isEmpty();
  }

  // this test fails, since the message is not yet committed to the database in the XA transaction
  // when NotifyBuilder reports route completion
  @Test
  @Transactional
  //@Ignore
  public void testSuccessWrong()
  {
    NotifyBuilder notify = new NotifyBuilder(camelContext)
      .fromRoute("demoRoute")
      .whenCompleted(1)
      .create();
    jmsTemplate.send("TestQueue", session -> session.createTextMessage("testtestte"));
    assertThat(notify.matches(30, TimeUnit.SECONDS)).isTrue();
    List<String> messages = jdbcTemplate.queryForList("select contents from message", String.class);
    assertThat(messages).hasSize(1);
    assertThat(messages.get(0)).isEqualTo("testtestte");
  }

  @Test
  @Transactional // you can also omit the @Transactional annotation here
  public void testSuccessRight() throws Exception
  {
    NotifyBuilder notify = new NotifyBuilder(camelContext)
      .fromRoute("queueRoute")
      .whenCompleted(1)
      .create();
    CountDownLatch transactionLatch = new CountDownLatch(1);
    AdviceWithRouteBuilder.adviceWith(camelContext, "demoRoute", context -> context.weaveAddFirst().process(exchange -> TransactionSynchronizationManager.registerSynchronization
      (
        new TransactionSynchronizationAdapter()
        {
          @Override
          public void afterCommit()
          {
            transactionLatch.countDown();
          }
        }
      )));
    jmsTemplate.send("TestQueue", session -> session.createTextMessage("testtestte"));
    assertThat(notify.matches(20, TimeUnit.SECONDS)).isTrue();
    assertTrue(transactionLatch.await(5, TimeUnit.SECONDS));
    List<String> messages = jdbcTemplate.queryForList("select contents from message", String.class);
    assertThat(messages).hasSize(1);
    assertThat(messages.get(0)).isEqualTo("testtestte");
  }
}
