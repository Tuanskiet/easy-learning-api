package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.entity.QuizItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizItemRepository extends JpaRepository<QuizItem, Integer> {
    List<QuizItem> findByQuiz_Id(Integer id);
}
