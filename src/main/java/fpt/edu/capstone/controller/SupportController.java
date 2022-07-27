package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Request;
import fpt.edu.capstone.implementService.IRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Slf4j
public class SupportController {

    @Autowired
    private IRequestService iRequestService;

    @MessageMapping("/support")
    @SendTo("/topic/support")
    public Request addRequest(@Payload Request request) {
        return request;
    }
}
