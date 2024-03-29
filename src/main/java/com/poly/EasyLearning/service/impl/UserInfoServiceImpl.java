package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.ImageResponse;
import com.poly.EasyLearning.entity.UserInfo;
import com.poly.EasyLearning.repository.UserInfoRepository;
import com.poly.EasyLearning.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepo;

    @Override
    public UserInfo insert(UserInfo userInfo) {
        return userInfoRepo.save(userInfo);
    }

    @Override
    public List<UserInfo> searchUserByKeyword(String keyword) {
        return userInfoRepo.findByFullNameContainingOrEmail(keyword, keyword);
    }

    @Override
    public UserInfo findByAccount(AccountApp accountApp) {
        return userInfoRepo.findByAccount(accountApp);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepo.save(userInfo);
    }
    public UserInfo findById(Integer id) {
        return userInfoRepo.findById(id).orElse(null);
    }

    @Override
    public UserInfo findByUsername(AccountApp username) {
        return this.userInfoRepo.findByAccount(username);
    }
}
