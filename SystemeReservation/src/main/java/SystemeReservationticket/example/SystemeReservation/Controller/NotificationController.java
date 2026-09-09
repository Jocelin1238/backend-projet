
package SystemeReservationticket.example.SystemeReservation.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import SystemeReservationticket.example.SystemeReservation.DTO.NotificationDTO;
import SystemeReservationticket.example.SystemeReservation.Model.Notification;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Repository.UtilisateurRepository;
import SystemeReservationticket.example.SystemeReservation.Services.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {


    private final NotificationService notificationService;

    private final UtilisateurRepository utilisateurRepository;


    // =====================================================
    // UTILISATEUR CONNECTÉ
    // =====================================================

    private Utilisateur getUtilisateurConnecte(
            Authentication authentication) {

        String email = authentication.getName();

        return utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Utilisateur connecté introuvable"
                        )
                );
    }


    // =====================================================
    // TOUTES LES NOTIFICATIONS
    // =====================================================

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications(
            Authentication authentication) {

        Utilisateur utilisateur =
                getUtilisateurConnecte(authentication);

        List<NotificationDTO> notifications =
                notificationService
                        .getNotificationsUtilisateur(
                                utilisateur.getId()
                        )
                        .stream()
                        .map(NotificationDTO::fromEntity)
                        .toList();

        return ResponseEntity.ok(notifications);
    }


    // =====================================================
    // NOTIFICATIONS NON LUES
    // =====================================================

    @GetMapping("/non-lues")
    public ResponseEntity<List<NotificationDTO>> getNotificationsNonLues(
            Authentication authentication) {

        Utilisateur utilisateur =
                getUtilisateurConnecte(authentication);

        List<NotificationDTO> notifications =
                notificationService
                        .getNotificationsNonLues(
                                utilisateur.getId()
                        )
                        .stream()
                        .map(NotificationDTO::fromEntity)
                        .toList();

        return ResponseEntity.ok(notifications);
    }


    // =====================================================
    // NOMBRE DE NOTIFICATIONS NON LUES
    // =====================================================

    @GetMapping("/count")
    public ResponseEntity<Long> compterNotificationsNonLues(
            Authentication authentication) {

        Utilisateur utilisateur =
                getUtilisateurConnecte(authentication);

        return ResponseEntity.ok(
                notificationService
                        .compterNotificationsNonLues(
                                utilisateur.getId()
                        )
        );
    }


    // =====================================================
    // MARQUER UNE NOTIFICATION COMME LUE
    // =====================================================

    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationDTO> marquerCommeLue(
            @PathVariable Long id,
            Authentication authentication) {

        Utilisateur utilisateur =
                getUtilisateurConnecte(authentication);

        Notification notification =
                notificationService.marquerCommeLue(
                        id,
                        utilisateur.getId()
                );

        return ResponseEntity.ok(
                NotificationDTO.fromEntity(notification)
        );
    }


    // =====================================================
    // MARQUER TOUTES LES NOTIFICATIONS COMME LUES
    // =====================================================

    @PatchMapping("/read-all")
    public ResponseEntity<Void> marquerToutesCommeLues(
            Authentication authentication) {

        Utilisateur utilisateur =
                getUtilisateurConnecte(authentication);

        notificationService.marquerToutesCommeLues(
                utilisateur.getId()
        );

        return ResponseEntity.ok().build();
    }


    // =====================================================
    // SUPPRIMER UNE NOTIFICATION
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerNotification(
            @PathVariable Long id,
            Authentication authentication) {

        Utilisateur utilisateur =
                getUtilisateurConnecte(authentication);

        notificationService.supprimerNotification(
                id,
                utilisateur.getId()
        );

        return ResponseEntity.ok().build();
    }

}

