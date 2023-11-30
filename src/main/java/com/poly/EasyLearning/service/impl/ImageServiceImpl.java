package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.entity.ImageResponse;
import com.poly.EasyLearning.repository.ImageRepository;
import com.poly.EasyLearning.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    @Override
    public ImageResponse create(ImageResponse imageResponse) {
        return this.imageRepository.save(imageResponse);
    }

    @Override
    public ImageResponse update(ImageResponse imageResponse) {
        return this.imageRepository.save(imageResponse);
    }

    @Override
    public void delete(String id) {
        this.imageRepository.deleteById(id);
    }
}
