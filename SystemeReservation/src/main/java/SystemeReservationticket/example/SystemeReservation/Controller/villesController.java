package SystemeReservationticket.example.SystemeReservation.Controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SystemeReservationticket.example.SystemeReservation.Model.villes;
import SystemeReservationticket.example.SystemeReservation.Repository.villesRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/villes")
@RequiredArgsConstructor
public class villesController {

    private final villesRepository villeRepository;

    @GetMapping
    public List<villes> getVilles() {

        return villeRepository
                .findByActifTrueOrderByNomAsc();

    }
}