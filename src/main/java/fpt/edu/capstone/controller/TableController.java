package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.ITablesService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TableController {
    @Autowired
    private ITablesService iTablesService;

    @GetMapping("/tables")
    private List<Tables> getAllTables(){
        return iTablesService.getAllTables();
    }
    @PostMapping("/tables/add")
    private Tables saveTable(@RequestBody Tables table){
        return iTablesService.addTable(table);
    }

    @PutMapping("/tables/update")
    private Tables updateTable(@RequestBody Tables table){
        return iTablesService.updateTable(table);
    }

    @DeleteMapping("/tables/delete/{id}")
    public boolean deleteTable(@PathVariable("id") Long id){
        return iTablesService.deleteTable(id);
    }

    @GetMapping("/tables/{id}")
    ResponseEntity<ResponseObject> findTableById(@PathVariable Long id){
        return iTablesService.getTableById(id);
    }

}
