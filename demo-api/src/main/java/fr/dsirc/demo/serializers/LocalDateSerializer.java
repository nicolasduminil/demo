package fr.dsirc.demo.serializers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.time.*;
import java.time.format.*;

@Slf4j
public class LocalDateSerializer extends StdSerializer<LocalDate>
{
  protected LocalDateSerializer()
  {
    super(LocalDate.class);
  }

  @Override
  public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
  {
    log.info("### LocalDateSerializer.serialize(): {}", localDate.toString());
    jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
  }
}
