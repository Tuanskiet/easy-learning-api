package com.poly.EasyLearning.api;


import com.poly.EasyLearning.dto.request.AuthRequest;
import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.dto.request.UpdateAccountRequest;
import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.dto.response.AuthResponse;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.service.AccountService;
import com.poly.EasyLearning.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class AccountApi {
    private final AccountService accountService;
    private final AuthService authService;

    @PostMapping({"/sign-up", "/admin/manager-user/create"})
    public ResponseEntity<?> doSignUp(@RequestBody UserRequest userRequest){
        AuthResponse authResponse = accountService.register(userRequest);
        return ResponseEntity.status(201).body(
                new ResponseObject(
                        "Create new user",
                        201,
                        authResponse
                )
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseObject> authenticate(@RequestBody AuthRequest authRequest){
        AuthResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Authentication!",
                        200,
                        authResponse
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

//    @PostMapping("/login")
//    public ResponseEntity<?> doLogin(@RequestBody UserRequest userRequest){
//        AccountApp account = accountService.login(userRequest);
//        return ResponseEntity.status(200).body(
//                new ResponseObject(
//                        "Login success",
//                        200,
//                        account
//                )
//        );
//    }


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

    @PostMapping("/update-account")
    public ResponseEntity<?> updateAccount(@RequestBody UpdateAccountRequest accountUpdate
                                           ){
        AccountApp accountUpdated = accountService
                .updateAccount(accountUpdate);
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Account has been updated successfully",
                        200,
                        accountUpdated
                )
        );
    }

    @PatchMapping("/update-avatar")
    public ResponseEntity<?> updateAvatar(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "avatar") MultipartFile avatarFile
    ){
        if(avatarFile != null){
            AccountApp accountUpdated = accountService.updateAvatar(username, avatarFile);
            return ResponseEntity.status(200).body(
                    new ResponseObject(
                            "Update avatar successfully",
                            200,
                            accountUpdated
                    )
            );
        }else{
            throw new IllegalArgumentException("Image file cannot be null!");
        }

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

    @GetMapping("/get-account-by-token")
    public ResponseEntity<ResponseObject> getAccountByToken(
            @AuthenticationPrincipal AccountApp accountApp){
        if(accountApp != null){
            return ResponseEntity.status(201).body(
                    new ResponseObject(
                            "Get account by token.",
                            200,
                            accountApp
                    )
            );
        }else{
            throw new BadCredentialsException("Bad credentials!");
        }
    }
}