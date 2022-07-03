package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IQRCodeService {
    public QRCode addQRCode(QRCode qrCode);

    public QRCode updateQRCode(QRCode qrCode);

    public boolean deleteQRCode(Long id);

    public List<QRCode> getAllQRCodes();
    public ResponseEntity<ResponseObject> getQRCodeById(Long id);

}
