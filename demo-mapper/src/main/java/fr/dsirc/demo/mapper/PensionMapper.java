package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jpa.*;
import fr.dsirc.demo.model.*;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PensionMapper
{
  Pension toPension (PensionEntity pensionEntity);
  @InheritInverseConfiguration
  PensionEntity fromPension (Pension pension);
}
