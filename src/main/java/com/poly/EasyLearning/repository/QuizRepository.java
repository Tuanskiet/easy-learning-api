package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    List<Quiz> findByActiveTrue();

    List<Quiz> findByTitleContainingAndActiveTrue(String key1);
}