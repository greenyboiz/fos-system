package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.dto.OrderDTO;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.IOrdersService;
import fpt.edu.capstone.implementService.IQRCodeService;
import fpt.edu.capstone.implementService.ITablesService;
import fpt.edu.capstone.response.ResponseObject;
import fpt.edu.capstone.response.ResponseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private IOrdersService iOrdersService;

    @GetMapping("/tables")
    public List<Tables> getAllTables(){
        return iTablesService.getAllTables();
    }

    @GetMapping("/tableByOrder/{tableId}")
    public ResponseEntity<?> getTableByOrderId(@PathVariable("tableId") Long tableId){
        Long qrCodeId = iTablesService.getQRCodeIdByTableId(tableId);
        Orders order = iOrdersService.getOrderIdByQRCodeId(qrCodeId);
        if(order != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successful",true, order)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Order not exist",false, null)
        );


    }

    @PutMapping("/changeTable/{tableIdOld}/{tableIdNew}")
    public ResponseEntity<?> changeTableByOrderId(
            @PathVariable("tableIdOld") Long tableIdOld,
            @PathVariable("tableIdNew") Long tableIdNew){
        try {
            Long qrCodeIdOld = iTablesService.getQRCodeIdByTableId(tableIdOld);
            Orders order = iOrdersService.getOrderIdByQRCodeId(qrCodeIdOld);
            if(order == null){
                throw new Exception();
            }
            Long qrCodeIdNew = iTablesService.getQRCodeIdByTableId(tableIdNew);
            QRCode qrCode = iqrCodeService.getQRCodeById(qrCodeIdNew);
            boolean checkTableIsEmpty = iTablesService.checkTableIsEmpty(qrCodeIdNew);
            if(checkTableIsEmpty){
                order.setQrCode(qrCode);
                iOrdersService.addOrder(order);
                // chuyển bàn cũ sang mới và update status table
                Tables tablesOld = iTablesService.getTableByQRCodeId(qrCodeIdOld);
                tablesOld.setStatus("1");
                iTablesService.addTable(tablesOld);

                Tables tablesNew = iTablesService.getTableByQRCodeId(qrCodeIdNew);
                tablesNew.setStatus("0");
                iTablesService.addTable(tablesNew);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "change table successful",true, order)
                );
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Table not empty",false, null)
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", e.getMessage(),false, null)
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/tables/add")
    public ResponseEntity<?> saveTable(@RequestBody Tables table){

        boolean checkQRCodeExist = iqrCodeService.checkQRCodeExist(table.getQrCode().getQRCodeLink());
        if(!checkQRCodeExist){
            iqrCodeService.addQRCode(table.getQrCode());
            table.setStatus("1");
            iTablesService.addTable(table);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, iTablesService.addTable(table))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("fail", "QRCode is exist",false, null)
        );
    }




    @PutMapping("/tables/update")
    public Tables updateTable(@RequestBody Tables table){
        return iTablesService.updateTable(table);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    //    @PostMapping( value = "/tables", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
//    private ResponseEntity<?> addTable(@RequestParam("file") MultipartFile file, @RequestPart("table") String table){
//        if(file.getSize() == 0){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("fail", "file null",false, null)
//            );
//        }else {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Tables tables = new Tables();
//            try {
//                tables = objectMapper.readValue(table, Tables.class);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//            iqrCodeService.addQRCode(tables.getQrCode());
//            Tables tables1 = iTablesService.addTableAndQRcodeImage(file,tables);
//
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "successfull",true, tables1)
//            );
//        }
//    }

}
