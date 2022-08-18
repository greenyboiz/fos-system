package fpt.edu.capstone.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.ITablesService;
import fpt.edu.capstone.repo.OrdersRepository;
import fpt.edu.capstone.repo.QRCodeRepository;
import fpt.edu.capstone.repo.TableRepository;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TableService implements ITablesService {
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Tables addTable(Tables table) {
        return tableRepository.save(table);
    }

    @Override
    public Tables updateTable(Tables table) {
//        QRCode qrCode = qrCodeRepository.findByQRCodeId(table.getQrCode().getQRCodeId());
        QRCode qrCode = qrCodeRepository.findByQRCodeLink(table.getQrCode().getQRCodeLink());
        if(table != null){
            Tables table1 = tableRepository.findTableById(table.getTableId());
            if (table1 != null){
                table1.setNumberOfSeats(table.getNumberOfSeats());
                table1.setStatus(table.getStatus());
                table1.setActive(table.getActive());
                table1.setQrCode(qrCode);

                return tableRepository.save(table1);
            }
        }
        return null;
    }

    @Override
    public boolean deleteTable(Long id) {
        Tables table = tableRepository.getById(id);
        if(table != null){
            tableRepository.delete(table);
            qrCodeRepository.delete(table.getQrCode());
            return true;    
        }
        return false;
    }

    @Override
    public List<Tables> getAllTables() {
        return tableRepository.findAll();
    }

    @Override
    public Tables getTableById(Long id) {
        return tableRepository.findTableById(id);
    }

    @Override
    public boolean checkTableExist(Long id) {
        Tables checkTableExist = tableRepository.findTableById(id);
        if (checkTableExist != null){
            return true;
        }
        return false;
    }

//    @Override
//    public Tables addTableAndQRcodeImage(MultipartFile file, Tables tables) {
//        try {
//            Map r = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));
//            String img = (String) r.get("secure_url");
//            tables.getQrCode().setQRCodeImage(img);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return tableRepository.save(tables);
//    }

    @Override
    public Long getQRCodeIdByTableId(Long tableId) {
        Long qrCodeId = tableRepository.findQRCodeIdByTableId(tableId);
        if(qrCodeId != null){
            return qrCodeId;
        }
        return null;
    }

    @Override
    public Tables getTableByQRCodeId(Long qrCodeId) {
        Tables tables = tableRepository.findTableByQRCodeId(qrCodeId);
        if(tables != null){
            return tables;
        }
        return null;
    }

    @Override
    public boolean checkTableIsEmpty(Long qrCodeIdNew) {

        return tableRepository.checkTableIsEmpty(qrCodeIdNew);
    }

    @Override
    public Tables changeActiveTable(Long id) {
        Tables tables = tableRepository.findTableById(id);
        Orders orders = ordersRepository.findOrderIdByQRCodeId(tables.getQrCode().getQRCodeId());
        if(orders == null){
            if(tables.getActive()== true){
                tables.setActive(false);
            }else {
                tables.setActive(true);
            }
            return tableRepository.save(tables);
        }
        return null;
    }

    @Override
    public List<Tables> getAllTablesStaff() {
        return tableRepository.getAllTablesStaff();
    }

    @Override
    public Orders getOrderIdByQRCodeId(Long qrCodeId) {
        Orders orders = ordersRepository.findOrderIdByQRCodeId(qrCodeId);
        if(orders != null){
            return orders;
        }
        return null;
    }
}
