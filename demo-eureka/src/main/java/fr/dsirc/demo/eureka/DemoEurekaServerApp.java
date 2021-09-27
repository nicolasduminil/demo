package fr.dsirc.demo.eureka;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.netflix.eureka.server.*;

@SpringBootApplication
@EnableEurekaServer
public class DemoEurekaServerApp
{
  public static void main (String[] args)
  {
    SpringApplication.run(DemoEurekaServerApp.class, args);
  }
}
