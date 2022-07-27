package fpt.edu.capstone.config;

import fpt.edu.capstone.entities.Request;
import fpt.edu.capstone.entities.RequestType;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;


@Component
public class WebSocketEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(WebSocketEventListener.class));

    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(final SessionConnectedEvent event){
        LOGGER.info("have a thoong bao");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(final SessionConnectedEvent event){
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        final Long tableId = (Long) headerAccessor.getSessionAttributes().get("tableId");

//        final Request request = Request.builder()
//                .requestType(RequestType.)
    }
}
