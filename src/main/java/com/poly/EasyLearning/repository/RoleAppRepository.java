package com.poly.EasyLearning.repository;

import java.util.Optional;

import javax.management.relation.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.RoleApp;
import com.poly.EasyLearning.enums.RoleName;

@Repository
public interface RoleAppRepository extends JpaRepository<RoleApp, Integer> {
    // findByName
    Optional<RoleApp> findByName(String role);
}
