package edu.eci.arsw.application.controllers.impl;

import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.User;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import edu.eci.arsw.application.controllers.BaseController;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Set;

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
        System.out.println(grupo.getMembers());
        if(phone != drawChatService.getCurrentUserSession().getTelefono()) {
            return new ResponseEntity<>("401 Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        try {
            grupo.setFechaCreacion(new Date());
            Set<User> users= grupo.getMembers();
            drawChatService.addGroup(phone, grupo);
            msgt.convertAndSend("/topic/groupsCreator."+phone, grupo);
            for(User u: users){
                System.out.println(u.getTelefono());
                msgt.convertAndSend("/topic/groupsCreator."+u.getTelefono(), grupo);
            }
            return new ResponseEntity<>("201 CREATED", HttpStatus.CREATED);
        } catch (AppException e) {
            e.printStackTrace();
            return new ResponseEntity<>("400 BAD REQUEST", HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/{id}/messages")
    public ResponseEntity<?> getGroupMessages(@PathVariable int id) throws AppException {
        return new ResponseEntity<>(drawChatService.getGroupChatMessages(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/remaining")
    public ResponseEntity<?> getMemebersNotInGroup(@PathVariable int id) throws AppException {
        return new ResponseEntity<>(drawChatService.getContactsExGroup(
                drawChatService.getCurrentUserSession().getTelefono(), id
        ), HttpStatus.OK);
    }

    @PutMapping("/addmembers")
    public ResponseEntity<?> addMembers(@RequestBody Group grupo) throws AppException {
        System.out.println(grupo.getMembers());
        System.out.println(grupo.getId());
        drawChatService.addUsersToGroup(drawChatService.getCurrentUserSession().getTelefono(), grupo);
        return new ResponseEntity<>("200 ACCEPTED", HttpStatus.ACCEPTED);
    }

    @PutMapping("/deletemembers")
    public ResponseEntity<?> deleteMembers(@RequestBody Group grupo)  {
        System.out.println(grupo.getMembers());
        System.out.println(grupo.getId());
        try {
            drawChatService.deleteUsersFromGroup(drawChatService.getCurrentUserSession().getTelefono(), grupo);
            return new ResponseEntity<>("200 ACCEPTED", HttpStatus.ACCEPTED);
        } catch (AppException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}/members")
    public ResponseEntity<?> getGroup(@PathVariable int id){
        Group a = null;
        try {
            a = drawChatService.getGroupById(id);
        } catch (AppException e) {
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(a.getMembers(), HttpStatus.OK);
    }


}
