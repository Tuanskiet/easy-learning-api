package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.repository.LessonRepo;
import com.poly.EasyLearning.service.LessonService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepo lessonRepo;
    @Override
    public List<Lesson> getAllActiveTrue() {
        return lessonRepo.findByActiveTrue();
    }

    @Override
    public Lesson create(LessonRequest lessonRequest) {
        Lesson newLesson = new Lesson();
        BeanUtils.copyProperties(lessonRequest, newLesson);
        Lesson createdLesson = lessonRepo.save(newLesson);
        createdLesson.getQuestions().forEach(question ->{
            question.setLesson(createdLesson);
        });
        return lessonRepo.save(createdLesson);
    }

    @Override
    public Lesson update(Lesson lesson) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        this.lessonRepo.deleteById(id);
    }

    @Override
    public List<Lesson> searchByKeyword(String keyword) {
        return lessonRepo.findByTitleContainingOrDescriptionContainingAndActiveTrue(keyword, keyword);
    }

    @Override
    public Lesson searchById(int id) {
        return lessonRepo.findById(id).orElseThrow();
    }
}
