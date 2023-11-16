package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.entity.UserInfo;
import com.poly.EasyLearning.repository.UserInfoRepository;
import com.poly.EasyLearning.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepo;

    @Override
    public UserInfo insert(UserInfo userInfo) {
        return userInfoRepo.save(userInfo);
    }
}
