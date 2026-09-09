package SystemeReservationticket.example.SystemeReservation.Repository;


import SystemeReservationticket.example.SystemeReservation.Model.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrajetRepository 
        extends JpaRepository<Trajet, Long> {


}