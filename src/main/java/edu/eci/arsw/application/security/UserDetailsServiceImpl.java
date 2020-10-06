package edu.eci.arsw.application.security;
import edu.eci.arsw.application.persistence.DrawPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/**
 * Servicio de detalles del usuario
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DrawPersistenceService drawPersistenceService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        edu.eci.arsw.application.entities.User current = null;
        try{
            current = drawPersistenceService.getUser(Long.parseLong(s));
            System.out.println(current);
        } catch (NumberFormatException e){
            throw new UsernameNotFoundException("User not Found");
        }
        if(current == null){
            throw new UsernameNotFoundException("User not Found");
        }
        UserDetails user = (UserDetails) new User(s, current.getContraseña(), new ArrayList<>());
        return user;
    }
}
