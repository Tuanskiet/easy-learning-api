package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.AuthRequest;
import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.dto.response.AuthResponse;
import com.poly.EasyLearning.entity.AccountApp;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public interface AccountService extends UserDetailsService{
    AuthResponse register(UserRequest user);
    Optional<AccountApp> findByUsername(String username);

    void deleteByUsername(String username);

    AccountApp updateAccount(String oldUsername, UserRequest userUpdate);

    AccountApp updateAvatar(String username, MultipartFile avatarFile);

//    AccountApp login(UserRequest user);
}
