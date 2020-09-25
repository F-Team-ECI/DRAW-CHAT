package edu.eci.arsw.application.controllers.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.application.controllers.BaseController;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;

@RestController
@RequestMapping(path = "/draw")
public class DrawController implements BaseController {

    @Autowired
    private DrawChatService drawChatService;

    @RequestMapping(path="/adduser", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user){
        
        try {
            drawChatService.addUser(user);
            return new ResponseEntity<>("HTTP 201 Created",HttpStatus.CREATED);
        } catch (AppException ex){
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 400 Bad Request",HttpStatus.BAD_REQUEST);
        } 
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(){
        try {
            drawChatService.getUsers();
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404 Not Found",HttpStatus.NOT_FOUND);
        }
    }
}
