package SystemeReservationticket.example.SystemeReservation.Controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import SystemeReservationticket.example.SystemeReservation.DTO.StaffVoyageDTO;
import SystemeReservationticket.example.SystemeReservation.Model.Voyage;
import SystemeReservationticket.example.SystemeReservation.Services.VoyageService;
import SystemeReservationticket.example.SystemeReservation.Mapper.StaffVoyageMapper;

@RestController
@RequestMapping("/api/voyages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class VoyageController {

    private final VoyageService voyageService;

    private final StaffVoyageMapper staffVoyageMapper;


    // ============================================
    // AJOUTER UN VOYAGE
    // ============================================

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<Voyage> create(
            @RequestBody Voyage voyage
    ) {

        return ResponseEntity.ok(
                voyageService.ajouterVoyage(voyage)
        );
    }


    // ============================================
    // VOIR TOUS LES VOYAGES
    // STAFF + ADMIN + SUPER ADMIN
    // ============================================

    @GetMapping
    public ResponseEntity<List<StaffVoyageDTO>> getAll() {

        List<Voyage> voyages =
                voyageService.getAllVoyages();

        List<StaffVoyageDTO> result =
                voyages.stream()
                        .map(staffVoyageMapper::toDTO)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


    // ============================================
    // DETAIL D'UN VOYAGE
    // ============================================

    @GetMapping("/{id}")
    public ResponseEntity<StaffVoyageDTO> getById(
            @PathVariable Long id
    ) {

        Voyage voyage =
                voyageService.getVoyageById(id);

        return ResponseEntity.ok(
                staffVoyageMapper.toDTO(voyage)
        );
    }


    // ============================================
    // MODIFIER UN VOYAGE
    // ============================================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<Voyage> update(
            @PathVariable Long id,
            @RequestBody Voyage voyage
    ) {

        return ResponseEntity.ok(
                voyageService.modifierVoyage(
                        id,
                        voyage
                )
        );
    }


    // ============================================
    // SUPPRIMER UN VOYAGE
    // ============================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ) {

        voyageService.supprimerVoyage(id);

        return ResponseEntity.ok(
                "Voyage supprimé"
        );
    }


    // ============================================
    // VOYAGES DU JOUR
    // ============================================

    @GetMapping("/du-jour")
    public ResponseEntity<List<StaffVoyageDTO>> getVoyagesDuJour() {

        List<Voyage> voyages =
                voyageService.getVoyagesDuJour();

        List<StaffVoyageDTO> result =
                voyages.stream()
                        .map(staffVoyageMapper::toDTO)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}