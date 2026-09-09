
package SystemeReservationticket.example.SystemeReservation.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.Paiement;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    Optional<Paiement> findByReferenceTransaction(
            String referenceTransaction
    );

    List<Paiement> findByReservationId(
            Long reservationId
    );

    // =========================
    // TOTAL DES REVENUS
    // =========================

    @Query("""
        SELECT COALESCE(SUM(p.montant), 0)
        FROM Paiement p
        WHERE p.statut = 'REUSSI'
    """)
    Double getTotalRevenus();


    // =========================
    // REVENUS PAR MOIS
    // =========================

    @Query("""
        SELECT MONTH(p.datePaiement), COALESCE(SUM(p.montant), 0)
        FROM Paiement p
        WHERE p.statut = 'REUSSI'
        GROUP BY MONTH(p.datePaiement)
        ORDER BY MONTH(p.datePaiement)
    """)
    List<Object[]> getRevenusParMois();

    // ==========================================
    // MES PAIEMENTS
    // ==========================================

    List<Paiement> findByReservationUtilisateurEmail(String email);

}

