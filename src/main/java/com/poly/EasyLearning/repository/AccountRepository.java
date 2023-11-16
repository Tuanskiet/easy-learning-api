package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.AccountApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountApp, Integer> {
    Optional<AccountApp> findByUsername(String username);
}
