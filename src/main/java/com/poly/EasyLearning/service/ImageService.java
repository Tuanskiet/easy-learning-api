package com.poly.EasyLearning.service;

import com.poly.EasyLearning.entity.ImageResponse;

public interface ImageService {
    ImageResponse create(ImageResponse imageResponse);
    ImageResponse update(ImageResponse imageResponse);
    void delete(String id);

}
