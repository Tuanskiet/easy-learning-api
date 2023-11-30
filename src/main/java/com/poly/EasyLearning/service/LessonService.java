package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.entity.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> getAllActiveTrue();

    Lesson create(LessonRequest lessonRequest);

    Lesson update(Lesson lesson);

    void delete(Integer id);

    List<Lesson> searchByKeyword(String keyword);

    Lesson searchById(int id);

}
