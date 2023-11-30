package com.poly.EasyLearning.service;

import com.poly.EasyLearning.entity.QuizItem;

public interface QuizItemService{

    QuizItem create(QuizItem quizItem);

    QuizItem findById(Integer id);
}
