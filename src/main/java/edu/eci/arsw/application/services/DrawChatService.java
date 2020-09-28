package edu.eci.arsw.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;


@Service
public interface DrawChatService {
    public void addUser(User user) throws AppException;
    public List<User> getUsers() throws AppException;
    public User getUser(long telefono) throws AppException;
    public List<User> getContacts(long telefono) throws AppException;
    public void addContact(long tUsuario1,long tUsuario2) throws AppException;
    public Chat getChat(long tUsuario1,long tUsuario2) throws AppException;
    public User getCurrentUserSession() throws AppException;


}
