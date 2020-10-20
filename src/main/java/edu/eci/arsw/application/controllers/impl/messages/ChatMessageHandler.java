package edu.eci.arsw.application.controllers.impl.messages;

import edu.eci.arsw.application.entities.Chat;
import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Controller
public class ChatMessageHandler {

    @Autowired
    private DrawChatService drawChatService;

    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("/chatsMessages.{id}")
    public void handleMessage(Message message, @DestinationVariable long id) throws AppException {
        System.out.println("GOODIE");
        message.setFecha(new Date());
        try{
            message.setEmisor(drawChatService.getCurrentUserSession());
            System.out.println(message);
        } catch (AppException e){

        }
        try{
            drawChatService.addMessage(message);
        } catch (AppException e){
        }
        msgt.convertAndSend("/topic/chatsMessages."+id, message);
        System.out.println("SEND " + id);
    }


    @MessageMapping("/chatsCreator")
    public void handleChatCreation(Chat chat) throws AppException {
        Chat product = null;
        try {
            product = drawChatService.addChat(chat.getUser1().getTelefono(), chat.getUser2().getTelefono());
            msgt.convertAndSend("/topic/chats/users."+chat.getUser1().getTelefono(), product);
            msgt.convertAndSend("/topic/chats/users."+chat.getUser2().getTelefono(), product);
        } catch (AppException e) {
            e.printStackTrace();
        }
        msgt.convertAndSend("/topic/chatsCreator."+product.getUser2().getTelefono(), product);
        msgt.convertAndSend("/topic/chatsCreator."+product.getUser1().getTelefono(), product);
    }

}
