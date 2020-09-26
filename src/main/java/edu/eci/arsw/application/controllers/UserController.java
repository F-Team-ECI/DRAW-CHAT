package edu.eci.arsw.application.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.persistence.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<?> register(@RequestBody String body){
        User nuevo = null;
        try {
            nuevo = mapper.readValue(body, User.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("400 BAD REQUEST", HttpStatus.BAD_REQUEST);
        }
        User user = userDAO.getUserByPhone(nuevo.getTelefono());
        if(user != null){
            return new ResponseEntity<>("400 FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        userDAO.createUser(nuevo);
        return new ResponseEntity<>("202 CREATED", HttpStatus.CREATED);
    }
}
