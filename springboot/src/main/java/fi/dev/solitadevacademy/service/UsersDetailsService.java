package fi.dev.solitadevacademy.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UsersDetailsService implements UserDetailsService {

    /**
     * Load users and rols from map and build a set of users
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, String> usuarios = Map.of(
                "solita", "USER"
        );
        var rol = usuarios.get(username);
        if (rol != null) {
            User.UserBuilder userBuilder = User.withUsername(username);
            // "solita2023" => [BCrypt] => $2y$10$/vNAl8uwXno3lem.AaMiV.IlV.k5b.kWIU.0l60b3QD4/HetDVDXq
            String encryptedPassword = "$2y$10$/vNAl8uwXno3lem.AaMiV.IlV.k5b.kWIU.0l60b3QD4/HetDVDXq";
            userBuilder.password(encryptedPassword).roles(rol);
            return userBuilder.build();
        } else {
            throw new UsernameNotFoundException(username);
        }

    }
}