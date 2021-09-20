package fr.dsirc.demo.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Individual
{
  private String ref;
  private String firstName;
  private String lastName;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
  private LocalDate birthDate;
  private List<Address> addresses;
  private List<Pension> pensions;
}
