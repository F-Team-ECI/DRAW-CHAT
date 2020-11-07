package edu.eci.arsw.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;

/**
 * Servicios de la aplicacion
 */
@Service
public interface DrawChatService {
    /**
     * Agregar un usuario a la aplicacion
     * @param user usuario a agregar
     * @throws AppException si es la informacion del usuario es invalida o no corresponde a los estandares establecidos
     */
    public void addUser(User user) throws AppException;

    /**
     * Usuarios de la aplicacion
     * @return Una lista de usuarios
     * @throws AppException cualquier error en la consulta
     */
    public List<User> getUsers() throws AppException;

    /**
     * Consultar usuario dado un telefono
     * @param telefono el numero de telefono del usuario
     * @return Usuario de la aplicacion
     * @throws AppException Si el telefono no corresponde a ningun usuario en la aplicacion
     */
    public User getUser(long telefono) throws AppException;

    /**
     * Consultar los contactos de un usuario dado el numero de telefono del usuario
     * @param telefono el numero de telefono del usuario
     * @return Lista de contactos del usuario
     * @throws AppException Si el telefono no corresponde a ningun usuario de la aplicacion
     */
    public List<User> getContacts(long telefono) throws AppException;

    /**
     * Agregar un contacto a un usuario dado el numero de telefono del usuario
     * @param tUsuario1 el numero de telefono del usuario
     * @param tUsuario2 el numero de telefono del usuario contacto
     * @throws AppException Si el numero de telefono no corresponde a ningun usuario de la aplicacion
     */
    public void addContact(long tUsuario1,long tUsuario2) throws AppException;
    
    /**
     * Consultar el usuario actual de la aplicacion
     * @return Usuario de la aplicacion
     * @throws AppException Si no hay un usuario actualmente
     */
    public User getCurrentUserSession() throws AppException;

    /**
     * Eliminar un usuario de la aplicacion
     * @param telefono el numero de telefono del usuario
     */
    public void deleteUser(long telefono);

    /**
     * Actualizar un usuario
     * @param user el usuario, que posee la informacion a actualizar
     * @throws AppException la informacion del usuario esta errada o el usuario no existe
     */
    public void updateUser(User user) throws AppException;
    
    /**
     * Chat entre 2 usuarios
     * @param tUsuario1 el numero de telefono del usuario
     * @param tUsuario2 el numero de telefono del usuario contacto
     * @return Chat entre ambos usuarios
     * @throws AppException error en la busqueda del chat
     */
    public Chat getChat(long tUsuario1,long tUsuario2) throws AppException;

    public List<Message> getChatMessages(int chatid) throws AppException;

    public List<Chat> getChats(long telefono) throws AppException;

    public Chat addChat(long tUsuario1,long tUsuario2) throws AppException;

    public void addMessage(Message msg) throws AppException;

    //
    public void deleteMessage(Message msg) throws AppException;

    public void addGroup(long tUsuario, Group grupo) throws AppException;

    public Group getGroup(String nombre) throws AppException;
    
    public void addUserToGroup(long tUsuario1,long tUsAdd,Group grupo) throws AppException;

    public void upgradeUserOnGroup(long tUsuario1,long tUsUp,Group grupo) throws AppException;

    public void deleteUserFromGroup(long tUsuario1,long tUsDel, Group grupo) throws AppException;

    public void addDrawSession(Group grupo) throws AppException;
}
