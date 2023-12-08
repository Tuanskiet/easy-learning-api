package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RequestMapping
public interface LessonRepo extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByActiveTrue();

    List<Lesson> findByUserInfoIdAndActiveTrue(Integer key);

    List<Lesson> findByTitleContainingOrDescriptionContainingAndActiveTrue(String key1, String key2);

    Page<Lesson> findByTitleContainingOrDescriptionContaining(String keyword, String keyword1, Pageable pageable);
}
