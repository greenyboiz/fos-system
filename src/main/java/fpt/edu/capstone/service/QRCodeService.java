package fpt.edu.capstone.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.IQRCodeService;
import fpt.edu.capstone.repo.QRCodeRepository;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
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
            QRCode qrCodeUpdate = qrCodeRepository.findByQRCodeId(qrCode.getQRCodeId());
            if(qrCodeUpdate != null){
                qrCodeUpdate.setQRCodeLink(qrCode.getQRCodeLink());
                return qrCodeRepository.save(qrCodeUpdate);
            }
        }
        return null;
    }

    @Override
    public boolean deleteQRCode(Long id) {
        QRCode qrCode = qrCodeRepository.findByQRCodeId(id);
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
    public QRCode getQRCodeById(Long id) {
        QRCode qrCode = qrCodeRepository.findByQRCodeId(id);
        if(qrCode != null){
            return qrCode;
        }
        return null;
    }

    @Override
    public boolean checkQRCodeExist(String qrCodeLink) {
        QRCode qrCode = qrCodeRepository.checkQRCodeExist(qrCodeLink);
        if(qrCode != null){
            return true;
        }
        return false;
    }

    //    @Override
//    public QRCode saveImageToDB(MultipartFile file, QRCode qrCode){
////        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        if(fileName.contains("..")){
//            System.out.println("not valid");
//        }
//        try {
//            qrCode.setQRCodeImage2(Base64.getEncoder().encodeToString(file.getBytes()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        qrCode.setQRCodeImage(qrCode.getQRCodeImage());
//        qrCode.setQRCodeLink(qrCode.getQRCodeLink());
//        return qrCodeRepository.save(qrCode);
//    }

//    @Override
//    public QRCode addQRCodeToDB(MultipartFile file, QRCode qrCode) {
////        boolean checkQRCodeExist = qrCodeRepository.checkQRCodeExist(qrCode.getQRCodeImage());
//        try {
//            Map r = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));
//            String img = (String) r.get("secure_url");
//            qrCode.setQRCodeImage(img);
//            qrCode.setQRCodeLink("link"+ qrCode.getQRCodeId());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return qrCodeRepository.save(qrCode);
//    }
}
