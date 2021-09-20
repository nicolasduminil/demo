package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jpa.*;
import fr.dsirc.demo.model.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PensionListMapper
{
  List<Pension> toPensionList (List<PensionEntity> adresses);
  @InheritInverseConfiguration
  List<PensionEntity> fromPensionList (List<Pension> addressList);
}
