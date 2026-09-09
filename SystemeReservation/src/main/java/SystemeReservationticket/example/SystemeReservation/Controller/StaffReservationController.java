package SystemeReservationticket.example.SystemeReservation.Controller;


import SystemeReservationticket.example.SystemeReservation.DTO.StaffReservationDTO;
import SystemeReservationticket.example.SystemeReservation.Services.StaffReservationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StaffReservationController {


    private final StaffReservationService staffReservationService;


    // =====================================================
    // TOUTES LES RESERVATIONS
    // =====================================================

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<StaffReservationDTO>>
    getAllReservations() {

        return ResponseEntity.ok(
                staffReservationService
                        .getAllReservations()
        );
    }


    // =====================================================
    // RESERVATIONS DU JOUR
    // =====================================================

    @GetMapping("/aujourdhui")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<StaffReservationDTO>>
    getReservationsAujourdhui() {

        return ResponseEntity.ok(
                staffReservationService
                        .getReservationsAujourdhui()
        );
    }


    // =====================================================
    // 5 DERNIERES RESERVATIONS
    // =====================================================

    @GetMapping("/recentes")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<StaffReservationDTO>>
    getReservationsRecentes() {

        return ResponseEntity.ok(
                staffReservationService
                        .getReservationsRecentes()
        );
    }


    // =====================================================
    // NOMBRE DE RESERVATIONS DU JOUR
    // =====================================================

    @GetMapping("/nombre-aujourdhui")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Long>
    getNombreReservationsAujourdhui() {

        return ResponseEntity.ok(
                staffReservationService
                        .getNombreReservationsAujourdhui()
        );
    }


    // =====================================================
    // RESERVATION PAR ID
    // =====================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getReservationById(
            @PathVariable Long id
    ) {

        StaffReservationDTO reservation =
                staffReservationService
                        .getReservationById(id);


        if (reservation == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Réservation introuvable.");
        }


        return ResponseEntity.ok(
                reservation
        );
    }


    // =====================================================
    // RESERVATIONS D'UN UTILISATEUR
    // =====================================================

    @GetMapping("/utilisateur/{utilisateurId}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<StaffReservationDTO>>
    getReservationsUtilisateur(
            @PathVariable Long utilisateurId
    ) {

        return ResponseEntity.ok(
                staffReservationService
                        .getReservationsUtilisateur(
                                utilisateurId
                        )
        );
    }

}