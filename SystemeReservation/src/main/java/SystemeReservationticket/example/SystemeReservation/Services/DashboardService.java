package SystemeReservationticket.example.SystemeReservation.Services;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import SystemeReservationticket.example.SystemeReservation.DTO.DashboardReservationDTO;
import SystemeReservationticket.example.SystemeReservation.DTO.DashboardStats;
import SystemeReservationticket.example.SystemeReservation.DTO.ReservationStatsDTO;
import SystemeReservationticket.example.SystemeReservation.DTO.RevenueStatsDTO;
import SystemeReservationticket.example.SystemeReservation.Enum.RolesTypes;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Model.Voyage;
import SystemeReservationticket.example.SystemeReservation.Repository.PaiementRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.ReservationRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.UtilisateurRepository;
import SystemeReservationticket.example.SystemeReservation.Repository.VoyageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {


    private final UtilisateurRepository utilisateurRepository;
    private final ReservationRepository reservationRepository;
private final VoyageRepository voyageRepository;
private final PaiementRepository paiementRepository;

public List<RevenueStatsDTO> getRevenueStats() {

    List<RevenueStatsDTO> stats =
            paiementRepository
                    .getRevenusParMois()
                    .stream()
                    .map(data -> {

                        Integer mois = ((Number) data[0]).intValue();

                        Double montant =
                                ((Number) data[1]).doubleValue();

                        return new RevenueStatsDTO(
                                getNomMois(mois),
                                montant
                        );

                    })
                    .toList();


    List<RevenueStatsDTO> result =
            new java.util.ArrayList<>();


    for (int i = 1; i <= 12; i++) {

        String nomMois = getNomMois(i);

        RevenueStatsDTO moisTrouve =
                stats.stream()
                        .filter(s ->
                                s.getMois().equals(nomMois)
                        )
                        .findFirst()
                        .orElse(null);


        if (moisTrouve != null) {

            result.add(moisTrouve);

        } else {

            result.add(
                    new RevenueStatsDTO(
                            nomMois,
                            0.0
                    )
            );

        }

    }


    return result;
}


public List<Voyage> getRecentVoyages() {

    return voyageRepository
            .findTop5ByOrderByDateCreationDesc();
}

    public List<Utilisateur> getRecentUsers(){

        return utilisateurRepository
                .findTop5ByOrderByDateCreationDesc();

    }

   public DashboardStats getStats() {

    long utilisateurs =
            utilisateurRepository.count();

    long administrateurs =
            utilisateurRepository
                    .findByRoles_Role(RolesTypes.ROLE_ADMIN)
                    .size();

    long staff =
            utilisateurRepository
                    .findByRoles_Role(RolesTypes.ROLE_STAFF)
                    .size();

    long voyages =
            voyageRepository.count();

    long reservations =
            reservationRepository.count();

    Double revenus =
            paiementRepository.getTotalRevenus();

    if (revenus == null) {
        revenus = 0.0;
    }

    return new DashboardStats(
            utilisateurs,
            administrateurs,
            staff,
            voyages,
            reservations,
            revenus
    );
}
   public List<ReservationStatsDTO> getReservationStats() {

    List<ReservationStatsDTO> stats =
            reservationRepository
                    .countReservationsByMonth()
                    .stream()
                    .map(data -> {

                        Integer mois = (Integer) data[0];
                        Long nombre = (Long) data[1];

                        return new ReservationStatsDTO(
                                getNomMois(mois),
                                nombre
                        );

                    })
                    .toList();


    List<ReservationStatsDTO> result = new java.util.ArrayList<>();


    for (int i = 1; i <= 12; i++) {

        String nomMois = getNomMois(i);

        ReservationStatsDTO moisTrouve =
                stats.stream()
                        .filter(s -> s.getMois().equals(nomMois))
                        .findFirst()
                        .orElse(null);


        if (moisTrouve != null) {

            result.add(moisTrouve);

        } else {

            result.add(
                new ReservationStatsDTO(
                    nomMois,
                    0L
                )
            );

        }

    }


    return result;
}
public List<DashboardReservationDTO> getRecentReservations(){


    return reservationRepository
            .findTop5ByOrderByIdDesc()
            .stream()
            .map(reservation -> {


                String client =
                        reservation.getUtilisateur().getNom()
                        + " "
                        + reservation.getUtilisateur().getPrenom();



                String trajet =
                        reservation.getVoyage()
                        .getTrajet()
                        .getVilleDepart()
                        +
                        " → "
                        +
                        reservation.getVoyage()
                        .getTrajet()
                        .getVilleArrivee();



                return new DashboardReservationDTO(

                        client,

                        trajet,

                        reservation.getStatut()

                );


            })
            .toList();


}



private String getNomMois(Integer mois){
  return switch(mois){

        case 1 -> "Janvier";
        case 2 -> "Février";
        case 3 -> "Mars";
        case 4 -> "Avril";
        case 5 -> "Mai";
        case 6 -> "Juin";
        case 7 -> "Juillet";
        case 8 -> "Août";
        case 9 -> "Septembre";
        case 10 -> "Octobre";
        case 11 -> "Novembre";
        case 12 -> "Décembre";

        default -> "Inconnu";

    };


}
}