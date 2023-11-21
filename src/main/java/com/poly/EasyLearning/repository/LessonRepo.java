package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface LessonRepo extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByActiveTrue();
}
