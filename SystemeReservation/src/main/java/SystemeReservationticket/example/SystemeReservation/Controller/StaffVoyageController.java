package SystemeReservationticket.example.SystemeReservation.Controller;

import SystemeReservationticket.example.SystemeReservation.DTO.StaffVoyageDTO;
import SystemeReservationticket.example.SystemeReservation.Services.StaffVoyageService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/staff/voyages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StaffVoyageController {


    private final StaffVoyageService staffVoyageService;


    // =====================================================
    // TOUS LES VOYAGES
    // =====================================================

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<StaffVoyageDTO>>
    getAllVoyages() {

        return ResponseEntity.ok(
                staffVoyageService.getAllVoyages()
        );
    }


    // =====================================================
    // VOYAGES DU JOUR
    // =====================================================

    @GetMapping("/aujourdhui")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<StaffVoyageDTO>>
    getVoyagesAujourdhui() {

        return ResponseEntity.ok(
                staffVoyageService
                        .getVoyagesAujourdhui()
        );
    }


    // =====================================================
    // VOYAGES D'UNE DATE
    // =====================================================

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<StaffVoyageDTO>>
    getVoyagesParDate(
            @PathVariable LocalDate date
    ) {

        return ResponseEntity.ok(
                staffVoyageService
                        .getVoyagesParDate(date)
        );
    }


    // =====================================================
    // NOMBRE DE VOYAGES DU JOUR
    // =====================================================

    @GetMapping("/nombre-aujourdhui")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Long>
    getNombreVoyagesAujourdhui() {

        return ResponseEntity.ok(
                staffVoyageService
                        .getNombreVoyagesAujourdhui()
        );
    }


    // =====================================================
    // VOYAGE PAR ID
    // =====================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getVoyageById(
            @PathVariable Long id
    ) {

        StaffVoyageDTO voyage =
                staffVoyageService
                        .getVoyageById(id);

        if (voyage == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Voyage introuvable.");
        }

        return ResponseEntity.ok(voyage);
    }
}