package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

    List<Result> findByActiveTrue();

    Optional<Result> findByRoom_Id(Integer id);
}