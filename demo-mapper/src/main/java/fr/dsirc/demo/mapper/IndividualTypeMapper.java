package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jpa.*;
import org.mapstruct.*;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE, uses = {AddressTypeMapper.class, PensionTypeMapper.class, AddressTypeListMapper.class, PensionTypeListMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IndividualTypeMapper
{
  IndividualType toIndividualType(IndividualEntity individual);
  @InheritInverseConfiguration
  IndividualEntity fromIndividualType(IndividualType individualType);
}