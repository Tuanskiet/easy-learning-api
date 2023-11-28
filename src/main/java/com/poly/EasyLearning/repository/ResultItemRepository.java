package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.ResultItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultItemRepository extends JpaRepository<ResultItem, Integer> {
    List<ResultItem> findByResult_Id(Integer id);
}