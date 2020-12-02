package edu.eci.arsw.application.controllers.impl.messages;

import edu.eci.arsw.application.entities.util.Line;
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

    @MessageMapping("/paint.{id}")
    public void handleBuyEvent(Line line, @DestinationVariable int id) throws Exception {
        System.out.println(line);

        System.out.println("Nuevo punto recibido en el servidor!:"+id);
        msgt.convertAndSend("/topic/paint."+id, line);
        drawChatService.saveDrawLine(id, line);
    }

}
