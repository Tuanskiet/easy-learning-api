package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.RoleApp;
import com.poly.EasyLearning.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleApp, Integer> {
    Optional<RoleApp> findByName(RoleName roleName);
}
