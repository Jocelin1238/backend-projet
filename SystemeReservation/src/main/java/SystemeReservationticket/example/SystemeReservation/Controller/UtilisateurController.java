package SystemeReservationticket.example.SystemeReservation.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import SystemeReservationticket.example.SystemeReservation.DTO.AuthenticationReponse;
import SystemeReservationticket.example.SystemeReservation.DTO.RegisterRequest;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Services.AuthenticationService;
import SystemeReservationticket.example.SystemeReservation.Services.UtilisateurService;
import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    private final AuthenticationService authenticationService;
 @GetMapping("/admins")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public ResponseEntity<List<Utilisateur>> getAdmins() {

    return ResponseEntity.ok(
        utilisateurService.getAdministrateurs()
    );

}
@GetMapping("/staff")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public ResponseEntity<List<Utilisateur>> getStaff() {

    return ResponseEntity.ok(
            utilisateurService.getStaff()
    );

}
    // ==========================================
    // LISTE UTILISATEURS
    // ==========================================

    @GetMapping
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
public ResponseEntity<List<Utilisateur>> getUsers(){
        return ResponseEntity.ok(
                utilisateurService.getAllUsers()
        );

    }
    @GetMapping("/recherche")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public ResponseEntity<List<Utilisateur>> rechercherUtilisateurs(

        @RequestParam String keyword

){

    return ResponseEntity.ok(

            utilisateurService
                    .rechercherUtilisateurs(keyword)

    );

}

    // ==========================================
    // CREATION UTILISATEUR PAR SUPER ADMIN
    // ==========================================
@PostMapping
@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AuthenticationReponse> createUser(
            @RequestBody RegisterRequest request
    ) {

        System.out.println("===== CREATION USER SUPER ADMIN =====");

        System.out.println(
                "Email : " + request.getEmail()
        );

        System.out.println(
                "Role : " + request.getRole()
        );

        return ResponseEntity.ok(

                authenticationService.createUserBySuperAdmin(request)

        );

    }

    // ==========================================
    // MODIFICATION UTILISATEUR
    // ==========================================

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Utilisateur> updateUser(

            @PathVariable Long id,

            @RequestBody RegisterRequest request

    ) {

        return ResponseEntity.ok(

                utilisateurService.updateUser(id, request)

        );

    }

    // ==========================================
    // SUPPRESSION UTILISATEUR
    // ==========================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {

        utilisateurService.deleteUser(id);

        return ResponseEntity.ok().build();

    }
// ==========================================
// UTILISATEUR CONNECTÉ
// ==========================================

@GetMapping("/me")
public ResponseEntity<Utilisateur> getUtilisateurConnecte(
        Authentication authentication
) {

    String email = authentication.getName();

    return ResponseEntity.ok(
            utilisateurService.getUserByEmail(email)
    );
}
// ==========================================
// PHOTO DE PROFIL
// ==========================================

@PostMapping("/me/photo")
public ResponseEntity<Utilisateur> modifierPhotoProfil(
        @RequestParam("photo") MultipartFile photo
) {

    return ResponseEntity.ok(
        utilisateurService.modifierPhotoProfil(photo)
    );
}

}