package com.poly.EasyLearning.service;

import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.ImageResponse;
import com.poly.EasyLearning.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo insert(UserInfo userInfo);
    List<UserInfo> searchUserByKeyword(String keyword);
    UserInfo findByAccount(AccountApp accountApp);
    UserInfo save(UserInfo userInfo);
    UserInfo findById(Integer id);

    UserInfo findByUsername(AccountApp username);
}
