package edu.eci.arsw.application.services.impl;

import java.util.List;
import java.util.Set;

import edu.eci.arsw.application.entities.util.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.Message;
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
    public User getCurrentUserSession() throws AppException {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return getUser(Long.parseLong(username));
    }

    @Override
    public void updateUser(User user) throws AppException {
        drawPersistenceService.updateUser(user);
    }

    @Override
    public void deleteUser(long telefono) {
        drawPersistenceService.deleteUser(telefono);
    }

    @Override
    public Chat getChat(long tUsuario1, long tUsuario2) throws AppException {
        return drawPersistenceService.getChat(tUsuario1, tUsuario2);
    }

    @Override
    public Chat addChat(long tUsuario1, long tUsuario2) throws AppException {
        return drawPersistenceService.addChat(tUsuario1, tUsuario2);
    }

    @Override
    public Message addMessage(Message msg) throws AppException {
        return drawPersistenceService.addMessage(msg);
    }

    @Override
    public List<Chat> getChats(long telefono) throws AppException {
        return drawPersistenceService.getChats(telefono);
    }

    @Override
    public Group addGroup(long tUsuario, Group grupo) throws AppException {
        return drawPersistenceService.addGroup(tUsuario,grupo);
    }

    @Override
    public Group getGroup(String nombre) throws AppException {
        return drawPersistenceService.getGroup(nombre);
    }

    @Override
    public List<Message> getChatMessages(int chatid) throws AppException {
        return drawPersistenceService.getChatMessages(chatid);
    }

    @Override
    public void addUserToGroup(long tUsuario1,long tUsAdd, Group grupo) throws AppException {
        drawPersistenceService.addUserToGroup(tUsuario1, tUsAdd, grupo);

    }

    @Override
    public void upgradeUserOnGroup(long tUsuario1, long tUsUp, Group grupo) throws AppException {
        drawPersistenceService.upgradeUserOnGroup(tUsuario1, tUsUp, grupo);
    }

    @Override
    public void upgradeUsersOnGroup(long tUsuario1, Group grupo) throws AppException {
        Set<User> tUsersUp = grupo.getMembers();
        for (User userUp : tUsersUp) {
            upgradeUserOnGroup(tUsuario1, userUp.getTelefono(), grupo);
        }

    }

    @Override
    public void deleteUserFromGroup(long tUsuario1,long tUsDel, Group grupo) throws AppException {
        drawPersistenceService.deleteUserFromGroup(tUsuario1, tUsDel, grupo);
    }

    @Override
    public void addDrawSession(Group grupo) throws AppException {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveDrawLine(int group, Line line) throws AppException {
        drawPersistenceService.saveDrawLine(group, line);
    }

    @Override
    public List<Line> getDrawLines(int group) throws AppException {
        return drawPersistenceService.getDrawLines(group);
    }

    @Override
    public void createNewSession(int group) throws AppException {
        drawPersistenceService.createNewSession(group);
    }

    @Override
    public List<Message> getGroupChatMessages(int groupid) throws AppException {
        return drawPersistenceService.getGroupChatMessages(groupid);
    }

    @Override
    public List<User> getContactsExGroup(long telefono, int idgrupo) throws AppException {
        return drawPersistenceService.getContactsExGroup(telefono, idgrupo);
    }

    @Override
    public void addUsersToGroup(long tUsuario1, Group grupo) throws AppException {
        Set<User> tUsersAdd = grupo.getMembers();
        for (User userAdd : tUsersAdd) {
            addUserToGroup(tUsuario1, userAdd.getTelefono(), grupo);
        }
    }

    @Override
    public void deleteUsersFromGroup(long tUsuario1, Group grupo) throws AppException {
        Set<User> tUsersDel = grupo.getMembers();
        for (User userDel : tUsersDel) {
            deleteUserFromGroup(tUsuario1,userDel.getTelefono(), grupo);
        }
    }

    @Override
    public Group getGroupById(int idgrupo) throws AppException {
        return drawPersistenceService.getGroupById(idgrupo);
    }

    @Override
    public boolean belongMemberToGroup(long tUsuario1, Group grupo) throws AppException {
        System.out.println("HERE " + drawPersistenceService);
        return drawPersistenceService.belongMemberToGroup(tUsuario1, grupo);
    }

    @Override
    public boolean belongAdminToGroup(long tUsuario1, Group grupo) throws AppException {
        return drawPersistenceService.belongAdminToGroup(tUsuario1, grupo);
    }

    @Override
    public void deleteMessage(Message msg) throws AppException {
        drawPersistenceService.deleteMessage(msg);
    }
}
