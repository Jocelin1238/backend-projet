
package SystemeReservationticket.example.SystemeReservation.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =========================
    // MESSAGE
    // =========================

    @Column(length = 255, nullable = false)
    private String message;


    // =========================
    // TYPE
    // =========================

    @Column(length = 50)
    private String type;

    /*
     * Exemples :
     *
     * RESERVATION
     * PAIEMENT
     * VOYAGE
     * UTILISATEUR
     * SYSTEME
     */


    // =========================
    // STATUT
    // =========================

    @Builder.Default
    @Column(nullable = false)
    private boolean lu = false;


    // =========================
    // UTILISATEUR
    // =========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "utilisateur_id",
        nullable = false
    )
    private Utilisateur utilisateur;


    // =========================
    // DATE CREATION
    // =========================

    @Column(
        nullable = false,
        updatable = false
    )
    private LocalDateTime dateCreation;


    // =========================
    // DATE MODIFICATION
    // =========================

    private LocalDateTime dateModification;


    // =========================
    // PRE-PERSIST
    // =========================

    @PrePersist
    public void prePersist() {

        LocalDateTime maintenant =
            LocalDateTime.now();

        this.dateCreation = maintenant;

        this.dateModification = maintenant;
    }


    // =========================
    // PRE-UPDATE
    // =========================

    @PreUpdate
    public void preUpdate() {

        this.dateModification =
            LocalDateTime.now();
    }

}

