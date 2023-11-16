package com.poly.EasyLearning.api;


import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class AccountApi {

    private final AccountService accountService;
    @PostMapping("/sign-up")
    public ResponseEntity<?> doSignUp(@RequestBody UserRequest userRequest){
        AccountApp newAccount = accountService.create(userRequest);
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Create new user",
                        204,
                        newAccount
                )
        );
    }
}
