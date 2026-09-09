package SystemeReservationticket.example.SystemeReservation.Repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.Ticket;

@Repository
public interface TicketRepository
        extends JpaRepository<Ticket, Long> {

List<Ticket> findByReservationUtilisateurId(Long utilisateurId);
    // =====================================================
    // RECHERCHE PAR CODE
    // =====================================================

    Optional<Ticket> findByCodeTicket(
            String codeTicket
    );


    // =====================================================
    // RECHERCHE PAR RESERVATION
    // =====================================================

    Optional<Ticket> findByReservationId(
            Long reservationId
    );


    // =====================================================
    // TICKETS SCANNES
    // =====================================================

    long countByStatut(
            String statut
    );


    // =====================================================
    // TICKET AVEC DETAILS
    // =====================================================

    @Query("""
        SELECT t
        FROM Ticket t
        LEFT JOIN FETCH t.reservation r
        LEFT JOIN FETCH r.utilisateur
        LEFT JOIN FETCH r.voyage v
        LEFT JOIN FETCH v.trajet
        WHERE t.id = :id
    """)
    Optional<Ticket> findByIdWithDetails(
            Long id
    );


    // =====================================================
    // TICKET PAR CODE AVEC DETAILS
    // =====================================================

    @Query("""
        SELECT t
        FROM Ticket t
        LEFT JOIN FETCH t.reservation r
        LEFT JOIN FETCH r.utilisateur
        LEFT JOIN FETCH r.voyage v
        LEFT JOIN FETCH v.trajet
        WHERE t.codeTicket = :codeTicket
    """)
    Optional<Ticket> findByCodeTicketWithDetails(
            String codeTicket
    );
    
}