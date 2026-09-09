package SystemeReservationticket.example.SystemeReservation.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SystemeReservationticket.example.SystemeReservation.Model.Reservation;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Model.Voyage;
import SystemeReservationticket.example.SystemeReservation.Repository.ReservationRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.UtilisateurRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.VoyageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final VoyageRepository voyageRepository;

    private final UtilisateurRepository utilisateurRepository;

    // =========================================================
    // SERVICE NOTIFICATION
    // =========================================================

    private final NotificationService notificationService;


    // =========================================================
    // CREER UNE RESERVATION
    // =========================================================

    @Transactional
    public Reservation creerReservation(
            Long voyageId,
            String emailUtilisateur
    ) {

        // =========================================================
        // 1. RÉCUPÉRER L'UTILISATEUR CONNECTÉ
        // =========================================================

        Utilisateur utilisateur =
                utilisateurRepository
                        .findByEmail(emailUtilisateur)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur introuvable"
                                )
                        );


        // =========================================================
        // 2. RÉCUPÉRER LE VOYAGE
        // =========================================================

        Voyage voyage =
                voyageRepository
                        .findById(voyageId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Voyage introuvable"
                                )
                        );


        // =========================================================
        // 3. VÉRIFIER SI L'UTILISATEUR A DÉJÀ RÉSERVÉ
        // =========================================================

        boolean dejaReserve =
                reservationRepository
                        .existsByUtilisateurIdAndVoyageId(
                                utilisateur.getId(),
                                voyage.getId()
                        );

        if (dejaReserve) {

            throw new RuntimeException(
                    "Vous avez déjà réservé ce voyage."
            );
        }


        // =========================================================
        // 4. VÉRIFIER LE STATUT DU VOYAGE
        // =========================================================

        String statut = voyage.getStatut();

        if (
            statut != null &&
            (
                statut.equalsIgnoreCase("ANNULE") ||
                statut.equalsIgnoreCase("ANNULÉ") ||
                statut.equalsIgnoreCase("TERMINE") ||
                statut.equalsIgnoreCase("TERMINÉ")
            )
        ) {

            throw new RuntimeException(
                    "Ce voyage n'est plus disponible à la réservation."
            );
        }


        // =========================================================
        // 5. VÉRIFIER LES PLACES
        // =========================================================

        if (
            voyage.getPlacesDisponibles() == null ||
            voyage.getPlacesDisponibles() <= 0
        ) {

            throw new RuntimeException(
                    "Plus de places disponibles."
            );
        }


        // =========================================================
        // 6. CRÉER LA RÉSERVATION
        // =========================================================

        Reservation reservation =
                Reservation.builder()
                        .dateReservation(
                                LocalDateTime.now()
                        )
                        .statut("EN_ATTENTE")
                        .voyage(voyage)
                        .utilisateur(utilisateur)
                        .build();


        // =========================================================
        // 7. DIMINUER LES PLACES
        // =========================================================

        voyage.setPlacesDisponibles(
                voyage.getPlacesDisponibles() - 1
        );


        // =========================================================
        // 8. SAUVEGARDER LE VOYAGE
        // =========================================================

        voyageRepository.save(voyage);


        // =========================================================
        // 9. SAUVEGARDER LA RÉSERVATION
        // =========================================================

        Reservation reservationSauvegardee =
                reservationRepository.save(reservation);


        // =========================================================
        // 10. CRÉER LA NOTIFICATION POUR LE CLIENT
        // =========================================================

        String message =
                "Votre réservation pour le voyage "
                + "a été enregistrée avec succès. "
                + "Statut : EN ATTENTE.";

        notificationService.creerNotification(
                message,
                "RESERVATION",
                utilisateur
        );


        // =========================================================
        // 11. RETOURNER LA RÉSERVATION
        // =========================================================

        return reservationSauvegardee;
    }


    // =========================================================
    // TOUTES LES RESERVATIONS
    // =========================================================

    public List<Reservation> getAllReservations() {

        return reservationRepository
                .findAllWithDetails();
    }


    // =========================================================
    // MES RESERVATIONS
    // =========================================================

    @Transactional(readOnly = true)
    public List<Reservation> mesReservations(
            Long utilisateurId
    ) {

        return reservationRepository
                .findByUtilisateurIdWithDetails(
                        utilisateurId
                );
    }
}