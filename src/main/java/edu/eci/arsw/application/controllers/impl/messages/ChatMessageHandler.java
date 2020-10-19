package edu.eci.arsw.application.controllers.impl.messages;

import edu.eci.arsw.application.services.DrawChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageHandler {

    @Autowired
    private DrawChatService drawChatService;

    @Autowired
    SimpMessagingTemplate msgt;


    /*
    @MessageMapping("/chats/messages.{id}")
    public void handle(Message message, @DestinationVariable long id) throws AppException {
        System.out.println("GOOD");
        System.out.println(drawChatService);
        message.setFecha(new Date());
        System.out.println(SecurityContextHolder.getContext());
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        message.setEmisor(drawChatService.getCurrentUserSession());
        System.out.println(message);
        msgt.convertAndSend("/topic/chats/messages."+id, message);
    }

     */



}
