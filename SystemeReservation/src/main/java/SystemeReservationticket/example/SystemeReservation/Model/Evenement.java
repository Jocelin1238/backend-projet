package SystemeReservationticket.example.SystemeReservation.Model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "evenements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String titre;

    @Column(length = 1000)
    private String description;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String lieu;

    @NotNull
    private LocalDate dateEvenement;

    @NotNull
    private LocalDateTime heureDebut;

    @Min(0)
    private int prix;

    @Min(1)
    private int nombrePlaces;

    private String image;

    @Builder.Default
    private boolean actif = true;

    // =========================
    // RELATION CATEGORIE
    // =========================
    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    // =========================
    // AUDIT
    // =========================
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    private LocalDateTime dateModification;

    @PrePersist
    public void prePersist() {
        this.dateCreation = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateModification = LocalDateTime.now();
    }
}