package SystemeReservationticket.example.SystemeReservation.Mapper;


import SystemeReservationticket.example.SystemeReservation.DTO.StaffVoyageDTO;
import SystemeReservationticket.example.SystemeReservation.Model.Confort;
import SystemeReservationticket.example.SystemeReservation.Model.Trajet;
import SystemeReservationticket.example.SystemeReservation.Model.Transport;
import SystemeReservationticket.example.SystemeReservation.Model.Voyage;

import org.springframework.stereotype.Component;

@Component
public class StaffVoyageMapper {

    // =====================================================
    // VOYAGE -> DTO
    // =====================================================

    public StaffVoyageDTO toDTO(Voyage voyage) {

        if (voyage == null) {
            return null;
        }

        StaffVoyageDTO dto =
                new StaffVoyageDTO();

        // =================================================
        // VOYAGE
        // =================================================

        dto.setId(voyage.getId());

        dto.setNumeroVoyage(
                voyage.getNumeroVoyage()
        );

        dto.setDateDepart(
                voyage.getDateDepart()
        );

        dto.setHeureDepart(
                voyage.getHeureDepart()
        );

        dto.setDateArrivee(
                voyage.getDateArrivee()
        );

        dto.setHeureArrivee(
                voyage.getHeureArrivee()
        );

        dto.setPrix(
                voyage.getPrix()
        );

        dto.setNombrePlaces(
                voyage.getNombrePlaces()
        );

        dto.setPlacesDisponibles(
                voyage.getPlacesDisponibles()
        );

        dto.setStatut(
                voyage.getStatut()
        );

        dto.setDescription(
                voyage.getDescription()
        );


        // =================================================
        // TRAJET
        // =================================================

        Trajet trajet =
                voyage.getTrajet();

        if (trajet != null) {

            dto.setTrajetId(
                    trajet.getId()
            );

            dto.setVilleDepart(
                    trajet.getVilleDepart()
            );

            dto.setVilleArrivee(
                    trajet.getVilleArrivee()
            );
        }


        // =================================================
        // CONFORT
        // =================================================

        Confort confort =
                voyage.getConfort();

        if (confort != null) {

            dto.setConfortId(
                    confort.getId()
            );

            /*
             * Adapte cette ligne si ton entité Confort
             * utilise un autre nom d'attribut.
             */
            dto.setConfort(
                    confort.getNom()
            );
        }


        // =================================================
        // TRANSPORT
        // =================================================

        Transport transport =
                voyage.getTransport();

        if (transport != null) {

            dto.setTransportId(
                    transport.getId()
            );

            /*
             * Adapte cette ligne si ton entité Transport
             * utilise un autre nom d'attribut.
             */
            dto.setTransport(
                    transport.getNom()
            );
        }


        return dto;
    }

}
