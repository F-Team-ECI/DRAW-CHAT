package edu.eci.arsw.application.persistence;

import java.util.List;

import edu.eci.arsw.application.exceptions.AppException;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;

@Service
public interface DrawPersistenceService {
    public void addUser(User user) throws AppException;

    public List<User> getUsers();

    public User getUser(long telefono);



    public List<User> getContacts(long telefono);

    public void addContact(long tUsuario1, long tUsuario2) throws AppException;
    
    public void deleteUser(long telefono);


}
