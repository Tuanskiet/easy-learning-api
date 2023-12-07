package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserInfoApi {
    private UserInfoService userInfoService;
    @Autowired
    public UserInfoApi(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/userInfo/search")
    public ResponseEntity<ResponseObject> search(@RequestParam(required = false) String keyword){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found user",
                        200,
                        userInfoService.searchUserByKeyword(keyword)
                )
        );
    }

    @GetMapping("/userInfo/get-user-by-token")
    public ResponseEntity<ResponseObject> getByToken(@AuthenticationPrincipal AccountApp accountApp){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found user",
                        200,
                        userInfoService.findByUsername(accountApp)
                )
        );
    }
}
