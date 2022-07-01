package com.example.demo.controller;

import com.example.demo.entities.Dishes;
import com.example.demo.entities.QRCode;
import com.example.demo.implementService.IQRCodeService;
import com.example.demo.repo.QRCodeRepository;
import com.example.demo.response.ResponseObject;
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
