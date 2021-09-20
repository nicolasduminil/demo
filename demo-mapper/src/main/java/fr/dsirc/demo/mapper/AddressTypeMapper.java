package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jpa.*;
import org.mapstruct.*;

@Mapper(uses = {AddressTypeListMapper.class, PensionTypeListMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddressTypeMapper
{
  AddressType toAddressType (AddressEntity addressEntity);
  @InheritInverseConfiguration
  AddressEntity fromAddressType (AddressType addressType);
}
