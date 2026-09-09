package SystemeReservationticket.example.SystemeReservation.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.Reservation;

@Repository
public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {


    // =====================================================
    // RESERVATIONS D'UN UTILISATEUR
    // =====================================================

    List<Reservation> findByUtilisateurId(
            Long utilisateurId
    );


    // =====================================================
    // STATISTIQUES SUPER ADMIN
    // =====================================================

    @Query("""
        SELECT MONTH(r.dateReservation), COUNT(r)
        FROM Reservation r
        GROUP BY MONTH(r.dateReservation)
        ORDER BY MONTH(r.dateReservation)
    """)
    List<Object[]> countReservationsByMonth();


    // =====================================================
    // 5 DERNIERES RESERVATIONS
    // =====================================================

    List<Reservation> findTop5ByOrderByIdDesc();

// =====================================================
// MES RESERVATIONS AVEC DETAILS
// =====================================================

@Query("""
    SELECT DISTINCT r
    FROM Reservation r
    LEFT JOIN FETCH r.utilisateur
    LEFT JOIN FETCH r.voyage v
    LEFT JOIN FETCH v.trajet
    WHERE r.utilisateur.id = :utilisateurId
    ORDER BY r.dateReservation DESC
""")
List<Reservation> findByUtilisateurIdWithDetails(
        Long utilisateurId
);


// =====================================================
// TOUTES LES RESERVATIONS AVEC DETAILS
// =====================================================

@Query("""
    SELECT DISTINCT r
    FROM Reservation r
    LEFT JOIN FETCH r.utilisateur
    LEFT JOIN FETCH r.voyage v
    LEFT JOIN FETCH v.trajet
    ORDER BY r.id DESC
""")
List<Reservation> findAllWithDetails();
 // =====================================================
    // VERIFIER SI L'UTILISATEUR A DEJA RESERVE LE VOYAGE
    // =====================================================

    boolean existsByUtilisateurIdAndVoyageId(
            Long utilisateurId,
            Long voyageId
    );
    // =====================================================
    // STAFF
    // =====================================================

    /**
     * Réservations effectuées aujourd'hui
     */
    @Query("""
        SELECT r
        FROM Reservation r
        LEFT JOIN FETCH r.utilisateur
        LEFT JOIN FETCH r.voyage v
        LEFT JOIN FETCH v.trajet
        WHERE r.dateReservation >= :debut
        AND r.dateReservation <= :fin
        ORDER BY r.dateReservation DESC
    """)
    List<Reservation> findReservationsDuJour(
            LocalDateTime debut,
            LocalDateTime fin
    );


    /**
     * Nombre de réservations effectuées aujourd'hui
     */
    long countByDateReservationBetween(
            LocalDateTime debut,
            LocalDateTime fin
    );


    /**
     * Dernières réservations avec toutes les informations
     */
    @Query("""
        SELECT DISTINCT r
        FROM Reservation r
        LEFT JOIN FETCH r.utilisateur
        LEFT JOIN FETCH r.voyage v
        LEFT JOIN FETCH v.trajet
        ORDER BY r.dateReservation DESC
    """)
    List<Reservation> findTop5WithDetails();
}