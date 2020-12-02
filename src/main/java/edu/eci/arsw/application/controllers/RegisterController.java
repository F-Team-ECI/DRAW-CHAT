package edu.eci.arsw.application.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.application.controllers.impl.messages.DrawController;
import edu.eci.arsw.application.entities.StateEnum;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/registro")
public class RegisterController {

    @Autowired
    private DrawChatService drawChatService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody String user) {
        System.out.println(user);
        try {
            User nuevo = mapper.readValue(user, User.class);
            nuevo.setContraseña(new BCryptPasswordEncoder().encode(nuevo.getContraseña()));
            nuevo.setEstado(StateEnum.DISCONNECTED.toString());
            nuevo.setFecharegistro(new Date());
            nuevo.setFechaconexion(new Date());
            drawChatService.addUser(nuevo);
        } catch (JsonProcessingException | AppException e) {
            Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("400 Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("201 CREATED", HttpStatus.CREATED);
    }
}
