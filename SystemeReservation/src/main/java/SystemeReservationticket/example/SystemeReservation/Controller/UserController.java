package SystemeReservationticket.example.SystemeReservation.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @GetMapping("/profil")
    public String profil(){

        return "Profil utilisateur connecté";

    }


    @GetMapping("/reservations")
    public String mesReservations(){

        return "Liste de mes réservations";

    }


    @PutMapping("/profil")
    public String modifierProfil(){

        return "Profil modifié";

    }

}