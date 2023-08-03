package ci.nkagou.parcauto.dtos.vehicule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VehiculeDto   {

  @NotNull
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min=2, max=30)
    private String immatriculation;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateAchat;

    @NotNull
    private String couleur;

    @NotNull
    private String statutVehicule;

    @NotNull
    private String carteGrise;

    @NotNull
    private String numeroChassis;

    @NotNull
    private Long marque;

    @NotNull
    private Long typeVehicule;

    @NotNull
    private String raison;



  // Explicitly named getter for the 'typevehicule' property
  public Long getTypeVehicule() {
    return typeVehicule;
  }

  // Explicitly named setter for the 'typevehicule' property
  public void setTypeVehicule(Long typeVehicule) {
    this.typeVehicule = typeVehicule;
  }

}
