package com.example.demo.service;

import com.example.demo.entities.QRCode;
import com.example.demo.entities.Tables;
import com.example.demo.implementService.IQRCodeService;
import com.example.demo.repo.QRCodeRepository;
import com.example.demo.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QRCodeService implements IQRCodeService {
    @Autowired
    private QRCodeRepository qrCodeRepository;
    @Override
    public QRCode addQRCode(QRCode qrCode) {
        return qrCodeRepository.save(qrCode);
    }

    @Override
    public QRCode updateQRCode(QRCode qrCode) {
        if(qrCode != null){
            QRCode qrCodeUpdate = qrCodeRepository.getById(qrCode.getQRCodeId());
            if(qrCodeUpdate != null){
                qrCodeUpdate.setQRCodeImage(qrCode.getQRCodeImage());
                qrCodeUpdate.setQRCodeLink(qrCode.getQRCodeLink());

                return qrCodeRepository.save(qrCodeUpdate);
            }
        }
        return null;
    }

    @Override
    public boolean deleteQRCode(Long id) {
        QRCode qrCode = qrCodeRepository.getById(id);
        if(qrCode != null){
            qrCodeRepository.delete(qrCode);
            return true;
        }
        return false;
    }

    @Override
    public List<QRCode> getAllQRCodes() {
        return qrCodeRepository.findAll();
    }

    @Override
    public ResponseEntity<ResponseObject> getQRCodeById(Long id) {
        Optional<QRCode> qrCode = qrCodeRepository.findById(id);
        if(qrCode.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, qrCode)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Can not find QRCodeID: "+id,false,"null")
            );
        }
    }
}
