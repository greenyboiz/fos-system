package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.IQRCodeService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/")
public class QRCodeController {

    @Autowired
    public IQRCodeService iqrCodeService;

    @GetMapping("/qrcode")
    private List<QRCode> getAllQRCode(){
        return iqrCodeService.getAllQRCodes();
    }

    @PostMapping(value = "/qrcode/add")
    private QRCode saveQRCode(@RequestBody QRCode qrCode){
        return iqrCodeService.addQRCode(qrCode);
    }

//    @PostMapping( value = "/qrcode/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
//    private QRCode uploadQRCode(@RequestParam("file")MultipartFile file, @RequestPart("qrcode") String qrcode){
//        ObjectMapper objectMapper = new ObjectMapper();
//        QRCode qrCode = new QRCode();
//        try {
//            qrCode = objectMapper.readValue(qrcode, QRCode.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return iqrCodeService.saveImageToDB(file,qrCode);
//    }

//    @PostMapping( value = "/qrcode", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
//    private ResponseEntity<?> addQRCode(@RequestParam("file")MultipartFile file, @RequestPart("qrcode") String qrcode){
//        ObjectMapper objectMapper = new ObjectMapper();
//        QRCode qrCode = new QRCode();
//        try {
//            qrCode = objectMapper.readValue(qrcode, QRCode.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        boolean checkQRCodeExist = iqrCodeService.checkQRCodeExist(qrCode.getQRCodeLink());
//        if(file.getSize() == 0){
//            iqrCodeService.addQRCode(qrCode);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "file imange is null",true, iqrCodeService.addQRCode(qrCode))
//            );
//        }else {
//            if (!checkQRCodeExist){
//                iqrCodeService.addQRCodeToDB(file,qrCode);
//                return ResponseEntity.status(HttpStatus.OK).body(
//                        new ResponseObject("ok", "successfull",true, iqrCodeService.addQRCodeToDB(file,qrCode))
//                );
//            }else {
//                return ResponseEntity.status(HttpStatus.OK).body(
//                        new ResponseObject("fail", "QRCode is exist",false, null)
//                );
//            }
//        }
//    }


    @PutMapping("/qrcode/update")
    private QRCode updateQRCode(@RequestBody QRCode qrCode){
        return iqrCodeService.updateQRCode(qrCode);
    }

    @DeleteMapping("/qrcode/delete/{id}")
    public boolean deleteQRCode(@PathVariable("id") Long id){
        return iqrCodeService.deleteQRCode(id);
    }

    @GetMapping("/qrcode/{id}")
    ResponseEntity<ResponseObject> findQRCodeById(@PathVariable Long id){
        return iqrCodeService.getQRCodeById(id);
    }
}
