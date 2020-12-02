package edu.eci.arsw.application.sockets;

import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.services.DrawChatService;
import edu.eci.arsw.application.services.impl.DrawChatServiceImpl;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import java.security.Principal;
import java.util.Arrays;


public class TopicSubscriptionInterceptor extends ChannelInterceptorAdapter {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(TopicSubscriptionInterceptor.class);


    public DrawChatService drawChatService = new DrawChatServiceImpl();


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            Principal userPrincipal = headerAccessor.getUser();
            System.out.println(userPrincipal);
            try {
                if (!validateSubscription(userPrincipal, headerAccessor.getDestination())) {
                    throw new IllegalArgumentException("No permission for this topic");
                }
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    private boolean validateSubscription(Principal principal, String topicDestination) throws AppException {
        String list[] = topicDestination.split("\\.", -1);
        System.out.println(Arrays.toString(list));
        String dest = list[0];
        System.out.println(drawChatService);
        long phone;
        try {
            phone = Long.parseLong(principal.getName());
            System.out.println(phone);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return true;
        }
        Group group = new Group();
        System.out.println(phone);
        if (principal == null) {
            // unauthenticated user
            return false;
        } else if (dest.equals("/topic/paint") || dest.equals("/topic/groupsMessages")){
            try {
                int gId = Integer.parseInt(list[1]);
                group.setId(gId);
                System.out.println(drawChatService);
                //drawChatService.belongMemberToGroup(phone, group);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }

        System.out.println("Validate subscription for {} to topic {}" + principal.getName()+ topicDestination);
        logger.debug("Validate subscription for {} to topic {}", principal.getName(), topicDestination);
        //Additional validation logic coming here
        return true;
    }

    @Bean
    public DrawChatService drawChatService() {
        return new DrawChatServiceImpl();
    }

}