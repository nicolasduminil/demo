package fr.dsirc.demo.tests;

import fr.dsirc.demo.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.web.client.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@EnableDiscoveryClient
public class ITDemoApi
{
  @Autowired
  @LoadBalanced
  private RestTemplate restTemplate;

  @Test
  public void test1()
  {
    assertThat(restTemplate).isNotNull();
    ResponseEntity<List<Individual>> individuals = restTemplate.exchange("http://demo-api/demo/individuals", HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
    assertThat(individuals).isNotNull();
    assertThat(individuals.getStatusCode()).isEqualTo(HttpStatus.OK);
    List<Individual> individualList = individuals.getBody();
    assertThat(individualList).isNotNull();
    assertThat(individualList.size()).isGreaterThan(0);
    Individual individual = individualList.get(0);
    assertThat(individual).isNotNull();
    assertThat(individual.getRef()).isNotEmpty();
  }
}
