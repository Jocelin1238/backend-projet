package SystemeReservationticket.example.SystemeReservation.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffTicketDTO {

    private Long id;

    private String codeTicket;

    private String statut;

    private String qrCode;

    private LocalDateTime dateCreation;

    private LocalDateTime dateModification;


    // =====================================================
    // RESERVATION
    // =====================================================

    private Long reservationId;

    private String numeroVoyage;


    // =====================================================
    // CLIENT
    // =====================================================

    private Long utilisateurId;

    private String nomClient;

    private String prenomClient;

    private String emailClient;


    // =====================================================
    // TRAJET
    // =====================================================

    private String villeDepart;

    private String villeArrivee;


    // =====================================================
    // VOYAGE
    // =====================================================

    private String dateDepart;

    private String heureDepart;
}