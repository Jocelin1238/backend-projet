package SystemeReservationticket.example.SystemeReservation.Controller;




import SystemeReservationticket.example.SystemeReservation.Model.Confort;
import SystemeReservationticket.example.SystemeReservation.Services.ConfortService;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/api/confort")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ConfortController {



    private final ConfortService confortService;



    // CREATE

    @PostMapping
    public ResponseEntity<Confort> create(
            @RequestBody Confort confort
    ){

        return ResponseEntity.ok(
                confortService.ajouterConfort(confort)
        );

    }





    // READ ALL

    @GetMapping
    public ResponseEntity<List<Confort>> getAll(){

        return ResponseEntity.ok(
                confortService.getAllConforts()
        );

    }





    // READ ONE

    @GetMapping("/{id}")
    public ResponseEntity<Confort> getById(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                confortService.getConfortById(id)
        );

    }





    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ){

        confortService.supprimerConfort(id);


        return ResponseEntity.ok(
                "Confort supprimée"
        );

    }


}