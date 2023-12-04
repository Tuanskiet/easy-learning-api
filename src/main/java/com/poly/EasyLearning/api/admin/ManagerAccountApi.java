package com.poly.EasyLearning.api.admin;


import com.poly.EasyLearning.dto.response.AccountResponse;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/admin/m-account")
public class ManagerAccountApi {
    private final AccountService accountService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAccount(
            @RequestParam(name = "username") Optional<String> username,
            @RequestParam(name = "page") Optional<Integer> page,
            @RequestParam(name = "size") Optional<Integer> size

    ){
        Page<AccountResponse> accountPage = accountService
                .findAllByUsername(username.orElse(""), page.orElse(0), size.orElse(10));
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Fetch accounts",
                        200,
                        accountPage
                )
        );
    }
}
