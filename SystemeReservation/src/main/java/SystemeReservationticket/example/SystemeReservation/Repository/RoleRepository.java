package SystemeReservationticket.example.SystemeReservation.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Enum.RolesTypes;
import SystemeReservationticket.example.SystemeReservation.Model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RolesTypes role);

    boolean existsByRole(RolesTypes role);

}