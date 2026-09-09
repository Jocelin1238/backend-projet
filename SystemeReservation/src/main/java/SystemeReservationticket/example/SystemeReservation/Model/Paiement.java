package SystemeReservationticket.example.SystemeReservation.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "paiements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Min(1)
@Column(nullable = false)
private Double montant;

    @Column(length = 50)
    private String methode; 
    // ex: MOMO, OM, CARTE
@Column(nullable = false)
private String statut;
    // ex: EN_COURS, REUSSI, ECHEC

    private String referenceTransaction;

    // =========================
    // RESERVATION
    // =========================
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    @JsonIgnore
    private Reservation reservation;

    // =========================
    // AUDIT
    // =========================
    @Column(nullable = false, updatable = false)
    private LocalDateTime datePaiement;

    private LocalDateTime dateModification;

    @PrePersist
    public void prePersist() {
        this.datePaiement = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateModification = LocalDateTime.now();
    }
}