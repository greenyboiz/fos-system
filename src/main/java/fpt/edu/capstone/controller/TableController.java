package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.IOrdersService;
import fpt.edu.capstone.implementService.IQRCodeService;
import fpt.edu.capstone.implementService.ITablesService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @GetMapping("/tables")
    public ResponseEntity<?> getAllTables(){
        List<Tables> tables = iTablesService.getAllTables();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successful",true, tables)
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @GetMapping("/tablesStaff")
    public ResponseEntity<?> getAllTablesStaff(){
        List<Tables> tables = iTablesService.getAllTablesStaff();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successful",true, tables)
        );
    }

    @GetMapping("/tableByOrder/{tableId}")
    public ResponseEntity<?> getTableByOrderId(@PathVariable("tableId") Long tableId){
        Long qrCodeId = iTablesService.getQRCodeIdByTableId(tableId);
        Orders order = iTablesService.getOrderIdByQRCodeId(qrCodeId);
        if(order != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successful",true, order)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Order not exist at table: " + tableId ,false, null)
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
                tablesOld.setStatus(true);
                iTablesService.addTable(tablesOld);

                Tables tablesNew = iTablesService.getTableByQRCodeId(qrCodeIdNew);
                tablesNew.setStatus(false);
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @PostMapping("/tables/add")
    public ResponseEntity<?> saveTable(@RequestBody Tables table){
        String qrCodeLink = table.getQrCode().getQRCodeLink();
        boolean checkQRCodeExist = iqrCodeService.checkQRCodeExist(qrCodeLink.trim());
        if(!checkQRCodeExist){
            table.getQrCode().setQRCodeLink(qrCodeLink.trim());
            iqrCodeService.addQRCode(table.getQrCode());
            table.setStatus(true);
            table.setActive(true);
            iTablesService.addTable(table);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, iTablesService.addTable(table))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "QRCode is exist",false, null)
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @PutMapping("/tables/update")
    public ResponseEntity<?> updateTable(@RequestBody Tables table){
        String qrCodeLink = table.getQrCode().getQRCodeLink();
        Tables table1 = iTablesService.getTableById(table.getTableId());
        QRCode qrCode = iqrCodeService.getQRCodeById(table1.getQrCode().getQRCodeId());

        if(qrCodeLink.trim().equals(qrCode.getQRCodeLink())){
            table.getQrCode().setQRCodeLink(qrCodeLink.trim());
            Tables tables = iTablesService.updateTable(table);
            if(tables != null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Update tableId "+ table.getTableId() + " successfull",true, tables)
                );
            }
        }
        boolean checkQRCodeExist = iqrCodeService.checkQRCodeExist(table.getQrCode().getQRCodeLink().trim());
        if(!checkQRCodeExist){
            qrCode.setQRCodeLink(qrCodeLink.trim());
            iqrCodeService.addQRCode(qrCode);
            Tables tables = iTablesService.updateTable(table);
            if(tables != null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Update tableId "+ table.getTableId() + " successfull",true, tables)
                );
            }
        }
        if(checkQRCodeExist){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "QRCode is exist in another table",false, null)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "This tableId " + table.getTableId() + " not exist",true, null)
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/tables/changeActive/{id}")
    public ResponseEntity<?> changeActiveTable(@PathVariable("id") Long id){
        boolean checkTableExist = iTablesService.checkTableExist(id);
        if(checkTableExist){
            Tables tables = iTablesService.changeActiveTable(id);
            if(tables != null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Change status of active tableId "+ id + " successfull",true, tables)
                );
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "This tableId " + id + " have people sitting, can't change the status ",true, tables)
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
