//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/FileUpLoad")
//public class FileUpLoadController {
//
//    @Autowired
//    private IOStorageService storageService;
//
//    @PostMapping("/uploadFile")
//    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String generatedFileName = storageService.storeFile(file);
//            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Upload file success");
//        } catch (Exception ex) {
//            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "Can not upload file");
//        }
//    }
//}
