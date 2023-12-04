package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.ResultItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultItemRepository extends JpaRepository<ResultItem, Integer> {
    List<ResultItem> findByResult_Id(Integer id);


}