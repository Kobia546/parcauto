package ci.nkagou.parcauto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorColumn(name = "entretien")
public abstract class Entretien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntretien;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEntretien;




}
