package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.IQRCodeService;
import fpt.edu.capstone.implementService.ITablesService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TableController {
    @Autowired
    private ITablesService iTablesService;

    @Autowired
    private IQRCodeService iqrCodeService;

    @GetMapping("/tables")
    private List<Tables> getAllTables(){
        return iTablesService.getAllTables();
    }
    @PostMapping("/tables/add")
    private Tables saveTable(@RequestBody Tables table){
        return iTablesService.addTable(table);
    }

    @PostMapping( value = "/tables", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    private ResponseEntity<?> addTable(@RequestParam("file") MultipartFile file, @RequestPart("table") String table){
        if(file.getSize() == 0){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("fail", "file null",false, null)
            );
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            Tables tables = new Tables();
            try {
                tables = objectMapper.readValue(table, Tables.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            iqrCodeService.addQRCode(tables.getQrCode());
            Tables tables1 = iTablesService.addTableAndQRcodeImage(file,tables);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, tables1)
            );
        }
    }


    @PutMapping("/tables/update")
    private Tables updateTable(@RequestBody Tables table){
        return iTablesService.updateTable(table);
    }

    @DeleteMapping("/tables/delete/{id}")
    public ResponseEntity<?> deleteTable(@PathVariable("id") Long id){
        boolean checkTableExist = iTablesService.checkTableExist(id);
        if(checkTableExist){
            iTablesService.deleteTable(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "delete tableId "+ id + " successfull",true, null)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "This tableId " + id + " not exist",true, null)
            );
        }
    }

    @GetMapping("/tables/{id}")
    public ResponseEntity<?> findTableById(@PathVariable Long id){
        Tables tables = iTablesService.getTableById(id);
        if (tables != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, tables)
            );
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("fail", "table id = " + id + " not exist",false, null)
            );
        }
    }

}
