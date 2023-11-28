package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.entity.QuizItem;
import com.poly.EasyLearning.repository.QuizItemRepository;
import com.poly.EasyLearning.service.QuizItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuizItemServiceImpl implements QuizItemService {

    private final QuizItemRepository quizItemRepository;
    @Override
    public QuizItem create(QuizItem quizItem) {
        return quizItemRepository.save(quizItem);
    }

    @Override
    public QuizItem findById(Integer id) {
        return quizItemRepository.findById(id).orElse(null);
    }
}
