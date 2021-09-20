package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jpa.*;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PensionTypeMapper
{
  PensionType toPensionType (PensionEntity pensionEntity);
  @InheritInverseConfiguration
  PensionEntity fromPensionType (PensionType pensionType);
}
