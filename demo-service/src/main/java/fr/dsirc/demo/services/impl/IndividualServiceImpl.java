package fr.dsirc.demo.services.impl;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jpa.*;
import fr.dsirc.demo.mapper.*;
import fr.dsirc.demo.model.*;
import fr.dsirc.demo.repo.*;
import fr.dsirc.demo.services.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.xml.bind.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

@Service
@Slf4j
public class IndividualServiceImpl implements IndividualService
{
  private final IndividualRepository individualRepo;
  private final IndividualMapper individualMapper;
  private final IndividualTypeMapper individualTypeMapper;

  @Autowired
  public IndividualServiceImpl (IndividualRepository individualRepo, IndividualMapper individualMapper,IndividualTypeMapper individualTypeMapper)
  {
    this.individualRepo = individualRepo;
    this.individualMapper = individualMapper;
    this.individualTypeMapper = individualTypeMapper;
  }

  @Override
  public List<Individual> getIndividuals()
  {
    return individualRepo.findAll().stream().map(individualMapper::toIndividual).collect(Collectors.toList());
  }

  @Override
  public Optional<Individual> getIndividual(Long id)
  {
    return individualRepo.findById(id).map(individualMapper::toIndividual);
  }

  @Override
  public Individual createIndividual(Individual individual)
  {
    individualRepo.saveAndFlush(individualMapper.fromIndividual(individual));
    return individual;
  }

  @Override
  public Optional<Individual> updateIndividual(Individual individual)
  {
    Optional<IndividualEntity> i2 = individualRepo.findByRef(individual.getRef());
    if (i2.isPresent())
    {
      IndividualEntity ie = i2.get();
      IndividualEntity ie2 = individualMapper.fromIndividual(individual);
      ie.setAddresses(ie2.getAddresses());
      ie.setBirthDate(ie2.getBirthDate());
      ie.setPensions(ie2.getPensions());
      ie.setLastName(ie2.getLastName());
      ie.setPensions(ie2.getPensions());
      ie.setFirstName(ie2.getFirstName());
      individualRepo.save(ie);
      Individual i3 = individualMapper.toIndividual(ie);
      return Optional.of(i3);
    }
    else
      return Optional.empty();
    //return individualRepo.findByRef(individual.getRef()).isPresent() ? Optional.of(individualMapper.toIndividual(individualRepo.save(individualMapper.fromIndividual(individual)))) : Optional.empty();
  }

  @Override
  public void deleteIndividual(Long id)
  {
    individualRepo.deleteById(id);
  }

  @Override
  public void bulkCreateIndividual(InputStream is) throws JAXBException
  {
    Individuals individuals = unmarshalIndividuals(is);
    List<IndividualType> individualTypeList = individuals.getIndividualTypes();
    individualTypeList.forEach(individualType -> {
      log.info ("### IndividualServiceImpl.bulkCreateIndividual(): Have got an individual type having ref {}, address {} and pension {}", individualType.getRef(), individualType.getAddressTypes().size(), individualType.getPensionTypes().size());
      IndividualEntity individualEntity = CommonMappers.individualTypeToIndividualEntity(individualType);
      if (individualEntity == null)
        log.info ("### IndividualServiceImpl.bulkCreateIndividual(): Have got a null individual entity");
      else
      {
        log.info("### IndividualServiceImpl.bulkCreateIndividual(): Have got an individual entity having ref {}, address {} and pension {}", individualEntity.getRef(), individualEntity.getAddresses().size(), individualEntity.getPensions().size());
        individualRepo.saveAndFlush(individualEntity);
      }
      //individualRepo.save(individualTypeMapper.fromIndividualType(individualType));
    });
  }

  @Override
  public void bulkUpdateIndividuals(InputStream is) throws JAXBException
  {
    unmarshalIndividuals(is).getIndividualTypes().forEach(individual -> updateIndividual(individualMapper.toIndividual(individualTypeMapper.fromIndividualType(individual))));
  }

  @Override
  public void bulkDeleteIndividuals(InputStream is) throws JAXBException
  {
    unmarshalIndividuals(is).getIndividualTypes().forEach(individualType -> deleteIndividual(individualRepo.findByRef(individualType.getRef()).map(IndividualEntity::getId).orElseThrow()));
  }

  private Individuals unmarshalIndividuals (InputStream is) throws JAXBException
  {
    return (Individuals) JAXBContext.newInstance(Individuals.class).createUnmarshaller().unmarshal(new InputStreamReader(is));
  }
}
