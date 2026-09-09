package SystemeReservationticket.example.SystemeReservation.Services;

import SystemeReservationticket.example.SystemeReservation.Model.Trajet;
import SystemeReservationticket.example.SystemeReservation.Repository.TrajetRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrajetService {

    private final TrajetRepository trajetRepository;


    // =====================================================
    // AJOUTER UN TRAJET
    // =====================================================

    @Transactional
    public Trajet ajouterTrajet(Trajet trajet) {

        // Vérifier la ville de départ
        if (
            trajet.getVilleDepart() == null ||
            trajet.getVilleDepart().trim().isEmpty()
        ) {

            throw new RuntimeException(
                "La ville de départ est obligatoire."
            );
        }


        // Vérifier la ville d'arrivée
        if (
            trajet.getVilleArrivee() == null ||
            trajet.getVilleArrivee().trim().isEmpty()
        ) {

            throw new RuntimeException(
                "La ville d'arrivée est obligatoire."
            );
        }


        // Départ et arrivée ne doivent pas être identiques
        if (
            trajet.getVilleDepart()
                .trim()
                .equalsIgnoreCase(
                    trajet.getVilleArrivee().trim()
                )
        ) {

            throw new RuntimeException(
                "La ville de départ et la ville d'arrivée doivent être différentes."
            );
        }


        // Nettoyage
        trajet.setVilleDepart(
            trajet.getVilleDepart().trim()
        );

        trajet.setVilleArrivee(
            trajet.getVilleArrivee().trim()
        );


        return trajetRepository.save(trajet);
    }


    // =====================================================
    // LISTE DES TRAJETS
    // =====================================================

    public List<Trajet> getAllTrajets() {

        return trajetRepository.findAll();

    }


    // =====================================================
    // TROUVER UN TRAJET
    // =====================================================

    public Trajet getTrajetById(Long id) {

        return trajetRepository.findById(id)

            .orElseThrow(() ->
                new RuntimeException(
                    "Trajet introuvable avec l'id : " + id
                )
            );

    }


    // =====================================================
    // MODIFIER
    // =====================================================

    @Transactional
    public Trajet modifierTrajet(
        Long id,
        Trajet trajet
    ) {

        Trajet trajetExistant =
            trajetRepository.findById(id)

                .orElseThrow(() ->
                    new RuntimeException(
                        "Trajet introuvable avec l'id : " + id
                    )
                );


        // Validation départ
        if (
            trajet.getVilleDepart() == null ||
            trajet.getVilleDepart().trim().isEmpty()
        ) {

            throw new RuntimeException(
                "La ville de départ est obligatoire."
            );
        }


        // Validation arrivée
        if (
            trajet.getVilleArrivee() == null ||
            trajet.getVilleArrivee().trim().isEmpty()
        ) {

            throw new RuntimeException(
                "La ville d'arrivée est obligatoire."
            );
        }


        // Vérifier départ ≠ arrivée
        if (
            trajet.getVilleDepart()
                .trim()
                .equalsIgnoreCase(
                    trajet.getVilleArrivee().trim()
                )
        ) {

            throw new RuntimeException(
                "La ville de départ et la ville d'arrivée doivent être différentes."
            );
        }


        trajetExistant.setVilleDepart(
            trajet.getVilleDepart().trim()
        );

        trajetExistant.setVilleArrivee(
            trajet.getVilleArrivee().trim()
        );

        trajetExistant.setDistance(
            trajet.getDistance()
        );

        trajetExistant.setDuree(
            trajet.getDuree()
        );


        return trajetRepository.save(
            trajetExistant
        );
    }


    // =====================================================
    // SUPPRIMER
    // =====================================================

    @Transactional
    public void supprimerTrajet(Long id) {

        if (!trajetRepository.existsById(id)) {

            throw new RuntimeException(
                "Trajet introuvable avec l'id : " + id
            );
        }


        trajetRepository.deleteById(id);

    }

}