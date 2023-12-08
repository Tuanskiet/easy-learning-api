package com.poly.EasyLearning.api;



import com.poly.EasyLearning.entity.ImageResponse;
import com.poly.EasyLearning.dto.response.ResponseObject;

import com.poly.EasyLearning.service.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UploadApi {

    private final ImageStorageService storageService;

    // nếu muốn lưu ảnh vào database, => lưu publicId và url image (các field trong ImageResponse) vào database
    // publicId sẽ được sử dụng để thao tác với Cloudinary (xóa)
    @PostMapping("/file/upload")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("image")MultipartFile file) throws IOException {

        ImageResponse imageResponse = storageService.upload( file,"/lesson", System.currentTimeMillis() + "");

        return ResponseEntity.status(201).body(new ResponseObject(
                "Upload image successfully!",
                201,
                imageResponse
        ));
    }

    @PutMapping("/file/update")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("image")MultipartFile file, String folder, String fileName) throws IOException {
        System.out.println("Upload image ....");
        ImageResponse imageResponse = storageService.upload( file,folder, fileName);
        return ResponseEntity.status(201).body(new ResponseObject(
                "Upload image successfully!",
                201,
                imageResponse
        ));
    }   

    @DeleteMapping("/file/delete")
    public ResponseEntity<?> deleteFile(@RequestParam("publicId") String publicId) throws IOException {
        storageService.delete(publicId);
        return ResponseEntity.status(204).body(null);
    }

}
