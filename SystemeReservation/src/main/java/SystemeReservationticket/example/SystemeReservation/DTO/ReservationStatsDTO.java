package SystemeReservationticket.example.SystemeReservation.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationStatsDTO {

    private String mois;

    private long nombreReservations;

}