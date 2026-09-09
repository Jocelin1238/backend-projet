
package SystemeReservationticket.example.SystemeReservation.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffDashboardDTO {

    // =====================================================
    // UTILISATEUR
    // =====================================================

    private String prenomStaff;


    // =====================================================
    // STATISTIQUES
    // =====================================================

    private long nombreVoyages;

    private long nombreReservations;

    private long nombreDeparts;

    private long ticketsScannes;


    // =====================================================
    // PROCHAINS DEPARTS
    // =====================================================

    private List<StaffVoyageDTO> prochainsDeparts;


    // =====================================================
    // RESERVATIONS RECENTES
    // =====================================================

    private List<StaffReservationDTO> reservationsRecentes;
}

