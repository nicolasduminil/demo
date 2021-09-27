package fr.dsirc.demo.batch;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.*;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoJobApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(DemoJobApplication.class, args);
  }
}
