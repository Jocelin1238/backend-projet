package SystemeReservationticket.example.SystemeReservation.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import SystemeReservationticket.example.SystemeReservation.Model.Ticket;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Services.TicketService;
import SystemeReservationticket.example.SystemeReservation.Repository.UtilisateurRepository;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

    private final TicketService ticketService;
    private final UtilisateurRepository utilisateurRepository;


    // =================================================
    // MES TICKETS - USER CONNECTÉ
    // =================================================

    @GetMapping("/mes-tickets")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Ticket>> mesTickets(
            Authentication authentication
    ) {

        String email = authentication.getName();

        Utilisateur utilisateur =
                utilisateurRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur introuvable"
                                )
                        );

        return ResponseEntity.ok(
                ticketService.mesTickets(
                        utilisateur.getId()
                )
        );
    }


    // =================================================
    // CREER UN TICKET
    // =================================================

    @PostMapping("/reservation/{reservationId}")
    public ResponseEntity<Ticket> creerTicket(
            @PathVariable Long reservationId
    ) {

        return ResponseEntity.ok(
                ticketService.creerTicket(reservationId)
        );
    }


    // =================================================
    // LISTE DE TOUS LES TICKETS
    // ADMIN / STAFF
    // =================================================

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','STAFF')")
    public ResponseEntity<List<Ticket>> getTickets() {

        return ResponseEntity.ok(
                ticketService.getAllTickets()
        );
    }


    // =================================================
    // RECHERCHE PAR ID
    // =================================================

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ticketService.getTicketById(id)
        );
    }


    // =================================================
    // RECHERCHE PAR CODE
    // =================================================

    @GetMapping("/code/{code}")
    public ResponseEntity<Ticket> getByCode(
            @PathVariable String code
    ) {

        return ResponseEntity.ok(
                ticketService.getTicketByCode(code)
        );
    }


    // =================================================
    // AFFICHER QR CODE IMAGE
    // =================================================

    @GetMapping("/{id}/qrcode")
    public ResponseEntity<Resource> afficherQRCode(
            @PathVariable Long id
    ) throws IOException {

        Ticket ticket =
                ticketService.getTicketById(id);

        Path chemin =
                Paths.get(ticket.getQrCode());

        Resource resource =
                new UrlResource(
                        chemin.toUri()
                );

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }


    // =================================================
    // VERIFIER QR CODE
    // =================================================

    @GetMapping("/verifier/{code}")
    public ResponseEntity<Ticket> verifier(
            @PathVariable String code
    ) {

        return ResponseEntity.ok(
                ticketService.verifierTicket(code)
        );
    }


    // =================================================
    // SCANNER QR CODE
    // =================================================

    @PutMapping("/scanner/{code}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','STAFF')")
    public ResponseEntity<Ticket> scanner(
            @PathVariable String code
    ) {

        return ResponseEntity.ok(
                ticketService.scannerTicket(code)
        );
    }


    // =================================================
    // UTILISER TICKET
    // =================================================

    @PutMapping("/{id}/utiliser")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','STAFF')")
    public ResponseEntity<Ticket> utiliser(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ticketService.utiliserTicket(id)
        );
    }


    // =================================================
    // ANNULER TICKET
    // =================================================

    @PutMapping("/{id}/annuler")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<Ticket> annuler(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ticketService.annulerTicket(id)
        );
    }


    // =================================================
    // SUPPRIMER TICKET
    // =================================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<String> supprimer(
            @PathVariable Long id
    ) {

        ticketService.supprimerTicket(id);

        return ResponseEntity.ok(
                "Ticket supprimé"
        );
    }
    
}