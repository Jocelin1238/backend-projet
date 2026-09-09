package SystemeReservationticket.example.SystemeReservation.Model;




import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "conforts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Confort {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false, unique = true)
    private String nom;


    // Exemple :
    // ECONOMIQUE
    // VIP
    // PREMIUM



    private String description;



    private Double supplement;



    // Exemple :
    // VIP +10000 FCFA
    // PREMIUM +5000 FCFA



    // =========================
    // RELATION VOYAGE
    // =========================

    @OneToMany(mappedBy = "confort")
    @JsonIgnore
    private Set<Voyage> voyages = new HashSet<>();


}