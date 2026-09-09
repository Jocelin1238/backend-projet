
package SystemeReservationticket.example.SystemeReservation.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.Voyage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoyageRepository
        extends JpaRepository<Voyage, Long> {

    // =====================================================
    // SUPER ADMIN
    // =====================================================

    List<Voyage> findTop5ByOrderByDateCreationDesc();


    // =====================================================
    // STAFF
    // =====================================================

    /**
     * Tous les voyages d'une journée
     */
    List<Voyage> findByDateDepart(
            LocalDate dateDepart
    );


    /**
     * Voyages d'une journée
     * triés par heure de départ
     */
    List<Voyage> findByDateDepartOrderByHeureDepartAsc(
            LocalDate dateDepart
    );


    /**
     * Nombre de voyages pour une journée
     */
    long countByDateDepart(
            LocalDate dateDepart
    );


    /**
     * =====================================================
     * PROCHAINS DEPARTS
     * =====================================================
     *
     * Récupère les voyages à partir d'une date donnée.
     *
     * Exemple :
     *
     * date = 2026-08-17
     *
     * récupère :
     *
     * 2026-08-17
     * 2026-08-18
     * 2026-08-19
     * etc.
     *
     * avec un tri par date puis heure.
     */
    List<Voyage> findByDateDepartGreaterThanEqualOrderByDateDepartAscHeureDepartAsc(
            LocalDate dateDepart
    );
@Query("""
    SELECT v
    FROM Voyage v
    LEFT JOIN FETCH v.trajet
    LEFT JOIN FETCH v.confort
    LEFT JOIN FETCH v.transport
""")
List<Voyage> findAllWithDetails();
}

