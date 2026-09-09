
package SystemeReservationticket.example.SystemeReservation.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SystemeReservationticket.example.SystemeReservation.Model.Notification;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;


    // =====================================================
    // CREER UNE NOTIFICATION
    // =====================================================

    public Notification creerNotification(
            String message,
            String type,
            Utilisateur utilisateur) {

        Notification notification = Notification.builder()

                // Titre interne obligatoire en base
            

                .message(message)

                .type(type)

                .lu(false)

                .utilisateur(utilisateur)

                .build();

        return notificationRepository.save(notification);
    }


    // =====================================================
    // GENERER LE TITRE INTERNE
    // =====================================================

    private String genererTitre(String type) {

        if (type == null) {
            return "Notification";
        }

        return switch (type) {

            case "RESERVATION" ->
                    "Nouvelle réservation";

            case "PAIEMENT" ->
                    "Nouveau paiement";

            case "VOYAGE" ->
                    "Information voyage";

            case "UTILISATEUR" ->
                    "Information utilisateur";

            case "SYSTEME" ->
                    "Notification système";

            default ->
                    "Notification";
        };
    }


    // =====================================================
    // RECUPERER TOUTES LES NOTIFICATIONS
    // =====================================================

    @Transactional(readOnly = true)
    public List<Notification> getNotificationsUtilisateur(
            Long utilisateurId) {

        return notificationRepository
                .findByUtilisateurIdOrderByDateCreationDesc(
                        utilisateurId
                );
    }


    // =====================================================
    // RECUPERER LES NOTIFICATIONS NON LUES
    // =====================================================

    @Transactional(readOnly = true)
    public List<Notification> getNotificationsNonLues(
            Long utilisateurId) {

        return notificationRepository
                .findByUtilisateurIdAndLuFalseOrderByDateCreationDesc(
                        utilisateurId
                );
    }


    // =====================================================
    // COMPTER LES NOTIFICATIONS NON LUES
    // =====================================================

    @Transactional(readOnly = true)
    public long compterNotificationsNonLues(
            Long utilisateurId) {

        return notificationRepository
                .countByUtilisateurIdAndLuFalse(
                        utilisateurId
                );
    }


    // =====================================================
    // MARQUER UNE NOTIFICATION COMME LUE
    // =====================================================

    public Notification marquerCommeLue(
            Long notificationId,
            Long utilisateurId) {

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification introuvable : "
                                                + notificationId
                                )
                        );


        // =================================================
        // VERIFICATION DE PROPRIETE
        // =================================================

        if (!notification.getUtilisateur()
                .getId()
                .equals(utilisateurId)) {

            throw new RuntimeException(
                    "Cette notification ne vous appartient pas"
            );
        }


        notification.setLu(true);

        return notificationRepository.save(notification);
    }


    // =====================================================
    // MARQUER TOUTES LES NOTIFICATIONS COMME LUES
    // =====================================================

    public void marquerToutesCommeLues(
            Long utilisateurId) {

        List<Notification> notifications =
                notificationRepository
                        .findByUtilisateurIdAndLuFalseOrderByDateCreationDesc(
                                utilisateurId
                        );


        for (Notification notification : notifications) {

            notification.setLu(true);

        }


        notificationRepository.saveAll(notifications);
    }


    // =====================================================
    // SUPPRIMER UNE NOTIFICATION
    // =====================================================

    public void supprimerNotification(
            Long notificationId,
            Long utilisateurId) {

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification introuvable : "
                                                + notificationId
                                )
                        );


        // =================================================
        // VERIFICATION DE PROPRIETE
        // =================================================

        if (!notification.getUtilisateur()
                .getId()
                .equals(utilisateurId)) {

            throw new RuntimeException(
                    "Cette notification ne vous appartient pas"
            );
        }


        notificationRepository.delete(notification);
    }

}

