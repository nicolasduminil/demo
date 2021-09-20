package fr.dsirc.demo.serializers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.time.*;
import java.time.format.*;

@Slf4j
public class LocalDateDeserializer extends StdDeserializer<LocalDate>
{
  public LocalDateDeserializer()
  {
    super(LocalDate.class);
  }

  @Override
  public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
  {
    log.info("### LocalDateDeserializer.deserialize");
    return LocalDate.parse(jsonParser.readValueAs(String.class), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
}
