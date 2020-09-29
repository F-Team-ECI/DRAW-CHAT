package edu.eci.arsw.application.controllers.impl;

import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/contacts")
public class ContactsController {
    @Autowired
    private DrawChatService drawChatService;

    @PostMapping
    public ResponseEntity<?> addContact(@RequestParam long userA,@RequestParam long userB){
        System.out.println(userA);
        System.out.println(userB);
        try {
            drawChatService.addContact(userA, userB);
        } catch (AppException e) {
            return new ResponseEntity<>("400 BAD REQUEST"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("200 Contact added", HttpStatus.OK);
    }
}
