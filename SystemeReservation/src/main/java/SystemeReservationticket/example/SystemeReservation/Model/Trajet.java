package SystemeReservationticket.example.SystemeReservation.Model;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "trajets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trajet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false)
    private String villeDepart;



    @Column(nullable = false)
    private String villeArrivee;



    private Double distance;



    private Integer duree;



    // Exemple:
    // distance = 250 km
    // duree = 300 minutes



    // ==========================
    // RELATION VOYAGE
    // ==========================

    @OneToMany(mappedBy = "trajet")
    @JsonIgnore
    private Set<Voyage> voyages= new HashSet<>();


}