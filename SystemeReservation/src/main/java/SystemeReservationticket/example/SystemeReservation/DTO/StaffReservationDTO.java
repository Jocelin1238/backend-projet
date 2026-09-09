package SystemeReservationticket.example.SystemeReservation.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffReservationDTO {

    private Long id;

    private LocalDateTime dateReservation;

    private String statut;


    // =====================================================
    // UTILISATEUR
    // =====================================================

    private Long utilisateurId;

    private String nomClient;

    private String prenomClient;

    private String emailClient;


    // =====================================================
    // VOYAGE
    // =====================================================

    private Long voyageId;

    private String numeroVoyage;

    private String villeDepart;

    private String villeArrivee;

    private String dateDepart;

    private String heureDepart;

    private Double prix;
    private String trajet;
}