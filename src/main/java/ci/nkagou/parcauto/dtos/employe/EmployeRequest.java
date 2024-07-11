package ci.nkagou.parcauto.dtos.employe;

import ci.nkagou.parcauto.entities.Direction;
import ci.nkagou.parcauto.entities.Site;
import ci.nkagou.parcauto.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeRequest {

    @NotBlank
    private String nom;
    @NotBlank
    private String prenoms;
    @NotNull(message = "Le champ matricule ne doit pas etre vide")
    @Min(value = 0L)
    private Long matricule;
    @Email
    private String email;
    @NotNull
    private Genre genre;


/*    private Long site;
    private Long direction;*/

    private Site site;
    private Direction direction;


    private Boolean estChauffeur;
    private Boolean estResponsable;
    private String fonction;

}
