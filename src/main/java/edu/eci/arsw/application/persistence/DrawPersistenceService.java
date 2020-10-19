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

	public void addChat(long tUsuario1, long tUsuario2) throws AppException;

	public Chat getChat(long tUsuario1, long tUsuario2) throws AppException;

    public void addMessage(Message msg)throws AppException;

	public List<Chat> getChats(long telefono)throws AppException;
    
    public void addGroup(Group grupo)throws AppException;

	public Group getGroup(String nombre)throws AppException;

	public List<Message> getChatMessages(int chatid) throws AppException;
}
