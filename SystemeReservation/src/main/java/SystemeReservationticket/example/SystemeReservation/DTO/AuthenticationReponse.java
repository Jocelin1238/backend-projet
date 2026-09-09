package SystemeReservationticket.example.SystemeReservation.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationReponse {

    private String token;

    private String email;

    private String nom;

    private String prenom;

    private String role;
}