package edu.eci.arsw.application.security;
import edu.eci.arsw.application.persistence.DAO.UserDAO;
import edu.eci.arsw.application.persistence.DrawPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DrawPersistenceService drawPersistenceService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("AQUÓ:  " + s);
        drawPersistenceService.getUsers();
        if(!s.equals("pepe")){
            throw new UsernameNotFoundException("User not Found");
        }

        UserDetails user = (UserDetails) new User("pepe", new BCryptPasswordEncoder().encode("pepe"), new ArrayList<>());
        return user;
    }
}
