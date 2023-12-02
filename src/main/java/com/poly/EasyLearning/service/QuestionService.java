package com.poly.EasyLearning.service;

import com.poly.EasyLearning.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllByLesson_Id(Integer id);

    List<Question> getAllActiveTrue();

    Question findById(Integer id);

    Question createQuestion(Question question);

    void delete(Integer id);
    Question updateQuestion(Question question);
}
