package edu.eci.arsw.application.persistence;

import java.util.List;

import edu.eci.arsw.application.exceptions.AppException;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.entities.User;

/**
 * Servicio que maneja la persistencia con la base de datos
 */
@Service
public interface DrawPersistenceService {

    /**
     * Insertar el usuario en la base de datos
     * @param user usuario con la informacion a insertar en la base de datos
     * @throws AppException
     */
    public void addUser(User user) throws AppException;

    /**
     * Consultar usuarios de la base de datos
     * @return Lista con los usuarios encontrados en la base de datos
     */
    public List<User> getUsers();

    /**
     * Consultar la informacion del usuario que esta en la base de datos
     * @param telefono el numero de telefono del usuario
     * @return Un usuario con la informacion de la base de datos
     */
    public User getUser(long telefono);

    /**
     * Consultar los contactos de un usuario en la base de datos
     * @param telefono el numero de telefono del usuario
     * @return Lista con los usuarios contacto
     */
    public List<User> getContacts(long telefono);

    /**
     * Insertar contacto en la base de datos dado el numero del usuario
     * @param tUsuario1 numero de telefono del usuario
     * @param tUsuario2 numero de telefono del usuario contacto
     * @throws AppException Si no existe el usuario en la base de datos
     */
    public void addContact(long tUsuario1, long tUsuario2) throws AppException;
    
    /**
     * Borrar usuario de la base de datos
     * @param telefono el numero de telefono del usuario
     */
    public void deleteUser(long telefono);

    /**
     * Actualizar la informacion del usuario
     * @param user un usuario con la informacion a modificar
     * @throws AppException si el usuario no existe en la base de datos
     */
	public void updateUser(User user) throws AppException;

    /**
     * Agregar un chat entre 2 usuarios
     * @param tUsuario1 telefono del usuario 1
     * @param tUsuario2 telefono del usuario 2
     * @return Chat entre ambos usuarios
     * @throws AppException cuando alguno de los usuarios no existe
     */
	public Chat addChat(long tUsuario1, long tUsuario2) throws AppException;

    /**
     * Consultar el chat de 2 usuarios
     * @param tUsuario1 usuario 1
     * @param tUsuario2 usuario 2
     * @return Chat entre ambos usuarios
     * @throws AppException
     */
	public Chat getChat(long tUsuario1, long tUsuario2) throws AppException;

    /**
     * Insertar un mensaje de la base de datos
     * @param msg mensaje a insertar en la base de datos 
     * @throws AppException error insertando el mensaje
     */
    public void addMessage(Message msg)throws AppException;

    /**
     * Consultar los chats de un usuario
     * @param telefono el telefono del usuario
     * @return Lista con los chats del usuario
     * @throws AppException error consultando los chats
     */
	public List<Chat> getChats(long telefono)throws AppException;

    /**
     * Consultar los mensajes de un chat
     * @param chatid id del chat
     * @return Lista de mensajes del chat
     * @throws AppException error consultando los chats o mensajes
     */
    public List<Message> getChatMessages(int chatid) throws AppException;
    
    /**
     * Insertar un grupo a la base de datos
     * @param tUsuario el usuario que insertara el grupo
     * @param grupo el grupo con sus respectivos miembros
     * @throws AppException error insertando grupo
     */
    public void addGroup(long tUsuario, Group grupo)throws AppException;

    /**
     * Consultar un grupo por el nombre
     * @param nombre el nombre del grupo
     * @return el grupo consultado de la base de datos
     * @throws AppException error consultando el grupo
     */
    public Group getGroup(String nombre)throws AppException;
    
    /**
     * Consultar el grupo por el id
     * @param idgroup id del grupo
     * @return el grupo consultado de la base de datos
     * @throws AppException error consultado de la base de datos
     */
    public Group getGroupById(int idgroup)throws AppException;

    /**
     * Insertar el usuario al grupo (se agrega la relacion entre usuario y grupo)
     * @param tUsuario1 telefono del usuario que va a agregar al miembro
     * @param tUsAdd telefono del proximo miembro del grupo
     * @param grupo el grupo al que se va a insertar
     * @throws AppException error insertando el usuario
     */
    public void addUserToGroup(long tUsuario1,long tUsAdd, Group grupo)throws AppException;

    /**
     * Actualizar permisos de un usuario (se actualiza el rol del usuario)
     * @param tUsuario1 el usuario que va a actualizar al miembro
     * @param tUsUp el usuario a actualizar sus permisos
     * @param grupo el grupo al que pertenecen
     * @throws AppException error actualizando el rol del miembro
     */
    public void upgradeUserOnGroup(long tUsuario1, long tUsUp, Group grupo)throws AppException;

    /**
     * Borrar un usuario del grupo (eliminar la relacion del usuario y el grupo)
     * @param tUsuario1 telefono del usuario que borra al miembro
     * @param tUsDel telefono del miembro del grupo
     * @param grupo el grupo al que pertenecen
     * @throws AppException error borrando los usuarios del grupo
     */
	public void deleteUserFromGroup(long tUsuario1, long tUsDel, Group grupo)throws AppException;

    /**
     * Consultar los mensajes de un grupo
     * @param groupid id del grupo
     * @return Lista de mensajes del grupo
     * @throws AppException error consultando los mensajes
     */
    public List<Message> getGroupChatMessages(int groupid) throws AppException;

    /**
     * Consultar los contactos de un usuario que no estan en el grupo
     * @param telefono
     * @param idgrupo
     * @return
     * @throws AppException
     */
	public List<User> getContactsExGroup(long telefono, int idgrupo)throws AppException;
}
