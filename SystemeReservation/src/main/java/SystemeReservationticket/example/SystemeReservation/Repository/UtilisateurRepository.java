package SystemeReservationticket.example.SystemeReservation.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Enum.RolesTypes;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);
    @Query("""
SELECT u
FROM Utilisateur u
WHERE
LOWER(u.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR
LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
List<Utilisateur> rechercherUtilisateurs(
        @Param("keyword") String keyword
);
 List<Utilisateur> findByRoles_Role(RolesTypes role);
   List<Utilisateur> findTop5ByOrderByDateCreationDesc();

}
