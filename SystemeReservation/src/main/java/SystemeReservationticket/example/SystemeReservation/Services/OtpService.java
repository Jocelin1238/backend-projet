package SystemeReservationticket.example.SystemeReservation.Services;


import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import SystemeReservationticket.example.SystemeReservation.Model.Otp;
import SystemeReservationticket.example.SystemeReservation.Repository.OtpRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OtpService {


    private final OtpRepository otpRepository;
    private final EmailService emailService;


    /**
     * Générer un OTP et l'envoyer par email
     */
    public void generateOtp(String email) {


        // Supprimer ancien OTP s'il existe
        otpRepository.deleteByEmail(email);


        // Génération code 6 chiffres
        String code = generateCode();


        Otp otp = Otp.builder()
                .email(email)
                .code(code)
                .dateExpiration(
                        LocalDateTime.now().plusMinutes(5)
                )
                .utilise(false)
                .build();


        otpRepository.save(otp);


        // Envoi email via MailDev
        emailService.sendOtpEmail(
                email,
                code
        );

    }



    /**
     * Vérifier un OTP
     */
    public boolean verifyOtp(
            String email,
            String code
    ) {


        Otp otp = otpRepository
                .findByEmailAndCode(email, code)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Code OTP incorrect."
                        )
                );


        // Vérifier expiration
        if (otp.getDateExpiration()
                .isBefore(LocalDateTime.now())) {


            throw new RuntimeException(
                    "Code OTP expiré."
            );

        }


        // Vérifier utilisation
        if (otp.isUtilise()) {


            throw new RuntimeException(
                    "Code OTP déjà utilisé."
            );

        }


        // Marquer utilisé
        otp.setUtilise(true);

        otpRepository.save(otp);


        return true;

    }



    /**
     * Supprimer OTP
     */
    public void deleteOtp(String email) {

        otpRepository.deleteByEmail(email);

    }



    /**
     * Génération aléatoire OTP
     */
    private String generateCode() {


        Random random = new Random();


        int code =
                100000 + random.nextInt(900000);


        return String.valueOf(code);

    }

}