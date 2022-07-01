package com.example.demo.repo;

import com.example.demo.entities.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode,Long> {

    QRCode findByQRCodeId(Long id);
}
