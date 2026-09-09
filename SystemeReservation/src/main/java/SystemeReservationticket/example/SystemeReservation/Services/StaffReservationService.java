
package SystemeReservationticket.example.SystemeReservation.Services;

import SystemeReservationticket.example.SystemeReservation.DTO.StaffReservationDTO;
import SystemeReservationticket.example.SystemeReservation.Mapper.StaffReservationMapper;
import SystemeReservationticket.example.SystemeReservation.Model.Reservation;
import SystemeReservationticket.example.SystemeReservation.Repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StaffReservationService {

    private final ReservationRepository reservationRepository;

    private final StaffReservationMapper staffReservationMapper;


    // =====================================================
    // TOUTES LES RESERVATIONS
    // =====================================================

    @Transactional(readOnly = true)
    public List<StaffReservationDTO> getAllReservations() {

        return reservationRepository
                .findAllWithDetails()
                .stream()
                .map(staffReservationMapper::toDTO)
                .toList();
    }


    // =====================================================
    // NOMBRE TOTAL DE RESERVATIONS
    // =====================================================

    @Transactional(readOnly = true)
    public long getNombreReservations() {

        return reservationRepository.count();
    }


    // =====================================================
    // RESERVATIONS DU JOUR
    // =====================================================

    @Transactional(readOnly = true)
    public List<StaffReservationDTO> getReservationsAujourdhui() {

        LocalDate aujourdHui =
                LocalDate.now();

        LocalDateTime debut =
                aujourdHui.atStartOfDay();

        LocalDateTime fin =
                aujourdHui.atTime(23, 59, 59);

        return reservationRepository
                .findReservationsDuJour(
                        debut,
                        fin
                )
                .stream()
                .map(staffReservationMapper::toDTO)
                .toList();
    }


    // =====================================================
    // 5 DERNIERES RESERVATIONS
    // =====================================================

    @Transactional(readOnly = true)
    public List<StaffReservationDTO> getReservationsRecentes() {

        return reservationRepository
                .findTop5WithDetails()
                .stream()
                .map(staffReservationMapper::toDTO)
                .toList();
    }


    // =====================================================
    // NOMBRE DE RESERVATIONS DU JOUR
    // =====================================================

    @Transactional(readOnly = true)
    public long getNombreReservationsAujourdhui() {

        LocalDate aujourdHui =
                LocalDate.now();

        LocalDateTime debut =
                aujourdHui.atStartOfDay();

        LocalDateTime fin =
                aujourdHui.atTime(23, 59, 59);

        return reservationRepository
                .countByDateReservationBetween(
                        debut,
                        fin
                );
    }


    // =====================================================
    // RESERVATION PAR ID
    // =====================================================

    @Transactional(readOnly = true)
    public StaffReservationDTO getReservationById(
            Long id
    ) {

        if (id == null) {
            return null;
        }

        Reservation reservation =
                reservationRepository
                        .findById(id)
                        .orElse(null);

        if (reservation == null) {
            return null;
        }

        return staffReservationMapper
                .toDTO(reservation);
    }


    // =====================================================
    // RESERVATIONS D'UN UTILISATEUR
    // =====================================================

    @Transactional(readOnly = true)
    public List<StaffReservationDTO> getReservationsUtilisateur(
            Long utilisateurId
    ) {

        if (utilisateurId == null) {
            return List.of();
        }

        return reservationRepository
                .findByUtilisateurId(utilisateurId)
                .stream()
                .map(staffReservationMapper::toDTO)
                .toList();
    }
}

