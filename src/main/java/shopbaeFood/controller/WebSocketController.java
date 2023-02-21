package shopbaeFood.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import shopbaeFood.model.Chat;
import shopbaeFood.model.Notification;

@Controller
public class WebSocketController {

//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/publicChatRoom")
//    public Chat sendMessage(@Payload Chat chat) {
//        return chat;
//    }
//    
//    @MessageMapping("/chat.sendNotification")
//    @SendTo("/topic/publicChatRoom")
//    public Notification sendNotification(@Payload Notification notification) {
//        return notification;
//    }
//
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/publicChatRoom")
//    public Chat addUser(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
//        // Add username in web socket session
//        headerAccessor.getSessionAttributes().put("username", chat.getSender());
//        return chat;
//    }
}
