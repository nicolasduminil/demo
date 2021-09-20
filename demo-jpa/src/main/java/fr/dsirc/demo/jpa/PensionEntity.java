package fr.dsirc.demo.jpa;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="PENSIONS")
public class PensionEntity
{
  @Id
  @SequenceGenerator(name="pensions_seq", sequenceName="pensions_seq", allocationSize=1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pensions_seq")
  private Long id;
  @NotEmpty
  @Column(name="NIR", length = 13)
  private String nir;
  @NotNull
  @Column(name="POINTS_NUMBER")
  private Integer pointsNumber;
  @NotEmpty
  @Column(name="CODE_FEDE", length = 20)
  private String codeFede;
  @NotEmpty
  @Column(name="INST", length = 20)
  private String inst;
  @ManyToOne(cascade = CascadeType.ALL)
  private IndividualEntity individual;
}
