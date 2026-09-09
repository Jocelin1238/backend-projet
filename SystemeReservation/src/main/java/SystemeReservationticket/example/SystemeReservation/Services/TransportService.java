package SystemeReservationticket.example.SystemeReservation.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import SystemeReservationticket.example.SystemeReservation.Model.Transport;
import SystemeReservationticket.example.SystemeReservation.Repository.TransportRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransportService {

    private final TransportRepository transportRepository;

    // Ajouter
    public Transport ajouterTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    // Liste
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    // Trouver par ID
    public Transport getTransportById(Long id) {

        return transportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Transport introuvable avec l'id : " + id
                        )
                );
    }

    // Modifier
    public Transport modifierTransport(
            Long id,
            Transport transport
    ) {

        Transport existant =
                transportRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Transport introuvable avec l'id : " + id
                                )
                        );

        existant.setNom(transport.getNom());
        existant.setType(transport.getType());
        existant.setCapacite(transport.getCapacite());
        existant.setStatut(transport.getStatut());

        return transportRepository.save(existant);
    }

    // Supprimer
    public void supprimerTransport(Long id) {

        Transport transport =
                transportRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Transport introuvable avec l'id : " + id
                                )
                        );

        transportRepository.delete(transport);
    }
}