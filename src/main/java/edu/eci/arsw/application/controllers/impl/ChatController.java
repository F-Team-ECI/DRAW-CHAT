package edu.eci.arsw.application.controllers.impl;

import edu.eci.arsw.application.controllers.impl.messages.DrawController;
import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.exceptions.AppException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import edu.eci.arsw.application.controllers.BaseController;
import edu.eci.arsw.application.services.DrawChatService;


@RestController
@RequestMapping(path = "/chats")
public class ChatController implements BaseController{
    
    @Autowired
    private DrawChatService drawChatService;

    @Autowired
    SimpMessagingTemplate msgt;

    @RequestMapping(value = "/users/{telefono}", method = RequestMethod.GET)
	public ResponseEntity<?> getChats(@PathVariable long telefono) {
		try {
			return new ResponseEntity<>(drawChatService.getChats(telefono), HttpStatus.OK);
		} catch (Exception ex) {
			Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("HTTP 404 Not Found", HttpStatus.NOT_FOUND);
		}
	}

    @GetMapping("/{id}/messages")
    public ResponseEntity<?> getChatMessages(@PathVariable int id) {
        List<Message> ans;
        System.out.println(id);
        try {
            ans = drawChatService.getChatMessages(id);
        } catch (AppException e) {
            return new ResponseEntity<>("200 Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
}
