package SystemeReservationticket.example.SystemeReservation.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SystemeReservationticket.example.SystemeReservation.Model.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {


    /**
     * Recherche un OTP par email et code
     */
    Optional<Otp> findByEmailAndCode(
            String email,
            String code
    );


    /**
     * Supprimer les anciens OTP d'un utilisateur
     */
    void deleteByEmail(
            String email
    );


    /**
     * Trouver le dernier OTP envoyé à un email
     */
    Optional<Otp> findTopByEmailOrderByDateCreationDesc(
            String email
    );


    /**
     * Vérifier si un OTP existe pour un email
     */
    boolean existsByEmail(
            String email
    );

}