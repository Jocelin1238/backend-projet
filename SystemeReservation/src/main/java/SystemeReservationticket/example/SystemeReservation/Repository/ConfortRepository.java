package SystemeReservationticket.example.SystemeReservation.Repository;


import SystemeReservationticket.example.SystemeReservation.Model.Confort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ConfortRepository 
        extends JpaRepository<Confort, Long> {


}