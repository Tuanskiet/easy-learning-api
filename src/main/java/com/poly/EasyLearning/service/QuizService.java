package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.QuizRequest;
import com.poly.EasyLearning.entity.Quiz;

import java.util.List;

public interface QuizService {
    List<Quiz> getAllActiveTrue();

    Quiz create(QuizRequest quizRequest);

    List<Quiz> searchByKeyword(String keyword);

    Quiz findById(Integer id);
}
