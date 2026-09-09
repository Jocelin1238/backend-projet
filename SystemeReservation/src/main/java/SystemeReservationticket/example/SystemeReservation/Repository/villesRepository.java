package SystemeReservationticket.example.SystemeReservation.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.villes;

@Repository
public interface villesRepository extends JpaRepository<villes, Long> {

    List<villes> findByActifTrueOrderByNomAsc();

}