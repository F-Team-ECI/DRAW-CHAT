package edu.eci.arsw.application.exceptions;

public class AppException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final String USER_NOT_REGISTERED = "The provided user does not exisy";

    public AppException(String message) {
        super(message);
    }
    
}
