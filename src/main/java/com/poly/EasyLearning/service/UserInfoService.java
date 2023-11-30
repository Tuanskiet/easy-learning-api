package com.poly.EasyLearning.service;

import com.poly.EasyLearning.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo insert(UserInfo userInfo);

    List<UserInfo> searchUserByKeyword(String keyword);
    // findById
    UserInfo findById(Integer id);
}
