package edu.eci.arsw.application.controllers.impl.messages;

import edu.eci.arsw.application.entities.Message;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;


@Controller
public class GroupMessageHandler {

    @Autowired
    private DrawChatService drawChatService;

    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("/groupsMessages.{id}")
    public void handleGroupMessage(Message message, @DestinationVariable long id) throws AppException {
        message.setEmisor(drawChatService.getCurrentUserSession());
        message.setFecha(new Date());
        System.out.println(message);
        drawChatService.addMessage(message);
        msgt.convertAndSend("/topic/groupsMessages."+id, message);
    }
}
