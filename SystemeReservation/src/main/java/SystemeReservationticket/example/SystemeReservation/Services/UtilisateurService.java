package SystemeReservationticket.example.SystemeReservation.Services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.io.IOException;
import java.nio.file.Path;


import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

import SystemeReservationticket.example.SystemeReservation.DTO.RegisterRequest;
import SystemeReservationticket.example.SystemeReservation.Enum.RolesTypes;
import SystemeReservationticket.example.SystemeReservation.Model.Role;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Repository.RoleRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.UtilisateurRepository;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // =========================================================
    // RÉCUPÉRER L'UTILISATEUR CONNECTÉ
    // =========================================================

    private Utilisateur getUtilisateurConnecte() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
            !authentication.isAuthenticated()) {

            throw new RuntimeException(
                "Utilisateur non authentifié"
            );
        }

        String email = authentication.getName();

        return utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Utilisateur connecté introuvable"
                    )
                );
    }

    // =========================================================
    // VÉRIFIER LE RÔLE DE L'UTILISATEUR CONNECTÉ
    // =========================================================

    private boolean estSuperAdmin(Utilisateur utilisateur) {

        return utilisateur.getRoles()
                .stream()
                .anyMatch(role ->
                    role.getRole()
                        == RolesTypes.ROLE_SUPER_ADMIN
                );
    }

    private boolean estAdmin(Utilisateur utilisateur) {

        return utilisateur.getRoles()
                .stream()
                .anyMatch(role ->
                    role.getRole()
                        == RolesTypes.ROLE_ADMIN
                );
    }

    // =========================================================
    // RÉCUPÉRER LE RÔLE D'UN UTILISATEUR
    // =========================================================

    private RolesTypes getRoleUtilisateur(
            Utilisateur utilisateur
    ) {

        if (utilisateur.getRoles() == null ||
            utilisateur.getRoles().isEmpty()) {

            return RolesTypes.ROLE_USER;
        }

        return utilisateur.getRoles()
                .iterator()
                .next()
                .getRole();
    }

    // =========================================================
    // VÉRIFIER SI UN RÔLE EST PROTÉGÉ
    // =========================================================

    private boolean roleProtege(
            RolesTypes role
    ) {

        return role == RolesTypes.ROLE_ADMIN
                || role == RolesTypes.ROLE_SUPER_ADMIN;
    }

    // =========================================================
    // RÉCUPÉRER TOUS LES UTILISATEURS
    // =========================================================

    public List<Utilisateur> getAllUsers() {

        return utilisateurRepository.findAll();
    }

    // =========================================================
    // RÉCUPÉRER LES STAFF
    // =========================================================

    public List<Utilisateur> getStaff() {

        return utilisateurRepository
                .findByRoles_Role(
                    RolesTypes.ROLE_STAFF
                );
    }

    // =========================================================
    // CRÉER UTILISATEUR
    // =========================================================

    public Utilisateur saveUser(
            Utilisateur utilisateur
    ) {

        Utilisateur createur =
                getUtilisateurConnecte();

        // -----------------------------------------
        // Rôle demandé
        // -----------------------------------------

        RolesTypes roleType =
                RolesTypes.ROLE_USER;

        if (utilisateur.getRoles() != null
                && !utilisateur.getRoles().isEmpty()) {

            roleType = utilisateur.getRoles()
                    .iterator()
                    .next()
                    .getRole();
        }

        // -----------------------------------------
        // ADMIN
        // -----------------------------------------

        if (estAdmin(createur)
                && !estSuperAdmin(createur)
                && roleProtege(roleType)) {

            throw new RuntimeException(
                "Un ADMIN ne peut pas créer un "
                + roleType
            );
        }

        // -----------------------------------------
        // Mot de passe
        // -----------------------------------------

        if (utilisateur.getMotDePasse() == null
                || utilisateur.getMotDePasse().isBlank()) {

            throw new RuntimeException(
                "Le mot de passe est obligatoire"
            );
        }

        utilisateur.setMotDePasse(
            passwordEncoder.encode(
                utilisateur.getMotDePasse()
            )
        );

        // -----------------------------------------
        // Récupérer le rôle depuis la BDD
        // -----------------------------------------

        final RolesTypes selectedRole =
                roleType;

        Role role = roleRepository
                .findByRole(selectedRole)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Rôle introuvable : "
                        + selectedRole
                    )
                );

        // -----------------------------------------
        // Nettoyer les anciens rôles
        // -----------------------------------------

        utilisateur.getRoles().clear();

        utilisateur.getRoles().add(role);

        // -----------------------------------------
        // Sauvegarde
        // -----------------------------------------

        return utilisateurRepository.save(
            utilisateur
        );
    }

    // =========================================================
    // MODIFIER UTILISATEUR
    // =========================================================

    public Utilisateur updateUser(
            Long id,
            RegisterRequest request
    ) {

        Utilisateur utilisateur =
                utilisateurRepository
                    .findById(id)
                    .orElseThrow(() ->
                        new RuntimeException(
                            "Utilisateur introuvable"
                        )
                    );

        Utilisateur modifier =
                getUtilisateurConnecte();

        // =====================================================
        // RÔLE ACTUEL DE LA CIBLE
        // =====================================================

        RolesTypes roleActuel =
                getRoleUtilisateur(utilisateur);

        // =====================================================
        // SI ADMIN
        // =====================================================

        if (estAdmin(modifier)
                && !estSuperAdmin(modifier)) {

            // ---------------------------------------------
            // ADMIN ne peut pas modifier ADMIN
            // ---------------------------------------------

            if (roleActuel
                    == RolesTypes.ROLE_ADMIN) {

                throw new RuntimeException(
                    "Un ADMIN ne peut pas modifier "
                    + "un autre ADMIN"
                );
            }

            // ---------------------------------------------
            // ADMIN ne peut pas modifier SUPER ADMIN
            // ---------------------------------------------

            if (roleActuel
                    == RolesTypes.ROLE_SUPER_ADMIN) {

                throw new RuntimeException(
                    "Un ADMIN ne peut pas modifier "
                    + "un SUPER ADMIN"
                );
            }

            // ---------------------------------------------
            // ADMIN ne peut pas modifier son propre rôle
            // ---------------------------------------------

            if (modifier.getId() != null
                    && modifier.getId().equals(id)
                    && request.getRole() != null) {

                throw new RuntimeException(
                    "Vous ne pouvez pas modifier "
                    + "votre propre rôle"
                );
            }

            // ---------------------------------------------
            // ADMIN ne peut pas attribuer ADMIN
            // ---------------------------------------------

            if (request.getRole() != null
                    && !request.getRole().isBlank()) {

                RolesTypes nouveauRole;

                try {

                    nouveauRole =
                        RolesTypes.valueOf(
                            request.getRole()
                        );

                } catch (IllegalArgumentException e) {

                    throw new RuntimeException(
                        "Rôle invalide : "
                        + request.getRole()
                    );
                }

                if (roleProtege(nouveauRole)) {

                    throw new RuntimeException(
                        "Un ADMIN ne peut pas attribuer "
                        + nouveauRole
                    );
                }
            }
        }

        // =====================================================
        // INFORMATIONS DE BASE
        // =====================================================

        utilisateur.setNom(
            request.getNom()
        );

        utilisateur.setPrenom(
            request.getPrenom()
        );

        utilisateur.setEmail(
            request.getEmail()
        );

        utilisateur.setTelephone(
            request.getTelephone()
        );

        utilisateur.setAdresse(
            request.getAdresse()
        );

        // =====================================================
        // MOT DE PASSE
        // =====================================================

        if (request.getMotDePasse() != null
                && !request.getMotDePasse().isBlank()) {

            utilisateur.setMotDePasse(
                passwordEncoder.encode(
                    request.getMotDePasse()
                )
            );
        }

        // =====================================================
        // MODIFICATION DU RÔLE
        // =====================================================

        if (request.getRole() != null
                && !request.getRole().isBlank()) {

            RolesTypes roleType;

            try {

                roleType =
                    RolesTypes.valueOf(
                        request.getRole()
                    );

            } catch (IllegalArgumentException e) {

                throw new RuntimeException(
                    "Rôle invalide : "
                    + request.getRole()
                );
            }

            // ---------------------------------------------
            // SUPER ADMIN : peut attribuer tous les rôles
            // ---------------------------------------------

            if (estSuperAdmin(modifier)) {

                Role role =
                    roleRepository
                        .findByRole(roleType)
                        .orElseThrow(() ->
                            new RuntimeException(
                                "Rôle introuvable : "
                                + roleType
                            )
                        );

                utilisateur.getRoles().clear();

                utilisateur.getRoles().add(role);
            }

            // ---------------------------------------------
            // ADMIN : uniquement USER / STAFF
            // ---------------------------------------------

            else if (estAdmin(modifier)) {

                if (roleProtege(roleType)) {

                    throw new RuntimeException(
                        "Un ADMIN ne peut pas attribuer "
                        + roleType
                    );
                }

                Role role =
                    roleRepository
                        .findByRole(roleType)
                        .orElseThrow(() ->
                            new RuntimeException(
                                "Rôle introuvable : "
                                + roleType
                            )
                        );

                utilisateur.getRoles().clear();

                utilisateur.getRoles().add(role);
            }
        }

        // =====================================================
        // SAUVEGARDE
        // =====================================================

        return utilisateurRepository.save(
            utilisateur
        );
    }

    // =========================================================
    // RECHERCHE
    // =========================================================

    public List<Utilisateur> rechercherUtilisateurs(
            String keyword
    ) {

        return utilisateurRepository
                .rechercherUtilisateurs(keyword);
    }

    // =========================================================
    // RÉCUPÉRER LES ADMINISTRATEURS
    // =========================================================

    public List<Utilisateur> getAdministrateurs() {

        return utilisateurRepository
                .findByRoles_Role(
                    RolesTypes.ROLE_ADMIN
                );
    }

    // =========================================================
    // SUPPRIMER UTILISATEUR
    // =========================================================

    public void deleteUser(Long id) {

        Utilisateur utilisateur =
                utilisateurRepository
                    .findById(id)
                    .orElseThrow(() ->
                        new RuntimeException(
                            "Utilisateur introuvable"
                        )
                    );

        Utilisateur supprimer =
                getUtilisateurConnecte();

        RolesTypes roleCible =
                getRoleUtilisateur(utilisateur);

        // =====================================================
        // ADMIN
        // =====================================================

        if (estAdmin(supprimer)
                && !estSuperAdmin(supprimer)) {

            // ---------------------------------------------
            // ADMIN ne peut pas supprimer ADMIN
            // ---------------------------------------------

            if (roleCible
                    == RolesTypes.ROLE_ADMIN) {

                throw new RuntimeException(
                    "Un ADMIN ne peut pas supprimer "
                    + "un autre ADMIN"
                );
            }

            // ---------------------------------------------
            // ADMIN ne peut pas supprimer SUPER ADMIN
            // ---------------------------------------------

            if (roleCible
                    == RolesTypes.ROLE_SUPER_ADMIN) {

                throw new RuntimeException(
                    "Un ADMIN ne peut pas supprimer "
                    + "un SUPER ADMIN"
                );
            }
        }

        // =====================================================
        // EMPÊCHER UN ADMIN DE SE SUPPRIMER LUI-MÊME
        // =====================================================

        if (supprimer.getId() != null
                && supprimer.getId().equals(id)) {

            throw new RuntimeException(
                "Vous ne pouvez pas supprimer "
                + "votre propre compte"
            );
        }

        // =====================================================
        // SUPPRESSION
        // =====================================================

        utilisateurRepository.deleteById(id);
    }

    // =========================================================
    // UTILISATEUR PAR EMAIL
    // =========================================================

    public Utilisateur getUserByEmail(
            String email
    ) {

        return utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Utilisateur introuvable : "
                        + email
                    )
                );
    }
    // =========================================================
// MODIFIER LA PHOTO DE PROFIL DE L'UTILISATEUR CONNECTÉ
// =========================================================

public Utilisateur modifierPhotoProfil(MultipartFile fichier) {

    // -----------------------------------------------------
    // Vérifier le fichier
    // -----------------------------------------------------

    if (fichier == null || fichier.isEmpty()) {
        throw new RuntimeException(
            "Veuillez sélectionner une photo"
        );
    }

    // -----------------------------------------------------
    // Vérifier le type du fichier
    // -----------------------------------------------------

    String contentType = fichier.getContentType();

    if (contentType == null ||
        !contentType.startsWith("image/")) {

        throw new RuntimeException(
            "Le fichier doit être une image"
        );
    }

    // -----------------------------------------------------
    // Récupérer l'utilisateur connecté
    // -----------------------------------------------------

    Utilisateur utilisateur = getUtilisateurConnecte();

    try {

        // -------------------------------------------------
        // Dossier de stockage
        // -------------------------------------------------

        Path dossier = Paths.get("uploads/profiles");

        if (!Files.exists(dossier)) {
            Files.createDirectories(dossier);
        }

        // -------------------------------------------------
        // Nom unique du fichier
        // -------------------------------------------------

        String extension = "";

        String nomOriginal = fichier.getOriginalFilename();

        if (nomOriginal != null &&
            nomOriginal.contains(".")) {

            extension =
                nomOriginal.substring(
                    nomOriginal.lastIndexOf(".")
                );
        }

        String nouveauNom =
            UUID.randomUUID().toString()
            + extension;

        Path destination =
            dossier.resolve(nouveauNom);

        // -------------------------------------------------
        // Supprimer l'ancienne photo
        // -------------------------------------------------

        if (utilisateur.getPhoto() != null &&
            !utilisateur.getPhoto().isBlank()) {

            String anciennePhoto =
                utilisateur.getPhoto();

            String ancienNom =
                anciennePhoto.substring(
                    anciennePhoto.lastIndexOf("/") + 1
                );

            Path ancienFichier =
                dossier.resolve(ancienNom);

            try {

                Files.deleteIfExists(
                    ancienFichier
                );

            } catch (Exception e) {

                System.out.println(
                    "Impossible de supprimer l'ancienne photo : "
                    + e.getMessage()
                );
            }
        }

        // -------------------------------------------------
        // Enregistrer la nouvelle photo
        // -------------------------------------------------

        Files.copy(
            fichier.getInputStream(),
            destination,
            StandardCopyOption.REPLACE_EXISTING
        );

        // -------------------------------------------------
        // Enregistrer le chemin en BDD
        // -------------------------------------------------

        utilisateur.setPhoto(
            "/uploads/profiles/" + nouveauNom
        );

        return utilisateurRepository.save(
            utilisateur
        );

    } catch (IOException e) {

        throw new RuntimeException(
            "Erreur lors de l'enregistrement de la photo",
            e
        );
    }
}
}