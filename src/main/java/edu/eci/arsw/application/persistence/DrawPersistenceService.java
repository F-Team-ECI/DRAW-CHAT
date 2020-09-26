package edu.eci.arsw.application.persistence;

import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;

@Service
public interface DrawPersistenceService {
    public void addUser(User user);
    public void getUsers();
    public void getUser(String telefono);
}
