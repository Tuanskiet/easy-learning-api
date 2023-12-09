package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.AuthRequest;
import com.poly.EasyLearning.dto.response.AuthResponse;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.exception.AccountException;
import com.poly.EasyLearning.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;

    public ResponseObject authenticate(AuthRequest authRequest) {
        try{
            AccountApp accountApp = accountService.findByUsername(authRequest.getUsername())
                    .orElseThrow(() -> new AccountException(MessageUtils.Account.NOT_FOUND.getValue()));
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            String token = jwtService.generateToken(accountApp);
            return new ResponseObject("Authentication success!",200,  new AuthResponse(token));
        }catch (NoSuchElementException e){
            return new ResponseObject("Authentication fail!",404,  e.getMessage());
        }catch (BadCredentialsException e){
            return new ResponseObject("Authentication fail!",401,  e.getMessage());
        }
        catch (Exception e){
            return new ResponseObject("Authentication fail!",500,  e.getMessage());
        }
    }

}
