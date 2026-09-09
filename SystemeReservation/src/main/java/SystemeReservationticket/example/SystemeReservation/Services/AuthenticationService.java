package SystemeReservationticket.example.SystemeReservation.Services;
import java.util.HashSet;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


import SystemeReservationticket.example.SystemeReservation.DTO.AuthenticationRequest;
import SystemeReservationticket.example.SystemeReservation.DTO.AuthenticationReponse;
import SystemeReservationticket.example.SystemeReservation.DTO.ForgotPassWordRequest;
import SystemeReservationticket.example.SystemeReservation.DTO.RegisterRequest;
import SystemeReservationticket.example.SystemeReservation.DTO.ResetPassWordRequest;

import SystemeReservationticket.example.SystemeReservation.Enum.RolesTypes;

import SystemeReservationticket.example.SystemeReservation.Model.Role;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;

import SystemeReservationticket.example.SystemeReservation.Repository.RoleRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.UtilisateurRepository;

import SystemeReservationticket.example.SystemeReservation.Security.JwtService;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {


    private final UtilisateurRepository utilisateurRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final OtpService otpService;

    private final EmailService emailService;



    // ==================================================
    // REGISTER
    // ==================================================

   public AuthenticationReponse register(RegisterRequest request) {

    if (utilisateurRepository.existsByEmail(request.getEmail())) {

        throw new RuntimeException(
                "Cet email existe déjà."
        );
    }

    Role roleUser =
            roleRepository.findByRole(RolesTypes.ROLE_USER)
            .orElseThrow(() ->
                    new RuntimeException(
                            "ROLE_USER introuvable."
                    )
            );

    Utilisateur utilisateur =
            Utilisateur.builder()

            .nom(request.getNom())

            .prenom(request.getPrenom())

            .email(request.getEmail())

            .motDePasse(
                    passwordEncoder.encode(
                            request.getMotDePasse()
                    )
            )

            .telephone(request.getTelephone())

            .adresse(request.getAdresse())

            .actif(false)

            .compteExpire(false)

            .compteVerrouille(false)

            .credentialExpire(false)

            .roles(new HashSet<>())

            .build();

    utilisateur.getRoles()
            .add(roleUser);

    utilisateurRepository.save(utilisateur);


    // Génération OTP
    otpService.generateOtp(
            utilisateur.getEmail()
    );




       return AuthenticationReponse.builder()

        

        .email(utilisateur.getEmail())

        .nom(utilisateur.getNom())

        .prenom(utilisateur.getPrenom())

        .role(
            utilisateur.getRoles()
                .stream()
                .findFirst()
                .map(role -> role.getRole().name())
                .orElse("ROLE_USER")
        )

        .build();
    }
  public AuthenticationReponse createUserBySuperAdmin(RegisterRequest request) {


    // Vérifier si l'email existe déjà
    if (utilisateurRepository.existsByEmail(request.getEmail())) {

        throw new RuntimeException(
                "Cet email existe déjà."
        );
    }


    /*
     * Gestion du rôle
     * Si aucun rôle n'est envoyé,
     * on donne ROLE_USER par défaut
     */
    RolesTypes roleType;


    if (request.getRole() == null || request.getRole().isBlank()) {

        roleType = RolesTypes.ROLE_USER;

    } else {

        try {

            roleType = RolesTypes.valueOf(
                    request.getRole()
                            .trim()
                            .toUpperCase()
            );

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Rôle invalide : " + request.getRole()
            );

        }

    }



    // Recherche du rôle dans la base
    Role role = roleRepository.findByRole(roleType)

            .orElseThrow(() ->
                    new RuntimeException(
                            "Rôle introuvable : " + roleType
                    )
            );




    // Création utilisateur
    Utilisateur utilisateur = Utilisateur.builder()

            .nom(request.getNom())

            .prenom(request.getPrenom())

            .email(request.getEmail())

            .motDePasse(
                    passwordEncoder.encode(
                            request.getMotDePasse()
                    )
            )

            .telephone(request.getTelephone())

            .adresse(request.getAdresse())


            // Créé directement actif par le super admin
            .actif(true)

            .compteExpire(false)

            .compteVerrouille(false)

            .credentialExpire(false)


            .roles(new HashSet<>())

            .build();



    // Ajout du rôle
    utilisateur.getRoles()
            .add(role);



    utilisateurRepository.save(utilisateur);



    return AuthenticationReponse.builder()

            .email(utilisateur.getEmail())

            .nom(utilisateur.getNom())

            .prenom(utilisateur.getPrenom())

            .build();

}


    // ==================================================
    // VERIFICATION OTP
    // ==================================================

    public void verifyAccount(
            String email,
            String otp
    ) {


        otpService.verifyOtp(
                email,
                otp
        );


        Utilisateur utilisateur =
                utilisateurRepository.findByEmail(email)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Utilisateur introuvable."
                        )
                );



        utilisateur.setActif(true);


        utilisateurRepository.save(utilisateur);

    }






    // ==================================================
    // LOGIN
    // ==================================================
public AuthenticationReponse authenticate(
        AuthenticationRequest request
) {

    System.out.println("========== LOGIN ==========");
    System.out.println("Email : " + request.getEmail());

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getMotDePasse()
            )
    );

    System.out.println("Authentification réussie !");

    Utilisateur utilisateur =
            utilisateurRepository.findByEmail(
                    request.getEmail()
            )
            .orElseThrow(() ->
                    new RuntimeException(
                            "Utilisateur introuvable."
                    )
            );

    // Génération du JWT
    String jwt =
            jwtService.generateToken(
                    utilisateur
            );

    // Récupération du rôle
    String role =
            utilisateur.getRoles()
                    .stream()
                    .findFirst()
                    .map(r -> r.getRole().name())
                    .orElse("ROLE_USER");

    return AuthenticationReponse.builder()

            .token(jwt)

            .email(utilisateur.getEmail())

            .nom(utilisateur.getNom())

            .prenom(utilisateur.getPrenom())

            .role(role)

            .build();
}



    // ==================================================
    // MOT DE PASSE OUBLIE
    // ==================================================

    public void forgotPassword(
            ForgotPassWordRequest request
    ) {


        Utilisateur utilisateur =
                utilisateurRepository.findByEmail(
                        request.getEmail()
                )

                .orElseThrow(() ->
                        new RuntimeException(
                                "Email inconnu."
                        )
                );



        otpService.generateOtp(
                utilisateur.getEmail()
        );

    }







    // ==================================================
    // RESET PASSWORD
    // ==================================================

    public void resetPassword(
            ResetPassWordRequest request
    ) {



        if(!request.getNouveauMotDePasse()
                .equals(
                 request.getConfirmationMotDePasse()
                )) {


            throw new RuntimeException(
                    "Les mots de passe ne correspondent pas."
            );
        }




        otpService.verifyOtp(

                request.getEmail(),

                request.getOtp()

        );




        Utilisateur utilisateur =

                utilisateurRepository.findByEmail(
                        request.getEmail()
                )

                .orElseThrow(() ->
                        new RuntimeException(
                                "Utilisateur introuvable."
                        )
                );




        utilisateur.setMotDePasse(

                passwordEncoder.encode(

                        request.getNouveauMotDePasse()

                )

        );



        utilisateurRepository.save(utilisateur);



        otpService.deleteOtp(
                request.getEmail()
        );


        emailService.sendPasswordChangedEmail(
                request.getEmail()
        );


    }






    // ==================================================
    // VALIDATION JWT
    // ==================================================

    public boolean validateToken(
            String token
    ) {


        try {


            String email =
                    jwtService.extractUsername(token);



            Utilisateur utilisateur =
                    utilisateurRepository.findByEmail(email)
                    .orElse(null);



            return utilisateur != null
                    &&
                    jwtService.isTokenValid(
                            token,
                            utilisateur
                    );


        }catch(Exception e){

            return false;

        }

    }

// ==================================================
// RENVOYER OTP
// ==================================================

public void resendOtp(String email) {


    Utilisateur utilisateur =
            utilisateurRepository.findByEmail(email)

            .orElseThrow(() ->
                    new RuntimeException(
                            "Utilisateur introuvable."
                    )
            );


    // Vérifier si le compte est déjà activé
    if (utilisateur.isActif()) {

        throw new RuntimeException(
                "Ce compte est déjà activé."
        );
    }


    // Générer et envoyer un nouveau OTP
    otpService.generateOtp(
            utilisateur.getEmail()
    );

}
}

