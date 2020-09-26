package edu.eci.arsw.application.controllers.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    private ObjectMapper objectMapper = new ObjectMapper();

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
            String json = objectToJson(drawChatService.getUsers());
            return new ResponseEntity<>(json,HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404 Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{telefono}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String telefono){
        try {
            String json = objectToJson(drawChatService.getUser(telefono));
            return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404 Not Found",HttpStatus.NOT_FOUND);
        }
    }

    private String objectToJson(Object a) {
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(a);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
