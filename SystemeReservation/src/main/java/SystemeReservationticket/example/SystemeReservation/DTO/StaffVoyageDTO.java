package SystemeReservationticket.example.SystemeReservation.DTO;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffVoyageDTO {

    private Long id;

    private String numeroVoyage;

    private LocalDate dateDepart;

    private LocalTime heureDepart;

    private LocalDate dateArrivee;

    private LocalTime heureArrivee;

    private Double prix;

    private Integer nombrePlaces;

    private Integer placesDisponibles;

    private String statut;

    private String description;


    // =====================================================
    // TRAJET
    // =====================================================

    private Long trajetId;

    private String villeDepart;

    private String villeArrivee;


    // =====================================================
    // CONFORT
    // =====================================================

    private Long confortId;

    private String confort;


    // =====================================================
    // TRANSPORT
    // =====================================================

    private Long transportId;

    private String transport;
}