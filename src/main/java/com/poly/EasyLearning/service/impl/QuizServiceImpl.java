package com.poly.EasyLearning.service.impl;


import com.poly.EasyLearning.dto.request.QuizRequest;
import com.poly.EasyLearning.entity.*;
import com.poly.EasyLearning.repository.LessonRepo;
import com.poly.EasyLearning.repository.QuizRepository;
import com.poly.EasyLearning.service.QuestionService;
import com.poly.EasyLearning.service.QuizItemService;
import com.poly.EasyLearning.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final LessonRepo lessonRepo;
    private final QuestionService questionService;
    private final QuizItemService quizItemService;
    @Override
    public List<Quiz> getAllActiveTrue() {
        return quizRepository.findByActiveTrue();
    }

    @Override
    public Quiz create(QuizRequest quizRequest, AccountApp accountApp) {
        Quiz newQuiz = new Quiz();
        BeanUtils.copyProperties(quizRequest, newQuiz);
        newQuiz.setUserInfo(accountApp.getUserApp());
        Quiz createdQuiz = quizRepository.save(newQuiz);
        quizRequest.getQuestionIdList().forEach((item) -> {
            Question question = questionService.findById(item);
            QuizItem quizItem = new QuizItem();
            quizItem.setQuiz(createdQuiz);
            quizItem.setQuestion(question);
            quizItemService.create(quizItem);
        });
        return createdQuiz;
    }

    @Override
    public List<Quiz> searchByKeyword(String keyword) {
        return quizRepository.findByTitleContainingAndActiveTrue(keyword);
    }

    @Override
    public Quiz findById(Integer id) {
        return quizRepository.findById(id).orElse(null);
    }


}
