package edu.eci.arsw.application.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;
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
    public void getUsers() {
        List<User> user = userDAO.findAll();
        for (User us : user) {
            System.out.println(us);
        }
    }

    @Override
    public void getUser(String telefono) {
        Optional<User> user = userDAO.findById(telefono);
        System.out.println(user);
    }
}
