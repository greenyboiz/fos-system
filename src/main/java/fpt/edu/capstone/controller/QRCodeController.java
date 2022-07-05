package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.IQRCodeService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping( value = "/qrcode/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    private QRCode uploadQRCode(@RequestParam("file")MultipartFile file, @RequestPart("qrcode") String qrcode){
        ObjectMapper objectMapper = new ObjectMapper();
        QRCode qrCode = new QRCode();
        try {
            qrCode = objectMapper.readValue(qrcode, QRCode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return iqrCodeService.saveImageToDB(file,qrCode);
    }

    @PostMapping( value = "/qrcode/upload2", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    private QRCode uploadQRCode2(@RequestParam("file")MultipartFile file, @RequestPart("qrcode") String qrcode){
        ObjectMapper objectMapper = new ObjectMapper();
        QRCode qrCode = new QRCode();
        try {
            qrCode = objectMapper.readValue(qrcode, QRCode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return iqrCodeService.saveImageToDB2(file,qrCode);
    }


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
