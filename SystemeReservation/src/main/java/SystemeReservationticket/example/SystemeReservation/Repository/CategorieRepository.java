package SystemeReservationticket.example.SystemeReservation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}