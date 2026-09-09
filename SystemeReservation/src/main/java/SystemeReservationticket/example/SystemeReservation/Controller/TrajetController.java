package SystemeReservationticket.example.SystemeReservation.Controller;



import SystemeReservationticket.example.SystemeReservation.Model.Trajet;
import SystemeReservationticket.example.SystemeReservation.Services.TrajetService;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/api/trajets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TrajetController {



    private final TrajetService trajetService;



    // CREATE

    @PostMapping
    public ResponseEntity<Trajet> create(
            @RequestBody Trajet trajet
    ){

        return ResponseEntity.ok(
                trajetService.ajouterTrajet(trajet)
        );

    }




    // READ ALL

    @GetMapping
    public ResponseEntity<List<Trajet>> getAll(){

        return ResponseEntity.ok(
                trajetService.getAllTrajets()
        );

    }




    // READ ONE

    @GetMapping("/{id}")
    public ResponseEntity<Trajet> getById(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                trajetService.getTrajetById(id)
        );

    }


// UPDATE

@PutMapping("/{id}")
public ResponseEntity<Trajet> update(
        @PathVariable Long id,
        @RequestBody Trajet trajet
) {

    return ResponseEntity.ok(
            trajetService.modifierTrajet(id, trajet)
    );
}


    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ){

        trajetService.supprimerTrajet(id);


        return ResponseEntity.ok(
                "Trajet supprimé"
        );

    }

}