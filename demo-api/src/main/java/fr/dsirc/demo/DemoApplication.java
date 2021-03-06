package fr.dsirc.demo;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@SpringBootApplication
@ComponentScan(basePackages = {"fr.dsirc.demo.api", "fr.dsirc.demo.config", "fr.dsirc.demo.provider", "fr.dsirc.demo.swagger",
  "fr.dsirc.demo.jaxb", "fr.dsirc.demo.jpa", "fr.dsirc.demo.mapper", "fr.dsirc.demo.model", "fr.dsirc.demo.repo",
  "fr.dsirc.demo.services", "fr.dsirc.demo.services.impl", "fr.dsirc.demo.serializers"})
public class DemoApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(DemoApplication.class, args);
  }

  @LoadBalanced
  @Bean
  public RestTemplate restTemplate()
  {
    return new RestTemplate();
  }
}
