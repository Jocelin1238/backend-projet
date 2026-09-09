package SystemeReservationticket.example.SystemeReservation.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;

    private LocalDateTime dateReservation;

    private String statut;

    // Client
    private Long utilisateurId;
    private String nomClient;
    private String prenomClient;
    private String emailClient;

    // Voyage
    private Long voyageId;
    private String numeroVoyage;

    // Trajet
    private String villeDepart;
    private String villeArrivee;

    // Date et heure du voyage
    private LocalDate dateDepart;
    private LocalTime heureDepart;

    private LocalDate dateArrivee;
    private LocalTime heureArrivee;

    // Prix
    private Double prixVoyage;

    // Paiement
    private Double montantPaye;
    private String methodePaiement;
    private String statutPaiement;
}