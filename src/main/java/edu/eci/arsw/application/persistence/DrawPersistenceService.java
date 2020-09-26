package edu.eci.arsw.application.persistence;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;

@Service
public interface DrawPersistenceService {
    public void addUser(User user);
    public List<User> getUsers();
    public User getUser(String telefono) throws AppException ;
}
