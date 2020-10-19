package edu.eci.arsw.application.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.persistence.DrawPersistenceService;
import edu.eci.arsw.application.persistence.DAO.ChatDAO;
import edu.eci.arsw.application.persistence.DAO.GroupDAO;
import edu.eci.arsw.application.persistence.DAO.MessageDAO;
import edu.eci.arsw.application.persistence.DAO.UserDAO;

@Service
public class DrawPersistenceImpl implements DrawPersistenceService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ChatDAO chatDAO;

    @Autowired
    private MessageDAO msgDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Override
    public void addUser(User user) throws AppException {
        User current = getUser(user.getTelefono());
        System.out.println(user);
        if (current != null) {
            throw new AppException("User already registered");
        } else if (!correctUser(user)) {
            throw new AppException("Incorrect credentials");
        }
        userDAO.save(user);
    }

    /**
     * Verificar que el usuario sea valido
     * 
     * @param user el usuario a verificar
     * @return true si es valido, false si no lo es
     */
    private boolean correctUser(User user) {
        boolean ans = true;
        if (user.getContraseña() == null || !user.validTelefono() || !user.validPass()) {
            ans = false;
        }
        if (user.getNombre() == null || !user.validName()) {
            ans = false;
        }
        if (user.getApellido() == null || !user.validApellido()) {
            ans = false;
        }
        return ans;
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUser(long telefono) {
        List<User> user = userDAO.findAll();
        User usuario = null;

        for (User us : user) {
            if (us.getTelefono() == telefono) {
                usuario = us;
                System.out.println(usuario.toString());
                break;
            }
        }
        return usuario;
    }

    public List<User> getContacts(long telefono) {
        List<User> contacts = userDAO.getContacts(telefono);
        return contacts;
    }

    @Override
    public void addContact(long tUsuario1, long tUsuario2) throws AppException {
        boolean check = checkPhone(tUsuario1) && checkPhone(tUsuario2);
        if (!check) {
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        userDAO.setContact(tUsuario1, tUsuario2);
        System.out.println("Contacto Registrado");

    }

    /**
     * Validar el telefono del usuario este en la base de datos
     * 
     * @param telefono el numero de telefono del usuario
     * @return true si es valido, false si no lo es
     */
    private boolean checkPhone(long telefono) {
        Optional<User> user = userDAO.findById(telefono);
        return user.isPresent();
    }

    @Override
    public void deleteUser(long telefono) {
        userDAO.delete(getUser(telefono));

    }

    @Override
    public void updateUser(User user) {
        User loaded = getUser(user.getTelefono());

        if (user.getNombre() != null && user.validName()) {
            loaded.setNombre(user.getNombre());
        }
        if (user.getApellido() != null && user.validApellido()) {
            loaded.setApellido(user.getApellido());
        }
        if (user.getContraseña() != null && user.validPass()) {
            loaded.setContraseña(user.getContraseña());
        }
        if (user.getEstado() != null) {
            loaded.setEstado(user.getEstado());
        }
        userDAO.save(loaded);
    }

    @Override
    public Chat addChat(long tUsuario1, long tUsuario2) throws AppException {
        User user1 = getUser(tUsuario1);
        User user2 = getUser(tUsuario2);
        if (user1==null || user2==null) {
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        String tipo = "normal";
        Chat nChat = new Chat(0, user1, user2, tipo);
        Chat ans = null;
        if(getChat(tUsuario1, tUsuario2)==null || getChat(tUsuario2, tUsuario1)==null){
            ans = chatDAO.save(nChat);
        } else {
            throw new AppException(AppException.CHAT_ALREADY_EXISTS);
        }
        return ans;
    }

    @Override
    public Chat getChat(long tUsuario1, long tUsuario2) {

        List<Chat> chats = chatDAO.findAll();
        Chat cht = null;

        for (Chat ch : chats) {
            if ((ch.getUser1().getTelefono() == tUsuario1 && ch.getUser2().getTelefono() == tUsuario2)||
                (ch.getUser2().getTelefono() == tUsuario1 && ch.getUser1().getTelefono() == tUsuario2)) {
                cht = ch;
                List<Message> msgs = msgDAO.getMessagesByChat(cht.getId());
                cht.setChat(cht.getId());
                cht.setMessages(msgs);
                break;
            }
        }
        return cht;
    }

    @Override
    public void addMessage(Message msg) {
        System.out.println("Dice " + msg.getEmisor().getNombre() + " que " + msg.getContenido());
        msgDAO.save(msg);
    }

    @Override
    public List<Chat> getChats(long telefono) throws AppException {
        List<Chat> chats = chatDAO.getChatsByUser(telefono);
        if (chats==null) {
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        return chats;
    }

    @Override
    public void addGroup(Group grupo) {
        System.out.println("add grupo");
        groupDAO.save(grupo);
    }

    @Override
    public Group getGroup(String nombre) {
        
        List<Group> grupos = groupDAO.findAll();
        Group grupo = null;

        for (Group grp : grupos) {
            if (grp.getNombre()==nombre) {
                grupo=grp;
                List<Message> msgs = msgDAO.getMessagesByGroup(grupo.getId());
                grupo.setChat(grupo.getId());
                grupo.setMessages(msgs);
            }
        }

        return grupo;
    }

    @Override
    public List<Message> getChatMessages(int chatid) throws AppException {
        Optional<Chat> chat = chatDAO.findById(chatid);
        List<Message> msgs = msgDAO.getMessagesByChat(chatid);
        if (chat==null) {
            throw new AppException(AppException.CHAT_NOT_EXISTS);
        }
        return msgs;
    }
}
