package edu.eci.arsw.application.controllers.impl;

import edu.eci.arsw.application.entities.util.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import edu.eci.arsw.application.controllers.BaseController;
import edu.eci.arsw.application.services.DrawChatService;


@Controller
public class DrawController implements BaseController {
    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    private DrawChatService drawChatService;

    @MessageMapping("/paint.{group}")
    public void handleBuyEvent(Point st, @DestinationVariable String group) throws Exception {
        System.out.println("Nuevo punto recibido en el servidor!:"+st);
        System.out.println("Session!:"+group);
        msgt.convertAndSend("/topic/paint."+group, st);
    }

}
