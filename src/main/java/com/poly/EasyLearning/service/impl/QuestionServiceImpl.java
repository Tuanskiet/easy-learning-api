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

    @Override
    public List<Question> getAllActiveTrue() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(Integer id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void delete(Integer id) {
        this.questionRepository.deleteById(id);
    }

    @Override
    public Question updateQuestion(Question question) {
        Question currentQuestion = questionRepository.findById(question.getId()).get();
        if(currentQuestion != null){
            currentQuestion.setQuestion(question.getQuestion());
            currentQuestion.setAnswerA(question.getAnswerA());
            currentQuestion.setAnswerB(question.getAnswerB());
            currentQuestion.setAnswerC(question.getAnswerC());
            currentQuestion.setCorrectAnswer(question.getCorrectAnswer());
        }
        return questionRepository.save(currentQuestion);
    }
}
