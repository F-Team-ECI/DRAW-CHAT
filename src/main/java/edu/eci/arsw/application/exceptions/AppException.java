package edu.eci.arsw.application.exceptions;

public class AppException extends Exception{

    /**
     * Excepcion de la aplicacion
     */
    private static final long serialVersionUID = 1L;
    public static final String USER_NOT_REGISTERED = "The provided user doesn't exists";

    public AppException(String message) {
        super(message);
    }
    
}
