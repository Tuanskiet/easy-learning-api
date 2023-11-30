package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {

    List<Result> findByActiveTrue();
}