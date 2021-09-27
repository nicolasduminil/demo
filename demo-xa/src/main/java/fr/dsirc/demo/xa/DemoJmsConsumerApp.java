package fr.dsirc.demo.xa;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.*;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoJmsConsumerApp
{
  public static void main(String[] args) {
    SpringApplication.run(DemoJmsConsumerApp.class, args);
  }
}
