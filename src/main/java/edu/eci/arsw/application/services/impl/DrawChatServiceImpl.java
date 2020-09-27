package edu.eci.arsw.application.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.persistence.DrawPersistenceService;
import edu.eci.arsw.application.services.DrawChatService;

import java.util.List;

@Service
public class DrawChatServiceImpl implements DrawChatService {

    @Autowired
    private DrawPersistenceService drawPersistenceService;

    @Override
    public void addUser(User user) throws AppException {
        drawPersistenceService.addUser(user);
    }

    @Override
    public List<User> getUsers() throws AppException {
        return drawPersistenceService.getUsers();
    }

    @Override
    public User getUser(String telefono) throws AppException {
        return drawPersistenceService.getUser(telefono);
    }
    
}
