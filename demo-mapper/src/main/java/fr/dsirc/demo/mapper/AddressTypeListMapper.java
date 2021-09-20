package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jpa.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddressTypeListMapper
{
  List<AddressType> toAddressTypeList (List<AddressEntity> adresses);
  @InheritInverseConfiguration
  List<AddressEntity> fromAddressTypeList (List<AddressType> addressList);
}
