package com.poly.EasyLearning.service;

import com.poly.EasyLearning.entity.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    ImageResponse upload(MultipartFile multipartFile, String folder, String nameImg);
    void delete(String publicId);
}
