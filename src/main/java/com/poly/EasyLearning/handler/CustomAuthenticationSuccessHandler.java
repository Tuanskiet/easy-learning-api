package com.poly.EasyLearning.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.enums.Provider;
import com.poly.EasyLearning.model.MyOAuth2User;
import com.poly.EasyLearning.repository.AccountRepository;
import com.poly.EasyLearning.service.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


//@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        AccountApp accountRes = new AccountApp();

        if(authentication.getPrincipal() instanceof MyOAuth2User){
            MyOAuth2User myOAuth2User = (MyOAuth2User) authentication.getPrincipal();
            Optional<AccountApp> accountCheck = accountService.findByUsername(myOAuth2User.getEmail());
            if(accountCheck.isEmpty()){
                UserRequest newUser = new UserRequest();
                newUser.setUsername(myOAuth2User.getEmail());
                newUser.setEmail(myOAuth2User.getEmail());
                newUser.setFullName(myOAuth2User.getName());
                newUser.setPassword(passwordEncoder.encode("123"));
                newUser.setAvatar(myOAuth2User.getAvatar());
                newUser.setProvider(Provider.SOCIAL); // check it
//                accountRes = accountService.register(newUser);
            }else{
                accountRes = accountCheck.get();
            }
        }else if(authentication.getPrincipal() instanceof AccountApp){
            accountRes = (AccountApp) authentication.getPrincipal();

        }
        objectMapper.writeValue(response.getOutputStream(), new ResponseObject(
                "Login success.",
                200,
                accountRes
        ));
    }
}
