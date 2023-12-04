package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.entity.Question;
import com.poly.EasyLearning.exception.QuestionException;
import com.poly.EasyLearning.repository.QuestionRepository;
import com.poly.EasyLearning.service.QuestionService;
import com.poly.EasyLearning.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Question save(Question question) {
        Optional<Question> checkQuestion = questionRepository.findById(question.getId());
        if(checkQuestion.isEmpty()){
            throw new QuestionException(MessageUtils.Question.NOT_FOUND.getValue());
        }
        return questionRepository.save(question);
    }

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
