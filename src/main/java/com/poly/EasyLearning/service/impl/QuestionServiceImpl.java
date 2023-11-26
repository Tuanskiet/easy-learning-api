package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.entity.Question;
import com.poly.EasyLearning.repository.QuestionRepository;
import com.poly.EasyLearning.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    @Override
    public List<Question> getAllByLesson_Id(Integer id) {
        return questionRepository.findAllByLesson_Id(id);
    }
}
