package edu.eci.arsw.application.security;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("AQUÓ:  " + s);
        if(!s.equals("pepe")){
            throw new UsernameNotFoundException("User not Found");
        }
        ;
        UserDetails user = (UserDetails) new User("pepe", new BCryptPasswordEncoder().encode("pepe"), new ArrayList<>());
        return user;
    }
}
