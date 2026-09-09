package SystemeReservationticket.example.SystemeReservation.Mapper;


import SystemeReservationticket.example.SystemeReservation.DTO.StaffTicketDTO;
import SystemeReservationticket.example.SystemeReservation.Model.Reservation;
import SystemeReservationticket.example.SystemeReservation.Model.Ticket;
import SystemeReservationticket.example.SystemeReservation.Model.Trajet;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Model.Voyage;

import org.springframework.stereotype.Component;

@Component
public class StaffTicketMapper {


    // =====================================================
    // TICKET -> DTO
    // =====================================================

    public StaffTicketDTO toDTO(Ticket ticket) {

        if (ticket == null) {
            return null;
        }


        StaffTicketDTO dto =
                new StaffTicketDTO();


        // =================================================
        // TICKET
        // =================================================

        dto.setId(
                ticket.getId()
        );

        dto.setCodeTicket(
                ticket.getCodeTicket()
        );

        dto.setStatut(
                ticket.getStatut()
        );

        dto.setQrCode(
                ticket.getQrCode()
        );

        dto.setDateCreation(
                ticket.getDateCreation()
        );

        dto.setDateModification(
                ticket.getDateModification()
        );


        // =================================================
        // RESERVATION
        // =================================================

        Reservation reservation =
                ticket.getReservation();

        if (reservation == null) {
            return dto;
        }


        dto.setReservationId(
                reservation.getId()
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

        if (voyage == null) {
            return dto;
        }


        dto.setNumeroVoyage(
                voyage.getNumeroVoyage()
        );


        if (voyage.getDateDepart() != null) {

            dto.setDateDepart(
                    voyage.getDateDepart().toString()
            );
        }


        if (voyage.getHeureDepart() != null) {

            dto.setHeureDepart(
                    voyage.getHeureDepart().toString()
            );
        }


        // =================================================
        // TRAJET
        // =================================================

        Trajet trajet =
                voyage.getTrajet();

        if (trajet != null) {

            dto.setVilleDepart(
                    trajet.getVilleDepart()
            );

            dto.setVilleArrivee(
                    trajet.getVilleArrivee()
            );
        }


        return dto;
    }
}