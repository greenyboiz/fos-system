package fpt.edu.capstone.controller;


import fpt.edu.capstone.entities.*;
import lombok.Data;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WebSocketController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/staffRoom")
    public TableWs sendMessage(@Payload Tables tables) {
        TableWs tableWs = new TableWs();
        tableWs.setTableId(tables.getTableId());
        tableWs.setType("order");
        System.out.println("noi dung: " + tables.getTableId());
        return tableWs;
    }

    @MessageMapping("/chat.sendSupportChangeTable")
    @SendTo("/topic/staffRoom")
    public TableWs sendSupportChangeTable(@Payload Tables tables) {
        TableWs tableWs = new TableWs();
        tableWs.setTableId(tables.getTableId());
        tableWs.setType("change_table");
        System.out.println("noi dung: " + tables.getTableId());
        return tableWs;
    }
    @MessageMapping("/chat.sendSupportTakeAway")
    @SendTo("/topic/staffRoom")
    public TableWs sendSupportTakeAway(@Payload Tables tables) {
        TableWs tableWs = new TableWs();
        tableWs.setTableId(tables.getTableId());
        tableWs.setType("take_away");
        System.out.println("noi dung: " + tables.getTableId());
        return tableWs;
    }

    @MessageMapping("/chat.sendSupportCallOut")
    @SendTo("/topic/staffRoom")
    public TableWs sendSupportCallOut(@Payload Tables tables) {
        TableWs tableWs = new TableWs();
        tableWs.setTableId(tables.getTableId());
        tableWs.setType("call_out");
        System.out.println("noi dung: " + tables.getTableId());
        return tableWs;
    }
    @Data
    class TableWs{
        Long tableId;
        String type;
    }

    @MessageMapping("/chat.sendOrder")
    @SendTo("/topic/staffRoom")
    public DishesWs sendOrder(@Payload List<OrderItem> orderItemList) {
        DishesWs dishesWs = new DishesWs();
        List<Long> dishesIdList = new ArrayList<>();
        for (OrderItem orderItem: orderItemList
             ) {
            dishesIdList.add(orderItem.getDishes().getDishesId());
        }
        dishesWs.setList(dishesIdList);
        return dishesWs;
    }
    @Data
    class DishesWs{
        List<Long> list;
    }

}
