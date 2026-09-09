

package SystemeReservationticket.example.SystemeReservation.Services;

import SystemeReservationticket.example.SystemeReservation.DTO.StaffDashboardDTO;
import SystemeReservationticket.example.SystemeReservation.DTO.StaffReservationDTO;
import SystemeReservationticket.example.SystemeReservation.DTO.StaffVoyageDTO;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Repository.UtilisateurRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffDashboardService {

    private final StaffVoyageService staffVoyageService;

    private final StaffReservationService staffReservationService;

    private final StaffTicketService staffTicketService;

    private final UtilisateurRepository utilisateurRepository;


    // =====================================================
    // DASHBOARD STAFF
    // =====================================================

    public StaffDashboardDTO getDashboard() {

        // =================================================
        // 1. RÉCUPÉRER LE STAFF CONNECTÉ
        // =================================================

        String prenomStaff =
                recupererPrenomUtilisateurConnecte();


        // =================================================
        // 2. NOMBRE TOTAL DE VOYAGES
        // =================================================

        long nombreVoyages =
                staffVoyageService
                        .getNombreVoyages();


        // =================================================
        // 3. PROCHAINS DÉPARTS
        // =================================================

        List<StaffVoyageDTO> prochainsDeparts =
                staffVoyageService
                        .getProchainsDeparts();


        // =================================================
        // 4. NOMBRE DE DÉPARTS À VENIR
        // =================================================

        long nombreDeparts =
                staffVoyageService
                        .getNombreDeparts();


        // =================================================
        // 5. NOMBRE TOTAL DE RÉSERVATIONS
        // =================================================

        long nombreReservations =
                staffReservationService
                        .getNombreReservations();


        // =================================================
        // 6. RÉSERVATIONS RÉCENTES
        // =================================================

        List<StaffReservationDTO> reservationsRecentes =
                staffReservationService
                        .getReservationsRecentes();


        // =================================================
        // 7. TICKETS SCANNÉS
        // =================================================

        long ticketsScannes =
                staffTicketService
                        .getNombreTicketsScannes();


        // =================================================
        // 8. CONSTRUCTION DU DASHBOARD
        // =================================================

        return StaffDashboardDTO.builder()

                .prenomStaff(
                        prenomStaff
                )

                .nombreVoyages(
                        nombreVoyages
                )

                .nombreReservations(
                        nombreReservations
                )

                .nombreDeparts(
                        nombreDeparts
                )

                .ticketsScannes(
                        ticketsScannes
                )

                .prochainsDeparts(
                        prochainsDeparts
                )

                .reservationsRecentes(
                        reservationsRecentes
                )

                .build();
    }


    // =====================================================
    // RÉCUPÉRER LE PRÉNOM DU STAFF CONNECTÉ
    // =====================================================

    private String recupererPrenomUtilisateurConnecte() {

        // =================================================
        // RÉCUPÉRER L'AUTHENTIFICATION
        // =================================================

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        // =================================================
        // VÉRIFIER L'AUTHENTIFICATION
        // =================================================

        if (
                authentication == null ||
                !authentication.isAuthenticated()
        ) {

            return "Staff";
        }


        // =================================================
        // RÉCUPÉRER L'EMAIL
        // =================================================

        String email =
                authentication.getName();


        if (
                email == null ||
                email.isBlank()
        ) {

            return "Staff";
        }


        // =================================================
        // RECHERCHER L'UTILISATEUR
        // =================================================

        Utilisateur utilisateur =
                utilisateurRepository
                        .findByEmail(email)
                        .orElse(null);


        // =================================================
        // UTILISATEUR INTROUVABLE
        // =================================================

        if (utilisateur == null) {

            return "Staff";
        }


        // =================================================
        // RÉCUPÉRER LE PRÉNOM
        // =================================================

        String prenom =
                utilisateur.getPrenom();


        if (
                prenom == null ||
                prenom.isBlank()
        ) {

            return "Staff";
        }


        return prenom;
    }
}
