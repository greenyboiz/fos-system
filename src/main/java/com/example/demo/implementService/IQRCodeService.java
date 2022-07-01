package com.example.demo.implementService;

import com.example.demo.entities.QRCode;
import com.example.demo.entities.Tables;
import com.example.demo.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IQRCodeService {
    public QRCode addQRCode(QRCode qrCode);

    public QRCode updateQRCode(QRCode qrCode);

    public boolean deleteQRCode(Long id);

    public List<QRCode> getAllQRCodes();
    public ResponseEntity<ResponseObject> getQRCodeById(Long id);

}
