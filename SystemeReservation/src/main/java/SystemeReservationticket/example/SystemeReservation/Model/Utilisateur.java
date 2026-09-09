package SystemeReservationticket.example.SystemeReservation.Model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String prenom;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    // Le vrai champ enregistré en base
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @NotBlank
    @JsonIgnore
    @Column(nullable = false)
    
    private String motDePasse;

    @Column(length = 20)
    private String telephone;

    @Column(length = 255)
    private String adresse;

    @Column(length = 255)
    private String photo;

    @Builder.Default
    private boolean actif = false;

    @Builder.Default
    private boolean compteVerrouille = false;

    @Builder.Default
    private boolean compteExpire = false;

    @Builder.Default
    private boolean credentialExpire = false;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "utilisateur_roles",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private Set<Notification> notifications = new HashSet<>();

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

    // =========================
    // Jackson (Angular)
    // =========================

    @JsonProperty("password")
public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
}

public String getMotDePasse() {
    return motDePasse;
}
    // =========================
    // Spring Security
    // =========================

    @Override
    @JsonIgnore
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return !compteExpire;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !compteVerrouille;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpire;
    }

    @Override
    public boolean isEnabled() {
        return actif;
    }
}