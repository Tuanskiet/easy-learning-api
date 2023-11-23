package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.RoleApp;
import com.poly.EasyLearning.entity.UserInfo;
import com.poly.EasyLearning.enums.RoleName;
import com.poly.EasyLearning.exception.AccountException;
import com.poly.EasyLearning.exception.AuthenticationFailException;
import com.poly.EasyLearning.service.RoleService;
import com.poly.EasyLearning.service.UserInfoService;
import com.poly.EasyLearning.utils.MessageUtils;
import com.poly.EasyLearning.repository.AccountRepository;
import com.poly.EasyLearning.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RoleService roleService;

    /**
     * Create new Account and UserInfo for this Account.
     * @param userRequest contains data fields to create a new Account.
     * @return new AccountApp  just created - if no error.
     * @throws AccountException – if Account already exist.
     */
    @Override
    public AccountApp create(UserRequest userRequest) {
        Optional<AccountApp> accountCheck = accountRepository.findByUsername(userRequest.getUsername());
        if(accountCheck.isPresent()){
            log.warn(MessageUtils.Account.ALREADY_EXIST.getValue() + "|| username : " + userRequest.getUsername());
            throw new AccountException(MessageUtils.Account.ALREADY_EXIST.getValue());
        }
        AccountApp newAccount = new AccountApp(
                userRequest.getUsername(),
                userRequest.getPassword()
        );
        /* Check if the role exists in the database, add a new ROLE_USER if it doesn't exist.*/
        Set<RoleApp> roles = new HashSet<>();
        RoleApp roleApp = roleService.findRole(userRequest.getRole())
                .orElse(roleService.findRole(RoleName.ROLE_USER)
                        .orElseGet(() -> {
                            RoleApp newRole = new RoleApp();
                            newRole.setName(RoleName.ROLE_USER);
                            return roleService.create(newRole);
                        }));
        roles.add(roleApp);
        newAccount.setRoles(roles); // set role for new Account

        UserInfo userInfo = UserInfo.builder()
                .email(userRequest.getEmail())
                .fullName(userRequest.getFullName())
                .avatar(userRequest.getAvatar())
                .account(newAccount)
                .build();
        newAccount.setUserApp(userInfo); // set user info for new Account
        accountRepository.save(newAccount);
        log.info("Created new user with username : {}", userRequest.getUsername());
        return newAccount;
    }

    @Override
    public AccountApp login(UserRequest user) {
        Optional<AccountApp> account = accountRepository.findByUsername(user.getUsername());
        if(account.isPresent()){
            if(account.get().getUsername().equals(user.getUsername()) && account.get().getPassword().equals(user.getPassword())){
                log.info(account.get().getUsername() + " " + account.get().getPassword());
                log.info("Login success with username : {}", user.getUsername());
                return account.get();
            }
            log.warn(MessageUtils.Account.WRONG_PASSWORD.getValue() + "|| username : " + user.getUsername());
            throw new AuthenticationFailException(MessageUtils.Account.WRONG_PASSWORD.getValue());
        }
        else {
            log.warn(MessageUtils.Account.WRONG_USERNAME.getValue() + "|| username : " + user.getUsername());
            throw new AuthenticationFailException(MessageUtils.Account.WRONG_USERNAME.getValue());
        }
    }
}
