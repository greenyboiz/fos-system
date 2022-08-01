package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode,Long> {

    QRCode findByQRCodeId(Long id);

    @Query("select c from QRCode c where c.QRCodeLink = ?1")
    QRCode checkQRCodeExist(String qrCodeLink);

    @Query("select c from QRCode c where c.QRCodeLink = ?1")
    QRCode findByQRCodeLink(String qrCodeLink);
}
