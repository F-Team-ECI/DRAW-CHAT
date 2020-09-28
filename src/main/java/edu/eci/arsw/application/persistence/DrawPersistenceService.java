package edu.eci.arsw.application.persistence;

import java.util.List;

import edu.eci.arsw.application.exceptions.AppException;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;

import java.util.List;

@Service
public interface DrawPersistenceService {
    public void addUser(User user) throws AppException;

    public List<User> getUsers();

    public User getUser(long telefono);

    public User getCurrentUserSession();

}
