package SystemeReservationticket.example.SystemeReservation.Config;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Utiliser setAllowedOriginPatterns au lieu de setAllowedOrigins pour plus de souplesse avec ngrok
        configuration.setAllowedOriginPatterns(List.of(
        "http://localhost:4200",
        
        "https://golden-seahorse-0af1d5.netlify.app/" // 👈 Remplacez par l'URL exacte de votre site Netlify
));

        // Méthodes HTTP autorisées
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
        ));

        // Headers autorisés (inclure les en-têtes personnalisés si nécessaire)
        configuration.setAllowedHeaders(List.of("*"));

        // Headers exposés
        configuration.setExposedHeaders(List.of("Authorization"));

        // Autoriser les credentials (cookies / tokens)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}