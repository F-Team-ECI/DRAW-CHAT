package edu.eci.arsw.application.sockets;

import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import java.security.Principal;
import java.util.Arrays;

public class TopicSubscriptionInterceptor extends ChannelInterceptorAdapter {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(TopicSubscriptionInterceptor.class);


    @Autowired
    private DrawChatService drawChatService;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            Principal userPrincipal = headerAccessor.getUser();
            System.out.println(userPrincipal);
            if (!validateSubscription(userPrincipal, headerAccessor.getDestination())) {
                throw new IllegalArgumentException("No permission for this topic");
            }
        }
        return message;
    }

    private boolean validateSubscription(Principal principal, String topicDestination) {
        String list[] = topicDestination.split("\\.", -1);
        System.out.println(Arrays.toString(list));
        String dest = list[0];
        long user = Long.parseLong(principal.getName());
        int var;
        try {
            var = Integer.parseInt(list[1]);
        }catch (NumberFormatException e){
            return true;
        }
        Group group = new Group();
        group.setId(var);
        System.out.println(var + user);
        if (principal == null) {
            // unauthenticated user
            return false;
        } else if (dest.equals("/topic/paint") || dest.equals("/groupsMessages")){
            try {
                //drawChatService.belongMemberToGroup(user, group);
            }catch (Exception e){
                return false;
            }

        }

        System.out.println("Validate subscription for {} to topic {}" + principal.getName()+ topicDestination);
        logger.debug("Validate subscription for {} to topic {}", principal.getName(), topicDestination);
        //Additional validation logic coming here
        return true;
    }

}