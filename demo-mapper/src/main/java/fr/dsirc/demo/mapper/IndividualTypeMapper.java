package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jpa.*;
import org.mapstruct.*;

import javax.xml.datatype.*;
import java.time.*;
import java.util.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IndividualTypeMapper
{
  default IndividualType toIndividualType(IndividualEntity individualEntity) throws DatatypeConfigurationException
  {
    LocalDate localDate = individualEntity.getBirthDate();
    XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), DatatypeConstants.FIELD_UNDEFINED);
    IndividualType individualType = new IndividualType();
    individualType.setBirthDate(xcal);
    individualType.setLastName(individualEntity.getLastName());
    individualType.setFirstName(individualEntity.getFirstName());
    individualType.setRef(individualEntity.getRef());
    individualType.getAddressTypes().addAll(new AddressTypeListMapperImpl().toAddressTypeList(individualEntity.getAddresses()));
    individualType.getPensionTypes().addAll(new PensionTypeListMapperImpl().toPensionTypeList(individualEntity.getPensions()));
    return individualType;
  }

  default IndividualEntity fromIndividualType(IndividualType individualType)
  {
    List<AddressEntity> addressEntities = new AddressTypeListMapperImpl().fromAddressTypeList(individualType.getAddressTypes());
    List<PensionEntity> pensionEntities = new PensionTypeListMapperImpl().fromPensionTypeList(individualType.getPensionTypes());
    XMLGregorianCalendar xcal = individualType.getBirthDate();
    IndividualEntity individualEntity = new IndividualEntity(null, individualType.getRef(), individualType.getFirstName(), individualType.getLastName(),
      LocalDate.of(xcal.getYear(), xcal.getMonth(), xcal.getDay()), addressEntities, pensionEntities);
    addressEntities.forEach(addressEntity -> addressEntity.setIndividual(individualEntity));
    pensionEntities.forEach(pensionEntity -> pensionEntity.setIndividual(individualEntity));
    return individualEntity;
  }
}