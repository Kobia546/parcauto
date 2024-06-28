package ci.nkagou.parcauto.dtos.vehicule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehiculeDtoOut {

    private Long id;
    private String immatriculation;
    private String couleur;
    private String statutVehicule;
    private String dateAchat;
    private String numeroChassis;
    private String carteGrise;
    private String marque;
    private Long idMarque;
    private String typeVehicule;
    private String raison;
    private Long countdownTimer;
    /*private String dateIndisponibilité;*/
    /*private String statutVehiculeIn;*/
    /*public static String formatDuration(Long seconds) {
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (days * 24);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (days * 24 * 60) - (hours * 60);
        long remainingSeconds = seconds - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60);

        return String.format("%dd %02dh %02dm %02ds", days, hours, minutes, remainingSeconds);
    }*/
}
