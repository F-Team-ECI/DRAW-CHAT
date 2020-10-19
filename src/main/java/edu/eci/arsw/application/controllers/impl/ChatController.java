package edu.eci.arsw.application.controllers.impl;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.exceptions.AppException;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody Chat chat){
        Chat product = null;
        try {
            product = drawChatService.addChat(chat.getUser1().getTelefono(), chat.getUser2().getTelefono());
            System.out.println(product);
            System.out.println(chat);
            msgt.convertAndSend("/topic/chats/users."+chat.getUser1().getTelefono(), product);
            msgt.convertAndSend("/topic/chats/users."+chat.getUser2().getTelefono(), product);
        } catch (AppException e) {
            e.printStackTrace();
            return new ResponseEntity<>("403", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("200 CREATED", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/{telefono}", method = RequestMethod.GET)
	public ResponseEntity<?> getChats(@PathVariable long telefono) {
		try {
			return new ResponseEntity<>(drawChatService.getChats(telefono), HttpStatus.OK);
		} catch (Exception ex) {
			Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("HTTP 404 Not Found", HttpStatus.NOT_FOUND);
		}
	}

    @PostMapping("/{id}/messages")
    public ResponseEntity<?> handle(@RequestBody Message message, @PathVariable long id)  {
        System.out.println("GOOD");
        System.out.println(drawChatService);
        message.setFecha(new Date());
        try{
            message.setEmisor(drawChatService.getCurrentUserSession());
            System.out.println(message);
        } catch (AppException e){
            return new ResponseEntity<>("401 Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        try{
            drawChatService.addMessage(message);
        } catch (AppException e){
            return new ResponseEntity<>("400 Bad Request", HttpStatus.BAD_REQUEST);
        }
        msgt.convertAndSend("/topic/chats/messages."+id, message);
        return new ResponseEntity<>("200 OK", HttpStatus.OK);
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
