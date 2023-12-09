package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.QuizRequest;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.Quiz;
import com.poly.EasyLearning.entity.UserInfo;

import java.util.List;

public interface QuizService {
    List<Quiz> getAllActiveTrue();

    Quiz create(QuizRequest quizRequest, AccountApp accountApp);

    List<Quiz> searchByKeyword(String keyword);

    Quiz findById(Integer id);
    void delete(Integer id);

    UserInfo findByIdQuiz(Integer id);

}
