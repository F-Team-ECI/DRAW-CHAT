package edu.eci.arsw.application.controllers.impl;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.util.Line;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.application.controllers.BaseController;
import edu.eci.arsw.application.services.DrawChatService;

//import javax.xml.ws.Response;

@RestController
@RequestMapping(path = "/chats")
public class ChatController implements BaseController{
    
    @Autowired
    private DrawChatService drawChatService;

    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("/chats.{phone}")
    public void handleBuyEvent(Chat chat, @DestinationVariable String phone) throws Exception {
        msgt.convertAndSend("/topic/chats."+phone, chat);
    }

    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody Chat chat){
        System.out.println(chat);
        return null;
    }

    @RequestMapping(value = "/{telefono}", method = RequestMethod.GET)
	public ResponseEntity<?> getChats(@PathVariable long telefono) {
		try {
			return new ResponseEntity<>(drawChatService.getChats(telefono), HttpStatus.OK);
		} catch (Exception ex) {
			Logger.getLogger(DrawController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("HTTP 404 Not Found", HttpStatus.NOT_FOUND);
		}
	}
}
