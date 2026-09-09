package SystemeReservationticket.example.SystemeReservation.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import SystemeReservationticket.example.SystemeReservation.Model.Voyage;
import SystemeReservationticket.example.SystemeReservation.Model.Confort;
import SystemeReservationticket.example.SystemeReservation.Model.Trajet;
import SystemeReservationticket.example.SystemeReservation.Repository.VoyageRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.ConfortRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.TrajetRepository;



@Service
@RequiredArgsConstructor
public class VoyageService {



    private final VoyageRepository voyageRepository;

    private final TrajetRepository trajetRepository;

   private final ConfortRepository confortRepository;




    // ==========================
    // AJOUT VOYAGE
    // ==========================
public Voyage ajouterVoyage(Voyage voyage) {

    // Vérification du trajet
    if (voyage.getTrajet() == null || voyage.getTrajet().getId() == null) {
        throw new RuntimeException("Veuillez sélectionner un trajet.");
    }

    Trajet trajet = trajetRepository.findById(voyage.getTrajet().getId())
            .orElseThrow(() -> new RuntimeException("Trajet introuvable"));

    voyage.setTrajet(trajet);

    // Vérification du confort
    if (voyage.getConfort() != null && voyage.getConfort().getId() != null) {

        Confort confort = confortRepository.findById(voyage.getConfort().getId())
                .orElseThrow(() -> new RuntimeException("Confort introuvable"));

        voyage.setConfort(confort);
    } else {
        voyage.setConfort(null);
    }

    return voyageRepository.save(voyage);
}




    // ==========================
    // LISTE VOYAGES
    // ==========================

    public List<Voyage> getAllVoyages(){


        return voyageRepository.findAll();


    }








    // ==========================
    // DETAIL VOYAGE
    // ==========================

    public Voyage getVoyageById(Long id){


        return voyageRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Voyage introuvable"
                        )
                );


    }









    // ==========================
    // MODIFIER VOYAGE
    // ==========================

    public Voyage modifierVoyage(
            Long id,
            Voyage nouveauVoyage
    ){



        Voyage voyage = getVoyageById(id);




        voyage.setNumeroVoyage(
                nouveauVoyage.getNumeroVoyage()
        );



        voyage.setDateDepart(
                nouveauVoyage.getDateDepart()
        );



        voyage.setHeureDepart(
                nouveauVoyage.getHeureDepart()
        );



        voyage.setDateArrivee(
                nouveauVoyage.getDateArrivee()
        );



        voyage.setHeureArrivee(
                nouveauVoyage.getHeureArrivee()
        );



        voyage.setPrix(
                nouveauVoyage.getPrix()
        );



        voyage.setNombrePlaces(
                nouveauVoyage.getNombrePlaces()
        );



        voyage.setPlacesDisponibles(
                nouveauVoyage.getPlacesDisponibles()
        );



        voyage.setStatut(
                nouveauVoyage.getStatut()
        );



        voyage.setDescription(
                nouveauVoyage.getDescription()
        );





        // ==========================
        // MODIFICATION TRAJET
        // ==========================

        if(nouveauVoyage.getTrajet() != null
                && nouveauVoyage.getTrajet().getId() != null){



            Trajet trajet = trajetRepository.findById(

                    nouveauVoyage.getTrajet().getId()

            )

            .orElseThrow(() ->

                    new RuntimeException(
                            "Trajet introuvable"
                    )

            );



            voyage.setTrajet(trajet);


        }





        return voyageRepository.save(voyage);


    }









    // ==========================
    // SUPPRESSION
    // ==========================

    public void supprimerVoyage(Long id){


        voyageRepository.deleteById(id);


    }

// ==========================
// VOYAGES DU JOUR
// ==========================

public List<Voyage> getVoyagesDuJour() {

    return voyageRepository.findByDateDepartOrderByHeureDepartAsc(
            LocalDate.now()
    );

}

}