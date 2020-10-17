package edu.eci.arsw.application.controllers.impl;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.util.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
