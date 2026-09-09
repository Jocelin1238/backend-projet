package SystemeReservationticket.example.SystemeReservation.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPassWordRequest {

    @Email(message = "Adresse email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

}