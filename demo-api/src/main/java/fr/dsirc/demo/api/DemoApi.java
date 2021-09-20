package fr.dsirc.demo.api;

import fr.dsirc.demo.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.xml.bind.*;
import java.io.*;
import java.util.*;

public interface DemoApi
{
  @GetMapping(path = "/individuals", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<Individual>> getIndividuals();
  @GetMapping(path = "/individual/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Individual> getIndividual(@PathVariable Long id);
  @PostMapping(path = "/individual", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Individual> createIndividual (@RequestBody Individual individual);
  @PutMapping(path = "/individual", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Individual> updateIndividual (@RequestBody Individual individual);
  @DeleteMapping(path = "/individual/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> deleteIndividual (@PathVariable Long id);
  @PostMapping(path = "/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> bulkCreateIndividuals(@RequestPart MultipartFile file) throws JAXBException, IOException;
  @PutMapping(path = "/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> bulkUpdateIndividuals(@RequestPart MultipartFile file) throws JAXBException, IOException;
  @DeleteMapping(path = "/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> bulkDeleteIndividuals(@RequestPart MultipartFile file) throws JAXBException, IOException;
}
