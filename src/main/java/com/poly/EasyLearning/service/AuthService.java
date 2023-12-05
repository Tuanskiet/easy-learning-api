package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.AuthRequest;
import com.poly.EasyLearning.dto.response.AuthResponse;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.exception.AccountException;
import com.poly.EasyLearning.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(AuthRequest authRequest) {
        AccountApp accountApp = accountService.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new AccountException(MessageUtils.Account.NOT_FOUND.getValue()));

//        if (!passwordEncoder.matches(authRequest.getPassword(), accountApp.getPassword())) {
//            throw new AccountException("Invalid password");
//        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        String token = jwtService.generateToken(accountApp);
        return new AuthResponse(token);
    }

}
