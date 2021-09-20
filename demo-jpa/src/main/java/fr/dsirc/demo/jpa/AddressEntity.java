package fr.dsirc.demo.jpa;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ADDRESSES")
public class AddressEntity
{
  @Id
  @SequenceGenerator(name="addresses_seq", sequenceName="addresses_seq", allocationSize=1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="addresses_seq")
  private Long id;
  @NotEmpty
  @Column(name="STREET_NAME", length = 80)
  private String streetName;
  @NotEmpty
  @Column(name="STREET_NUMBER", length = 10)
  private String streetNumber;
  @NotEmpty
  @Column(name="ZIP_CODE", length = 6)
  private String zipCode;
  @NotEmpty
  @Column(name="CITY_NAME", length = 20)
  private String cityName;
  @NotEmpty
  @Column(name="COUNTRY_NAME", length = 20)
  private String countryName;
  @ManyToOne(cascade = CascadeType.ALL)
  private IndividualEntity individual;

  /*public IndividualEntity getIndividual()
  {
    return individual;
  }

  public void setIndividual(IndividualEntity individual)
  {
    this.individual = individual;
  }*/
}
