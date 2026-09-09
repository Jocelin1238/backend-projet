package SystemeReservationticket.example.SystemeReservation.Services;

import SystemeReservationticket.example.SystemeReservation.DTO.StaffTicketDTO;
import SystemeReservationticket.example.SystemeReservation.Mapper.StaffTicketMapper;
import SystemeReservationticket.example.SystemeReservation.Model.Ticket;
import SystemeReservationticket.example.SystemeReservation.Repository.TicketRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StaffTicketService {


    private final TicketRepository ticketRepository;

    private final StaffTicketMapper staffTicketMapper;


    // =====================================================
    // RECHERCHER PAR CODE
    // =====================================================

    @Transactional(readOnly = true)
    public StaffTicketDTO rechercherParCode(
            String codeTicket
    ) {

        if (codeTicket == null ||
            codeTicket.trim().isEmpty()) {

            return null;
        }


        Ticket ticket =
                ticketRepository
                        .findByCodeTicketWithDetails(
                                codeTicket
                        )
                        .orElse(null);


        if (ticket == null) {
            return null;
        }


        return staffTicketMapper.toDTO(
                ticket
        );
    }


    // =====================================================
    // TICKET PAR ID
    // =====================================================

    @Transactional(readOnly = true)
    public StaffTicketDTO getTicketById(
            Long id
    ) {

        if (id == null) {
            return null;
        }


        Ticket ticket =
                ticketRepository
                        .findByIdWithDetails(id)
                        .orElse(null);


        if (ticket == null) {
            return null;
        }


        return staffTicketMapper.toDTO(
                ticket
        );
    }


    // =====================================================
    // TICKET D'UNE RESERVATION
    // =====================================================

    @Transactional(readOnly = true)
    public StaffTicketDTO getTicketByReservation(
            Long reservationId
    ) {

        if (reservationId == null) {
            return null;
        }


        Ticket ticket =
                ticketRepository
                        .findByReservationId(
                                reservationId
                        )
                        .orElse(null);


        if (ticket == null) {
            return null;
        }


        return staffTicketMapper.toDTO(
                ticket
        );
    }


    // =====================================================
    // NOMBRE DE TICKETS SCANNES
    // =====================================================

    @Transactional(readOnly = true)
    public long getNombreTicketsScannes() {

        return ticketRepository
                .countByStatut("UTILISE");
    }


    // =====================================================
    // SCANNER UN TICKET
    // =====================================================

    public StaffTicketDTO scannerTicket(
            String codeTicket
    ) {

        if (codeTicket == null ||
            codeTicket.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le code du ticket est obligatoire."
            );
        }


        Ticket ticket =
                ticketRepository
                        .findByCodeTicketWithDetails(
                                codeTicket
                        )
                        .orElse(null);


        if (ticket == null) {

            throw new IllegalArgumentException(
                    "Ticket introuvable."
            );
        }


        // =================================================
        // TICKET DEJA UTILISE
        // =================================================

        if ("UTILISE".equalsIgnoreCase(
                ticket.getStatut()
        )) {

            throw new IllegalStateException(
                    "Ce ticket a déjà été utilisé."
            );
        }


        // =================================================
        // TICKET ANNULE
        // =================================================

        if ("ANNULE".equalsIgnoreCase(
                ticket.getStatut()
        )) {

            throw new IllegalStateException(
                    "Ce ticket est annulé."
            );
        }


        // =================================================
        // VALIDATION
        // =================================================

        ticket.setStatut("UTILISE");


        Ticket ticketSauvegarde =
                ticketRepository.save(ticket);


        return staffTicketMapper.toDTO(
                ticketSauvegarde
        );
    }
}