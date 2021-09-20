package fr.dsirc.demo.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Pension
{
  private String nir;
  private Integer pointsNumber;
  private String codeFede;
  private String inst;
}
