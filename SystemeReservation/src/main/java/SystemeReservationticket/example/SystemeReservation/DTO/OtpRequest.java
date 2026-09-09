package SystemeReservationticket.example.SystemeReservation.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequest {

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "Le code OTP est obligatoire")
    @Pattern(
        regexp = "\\d{6}",
        message = "Le code OTP doit contenir exactement 6 chiffres"
    )
    private String otp;

}