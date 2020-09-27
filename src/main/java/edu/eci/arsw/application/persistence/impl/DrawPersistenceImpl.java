package edu.eci.arsw.application.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void addUser(User user) {
        userDAO.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUser(String telefono) throws AppException {
        Optional<User> user = userDAO.findById(telefono);
        User usuario = new User();
        
        if(user.isPresent()){
            usuario = user.get();
            System.out.println(usuario);
        }else{
            usuario = null;
        }
        return usuario;
    }

}
