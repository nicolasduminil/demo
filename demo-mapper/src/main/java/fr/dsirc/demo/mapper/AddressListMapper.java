package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jpa.*;
import fr.dsirc.demo.model.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddressListMapper
{
  List<Address> toAddressList (List<AddressEntity> adresses);
  @InheritInverseConfiguration
  List<AddressEntity> fromAddressList (List<Address> addressList);
}
