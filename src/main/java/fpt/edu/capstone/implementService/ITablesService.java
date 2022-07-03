package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITablesService {
    public Tables addTable(Tables table);

    public Tables updateTable(Tables table);

    public boolean deleteTable(Long id);

    public List<Tables> getAllTables();
    public ResponseEntity<ResponseObject> getTableById(Long id);

}
