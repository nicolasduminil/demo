package fr.dsirc.demo.gateway;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.netflix.eureka.*;

@SpringBootApplication
@EnableEurekaClient
public class DemoGatewayApp
{
  public static void main (String[] args)
  {
    SpringApplication.run (DemoGatewayApp.class);
  }
}
