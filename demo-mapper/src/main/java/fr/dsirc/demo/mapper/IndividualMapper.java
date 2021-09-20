package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jpa.*;
import fr.dsirc.demo.model.*;
import org.mapstruct.*;

@Mapper(uses = {AddressMapper.class, PensionMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IndividualMapper
{
  Individual toIndividual(IndividualEntity individualEntity);
  @InheritInverseConfiguration
  IndividualEntity fromIndividual(Individual individual);
}

