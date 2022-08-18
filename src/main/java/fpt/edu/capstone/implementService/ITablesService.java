package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.response.ResponseObject;
import javafx.scene.control.Tab;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Table;
import java.util.List;

public interface ITablesService {
    public Tables addTable(Tables table);

    public Tables updateTable(Tables table);

    public boolean deleteTable(Long id);

    public List<Tables> getAllTables();
    public Tables getTableById(Long id);

    boolean checkTableExist(Long id);

//    Tables addTableAndQRcodeImage(MultipartFile file, Tables tables);

    Long getQRCodeIdByTableId(Long tableId);

    Tables getTableByQRCodeId(Long qrCodeId);

    boolean checkTableIsEmpty(Long qrCodeIdNew);

    Tables changeActiveTable(Long id);

    List<Tables> getAllTablesStaff();
}
