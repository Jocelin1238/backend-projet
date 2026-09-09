package SystemeReservationticket.example.SystemeReservation.Services;

import SystemeReservationticket.example.SystemeReservation.DTO.StaffVoyageDTO;
import SystemeReservationticket.example.SystemeReservation.Mapper.StaffVoyageMapper;
import SystemeReservationticket.example.SystemeReservation.Model.Voyage;
import SystemeReservationticket.example.SystemeReservation.Repository.VoyageRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffVoyageService {

    private final VoyageRepository voyageRepository;

    private final StaffVoyageMapper staffVoyageMapper;


    // =====================================================
    // TOUS LES VOYAGES
    // =====================================================

    public List<StaffVoyageDTO> getAllVoyages() {

       return voyageRepository
        .findAllWithDetails()
        .stream()
        .map(staffVoyageMapper::toDTO)
        .toList();
    }


    // =====================================================
    // NOMBRE TOTAL DE VOYAGES
    // =====================================================

    public long getNombreVoyages() {

        return voyageRepository.count();
    }


    // =====================================================
    // VOYAGES DU JOUR
    // =====================================================

    public List<StaffVoyageDTO> getVoyagesAujourdhui() {

        LocalDate aujourdHui = LocalDate.now();

        return voyageRepository
                .findByDateDepartOrderByHeureDepartAsc(aujourdHui)
                .stream()
                .map(staffVoyageMapper::toDTO)
                .toList();
    }


    // =====================================================
    // VOYAGES D'UNE DATE
    // =====================================================

    public List<StaffVoyageDTO> getVoyagesParDate(
            LocalDate date
    ) {

        if (date == null) {
            return List.of();
        }

        return voyageRepository
                .findByDateDepartOrderByHeureDepartAsc(date)
                .stream()
                .map(staffVoyageMapper::toDTO)
                .toList();
    }


    // =====================================================
    // NOMBRE DE VOYAGES DU JOUR
    // =====================================================

    public long getNombreVoyagesAujourdhui() {

        return voyageRepository.countByDateDepart(
                LocalDate.now()
        );
    }


    // =====================================================
    // PROCHAINS DÉPARTS
    // =====================================================

    public List<StaffVoyageDTO> getProchainsDeparts() {

        LocalDateTime maintenant =
                LocalDateTime.now();

        return voyageRepository
                .findAll()
                .stream()

                // -------------------------------------------------
                // Vérifier la date et l'heure
                // -------------------------------------------------

                .filter(voyage ->
                        voyage.getDateDepart() != null
                        &&
                        voyage.getHeureDepart() != null
                )

                // -------------------------------------------------
                // Construire date + heure
                // -------------------------------------------------

                .filter(voyage -> {

                    LocalDateTime depart =
                            LocalDateTime.of(
                                    voyage.getDateDepart(),
                                    voyage.getHeureDepart()
                            );

                    return depart.isAfter(maintenant);
                })

                // -------------------------------------------------
                // Exclure les voyages terminés
                // -------------------------------------------------

                .filter(voyage ->
                        voyage.getStatut() == null
                        ||
                        !voyage.getStatut()
                                .equalsIgnoreCase("TERMINE")
                )

                // -------------------------------------------------
                // Trier par date/heure
                // -------------------------------------------------

                .sorted(
                        Comparator.comparing(
                                voyage ->
                                        LocalDateTime.of(
                                                voyage.getDateDepart(),
                                                voyage.getHeureDepart()
                                        )
                        )
                )

                // -------------------------------------------------
                // Les 5 prochains
                // -------------------------------------------------

                .limit(5)

                // -------------------------------------------------
                // Conversion DTO
                // -------------------------------------------------

                .map(staffVoyageMapper::toDTO)

                .toList();
    }


    // =====================================================
    // NOMBRE DE DÉPARTS À VENIR
    // =====================================================

    public long getNombreDeparts() {

        LocalDateTime maintenant =
                LocalDateTime.now();

        return voyageRepository
                .findAll()
                .stream()

                // Date + heure obligatoires
                .filter(voyage ->
                        voyage.getDateDepart() != null
                        &&
                        voyage.getHeureDepart() != null
                )

                // Départ futur
                .filter(voyage -> {

                    LocalDateTime depart =
                            LocalDateTime.of(
                                    voyage.getDateDepart(),
                                    voyage.getHeureDepart()
                            );

                    return depart.isAfter(maintenant);
                })

                // Ne pas compter les terminés
                .filter(voyage ->
                        voyage.getStatut() == null
                        ||
                        !voyage.getStatut()
                                .equalsIgnoreCase("TERMINE")
                )

                .count();
    }


    // =====================================================
    // VOYAGE PAR ID
    // =====================================================

    public StaffVoyageDTO getVoyageById(Long id) {

        if (id == null) {
            return null;
        }

        Voyage voyage =
                voyageRepository
                        .findById(id)
                        .orElse(null);

        if (voyage == null) {
            return null;
        }

        return staffVoyageMapper.toDTO(voyage);
    }
}