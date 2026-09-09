package SystemeReservationticket.example.SystemeReservation.Controller;

import SystemeReservationticket.example.SystemeReservation.DTO.StaffTicketDTO;
import SystemeReservationticket.example.SystemeReservation.Services.StaffTicketService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff/tickets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StaffTicketController {


    private final StaffTicketService staffTicketService;


    // =====================================================
    // RECHERCHER PAR CODE
    // =====================================================

    @GetMapping("/code/{codeTicket}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> rechercherTicket(
            @PathVariable String codeTicket
    ) {

        StaffTicketDTO ticket =
                staffTicketService
                        .rechercherParCode(codeTicket);


        if (ticket == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Ticket introuvable.");
        }


        return ResponseEntity.ok(ticket);
    }


    // =====================================================
    // TICKET PAR ID
    // =====================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getTicketById(
            @PathVariable Long id
    ) {

        StaffTicketDTO ticket =
                staffTicketService
                        .getTicketById(id);


        if (ticket == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Ticket introuvable.");
        }


        return ResponseEntity.ok(ticket);
    }


    // =====================================================
    // NOMBRE DE TICKETS SCANNES
    // =====================================================

    @GetMapping("/nombre-scannes")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Long>
    getNombreTicketsScannes() {

        return ResponseEntity.ok(
                staffTicketService
                        .getNombreTicketsScannes()
        );
    }


    // =====================================================
    // TICKET D'UNE RESERVATION
    // =====================================================

    @GetMapping("/reservation/{reservationId}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getTicketByReservation(
            @PathVariable Long reservationId
    ) {

        StaffTicketDTO ticket =
                staffTicketService
                        .getTicketByReservation(
                                reservationId
                        );


        if (ticket == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Ticket introuvable.");
        }


        return ResponseEntity.ok(ticket);
    }


    // =====================================================
    // SCANNER
    // =====================================================

    @PutMapping("/scanner/{codeTicket}")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> scannerTicket(
            @PathVariable String codeTicket
    ) {

        try {

            StaffTicketDTO ticket =
                    staffTicketService
                            .scannerTicket(
                                    codeTicket
                            );

            return ResponseEntity.ok(ticket);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}