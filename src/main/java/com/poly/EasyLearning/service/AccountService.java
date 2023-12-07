package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.UpdateAccountRequest;
import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.dto.response.AccountResponse;
import com.poly.EasyLearning.dto.response.AuthResponse;
import com.poly.EasyLearning.entity.AccountApp;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public interface AccountService{
    AuthResponse register(UserRequest user);
    Optional<AccountApp> findByUsername(String username);

    void deleteByUsername(String username);

    AccountApp updateAccount( UpdateAccountRequest accountUpdate);

    AccountApp updateAvatar(String username, MultipartFile avatarFile);

    Page<AccountResponse> findAllByUsername(String username, Integer page, Integer size);

    Optional<AccountApp> findById(Integer accountId);

    AccountApp save(AccountApp user);

//    AccountApp login(UserRequest user);

}
