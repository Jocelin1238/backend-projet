package SystemeReservationticket.example.SystemeReservation.Model;




import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity

@Table(name = "voyages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroVoyage;

    @Column(nullable = false)
    private LocalDate dateDepart;

    @Column(nullable = false)
    private LocalTime heureDepart;

    private LocalDate dateArrivee;

    private LocalTime heureArrivee;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = false)
    private Integer nombrePlaces;

    @Column(nullable = false)
    private Integer placesDisponibles;

    private String statut;

    private String description;


    // ==========================
    // RELATION TRAJET
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;



    // ==========================
    // RELATION CONFORT
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confort_id")
    private Confort confort;


// ==========================
// RELATION TRANSPORT
// ==========================

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "transport_id")
private Transport transport;

    // ==========================
    // RELATION RESERVATIONS
    // ==========================

    @OneToMany(mappedBy = "voyage")
    @JsonIgnore
    private Set<Reservation> reservations = new HashSet<>();


    // ==========================
    // AUDIT
    // ==========================

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    private LocalDateTime dateModification;


    @PrePersist
    public void prePersist() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }


    @PreUpdate
    public void preUpdate() {
        dateModification = LocalDateTime.now();
    }
}