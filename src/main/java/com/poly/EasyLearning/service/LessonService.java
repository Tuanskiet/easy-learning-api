package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.Lesson;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Page;
import java.util.List;

public interface LessonService {
    List<Lesson> getAllActiveTrue();

    List<Lesson> getAllActiveTrueByUser(Integer Id);

    Page<Lesson> findAll(Pageable pageable);

    Lesson create(LessonRequest lessonRequest, AccountApp accountApp);

    void delete(Integer id);

    List<Lesson> searchByKeyword(String keyword);

    Page<Lesson> searchByKeyword(String keyword, Pageable pageable);

    Lesson findById(Integer lessonId);

    void deleteById(Integer lessonId);

    Lesson uploadImage(Integer lessonId, MultipartFile imageFile);

    Lesson updateLesson(Lesson lesson);

}
