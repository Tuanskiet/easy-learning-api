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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AccountService accountService;

    public AuthResponse authenticate(AuthRequest authRequest) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        AccountApp accountApp = accountService.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new AccountException(MessageUtils.Account.NOT_FOUND.getValue()));
        String token = jwtService.generateToken(accountApp);
        return new AuthResponse(token);
    }

}
