package fr.dsirc.demo.mapper.tests;

import fr.dsirc.demo.jaxb.*;
import fr.dsirc.demo.jpa.*;
import fr.dsirc.demo.mapper.*;
import fr.dsirc.demo.mapper.tests.config.*;
import fr.dsirc.demo.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import javax.xml.bind.*;
import javax.xml.datatype.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfig.class)
public class TestMappers
{
  private static List<IndividualType> individualTypes;
  @Autowired
  private IndividualMapper individualMapper;
  @Autowired
  private IndividualTypeMapper individualTypeMapper;
  @Autowired
  private AddressTypeMapper addressTypeMapper;
  @Autowired
  private PensionTypeMapper pensionTypeMapper;
  @Autowired
  private AddressTypeListMapper addressTypeListMapper;
  @Autowired
  private AddressMapper addressMapper;
  @Autowired
  private PensionMapper pensionMapper;
  @Autowired
  private AddressListMapper addressListMapper;
  @Autowired
  private PensionListMapper pensionListMapper;
  @Autowired
  private PensionTypeListMapper pensionTypeListMapper;

  @BeforeAll
  public static void beforeAll() throws JAXBException, FileNotFoundException
  {
    Individuals individuals = (Individuals) JAXBContext.newInstance(Individuals.class).createUnmarshaller().unmarshal(new InputStreamReader(new FileInputStream("src/main/resources/xml/individuals.xml")));
    assertNotNull(individuals);
    assertEquals(3, individuals.getIndividualTypes().size());
    individualTypes = individuals.getIndividualTypes();
    assertEquals(3, individualTypes.size());
  }

  @Test
  public void testIndividualMapper() throws DatatypeConfigurationException
  {
    IndividualType individualType = individualTypes.get(0);
    assertNotNull(individualType.getPensionTypes());
    assertNotNull(individualType.getAddressTypes());
    IndividualEntity individualEntity = individualTypeMapper.fromIndividualType(individualType);
    assertNotNull(individualEntity);
    assertEquals("laurence-charron", individualEntity.getRef());
    assertNotNull(individualEntity.getAddresses());
    assertEquals(2, individualEntity.getAddresses().size());
    assertNotNull(individualEntity.getPensions());
    assertEquals(2, individualEntity.getPensions().size());
    individualType = individualTypeMapper.toIndividualType(individualEntity);
    assertNotNull(individualType);
    assertEquals("laurence-charron", individualType.getRef());
    assertNotNull(individualType.getAddressTypes());
    assertNotNull(individualType.getPensionTypes());
    Individual individual = individualMapper.toIndividual(individualEntity);
    assertNotNull(individual);
    assertEquals("laurence-charron", individual.getRef());
    assertNotNull(individual.getAddresses());
    assertNotNull(individual.getPensions());
    individualEntity = individualMapper.fromIndividual(individual);
    assertNotNull(individualEntity);
    assertEquals("laurence-charron", individualEntity.getRef());
    assertNotNull(individualEntity.getAddresses());
    assertEquals(2, individualEntity.getAddresses().size());
    assertNotNull(individualEntity.getPensions());
    assertEquals(2, individualEntity.getPensions().size());
  }

  @Test
  public void testAddressMapper()
  {
    AddressType addressType = individualTypes.get(0).getAddressTypes().get(0);
    AddressEntity addressEntity = addressTypeMapper.fromAddressType(addressType);
    assertEquals("Quimper", addressEntity.getCityName());
    addressType = addressTypeMapper.toAddressType(addressEntity);
    assertNotNull(addressType);
    assertEquals("Quimper", addressType.getCityName());
    Address address = addressMapper.toAddress(addressEntity);
    assertNotNull(address);
    assertEquals("Quimper", address.getCityName());
    addressEntity = addressMapper.fromAddress(address);
    assertNotNull(address);
    assertEquals("Quimper", address.getCityName());
  }

  @Test
  public void testPensionMapper()
  {
    PensionType pensionType = individualTypes.get(0).getPensionTypes().get(0);
    PensionEntity pensionEntity = pensionTypeMapper.fromPensionType(pensionType);
    assertNotNull(pensionEntity);
    assertEquals("1024", pensionEntity.getNir());
    pensionType = pensionTypeMapper.toPensionType(pensionEntity);
    assertNotNull(pensionType);
    assertEquals("1024", pensionType.getNir());
    Pension pension = pensionMapper.toPension(pensionEntity);
    assertNotNull(pension);
    assertEquals("1024", pension.getNir());
    pensionEntity = pensionMapper.fromPension(pension);
    assertNotNull(pensionEntity);
    assertEquals("1024", pensionEntity.getNir());
  }

  @Test
  public void testAddressListMapper()
  {
    List<AddressType> addressTypes = individualTypes.get(0).getAddressTypes();
    assertNotNull(addressTypes);
    assertEquals(2, addressTypes.size());
    List<AddressEntity> addressEntities = addressTypeListMapper.fromAddressTypeList(addressTypes);
    assertNotNull(addressEntities);
    assertEquals(2, addressEntities.size());
    addressTypes = addressTypeListMapper.toAddressTypeList(addressEntities);
    assertNotNull(addressTypes);
    assertEquals(2, addressTypes.size());
    List<Address> addresses = addressListMapper.toAddressList(addressEntities);
    assertNotNull(addresses);
    assertEquals(2, addresses.size());
    addressEntities = addressListMapper.fromAddressList(addresses);
    assertNotNull(addressEntities);
    assertEquals(2, addressEntities.size());
  }

  @Test
  public void testPensionListMapper()
  {
    List<PensionType> pensionTypes = individualTypes.get(0).getPensionTypes();
    assertNotNull(pensionTypes);
    assertEquals(2, pensionTypes.size());
    List<PensionEntity> pensionEntities = pensionTypeListMapper.fromPensionTypeList(pensionTypes);
    assertNotNull(pensionEntities);
    assertEquals(2, pensionEntities.size());
    pensionTypes = pensionTypeListMapper.toPensionTypeList(pensionEntities);
    assertNotNull(pensionTypes);
    assertEquals(2, pensionTypes.size());
    List<Pension> pensions = pensionListMapper.toPensionList(pensionEntities);
    assertNotNull(pensions);
    assertEquals(2, pensions.size());
    pensionEntities = pensionListMapper.fromPensionList(pensions);
    assertNotNull(pensionEntities);
    assertEquals(2, pensionEntities.size());
  }
}
