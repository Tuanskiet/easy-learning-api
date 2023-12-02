package com.poly.EasyLearning.service;

import com.poly.EasyLearning.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllByLesson_Id(Integer id);

    List<Question> getAllActiveTrue();

    Question findById(Integer id);

    Question save(Question question);
}
