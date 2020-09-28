package edu.eci.arsw.application.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.persistence.DrawPersistenceService;
import edu.eci.arsw.application.persistence.DAO.UserDAO;

@Service
public class DrawPersistenceImpl implements DrawPersistenceService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void addUser(User user) throws  AppException{
        User current = getUser(user.getTelefono());
        System.out.println(current);
        if(current!=null){
            throw new AppException("User already registered");
        } else if(user == null){
            throw new AppException("Invalid user");
        } else if(!correctUSer(user)){
            throw new AppException("Incorrect credentials");
        }
        userDAO.save(user);
    }


    private boolean correctUSer(User user) {
        boolean ans = true;
        if (user.getContraseña() == null || Long.toString(user.getTelefono()).length() != 10) {
            ans = false;
        }
        if (user.getNombre() == null || user.getNombre().length() < 3) {
            ans = false;
        }
        if (user.getApellido() == null || user.getApellido().length() < 3) {
            ans = false;
        }
        return ans;
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUser(long telefono){
        List<User> user = userDAO.findAll();
        User usuario = null;

        for (User us : user) {
            if(us.getTelefono()==telefono){
                usuario = us;
                System.out.println(usuario.toString());
                break;
            }
        }
        return usuario;

    }

    @Override
    public List<User> getContacts(long telefono) {
        List<User> contacts = userDAO.getContacts(telefono);
        return contacts;
    }

    @Override
    public void addContact(long tUsuario1, long tUsuario2) throws AppException {

    }

}
