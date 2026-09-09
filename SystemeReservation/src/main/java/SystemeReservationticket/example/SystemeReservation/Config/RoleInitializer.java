package SystemeReservationticket.example.SystemeReservation.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import SystemeReservationticket.example.SystemeReservation.Enum.RolesTypes;
import SystemeReservationticket.example.SystemeReservation.Model.Role;
import SystemeReservationticket.example.SystemeReservation.Repository.RoleRepository;



@Configuration
public class RoleInitializer {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    public CommandLineRunner initRoles() {
        return args -> {

            for (RolesTypes roleType : RolesTypes.values()) {

                if (!roleRepository.existsByRole(roleType)) {

                    Role role = Role.builder()
                            .role(roleType)
                            .description(roleType.name())
                            .build();

                    roleRepository.save(role);
                }
            }
        }; 
    }
}