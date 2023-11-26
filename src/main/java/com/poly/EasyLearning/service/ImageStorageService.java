package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageStorageService {
    ImageResponse upload(MultipartFile multipartFile, String folder, String nameImg);
    void delete(String publicId);
}
