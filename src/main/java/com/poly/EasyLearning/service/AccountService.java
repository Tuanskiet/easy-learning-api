package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.entity.AccountApp;


public interface AccountService {
    AccountApp create(UserRequest user);
    AccountApp login(UserRequest user);
}
