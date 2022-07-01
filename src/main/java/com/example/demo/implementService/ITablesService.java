package com.example.demo.implementService;

import com.example.demo.entities.FOSUser;
import com.example.demo.entities.Tables;
import com.example.demo.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITablesService {
    public Tables addTable(Tables table);

    public Tables updateTable(Tables table);

    public boolean deleteTable(Long id);

    public List<Tables> getAllTables();
    public ResponseEntity<ResponseObject> getTableById(Long id);

}
