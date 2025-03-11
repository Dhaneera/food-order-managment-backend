package com.foodmanagement.Controller;

import com.foodmanagement.Entity.ImageStore;
import com.foodmanagement.Service.ImageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/images")
public class ImageStoreController {

    @Autowired
    private ImageStoreService imageStoreService;

    @PostMapping("/upload")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("id") long id) {
            try{
            ImageStore savedImage = imageStoreService.saveImage(file,id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(savedImage.getFileData(),headers,HttpStatus.OK);
            } catch (IOException e) {
                log.error("Error uploading file {}", e.getMessage());
                throw new RuntimeException(e);
            }
    }
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        return imageStoreService.getImageById(id)
                .map(image -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                        .body(image.getFileData()))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/image/base64/{id}")
    public ResponseEntity<byte[]> getImageBase64(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        byte[] base64Image = imageStoreService.getImageBase64ById(id);
        return new ResponseEntity(base64Image,headers,HttpStatus.OK);
    }
}