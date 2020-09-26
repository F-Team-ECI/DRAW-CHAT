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
<<<<<<< HEAD
    public List<User> getUsers() {
        return userDAO.findAll();
=======
    public void getUsers() {
        //List<User> user = userDAO.findAll();
        //for (User us : user) {
        //    System.out.println(us);
        //}
        userDAO.findById("0005");
        List<User> user = userDAO.findAll();
        for (User us : user) {
            System.out.println(us);
        }

>>>>>>> e4cd67ff7d8c3ce1f0ca193a6136feaadb261ed9
    }

    @Override
    public User getUser(String telefono) throws AppException {
        Optional<User> user = userDAO.findById(telefono);
        User usuario = new User();
        
        if(user.isPresent()){
            usuario = user.get();
        }else{
            throw new AppException("No se pudo encontrar el usuario");
        }
        return usuario;
    }

}
