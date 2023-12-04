package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.QuizItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizItemRepository extends JpaRepository<QuizItem, Integer> {
    List<QuizItem> findByQuiz_Id(Integer id);

    List<QuizItem> findByQuestion_Id(Integer id);
}
