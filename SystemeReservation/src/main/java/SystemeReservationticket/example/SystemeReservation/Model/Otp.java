package SystemeReservationticket.example.SystemeReservation.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "otps")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Otp {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * Email de l'utilisateur concerné
     */
    private String email;


    /**
     * Code OTP à 6 chiffres
     */
    private String code;


    /**
     * Date d'expiration du code
     */
    private LocalDateTime dateExpiration;


    /**
     * Indique si le code a déjà été utilisé
     */
    private boolean utilise;


    /**
     * Date de création
     */
    private LocalDateTime dateCreation;


    /**
     * Initialisation automatique
     */
    public Otp(
            String email,
            String code,
            LocalDateTime dateExpiration
    ) {

        this.email = email;
        this.code = code;
        this.dateExpiration = dateExpiration;
        this.utilise = false;
        this.dateCreation = LocalDateTime.now();

    }

}