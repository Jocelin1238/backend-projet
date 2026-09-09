package SystemeReservationticket.example.SystemeReservation.Controller;

import SystemeReservationticket.example.SystemeReservation.DTO.StaffDashboardDTO;
import SystemeReservationticket.example.SystemeReservation.Services.StaffDashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StaffDashboardController {


    private final StaffDashboardService staffDashboardService;


    // =====================================================
    // DASHBOARD STAFF
    // =====================================================

    @GetMapping
    @PreAuthorize(
        "hasAnyRole('STAFF', 'ADMIN', 'SUPER_ADMIN')"
    )
    public ResponseEntity<StaffDashboardDTO>
    getDashboard() {

        StaffDashboardDTO dashboard =
                staffDashboardService
                        .getDashboard();

        return ResponseEntity.ok(
                dashboard
        );
    }
}
