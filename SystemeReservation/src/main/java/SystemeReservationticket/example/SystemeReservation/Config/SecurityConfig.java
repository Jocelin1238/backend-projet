package SystemeReservationticket.example.SystemeReservation.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import SystemeReservationticket.example.SystemeReservation.Security.JwtAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserDetailsService userDetailsService;

private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            UserDetailsService userDetailsService,
             CorsConfigurationSource corsConfigurationSource
    ) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
          this.corsConfigurationSource = corsConfigurationSource;


    }




@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http
) throws Exception {


    http

        .cors(cors ->
            cors.configurationSource(corsConfigurationSource)
        )


        .csrf(csrf ->
            csrf.disable()
        )


        .sessionManagement(session ->
            session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
            )
        )

.authorizeHttpRequests(auth -> auth



    // ==========================
    // PUBLIC
    // ==========================
    .requestMatchers(
        "/api/auth/**",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    ).permitAll()

    // ==========================
    // UTILISATEUR CONNECTÉ (Profil & Photo)
    // Doit être placé AVANT la gestion globale des utilisateurs !
    // ==========================
    .requestMatchers(
        "/api/utilisateurs/me",
        "/api/utilisateurs/me/**" // 👈 Débloque POST /api/utilisateurs/me/photo
    ).authenticated()

    // =========================================================
    // UTILISATEURS - GESTION GLOBALE ADMIN
    // =========================================================
    .requestMatchers(HttpMethod.GET, "/api/utilisateurs/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
    .requestMatchers(HttpMethod.POST, "/api/utilisateurs/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
    .requestMatchers(HttpMethod.PUT, "/api/utilisateurs/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
    .requestMatchers(HttpMethod.DELETE, "/api/utilisateurs/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

// ==========================
// GESTION DES COMPTES
// SUPER ADMIN SEULEMENT
// ==========================
// =========================================================
// UTILISATEURS - CONSULTATION
// SUPER_ADMIN + ADMIN
// =========================================================
.requestMatchers(
    HttpMethod.GET,
    "/api/utilisateurs/**"
)
.hasAnyRole(
    "SUPER_ADMIN",
    "ADMIN"
)

// =========================================================
// UTILISATEURS - CRÉATION
// SUPER_ADMIN + ADMIN
// Le service empêchera l'ADMIN de créer un SUPER_ADMIN/ADMIN
// =========================================================
.requestMatchers(
    HttpMethod.POST,
    "/api/utilisateurs/**"
)
.hasAnyRole(
    "SUPER_ADMIN",
    "ADMIN"
)

// =========================================================
// UTILISATEURS - MODIFICATION
// SUPER_ADMIN + ADMIN
// Le service fera la différence
// =========================================================
.requestMatchers(
    HttpMethod.PUT,
    "/api/utilisateurs/**"
)
.hasAnyRole(
    "SUPER_ADMIN",
    "ADMIN"
)

// =========================================================
// UTILISATEURS - SUPPRESSION
// SUPER_ADMIN + ADMIN
// Le service fera la différence
// =========================================================
.requestMatchers(
    HttpMethod.DELETE,
    "/api/utilisateurs/**"
)
.hasAnyRole(
    "SUPER_ADMIN",
    "ADMIN"
)



    // ==========================
    // ADMINISTRATION
    // ==========================
    .requestMatchers(
        "/api/admin/**"
    )
    .hasAnyRole(
        "SUPER_ADMIN",
        "ADMIN"
    )
   .requestMatchers("/api/dashboard/**")
.hasAnyRole(
    "SUPER_ADMIN",
    "ADMIN"
)
   // =========================================================
// VOYAGES
// =========================================================

// Consultation des voyages
// USER + STAFF + ADMIN + SUPER_ADMIN
.requestMatchers(
    HttpMethod.GET,
    "/api/voyages",
    "/api/voyages/**"
)
.hasAnyAuthority(
    "ROLE_USER",
    "ROLE_STAFF",
    "ROLE_ADMIN",
    "ROLE_SUPER_ADMIN"
)

// Création
.requestMatchers(
    HttpMethod.POST,
    "/api/voyages/**"
)
.hasAnyAuthority(
    "ROLE_ADMIN",
    "ROLE_SUPER_ADMIN"
)

// Modification
.requestMatchers(
    HttpMethod.PUT,
    "/api/voyages/**"
)
.hasAnyAuthority(
    "ROLE_ADMIN",
    "ROLE_SUPER_ADMIN"
)

// Suppression
.requestMatchers(
    HttpMethod.DELETE,
    "/api/voyages/**"
)
.hasAnyAuthority(
    "ROLE_ADMIN",
    "ROLE_SUPER_ADMIN"
)
.requestMatchers(
    "/error"
).permitAll()


    // ==========================
    // RESERVATIONS
    // ==========================

    // Création d'une réservation par un CLIENT
.requestMatchers(
    HttpMethod.POST,
    "/api/reservations/*"
)
.hasRole("USER")

// Mes réservations
.requestMatchers(
    HttpMethod.GET,
    "/api/reservations/mes-reservations"
)
.hasRole("USER")

// Consultation / gestion des réservations
.requestMatchers(
    "/api/reservations/**"
)
.hasAnyRole(
    "SUPER_ADMIN",
    "ADMIN",
    "STAFF"
)



    // ==========================
    // TICKETS
    // ==========================

    .requestMatchers(
        "/api/tickets/**"
    )
    .hasAnyRole(
        "SUPER_ADMIN",
        "ADMIN",
        "STAFF"
    )



    // ==========================
    // PAIEMENTS
    // ==========================
.requestMatchers(
    "/api/paiements/**"
)
.hasAnyRole(
    "SUPER_ADMIN",
    "ADMIN",
    "STAFF",
    "USER"
)



    // ==========================
    // CLIENT
    // ==========================

    .requestMatchers(
        "/api/client/**"
    )
    .hasRole("USER")

.requestMatchers("/api/staff/**")
.hasAuthority("ROLE_STAFF")
.requestMatchers("/api/voyages/du-jour")
.hasAnyRole("SUPER_ADMIN", "ADMIN", "STAFF")
.requestMatchers(
    HttpMethod.GET,
    "/api/villes"
)
.hasAnyAuthority(
    "ROLE_USER",
    "ROLE_STAFF",
    "ROLE_ADMIN",
    "ROLE_SUPER_ADMIN"
)
.requestMatchers(
    HttpMethod.GET,
    "/api/transports"
)
.hasAnyAuthority(
    "ROLE_USER",
    "ROLE_STAFF",
    "ROLE_ADMIN",
    "ROLE_SUPER_ADMIN"
)
.requestMatchers(
    "/uploads/**"
).permitAll()
    // Tout le reste
    .anyRequest()
    .authenticated()

)


        .addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class
        );


    return http.build();

}




    @Bean
    public AuthenticationProvider authenticationProvider() {


        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();


        provider.setUserDetailsService(
                userDetailsService
        );


        provider.setPasswordEncoder(
                passwordEncoder()
        );


        return provider;

    }







    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }







    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {


        return configuration.getAuthenticationManager();

    }


}