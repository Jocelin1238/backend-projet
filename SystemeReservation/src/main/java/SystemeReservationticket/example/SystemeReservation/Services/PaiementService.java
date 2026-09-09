package SystemeReservationticket.example.SystemeReservation.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import SystemeReservationticket.example.SystemeReservation.Model.Paiement;
import SystemeReservationticket.example.SystemeReservation.Model.Reservation;
import SystemeReservationticket.example.SystemeReservation.Repository.PaiementRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.ReservationRepository;


@Service
@RequiredArgsConstructor
public class PaiementService {


    private final PaiementRepository paiementRepository;

    private final ReservationRepository reservationRepository;

    private final TicketService ticketService;





    // ================================
    // CREER UN PAIEMENT
    // ================================

    public Paiement creerPaiement(
            Long reservationId,
            double montant,
            String methode
    ) {


        Reservation reservation =
                reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Reservation introuvable"
                        )
                );



        Paiement paiement = Paiement.builder()

                .montant(montant)

                .methode(methode)

                .statut("EN_COURS")

                .referenceTransaction(
                        "TRANS-" + System.currentTimeMillis()
                )

                .reservation(reservation)

                .datePaiement(
                        LocalDateTime.now()
                )

                .dateModification(
                        LocalDateTime.now()
                )

                .build();



        return paiementRepository.save(paiement);

    }







    // ================================
    // VALIDER UN PAIEMENT
    // GENERATION DU TICKET QR
    // ================================

    public Paiement validerPaiement(Long id){



        Paiement paiement =
                paiementRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Paiement introuvable"
                        )
                );



        // changement statut paiement

        paiement.setStatut("REUSSI");

        paiement.setDateModification(
                LocalDateTime.now()
        );




        // récupération réservation

        Reservation reservation =
                paiement.getReservation();




        // confirmation réservation

        reservation.setStatut("CONFIRMEE");

        reservationRepository.save(reservation);




        // génération automatique du ticket QR

        ticketService.creerTicket(
                reservation.getId()
        );




        return paiementRepository.save(paiement);

    }







    // ================================
    // LISTE DES PAIEMENTS
    // ================================

    public List<Paiement> getAllPaiements(){

        return paiementRepository.findAll();

    }







    // ================================
    // PAIEMENT PAR ID
    // ================================

    public Paiement getPaiementById(Long id){

        return paiementRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Paiement introuvable"
                        )
                );

    }







    // ================================
    // PAIEMENTS D'UNE RESERVATION
    // ================================

    public List<Paiement> getPaiementsReservation(
            Long reservationId
    ){

        return paiementRepository
                .findByReservationId(reservationId);

    }







    // ================================
    // MODIFIER STATUT
    // ================================

    public Paiement modifierStatut(
            Long paiementId,
            String nouveauStatut
    ){


        Paiement paiement =
                getPaiementById(paiementId);


        paiement.setStatut(nouveauStatut);


        paiement.setDateModification(
                LocalDateTime.now()
        );


        return paiementRepository.save(paiement);

    }







    // ================================
    // RECHERCHE TRANSACTION
    // ================================

    public Paiement chercherParReference(
            String reference
    ){

        return paiementRepository

                .findByReferenceTransaction(reference)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Transaction introuvable"
                        )
                );

    }
// ==========================================
// MES PAIEMENTS UTILISATEUR
// ==========================================

public List<Paiement> getPaiementsUtilisateur(String email) {

    return paiementRepository
            .findByReservationUtilisateurEmail(email);

}
}