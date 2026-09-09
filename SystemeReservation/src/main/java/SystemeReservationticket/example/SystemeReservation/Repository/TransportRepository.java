package SystemeReservationticket.example.SystemeReservation.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.Transport;
import SystemeReservationticket.example.SystemeReservation.Enum.TypesTransport;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {

    List<Transport> findByType(TypesTransport type);

    List<Transport> findByStatut(String statut);
}