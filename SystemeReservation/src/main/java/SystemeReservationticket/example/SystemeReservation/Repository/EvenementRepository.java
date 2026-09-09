package SystemeReservationticket.example.SystemeReservation.Repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.Evenement;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {

    List<Evenement> findByActifTrue();

    List<Evenement> findByDateEvenementAfter(LocalDate date);

    List<Evenement> findByTitreContainingIgnoreCase(String titre);

}