package SystemeReservationticket.example.SystemeReservation.Mapper;

import SystemeReservationticket.example.SystemeReservation.DTO.StaffReservationDTO;
import SystemeReservationticket.example.SystemeReservation.Model.Reservation;
import SystemeReservationticket.example.SystemeReservation.Model.Trajet;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Model.Voyage;

import org.springframework.stereotype.Component;

@Component
public class StaffReservationMapper {

    // =====================================================
    // RESERVATION -> DTO
    // =====================================================

    public StaffReservationDTO toDTO(
            Reservation reservation
    ) {

        if (reservation == null) {
            return null;
        }

        StaffReservationDTO dto =
                new StaffReservationDTO();

        // =================================================
        // RESERVATION
        // =================================================

        dto.setId(
                reservation.getId()
        );

        dto.setDateReservation(
                reservation.getDateReservation()
        );

        dto.setStatut(
                reservation.getStatut()
        );


        // =================================================
        // UTILISATEUR
        // =================================================

        Utilisateur utilisateur =
                reservation.getUtilisateur();

        if (utilisateur != null) {

            dto.setUtilisateurId(
                    utilisateur.getId()
            );

            dto.setNomClient(
                    utilisateur.getNom()
            );

            dto.setPrenomClient(
                    utilisateur.getPrenom()
            );

            dto.setEmailClient(
                    utilisateur.getEmail()
            );
        }


        // =================================================
        // VOYAGE
        // =================================================

        Voyage voyage =
                reservation.getVoyage();

        if (voyage != null) {

            dto.setVoyageId(
                    voyage.getId()
            );

            dto.setNumeroVoyage(
                    voyage.getNumeroVoyage()
            );

            dto.setDateDepart(
                    voyage.getDateDepart() != null
                            ? voyage.getDateDepart().toString()
                            : null
            );

            dto.setHeureDepart(
                    voyage.getHeureDepart() != null
                            ? voyage.getHeureDepart().toString()
                            : null
            );

            dto.setPrix(
                    voyage.getPrix()
            );


            // =============================================
            // TRAJET
            // =============================================

            Trajet trajet =
                    voyage.getTrajet();

            if (trajet != null) {

                dto.setVilleDepart(
                        trajet.getVilleDepart()
                );

                dto.setVilleArrivee(
                        trajet.getVilleArrivee()
                );


                // =========================================
                // TRAJET COMPLET
                // =========================================

                String trajetComplet =
                        trajet.getVilleDepart()
                        + " → "
                        + trajet.getVilleArrivee();

                dto.setTrajet(
                        trajetComplet
                );
            }
        }

        return dto;
    }
}