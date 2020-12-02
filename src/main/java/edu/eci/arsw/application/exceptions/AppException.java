package edu.eci.arsw.application.exceptions;

public class AppException extends Exception{

    /**
     * Excepcion de la aplicacion
     */
    private static final long serialVersionUID = 1L;
    public static final String USER_NOT_REGISTERED = "The provided user doesn't exists";
    public static final String MESSAGE_NOT_EXISTS = "The provided message doesn't exists";
    public static final String CHAT_NOT_EXISTS = "The provided chat doesn't exists";
    public static final String CHAT_ALREADY_EXISTS = "The chat already exists";
    public static final String GROUP_NOT_EXISTS = "The provided group doesn't exists";
    public static final String GROUP_ALREADY_EXISTS = "The group already exists";
    public static final String GROUP_FULL = "The group already has too many users";
    public static final String NOT_PERMISSION_ON_GROUP = "This user doesn't have permissions on group";
    public static final String FULL_PERMISSION_ON_GROUP = "This user doesn't have more permissions on group";
    public static final String USER_NOT_EXISTS_ON_CONTACTS = "This user doesn't belong to the contacts";
    public static final String USER_NOT_EXISTS_ON_GROUP = "This user doesn't belong to the group";
    public static final String USER_ALREADY_EXISTS_ON_GROUP = "This user is already in the group";

    public AppException(String message) {
        super(message);
    }
    
}
