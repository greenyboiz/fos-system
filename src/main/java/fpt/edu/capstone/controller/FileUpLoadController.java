//package fpt.edu.capstone.controller;
//
//import fpt.edu.capstone.response.ResponseObject;
//import fpt.edu.capstone.service.IOStorageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/FileUpLoad")
//public class FileUpLoadController {
//
//    @Autowired
//    private IOStorageService storageService;
//
//    @PostMapping("/uploadFile")
//    public ResponseObject uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String generatedFileName = storageService.storeFile(file);
////            return new ResponseObject(Enums.ResponseStatus.SUCCESS.getStatus(), "Upload file success");
//
//            return new ResponseObject("ok", "Upload file success",true, generatedFileName);
//
//
//        } catch (Exception ex) {
//
//            return new ResponseObject("fail", "Upload file unsuccess",true, null);
//
//        }
//    }
//
////    @PostMapping("/upload-file")
////    public String uploadFile(@RequestParam("file")MultipartFile file){
////        System.out.println(file.getOriginalFilename());
////        System.out.println(file.getName());
////        return "upload imange successfully";
////    }
//}
