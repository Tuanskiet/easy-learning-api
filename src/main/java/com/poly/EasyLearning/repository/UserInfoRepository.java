package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    List<UserInfo> findByFullNameContainingOrEmail(String key1, String key2);
}
