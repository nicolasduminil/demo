package fr.dsirc.demo.batch.config;

import org.springframework.boot.actuate.trace.http.*;
import org.springframework.context.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
  ApiInfo apiInfo()
  {
    return new ApiInfo("Demo Job",
      "Rest API for demonstrating the Demo job batch processing.",
      "0.0.1-SNAPSHOT", "Terms of service",
      new Contact("Nicolas DUMINIL", "www.agirc-arrco.fr", "nduminil-ext@agirc-arrco.fr"),
      "License of API", "API license URL", Collections.emptyList());
  }

  @Bean
  public Docket configureControllerPackageAndConvertors()
  {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .paths(PathSelectors.ant("/demo/**")).build()
      .apiInfo(apiInfo());
  }

  @Bean
  public HttpTraceRepository httpTraceRepository()
  {
    return new InMemoryHttpTraceRepository();
  }
}
