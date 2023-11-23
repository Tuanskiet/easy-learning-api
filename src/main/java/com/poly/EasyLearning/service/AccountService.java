package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.UserLogin;
import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.entity.AccountApp;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AccountService extends UserDetailsService {
    AccountApp create(UserRequest user);
}
