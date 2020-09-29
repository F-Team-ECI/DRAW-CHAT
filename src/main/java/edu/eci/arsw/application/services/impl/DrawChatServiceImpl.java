package edu.eci.arsw.application.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.persistence.DrawPersistenceService;
import edu.eci.arsw.application.services.DrawChatService;

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
    public User getUser(long telefono) throws AppException {
        return drawPersistenceService.getUser(telefono);
    }

    @Override
    public List<User> getContacts(long telefono) throws AppException {
        return drawPersistenceService.getContacts(telefono);
    }

    @Override
    public void addContact(long tUsuario1, long tUsuario2) throws AppException {
        drawPersistenceService.addContact(tUsuario1, tUsuario2);
    }
    
    @Override
    public Chat getChat(long tUsuario1, long tUsuario2) throws AppException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getCurrentUserSession() throws AppException{
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println(username);
        return getUser(Long.parseLong(username));
    }

	@Override
	public void deleteUser(long telefono) {
		drawPersistenceService.deleteUser(telefono);
	}
}
