package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LessonService {
    List<Lesson> getAllActiveTrue();

    Lesson create(LessonRequest lessonRequest, AccountApp accountApp);

    void delete(Integer id);

    List<Lesson> searchByKeyword(String keyword);

    Lesson findById(Integer lessonId);

    void deleteById(Integer lessonId);

    Lesson uploadImage(Integer lessonId, MultipartFile imageFile);

    Lesson updateLesson(Lesson lesson);

    UserInfo findByIdLesson(Integer id);
}
