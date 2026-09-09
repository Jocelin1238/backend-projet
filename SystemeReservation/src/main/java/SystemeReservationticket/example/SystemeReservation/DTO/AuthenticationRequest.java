package SystemeReservationticket.example.SystemeReservation.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {


    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;


    
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;

}