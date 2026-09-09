package SystemeReservationticket.example.SystemeReservation.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Toutes les notifications d'un utilisateur
    List<Notification> findByUtilisateurIdOrderByDateCreationDesc(
            Long utilisateurId
    );

    // Notifications non lues
    List<Notification> findByUtilisateurIdAndLuFalseOrderByDateCreationDesc(
            Long utilisateurId
    );

    // Nombre de notifications non lues
    long countByUtilisateurIdAndLuFalse(
            Long utilisateurId
    );

}