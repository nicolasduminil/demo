package fr.dsirc.demo.provider;

import fr.dsirc.demo.api.*;
import fr.dsirc.demo.model.*;
import fr.dsirc.demo.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.xml.bind.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping(value = "/demo")
public class DemoApiProvider implements DemoApi
{
  private final IndividualService individualService;

  @Autowired
  public DemoApiProvider(IndividualService individualService)
  {
    this.individualService = individualService;
  }

  @Override
  public ResponseEntity<List<Individual>> getIndividuals()
  {
    return new ResponseEntity<>(individualService.getIndividuals(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Individual> getIndividual(Long id)
  {
    return new ResponseEntity<>(individualService.getIndividual(id).orElseThrow(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Individual> createIndividual(Individual individual)
  {
    return new ResponseEntity<>(individualService.createIndividual(individual), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Individual> updateIndividual(Individual individual)
  {
    return new ResponseEntity<>(individualService.updateIndividual(individual).orElseThrow(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<String> deleteIndividual(Long id)
  {
    individualService.deleteIndividual(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<String> bulkCreateIndividuals(MultipartFile file) throws JAXBException, IOException
  {
    InputStream is = new BufferedInputStream(file.getInputStream());
    individualService.bulkCreateIndividual(is);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<String> bulkUpdateIndividuals(MultipartFile file) throws JAXBException, IOException
  {
    individualService.bulkUpdateIndividuals(new BufferedInputStream(file.getInputStream()));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<String> bulkDeleteIndividuals(MultipartFile file) throws JAXBException, IOException
  {
    individualService.bulkDeleteIndividuals(new BufferedInputStream(file.getInputStream()));
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
