package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.IQRCodeService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/qrcode/add")
    private QRCode saveQRCode(@RequestBody QRCode qrCode){
        return iqrCodeService.addQRCode(qrCode);
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
