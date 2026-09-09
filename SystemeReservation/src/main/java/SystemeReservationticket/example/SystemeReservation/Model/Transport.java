package SystemeReservationticket.example.SystemeReservation.Model;

import SystemeReservationticket.example.SystemeReservation.Enum.TypesTransport;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypesTransport type;

    @Column(nullable = false)
    private Integer capacite;

    @Column(nullable = false, length = 30)
    private String statut;
}