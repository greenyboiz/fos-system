package fpt.edu.capstone.controller;


import fpt.edu.capstone.entities.Chat;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WebSocketController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChatRoom")
    public Chat sendMessage(@Payload Chat chat) {
        System.out.println("noi dung: " + chat.getContent());
        return chat;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public Chat addUser(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chat.getSender());
        return chat;
    }

}
