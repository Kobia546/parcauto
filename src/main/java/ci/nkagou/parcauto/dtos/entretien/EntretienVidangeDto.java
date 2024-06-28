package ci.nkagou.parcauto.dtos.entretien;

import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EntretienVidangeDto {

     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private LocalDate dateEntretien;

     private Vehicule vehicule;

     private int ancienKilometrage;

     private int nouveauKilometrage;

     private int montantTotal;

     @Column(name="recuEntretien")
     private String recuEntretienFilename;

     private List<DetailVidangeDto> detailVidangeDto;



}
