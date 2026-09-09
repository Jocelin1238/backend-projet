package SystemeReservationticket.example.SystemeReservation.Controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SystemeReservationticket.example.SystemeReservation.Model.Reservation;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Repository.UtilisateurRepository;
import SystemeReservationticket.example.SystemeReservation.Services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final UtilisateurRepository utilisateurRepository;

    @PostMapping("/{voyageId}")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<?> reserver(
        @PathVariable Long voyageId,
        Authentication authentication
) {

    String email = authentication.getName();

    Reservation reservation =
            reservationService.creerReservation(
                    voyageId,
                    email
            );

    return ResponseEntity.ok(reservation);
}

@GetMapping("/mes-reservations")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<?> mesReservations(
        Authentication authentication
) {

    String email = authentication.getName();

    Utilisateur utilisateur =
            utilisateurRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Utilisateur introuvable"
                            ));

    return ResponseEntity.ok(
            reservationService.mesReservations(
                    utilisateur.getId()
            )
    );
}
@GetMapping
@PreAuthorize(
    "hasAnyRole('SUPER_ADMIN','ADMIN','STAFF')"
)
public List<Reservation> getAll() {

    return reservationService.getAllReservations();

}
}
