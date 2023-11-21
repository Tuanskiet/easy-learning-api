package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserInfoApi {
    private final UserInfoService userInfoService;

    @GetMapping("/userInfo/search/{keyword}")
    public ResponseEntity<ResponseObject> search(@PathVariable String keyword){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found user",
                        200,
                        userInfoService.searchUserByKeyword(keyword)
                )
        );
    }
}
