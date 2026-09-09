package SystemeReservationticket.example.SystemeReservation.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import SystemeReservationticket.example.SystemeReservation.Model.Transport;
import SystemeReservationticket.example.SystemeReservation.Services.TransportService;

@RestController
@RequestMapping("/api/transports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TransportController {

    private final TransportService transportService;


    // ==========================================
    // AJOUTER UN TRANSPORT
    // ==========================================

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Transport> ajouterTransport(
            @RequestBody Transport transport
    ) {

        return ResponseEntity.ok(
                transportService.ajouterTransport(transport)
        );
    }


    // ==========================================
    // LISTE DES TRANSPORTS
    // ==========================================

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','STAFF','USER')")
    public ResponseEntity<List<Transport>> getAllTransports() {

        return ResponseEntity.ok(
                transportService.getAllTransports()
        );
    }


    // ==========================================
    // TRANSPORT PAR ID
    // ==========================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','STAFF')")
    public ResponseEntity<Transport> getTransportById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                transportService.getTransportById(id)
        );
    }


    // ==========================================
    // MODIFIER UN TRANSPORT
    // ==========================================

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Transport> modifierTransport(
            @PathVariable Long id,
            @RequestBody Transport transport
    ) {

        return ResponseEntity.ok(
                transportService.modifierTransport(
                        id,
                        transport
                )
        );
    }


    // ==========================================
    // SUPPRIMER UN TRANSPORT
    // ==========================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> supprimerTransport(
            @PathVariable Long id
    ) {

        transportService.supprimerTransport(id);

        return ResponseEntity.noContent().build();
    }

}