package ci.nkagou.parcauto.dtos.vehicule;

import ci.nkagou.parcauto.entities.Marque;
import ci.nkagou.parcauto.entities.Typevehicule;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.Couleur;
import ci.nkagou.parcauto.enums.StatutVehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
public class VehiculeDtoZ {

    private Long idVehicule;
    private String immatriculation;
    private Couleur couleur;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateAchat;
    private String carteGrise;
    private String numeroChassis;
    private StatutVehicule statutVehicule;
    private Marque marque;
    private Typevehicule typeVehicule;
    private String raison;
    private Long countdownTimer;

    public void updateCountdownTimer() {
        if (statutVehicule == StatutVehicule.INDISPONIBLE && countdownTimer > 0) {
            countdownTimer--;
            if (countdownTimer == 0) {
                // Change the status to DISPONIBLE when countdown reaches zero
                statutVehicule = StatutVehicule.DISPONIBLE;
            }
        }
    }

    /*public static String formatDuration(Long seconds) {
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (days * 24);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (days * 24 * 60) - (hours * 60);
        long remainingSeconds = seconds - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60);

        return String.format("%dd %02dh %02dm %02ds", days, hours, minutes, remainingSeconds);
    }*/

}
