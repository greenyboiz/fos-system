package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Tables, Long> {
    @Query("select c from Tables c where c.tableId = ?1")
    Tables findTableById(Long id);

    @Query("select c.qrCode.QRCodeId from Tables c where c.tableId = ?1 and c.active = 1")
    Long findQRCodeIdByTableId(Long tableId);

    @Query("select c from  Tables c where c.qrCode.QRCodeId = ?1 and c.active = 1")
    Tables findTableByQRCodeId(Long qrCodeId);

    @Query("select new java.lang.Boolean(count(t) > 0) from Tables t where t.qrCode.QRCodeId = ?1 and t.status = 1 and t.active = 1")
    boolean checkTableIsEmpty(Long qrCodeIdNew);

    @Query("select t from  Tables t where t.active = 1")
    List<Tables> getAllTablesStaff();
}
