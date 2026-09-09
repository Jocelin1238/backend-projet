package SystemeReservationticket.example.SystemeReservation.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import SystemeReservationticket.example.SystemeReservation.DTO.AuthenticationRequest;
import SystemeReservationticket.example.SystemeReservation.DTO.AuthenticationReponse;
import SystemeReservationticket.example.SystemeReservation.DTO.ForgotPassWordRequest;
import SystemeReservationticket.example.SystemeReservation.DTO.OtpRequest;
import SystemeReservationticket.example.SystemeReservation.DTO.RegisterRequest;
import SystemeReservationticket.example.SystemeReservation.DTO.ResetPassWordRequest;
import SystemeReservationticket.example.SystemeReservation.Services.AuthenticationService;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {



    private final AuthenticationService authenticationService;





    // =====================================================
    // INSCRIPTION
    // =====================================================

    @PostMapping("/register")
    public ResponseEntity<AuthenticationReponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    authenticationService.register(request)
                );

    }







    // =====================================================
    // VERIFICATION OTP COMPTE
    // =====================================================

    @PostMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(
            @Valid @RequestBody OtpRequest request
    ) {


        authenticationService.verifyAccount(
                request.getEmail(),
                request.getOtp()
        );


        return ResponseEntity.ok(
                "Compte activé avec succès."
        );

    }








    // =====================================================
    // CONNEXION
    // =====================================================

    @PostMapping("/login")
    
    public ResponseEntity<AuthenticationReponse> login(
        @Valid @RequestBody AuthenticationRequest request
) {

    System.out.println("LOGIN RECU : " + request.getEmail());

    return ResponseEntity.ok(
            authenticationService.authenticate(request)
    );
}








    // =====================================================
    // RENVOYER OTP
    // =====================================================

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(
            @RequestParam String email
    ) {


        authenticationService.resendOtp(email);


        return ResponseEntity.ok(
                "Un nouveau code OTP a été envoyé."
        );

    }








    // =====================================================
    // MOT DE PASSE OUBLIE
    // =====================================================

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody ForgotPassWordRequest request
    ) {


        authenticationService.forgotPassword(request);


        return ResponseEntity.ok(
                "Code OTP envoyé par email."
        );

    }








    // =====================================================
    // RESET PASSWORD
    // =====================================================

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPassWordRequest request
    ) {


        authenticationService.resetPassword(request);



        return ResponseEntity.ok(
                "Mot de passe modifié avec succès."
        );

    }








    // =====================================================
    // VALIDATION TOKEN
    // =====================================================

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(
            @RequestParam String token
    ) {


        return ResponseEntity.ok(

                authenticationService.validateToken(token)

        );

    }








    // =====================================================
    // LOGOUT JWT
    // =====================================================

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {


        return ResponseEntity.ok(
                "Déconnexion réussie. Supprimez le token côté client."
        );

    }


}