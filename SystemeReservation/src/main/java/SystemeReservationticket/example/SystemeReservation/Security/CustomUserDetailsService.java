package SystemeReservationticket.example.SystemeReservation.Security;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import SystemeReservationticket.example.SystemeReservation.Model.Utilisateur;
import SystemeReservationticket.example.SystemeReservation.Repository.UtilisateurRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
   public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {

    System.out.println("Recherche de : " + email);

    Utilisateur user =
            utilisateurRepository.findByEmail(email)
            .orElseThrow(() ->
                    new UsernameNotFoundException(email));

    System.out.println("Utilisateur trouvé : " + user.getEmail());

    return user;
}
}
