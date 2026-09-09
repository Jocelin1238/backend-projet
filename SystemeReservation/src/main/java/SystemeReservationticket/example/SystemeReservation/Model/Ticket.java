package SystemeReservationticket.example.SystemeReservation.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, length = 100)
    private String codeTicket;


    @Column(length = 50)
    private String statut;
    // VALIDE, UTILISE, ANNULE



    // =========================
    // QR CODE
    // =========================
    @Column(length = 255)
    private String qrCode;



    // =========================
    // RESERVATION
    // =========================
    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;



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