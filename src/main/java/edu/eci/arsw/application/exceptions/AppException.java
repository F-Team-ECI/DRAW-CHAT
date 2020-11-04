package edu.eci.arsw.application.exceptions;

public class AppException extends Exception{

    /**
     * Excepcion de la aplicacion
     */
    private static final long serialVersionUID = 1L;
    public static final String USER_NOT_REGISTERED = "The provided user doesn't exists";
    public static final String CHAT_NOT_EXISTS = "The provided chat doesn't exists";
    public static final String CHAT_ALREADY_EXISTS = "The chat already exists";
    public static final String GROUP_ALREADY_EXISTS = "The group already exists";

    public AppException(String message) {
        super(message);
    }
    
}
