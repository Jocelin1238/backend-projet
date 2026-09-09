package SystemeReservationticket.example.SystemeReservation.Services;




import SystemeReservationticket.example.SystemeReservation.Model.Confort;
import SystemeReservationticket.example.SystemeReservation.Repository.ConfortRepository;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class ConfortService {

    private final ConfortRepository confortRepository;

    public Confort ajouterConfort(Confort confort) {
        return confortRepository.save(confort);
    }

    public List<Confort> getAllConforts() {
        return confortRepository.findAll();
    }

    public Confort getConfortById(Long id) {
        return confortRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Confort introuvable"));
    }

    public void supprimerConfort(Long id) {
        confortRepository.deleteById(id);
    }
}