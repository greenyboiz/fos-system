package fpt.edu.capstone.controller;


import fpt.edu.capstone.entities.Chat;
import fpt.edu.capstone.entities.Tables;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WebSocketController {

//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/staffRoom")
//    public Chat sendMessage(@Payload Chat chat) {
//        System.out.println("noi dung: " + chat.getContent());
//        return chat;
//    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/staffRoom")
    public Tables sendMessage(@Payload Tables tables) {
        System.out.println("noi dung: " + tables.getTableId());
        return tables;
    }

//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/staffRoom")
//    public Chat addUser(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
//        // Add username in web socket session
//        headerAccessor.getSessionAttributes().put("username", chat.getSender());
//        return chat;
//    }

}
