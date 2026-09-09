package SystemeReservationticket.example.SystemeReservation.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime dateReservation;


    private String statut;
    // EN_ATTENTE, CONFIRMEE, ANNULEE


    // ==========================
    // RELATION VOYAGE
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voyage_id")
    private Voyage voyage;




    // ==========================
// RELATION UTILISATEUR
// ==========================

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "utilisateur_id")
private Utilisateur utilisateur;
@OneToMany(mappedBy = "reservation",
           cascade = CascadeType.ALL)
private Set<Paiement> paiements = new HashSet<>();
}