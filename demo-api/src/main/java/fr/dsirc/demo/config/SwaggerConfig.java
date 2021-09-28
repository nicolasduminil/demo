package fr.dsirc.demo.config;

import org.springframework.boot.actuate.trace.http.*;
import org.springframework.context.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

import java.net.*;
import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
  ApiInfo apiInfo() throws UnknownHostException
  {
    return new ApiInfo("Demo API on " + InetAddress.getLocalHost().toString(),
      "Rest API for demonstrating the Demo application.",
      "0.0.1-SNAPSHOT", "Terms of service",
      new Contact("Nicolas DUMINIL", "www.agirc-arrco.fr", "nduminil-ext@agirc-arrco.fr"),
      "License of API", "API license URL", Collections.emptyList());
  }

  @Bean
  public Docket configureControllerPackageAndConvertors() throws UnknownHostException
  {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .paths(PathSelectors.ant("/demo/**")).build()
      .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
      .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
      .apiInfo(apiInfo());
  }

  @Bean
  public HttpTraceRepository httpTraceRepository()
  {
    return new InMemoryHttpTraceRepository();
  }
}
