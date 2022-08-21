package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.IQRCodeService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getAllQRCode(){
        List<QRCode> qrCodeList = iqrCodeService.getAllQRCodes();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successfull",true, qrCodeList)
        );
    }

    @PostMapping(value = "/qrcode/add")
    public ResponseEntity<?> saveQRCode(@RequestBody QRCode qrCode){
        boolean checkQRCodeExist = iqrCodeService.checkQRCodeExist(qrCode.getQRCodeLink());
        if (!checkQRCodeExist){
            iqrCodeService.addQRCode(qrCode);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, iqrCodeService.addQRCode(qrCode))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("fail", "QRCode is exist",false, null)
        );

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
        QRCode qrCode = iqrCodeService.getQRCodeById(id);
        if(qrCode != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, qrCode)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("fail", "QRCode is not exist",false, qrCode)
        );
    }
}
