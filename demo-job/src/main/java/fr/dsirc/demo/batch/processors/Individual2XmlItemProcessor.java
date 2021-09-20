package fr.dsirc.demo.batch.processors;

import fr.dsirc.demo.jpa.*;
import fr.dsirc.demo.mapper.*;
import fr.dsirc.demo.model.*;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import javax.xml.bind.*;
import java.io.*;

public class Individual2XmlItemProcessor implements ItemProcessor<IndividualEntity, String>
{
  @Autowired
  IndividualMapper individualMapper;

  @Override
  public String process(IndividualEntity individualEntity) throws Exception
  {
    Individual individual = individualMapper.toIndividual(individualEntity);
    StringWriter stringWriter = new StringWriter();
    JAXBContext.newInstance(Individual.class).createMarshaller().marshal(individual, stringWriter);
    return stringWriter.toString();
  }
}
