package SystemeReservationticket.example.SystemeReservation.Controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import SystemeReservationticket.example.SystemeReservation.Model.Paiement;
import SystemeReservationticket.example.SystemeReservation.Services.PaiementService;



@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaiementController {



    private final PaiementService paiementService;



    // ==================================
    // CREER UN PAIEMENT
    // ==================================

    @PostMapping("/{reservationId}")
    public ResponseEntity<Paiement> creerPaiement(

            @PathVariable Long reservationId,

            @RequestParam double montant,

            @RequestParam String methode

    ){

        return ResponseEntity.ok(
                paiementService.creerPaiement(
                        reservationId,
                        montant,
                        methode
                )
        );

    }


// ==================================
// MES PAIEMENTS UTILISATEUR
// ==================================

@GetMapping("/mes-paiements")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<List<Paiement>> mesPaiements(
        Authentication authentication
) {

    String email = authentication.getName();

    return ResponseEntity.ok(
            paiementService.getPaiementsUtilisateur(email)
    );

}




    // ==================================
    // LISTE DES PAIEMENTS
    // ==================================

    @GetMapping
    public ResponseEntity<List<Paiement>> getAllPaiements(){

        return ResponseEntity.ok(
                paiementService.getAllPaiements()
        );

    }








    // ==================================
    // PAIEMENT PAR ID
    // ==================================

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(

            @PathVariable Long id

    ){

        return ResponseEntity.ok(
                paiementService.getPaiementById(id)
        );

    }








    // ==================================
    // PAIEMENTS D'UNE RESERVATION
    // ==================================

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<Paiement>> getPaiementsReservation(

            @PathVariable Long reservationId

    ){

        return ResponseEntity.ok(
                paiementService.getPaiementsReservation(
                        reservationId
                )
        );

    }









    // ==================================
    // MODIFIER STATUT PAIEMENT
    // ==================================

    @PutMapping("/{id}/statut")
    public ResponseEntity<Paiement> modifierStatut(

            @PathVariable Long id,

            @RequestParam String statut

    ){

        return ResponseEntity.ok(
                paiementService.modifierStatut(
                        id,
                        statut
                )
        );

    }









    // ==================================
    // RECHERCHE PAR REFERENCE
    // ==================================

    @GetMapping("/reference/{reference}")
    public ResponseEntity<Paiement> chercherReference(

            @PathVariable String reference

    ){

        return ResponseEntity.ok(
                paiementService.chercherParReference(
                        reference
                )
        );

    }







    // ==================================
    // VALIDER UN PAIEMENT
    // GENERATION AUTOMATIQUE DU TICKET QR
    // ==================================

    @PutMapping("/{id}/valider")
    public ResponseEntity<Paiement> validerPaiement(

            @PathVariable Long id

    ){

        return ResponseEntity.ok(

                paiementService.validerPaiement(id)

        );

    }



}