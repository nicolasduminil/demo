package fr.dsirc.demo.mapper;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jpa.*;

import javax.xml.datatype.*;
import java.time.*;

public class CommonMappers
{
  private static final AddressTypeListMapper addressTypeListMapper = new AddressTypeListMapperImpl();
  private static final PensionTypeListMapper pensionTypeListMapper = new PensionTypeListMapperImpl();

  public static IndividualEntity individualTypeToIndividualEntity(IndividualType individualType)
  {
    return new IndividualEntity(null, individualType.getRef(), individualType.getFirstName(), individualType.getLastName(),
      xmlGregorianCalendarToLocalDate(individualType.getBirthDate()), addressTypeListMapper.fromAddressTypeList(individualType.getAddressTypes()),
      pensionTypeListMapper.fromPensionTypeList(individualType.getPensionTypes()));
  }

  public static IndividualType individualEntityToIndividualType(IndividualEntity individualEntity) throws DatatypeConfigurationException
  {
    IndividualType individualType = new IndividualType();
    individualType.setBirthDate(localDateToXmlGregorianCalendar(individualEntity.getBirthDate()));
    individualType.setLastName(individualEntity.getLastName());
    individualType.setFirstName(individualEntity.getFirstName());
    individualType.setRef(individualEntity.getRef());
    individualType.getAddressTypes().addAll(addressTypeListMapper.toAddressTypeList(individualEntity.getAddresses()));
    individualType.getPensionTypes().addAll(pensionTypeListMapper.toPensionTypeList(individualEntity.getPensions()));
    return individualType;
  }

  private static XMLGregorianCalendar localDateToXmlGregorianCalendar(LocalDate localDate) throws DatatypeConfigurationException
  {
    return DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
      localDate.getYear(),
      localDate.getMonthValue(),
      localDate.getDayOfMonth(),
      DatatypeConstants.FIELD_UNDEFINED);
  }

  private static LocalDate xmlGregorianCalendarToLocalDate(XMLGregorianCalendar xcal)
  {
    return LocalDate.of(xcal.getYear(), xcal.getMonth(), xcal.getDay());
  }

  public static AddressTypeListMapper getAddressTypeListMapper()
  {
    return addressTypeListMapper;
  }

  public static PensionTypeListMapper getPensionTypeListMapper()
  {
    return pensionTypeListMapper;
  }
}
