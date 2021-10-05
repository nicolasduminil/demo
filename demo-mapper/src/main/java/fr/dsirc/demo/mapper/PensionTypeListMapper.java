package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jpa.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PensionTypeListMapper
{
  List<PensionType> toPensionTypeList (List<PensionEntity> pensions);
  @InheritInverseConfiguration
  List<PensionEntity> fromPensionTypeList (List<PensionType> pensionTypeList);
}
