package edu.eci.arsw.application.services;

import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;

import java.util.List;

@Service
public interface DrawChatService {
    public void addUser(User user) throws AppException;
    public List<User> getUsers() throws AppException;
    public User getUser(String telefono) throws AppException;
}
