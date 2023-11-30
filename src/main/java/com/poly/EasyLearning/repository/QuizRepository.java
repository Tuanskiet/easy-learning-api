package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    List<Quiz> findByActiveTrue();

    List<Quiz> findByTitleContainingAndActiveTrue(String key1);
}