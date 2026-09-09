package SystemeReservationticket.example.SystemeReservation.Services;

 
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;


    /**
     * Envoyer un email simple
     */
    public void sendEmail(
            String to,
            String subject,
            String message
    ) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);

        mailSender.send(mail);
    }


    /**
     * Envoyer le code OTP
     */
    public void sendOtpEmail(
            String email,
            String otp
    ) {

        String subject = "Code de vérification OTP";

        String message =
                "Bonjour,\n\n"
                + "Votre code de vérification est : "
                + otp
                + "\n\n"
                + "Ce code est valable pendant quelques minutes.\n\n"
                + "Merci.";

        sendEmail(
                email,
                subject,
                message
        );
    }


    /**
     * Email de confirmation d'inscription
     */
    public void sendWelcomeEmail(
            String email,
            String nom
    ) {

        String subject = "Bienvenue dans notre système de réservation";

        String message =
                "Bonjour "
                + nom
                + ",\n\n"
                + "Votre compte a été créé avec succès.\n\n"
                + "Bienvenue parmi nous.";

        sendEmail(
                email,
                subject,
                message
        );
    }


    /**
     * Email de changement de mot de passe
     */
    public void sendPasswordChangedEmail(
            String email
    ) {

        String subject = "Modification du mot de passe";

        String message =
                "Votre mot de passe a été modifié avec succès.\n\n"
                + "Si vous n'êtes pas à l'origine de cette action, contactez l'administrateur.";

        sendEmail(
                email,
                subject,
                message
        );
    }

}