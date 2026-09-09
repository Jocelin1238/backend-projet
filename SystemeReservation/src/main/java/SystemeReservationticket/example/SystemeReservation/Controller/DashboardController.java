package SystemeReservationticket.example.SystemeReservation.Controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import SystemeReservationticket.example.SystemeReservation.DTO.DashboardReservationDTO;
import SystemeReservationticket.example.SystemeReservation.DTO.RevenueStatsDTO;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Model.Voyage;
import SystemeReservationticket.example.SystemeReservation.Services.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    private final DashboardService dashboardService;


    // =====================================================
    // STATISTIQUES
    // ADMIN + SUPER ADMIN
    // =====================================================

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getStats() {

        return ResponseEntity.ok(
                dashboardService.getStats()
        );
    }


    // =====================================================
    // STATISTIQUES RÉSERVATIONS
    // ADMIN + SUPER ADMIN
    // =====================================================

    @GetMapping("/reservations")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getReservations() {

        return ResponseEntity.ok(
                dashboardService.getReservationStats()
        );
    }


    // =====================================================
    // DERNIÈRES RÉSERVATIONS
    // ADMIN + SUPER ADMIN
    // =====================================================

    @GetMapping("/recent-reservations")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<DashboardReservationDTO>> recentReservations() {

        return ResponseEntity.ok(
                dashboardService.getRecentReservations()
        );
    }


    // =====================================================
    // DERNIERS UTILISATEURS
    // ADMIN + SUPER ADMIN
    // =====================================================

    @GetMapping("/recent-users")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<Utilisateur>> recentUsers() {

        return ResponseEntity.ok(
                dashboardService.getRecentUsers()
        );
    }


    // =====================================================
    // DERNIERS VOYAGES
    // ADMIN + SUPER ADMIN
    // =====================================================

    @GetMapping("/recent-voyages")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<Voyage>> recentVoyages() {

        return ResponseEntity.ok(
                dashboardService.getRecentVoyages()
        );
    }


    // =====================================================
    // REVENUS
    // ADMIN + SUPER ADMIN
    // =====================================================

    @GetMapping("/revenus")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<RevenueStatsDTO>> getRevenueStats() {

        return ResponseEntity.ok(
                dashboardService.getRevenueStats()
        );
    }

}