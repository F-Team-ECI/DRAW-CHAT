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

    /**
     * Mensajes de un chat
     * @param chatid id del chat
     * @return Lista con los mensajes que pertenecen al chat
     * @throws AppException error en busqueda del chat
     */
    public List<Message> getChatMessages(int chatid) throws AppException;

    /**
     * Chats de un usuario
     * @param telefono numero de telefono que pertenece al usuario
     * @return Lista de los chats que pertenecen al usuario
     * @throws AppException error buscando al usuario
     */
    public List<Chat> getChats(long telefono) throws AppException;

    /**
     * Agregar un chat entre 2 usuarios
     * @param tUsuario1 Usuario 1
     * @param tUsuario2 Usuario 2
     * @return Chat entre ambos usuarios
     * @throws AppException error buscando usuarios que no existen en la aplicacion
     */
    public Chat addChat(long tUsuario1,long tUsuario2) throws AppException;

    /**
     * Agregar un mensaje a la aplicacion
     * @param msg mensaje a registrar
     * @throws AppException error en la construccion del mensaje
     */
    public void addMessage(Message msg) throws AppException;

    /**
     * Borrar un mensaje de la aplicacion
     * @param msg mensage a borrar
     * @throws AppException error en el borrado del mensaje
     */
    public void deleteMessage(Message msg) throws AppException;

    /**
     * Agregar un grupo a la aplicacion
     * @param tUsuario el usuario owner
     * @param grupo el grupo a agregar
     * @throws AppException error en la construccion del grupo
     */
    public void addGroup(long tUsuario, Group grupo) throws AppException;

    /**
     * Mensajes de un grupo
     * @param groupid id del grupo
     * @return Lista de todos los mensajes de un grupo
     * @throws AppException error en busqueda de grupo
     */
    public List<Message> getGroupChatMessages(int groupid) throws AppException;

    /**
     * Grupo buscado por el nombre
     * @param nombre el nombre del grupo
     * @return Grupo existente en la aplicacion
     * @throws AppException error en busqueda de grupo
     */
    public Group getGroup(String nombre) throws AppException;

    /**
     * Grupo buscado por el id
     * @param idgrupo el id del grupo
     * @return Grupo existente en la aplicacion
     * @throws AppException error en busqueda de grupo
     */
    public Group getGroupById(int idgrupo) throws AppException;

    /**
     * Contactos que no estan en el grupo
     * @param telefono el telefono del usuario
     * @param idgrupo id del grupo
     * @return Lista de usuarios que son contactos del usuario y aun no estan en el grupo
     * @throws AppException error en la busqueda de los contactos, usuario o el grupo
     */
    public List<User> getContactsExGroup(long telefono,int idgrupo) throws AppException;
    
    /**
     * Agregar un usuario al grupo
     * @param tUsuario1 el usuario que va a agregar al miembro
     * @param tUsAdd el usuario a agregar
     * @param grupo el grupo al que va agregar el usuario
     * @throws AppException error en la busqueda de ambos usuarios o el grupo
     */
    public void addUserToGroup(long tUsuario1,long tUsAdd,Group grupo) throws AppException;

    /**
     * Agregar usuarios al grupo
     * @param tUsuario1 el usuario que va a agregar a los miembros
     * @param grupo el grupo que contiene la lista de proximos miembros del grupo
     * @throws AppException error agregando alguno de los usuarios
     */
    public void addUsersToGroup(long tUsuario1,Group grupo) throws AppException;

    /**
     * Actualizar el rol de un usuario
     * @param tUsuario1 el usuario que va actualizar al miembro
     * @param tUsUp el miembro a actualizar
     * @param grupo el grupo al que pertenecen
     * @throws AppException error actualizando el rol del miembro
     */
    public void upgradeUserOnGroup(long tUsuario1,long tUsUp,Group grupo) throws AppException;

    /**
     * Borrar un usuario de un grupo
     * @param tUsuario1 el usuario que borrara al miembro
     * @param tUsDel el miembro a eliminar
     * @param grupo el grupo al que pertenecen
     * @throws AppException error buscando el usuario a borrar
     */
    public void deleteUserFromGroup(long tUsuario1,long tUsDel, Group grupo) throws AppException;

    /**
     * Borrar usuarios de un grupo
     * @param tUsuario1 el usuario que borrara a los miembros
     * @param grupo el grupo al que pertenecen
     * @throws AppException error buscando el usuario a borrar
     */
    public void deleteUsersFromGroup(long tUsuario1,Group grupo) throws AppException;

    /**
     * Sesion de dibujo a un grupo
     * @param grupo
     * @throws AppException
     */
    public void addDrawSession(Group grupo) throws AppException;
}
