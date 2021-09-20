package fr.dsirc.demo.jaxb.tests;

import fr.dsirc.demo.jaxb.*;
import org.junit.jupiter.api.*;

import javax.xml.bind.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnmarshalIndividualsXmlFile
{
  @Test
  public void testUnmarshalIndividualsXmlFile() throws JAXBException, FileNotFoundException
  {
    Individuals individuals = (Individuals) JAXBContext.newInstance(Individuals.class).createUnmarshaller().unmarshal(new InputStreamReader(new FileInputStream("src/main/resources/xml/individuals.xml")));
    assertNotNull(individuals);
    List<IndividualType> individualTypes = individuals.getIndividualTypes();
    assertNotNull(individualTypes);
    assertEquals(3, individualTypes.size());
    List<AddressType> addressTypes = individualTypes.get(0).getAddressTypes();
    assertNotNull(addressTypes);
    assertEquals(2, addressTypes.size());
    List<PensionType> pensionTypes = individualTypes.get(0).getPensionTypes();
    assertNotNull(pensionTypes);
    assertEquals(2, pensionTypes.size());
  }
}
