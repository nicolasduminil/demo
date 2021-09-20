package fr.dsirc.demo.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Address
{
  private String streetName;
  private String streetNumber;
  private String zipCode;
  private String cityName;
  private String countryName;
}
