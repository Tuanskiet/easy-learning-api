package com.poly.EasyLearning.api;


import com.poly.EasyLearning.dto.request.UserLogin;
import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class AccountApi {
    private final AccountService accountService;

    @PostMapping({"/sign-up", "/admin/manager-user/create"})
    public ResponseEntity<?> doSignUp(@RequestBody UserRequest userRequest){
        AccountApp newAccount = accountService.create(userRequest);
        return ResponseEntity.status(201).body(
                new ResponseObject(
                        "Create new user",
                        201,
                        newAccount
                )
        );
    }

    @GetMapping({"/get-account", "/admin/m-account/get-account"})
    public  ResponseEntity<?> getInfoAccount(@RequestParam(name = "username") String username){
        Optional<AccountApp> account = accountService.findByUsername(username);
        if (account.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new ResponseObject(
                            "Account not found.",
                            404,
                            null
                    )
            );
        }
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get info account :: " + username,
                        200,
                        account
                )
        );
    }

    @DeleteMapping({"/delete-account"})
    public ResponseEntity<?> deleteAccount(@RequestParam(name = "username") String username) {
        accountService.deleteByUsername(username);
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Account has been deleted successfully :: " + username,
                        204,
                        null
                )
        );
    }


//    check account exists
    @GetMapping("/check-account-exists")
    public ResponseEntity<?> checkAccountExists(@RequestParam String username){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Check account exists",
                        200,
                        accountService.findByUsername(username).isPresent()
                )
        );
    }
}