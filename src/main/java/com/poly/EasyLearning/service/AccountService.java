package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.UserLogin;
import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.entity.AccountApp;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


public interface AccountService extends UserDetailsService {
    AccountApp create(UserRequest user);
    Optional<AccountApp> findByUsername(String username);

    void deleteByUsername(String username);
}
