package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.dto.request.QuizRequest;
import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.entity.Quiz;
import com.poly.EasyLearning.repository.LessonRepo;
import com.poly.EasyLearning.repository.QuizRepository;
import com.poly.EasyLearning.service.LessonService;
import com.poly.EasyLearning.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    @Override
    public List<Quiz> getAllActiveTrue() {
        return quizRepository.findByActiveTrue();
    }

    @Override
    public Quiz create(QuizRequest quizRequest) {
        Quiz newQuiz = new Quiz();
        BeanUtils.copyProperties(quizRequest, newQuiz);
        return quizRepository.save(newQuiz);
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
