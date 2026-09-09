package SystemeReservationticket.example.SystemeReservation.DTO;




import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DashboardStats {


    private long utilisateurs;

    private long administrateurs;

    private long staff;

    private long voyages;

    private long reservations;

    private double revenus;


}