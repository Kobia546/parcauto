package ci.nkagou.parcauto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("CC")

public class CarburantAtt extends Attribution{




     @Column(name="recuCarburant")
     private String recuCarburant;

     private String immatriculationVehicule;
     private int montant;
     private int litre;


}
