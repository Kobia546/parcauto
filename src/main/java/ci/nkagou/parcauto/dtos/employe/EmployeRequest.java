package ci.nkagou.parcauto.dtos.employe;

import ci.nkagou.parcauto.entities.Direction;
import ci.nkagou.parcauto.entities.Site;
import ci.nkagou.parcauto.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeRequest {

//    @NotBlank
    private String nom;
//    @NotBlank
    private String prenom;
//    @NotNull(message = "Le champ matricule ne doit pas etre vide")
//    @Min(value = 0L)
    private Long numMatEmpl;
//    @Email
    private String email;
//    @NotNull
    private Genre genre;


/*    private Long site;
    private Long direction;*/

    private Site site;
    private Direction direction;


    private Boolean estChauffeur;
    private Boolean estSuperieureHierachique;
    private String fonction;


}
