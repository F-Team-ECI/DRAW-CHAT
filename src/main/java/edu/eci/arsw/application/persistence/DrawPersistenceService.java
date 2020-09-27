package edu.eci.arsw.application.persistence;

import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;

import java.util.List;

@Service
public interface DrawPersistenceService {
    public void addUser(User user);
    public List<User> getUsers();
    public User getUser(String telefono);
}
