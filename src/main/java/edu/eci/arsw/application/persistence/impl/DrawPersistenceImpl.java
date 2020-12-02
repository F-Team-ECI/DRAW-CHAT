package edu.eci.arsw.application.persistence.impl;

import java.util.*;

import edu.eci.arsw.application.entities.util.Line;
import edu.eci.arsw.application.cache.redis.DrawDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.entities.RolEnum;
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

    @Autowired
    private DrawDAO drawDAO;

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
    public Message addMessage(Message msg) {
        System.out.println("Dice " + msg.getEmisor().getNombre() + " que " + msg.getContenido());
        System.out.println(msg);
        return msgDAO.save(msg);
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
    public List<Message> getChatMessages(int chatid) throws AppException {
        Optional<Chat> chat = chatDAO.findById(chatid);
        List<Message> msgs = msgDAO.getMessagesByChat(chatid);
        if (chat==null) {
            throw new AppException(AppException.CHAT_NOT_EXISTS);
        }
        return msgs;
    }

    @Override
    public void addGroup(long tUsuario1, Group grupo) throws AppException {
        User user = getUser(tUsuario1);
        if (user==null) {
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        if (getGroup(grupo.getNombre())!=null){
            throw new AppException(AppException.GROUP_ALREADY_EXISTS);
        }
        System.out.println("add grupo");
        Set<User> members= grupo.getMembers();
        grupo.setMembers(new TreeSet<>());
        groupDAO.save(grupo);
        groupDAO.addUserToGroup(user.getTelefono(), getGroup(grupo.getNombre()).getId(), RolEnum.OWNER.toString());
        Group groupP = getGroup(grupo.getNombre());

        for (User usr : members) {
            addUserToGroup(user.getTelefono(),usr.getTelefono(),groupP);
        }
    }

    @Override
    public void addUserToGroup(long tUsuario1, long tUsAdd, Group grupo) throws AppException {
        User user1 = getUser(tUsuario1);
        User user2 = getUser(tUsAdd);
        if (user1==null || user2==null) {
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        Group grupo1 = getGroupById(grupo.getId());
        if (grupo1==null){
            throw new AppException(AppException.GROUP_NOT_EXISTS);
        }
        if (grupo1.getMembers().size()==100){
            throw new AppException(AppException.GROUP_FULL);
        }

        String rol1 = groupDAO.getRole(tUsuario1, grupo1.getId());
        String rol2 = groupDAO.getRole(tUsAdd, grupo1.getId());

        if (rol1==null){
            throw new AppException(AppException.USER_NOT_EXISTS_ON_GROUP);
        }
        if (rol2!=null){
            throw new AppException(AppException.USER_ALREADY_EXISTS_ON_GROUP);
        }
        if (rol1==null || rol1=="MEMBER"){
            throw new AppException(AppException.NOT_PERMISSION_ON_GROUP);
        }
        
        boolean isContact=false;
        List<User> adminContacts = getContacts(tUsuario1);
        for (User us : adminContacts) {
            if (us.getTelefono()==user2.getTelefono()) {
                isContact=true;
            }
        }

        if (!isContact){
            throw new AppException(AppException.USER_NOT_EXISTS_ON_CONTACTS);
        }
        groupDAO.addUserToGroup(user2.getTelefono(), grupo1.getId(), RolEnum.MEMBER.toString());
    }

    @Override
    public void upgradeUserOnGroup(long tUsuario1, long tUsUp, Group grupo) throws AppException {
        User user1 = getUser(tUsuario1);
        User user2 = getUser(tUsUp);
        if (user1==null || user2==null) {
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        Group grupo1 = getGroupById(grupo.getId());
        if (grupo1==null){
            throw new AppException(AppException.GROUP_NOT_EXISTS);
        }
        String rol1 = groupDAO.getRole(tUsuario1, grupo1.getId());
        String rol2 = groupDAO.getRole(tUsUp, grupo1.getId());

        System.out.println(rol1 + "|" +rol2);
        if (rol1==null || rol2==null){
            throw new AppException(AppException.USER_NOT_EXISTS_ON_GROUP);
        }

        if ((rol1=="MEMBER" && rol2 == "ADMIN")
            || (rol1=="MEMBER" && rol2 == "MEMBER")
            || (rol1=="MEMBER" && rol2 == "OWNER")){
            throw new AppException(AppException.NOT_PERMISSION_ON_GROUP);
        }

        if ((rol1=="ADMIN" && rol2 == "ADMIN")
            || (rol1=="OWNER" && rol2 == "ADMIN")
            || (rol1=="ADMIN" && rol2 == "OWNER")){
            throw new AppException(AppException.FULL_PERMISSION_ON_GROUP);
        }

        groupDAO.updateUserOnGroup(tUsUp, grupo1.getId());

    }

    @Override
    public void deleteUserFromGroup(long tUsuario1, long tUsDel, Group grupo) throws AppException {
        User user1 = getUser(tUsuario1);
        User user2 = getUser(tUsDel);
        if (user1==null || user2==null) {
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        Group grupo1 = getGroupById(grupo.getId());
        if (grupo1==null){
            throw new AppException(AppException.GROUP_NOT_EXISTS);
        }
        String rol1 = groupDAO.getRole(tUsuario1, grupo1.getId());
        String rol2 = groupDAO.getRole(tUsDel, grupo1.getId());

        if (rol1==null || rol2==null){
            throw new AppException(AppException.USER_NOT_EXISTS_ON_GROUP);
        }

        if ((rol1=="MEMBER" && rol2 == "ADMIN")
            || (rol1=="MEMBER" && rol2 == "OWNER")
            || (rol1=="ADMIN" && rol2 == "OWNER")){
            throw new AppException(AppException.NOT_PERMISSION_ON_GROUP);
        }
        groupDAO.deleteUserFromGroup(user2.getTelefono(), grupo1.getId());
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
    public Group getGroupById(int groupid) {
        Optional<Group> grupo = groupDAO.findById(groupid);
        List<Message> msgs = msgDAO.getMessagesByGroup(grupo.get().getId());
        grupo.get().setChat(grupo.get().getId());
        grupo.get().setMessages(msgs);
        Group grp=grupo.get();
        return grp;
    }

    @Override
    public List<Message> getGroupChatMessages(int groupid) throws AppException {
        List<Message> msgs = msgDAO.getMessagesByGroup(groupid);
        return msgs;
    }

    @Override
    public List<User> getContactsExGroup(long telefono, int idgrupo) throws AppException {
        List<User> contacts = userDAO.getContactsExGroup(telefono, idgrupo);
        return contacts;
    }

    @Override
    public boolean belongMemberToGroup(long tUsuario1, Group grupo) throws AppException {
        boolean isMember =false;
        User user1 = getUser(tUsuario1);
        System.out.println(user1);
        System.out.println(grupo);
        if (user1==null) {
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        Group grupo1 = getGroupById(grupo.getId());
        if (grupo1==null){
            throw new AppException(AppException.GROUP_NOT_EXISTS);
        }
        String rol1 = groupDAO.getRole(tUsuario1, grupo.getId());
        if (rol1==null){
            throw new AppException(AppException.USER_NOT_EXISTS_ON_GROUP);
        }
        if (rol1=="MEMBER"|| rol1=="ADMIN" || rol1=="OWNER"){
            isMember=true;
        }
        return isMember;
    }

    @Override
    public boolean belongAdminToGroup(long tUsuario1, Group grupo) throws AppException {
        boolean isAdmin=false;
        User user1 = getUser(tUsuario1);
        if (user1==null) {
            throw new AppException(AppException.USER_NOT_REGISTERED);
        }
        Group grupo1 = getGroupById(grupo.getId());
        if (grupo1==null){
            throw new AppException(AppException.GROUP_NOT_EXISTS);
        }
        String rol1 = groupDAO.getRole(tUsuario1, grupo.getId());
        if (rol1==null){
            throw new AppException(AppException.USER_NOT_EXISTS_ON_GROUP);
        }
        if (rol1=="ADMIN" || rol1=="OWNER"){
            isAdmin=true;
        }
        if (rol1=="MEMBER"){
            isAdmin=false;
        }
        return isAdmin;
    }

    @Override
    public void saveDrawLine(int group, Line line) throws AppException {
        drawDAO.save(group, line);
    }

    @Override
    public List<Line> getDrawLines(int group) throws AppException {
        return drawDAO.getLines(group);
    }

    @Override
    public void createNewSession(int group) throws AppException {
        drawDAO.createSession(group);
    }

    public void deleteMessage(Message msg) throws AppException {
        Optional<Message> message= msgDAO.findById(msg.getId());
        if (message==null){
            throw new AppException(AppException.MESSAGE_NOT_EXISTS);
        }
        if (msg.allowedToDelete()) {
            msg.setContenido("Mensaje Borrado");
            msgDAO.save(msg);
        }
    }
}
