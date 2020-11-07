package edu.eci.arsw.application.controllers.impl;

import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.application.controllers.BaseController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/groups")
public class GroupController implements BaseController {

    @Autowired
    public DrawChatService drawChatService;


    @Autowired
    SimpMessagingTemplate msgt;

    @GetMapping()
    public ResponseEntity<?> getGroups() throws AppException {
        return new ResponseEntity<>(drawChatService.getUser(drawChatService.getCurrentUserSession().getTelefono()).getGroups(), HttpStatus.OK);
    }

    @PostMapping(value = "/{phone}")
    public ResponseEntity<?> createGroup(@PathVariable long phone, @RequestBody Group grupo) throws AppException {
        if(phone != drawChatService.getCurrentUserSession().getTelefono()){
            return new ResponseEntity<>("401 Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        try {
            grupo.setFechaCreacion(new Date());
            drawChatService.addGroup(phone, grupo);
            msgt.convertAndSend("/topic/chatsCreator."+phone, grupo);
            for(User u: grupo.getMembers()){
                msgt.convertAndSend("/topic/chatsCreator."+u.getTelefono(), grupo);
            }


            return new ResponseEntity<>("201 CREATED", HttpStatus.CREATED);
        } catch (AppException e) {
            e.printStackTrace();
            return new ResponseEntity<>("400 BAD REQUEST", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{id}/messages")
    public ResponseEntity<?> getGroupMessages(@PathVariable int id) {
        List<Message> ans = new ArrayList<>();
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

}
