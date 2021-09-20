package fr.dsirc.demo.services;

import fr.dsirc.demo.model.*;

import javax.xml.bind.*;
import java.io.*;
import java.util.*;

public interface IndividualService
{
  List<Individual> getIndividuals();
  Optional<Individual> getIndividual(Long id);
  Individual createIndividual (Individual individualEntity);
  Optional<Individual> updateIndividual (Individual individual);
  void deleteIndividual (Long id);
  void bulkCreateIndividual (InputStream is) throws JAXBException;
  void bulkUpdateIndividuals (InputStream is) throws JAXBException;
  void bulkDeleteIndividuals (InputStream is) throws JAXBException;
}
