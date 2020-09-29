package edu.eci.arsw.application.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.application.controllers.impl.DrawController;
import edu.eci.arsw.application.entities.StateEnum;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@Service
public class UserController {

    @Autowired
    private DrawChatService drawChatService;

    private ObjectMapper mapper = new ObjectMapper();


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody String user){
        System.out.println(user);
        try {
            User nuevo = mapper.readValue(user, User.class);
            nuevo.setContraseña(new BCryptPasswordEncoder().encode(nuevo.getContraseña()));
            nuevo.setEstado(StateEnum.DISCONNECTED.toString());
            drawChatService.addUser(nuevo);
        } catch (JsonProcessingException | AppException e){
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("400 Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("201 CREATED", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(){
        try {
            drawChatService.getUsers();
            return new ResponseEntity<>(drawChatService.getUsers(), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404 Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{telefono}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable long telefono){
        try {
            drawChatService.getUser(telefono);
            return new ResponseEntity<>(drawChatService.getUser(telefono), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404 Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{telefono}/contacts", method = RequestMethod.GET)
    public ResponseEntity<?> getContactsByUser(@PathVariable long telefono){
        try {
            return new ResponseEntity<>(drawChatService.getContacts(telefono), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404 Not Found",HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentUser() {
        try {
            return new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                    drawChatService.getCurrentUserSession()
            ), HttpStatus.OK);
        } catch (JsonProcessingException | AppException e) {
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("500 Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
