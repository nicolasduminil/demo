package fr.dsirc.demo.jpa;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "INDIVIDUALS")
public class IndividualEntity
{
  @Id
  @SequenceGenerator(name = "individuals_seq", sequenceName = "individuals_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "individuals_seq")
  private Long id;
  @NotEmpty
  @Column(name = "REFERENCE", length = 40, unique = true)
  private String ref;
  @NotEmpty
  @Column(name = "FIRST_NAME", length = 40)
  private String firstName;
  @NotEmpty
  @Column(name = "LAST_NAME", length = 40)
  private String lastName;
  @NotNull
  @Column(name = "BIRTH_DATE", columnDefinition = "DATE")
  private LocalDate birthDate;
  @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL)
  private List<AddressEntity> addresses;
  @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL)
  private List<PensionEntity> pensions;
}
