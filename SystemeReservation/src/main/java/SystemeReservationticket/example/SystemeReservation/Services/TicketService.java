package SystemeReservationticket.example.SystemeReservation.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import SystemeReservationticket.example.SystemeReservation.Model.Reservation;
import SystemeReservationticket.example.SystemeReservation.Model.Ticket;
import SystemeReservationticket.example.SystemeReservation.Repository.ReservationRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.TicketRepository;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    private final QRCodeService qrCodeService;


    // =================================================
    // CREER UN TICKET AVEC QR CODE
    // =================================================

    public Ticket creerTicket(Long reservationId) {

        Reservation reservation =
                reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                    new RuntimeException("Réservation introuvable")
                );

        ticketRepository.findByReservationId(reservationId)
                .ifPresent(ticket -> {
                    throw new RuntimeException(
                        "Cette réservation possède déjà un ticket"
                    );
                });

        String codeTicket = UUID.randomUUID().toString();

        String qrCode =
                qrCodeService.genererQRCode(codeTicket);

        Ticket ticket = Ticket.builder()
                .codeTicket(codeTicket)
                .qrCode(qrCode)
                .statut("VALIDE")
                .reservation(reservation)
                .build();

        return ticketRepository.save(ticket);
    }


    // =================================================
    // LISTE DES TICKETS
    // =================================================

    public List<Ticket> getAllTickets() {

        return ticketRepository.findAll();
    }


    // =================================================
    // RECHERCHER PAR ID
    // =================================================

    public Ticket getTicketById(Long id) {

        return ticketRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Ticket introuvable")
                );
    }


    // =================================================
    // RECHERCHER PAR CODE
    // =================================================

    public Ticket getTicketByCode(String code) {

        return ticketRepository.findByCodeTicket(code)
                .orElseThrow(() ->
                    new RuntimeException("Ticket introuvable")
                );
    }


    // =================================================
    // VERIFIER QR CODE
    // =================================================

    public Ticket verifierTicket(String code) {

        System.out.println("Code reçu : " + code);

        Ticket ticket =
                ticketRepository
                .findByCodeTicketWithDetails(code)
                .orElseThrow(() ->
                    new RuntimeException("QR Code invalide")
                );

        System.out.println(
                "Ticket trouvé : " + ticket.getId()
        );

        return ticket;
    }


    // =================================================
    // SCANNER / VALIDER QR CODE
    // =================================================

    public Ticket scannerTicket(String code) {

        Ticket ticket =
                ticketRepository
                .findByCodeTicketWithDetails(code)
                .orElseThrow(() ->
                    new RuntimeException("QR Code invalide")
                );


        // Ticket annulé
        if ("ANNULE".equals(ticket.getStatut())) {

            throw new RuntimeException(
                    "Ticket annulé"
            );
        }


        // Ticket déjà utilisé
        if ("UTILISE".equals(ticket.getStatut())) {

            throw new RuntimeException(
                    "Ticket déjà utilisé"
            );
        }


        // Ticket différent de VALIDE
        if (!"VALIDE".equals(ticket.getStatut())) {

            throw new RuntimeException(
                    "Ticket non valide"
            );
        }


        // Validation
        ticket.setStatut("UTILISE");

        return ticketRepository.save(ticket);
    }


    // =================================================
    // UTILISER PAR ID
    // =================================================

    public Ticket utiliserTicket(Long id) {

        Ticket ticket = getTicketById(id);

        if ("ANNULE".equals(ticket.getStatut())) {

            throw new RuntimeException(
                    "Ticket annulé"
            );
        }

        ticket.setStatut("UTILISE");

        return ticketRepository.save(ticket);
    }


    // =================================================
    // ANNULER TICKET
    // =================================================

    public Ticket annulerTicket(Long id) {

        Ticket ticket = getTicketById(id);

        ticket.setStatut("ANNULE");

        return ticketRepository.save(ticket);
    }


    // =================================================
    // SUPPRIMER TICKET
    // =================================================

    public void supprimerTicket(Long id) {

        Ticket ticket = getTicketById(id);

        ticketRepository.delete(ticket);
    }
    // =================================================
// MES TICKETS
// =================================================
public List<Ticket> mesTickets(Long utilisateurId) {

    return ticketRepository.findByReservationUtilisateurId(utilisateurId);
}
}