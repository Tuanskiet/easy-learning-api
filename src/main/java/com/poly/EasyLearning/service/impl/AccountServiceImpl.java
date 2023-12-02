package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.ImageResponse;
import com.poly.EasyLearning.entity.RoleApp;
import com.poly.EasyLearning.entity.UserInfo;
import com.poly.EasyLearning.enums.RoleName;
import com.poly.EasyLearning.exception.AccountException;
import com.poly.EasyLearning.exception.RoleException;
import com.poly.EasyLearning.service.ImageStorageService;
import com.poly.EasyLearning.service.RoleService;
import com.poly.EasyLearning.service.UserInfoService;
import com.poly.EasyLearning.utils.MessageUtils;
import com.poly.EasyLearning.repository.AccountRepository;
import com.poly.EasyLearning.service.AccountService;
import com.poly.EasyLearning.utils.UploadFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ImageStorageService storageService;
    private final UserInfoService userInfoService;
    private final ImageStorageService imageStorageService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AccountApp> user = accountRepository.findByUsername(username);
        if (user.isPresent()){
            return user.get();
        }else {
            throw new UsernameNotFoundException(MessageUtils.Account.NOT_FOUND.getValue());
        }
    }

    public Optional<AccountApp> findByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    /**
     * Delete all the related date,
     * including UserInFo, Lesson, Quiz, quizItems, Room, Question, Result, ResultItem.
     * @param username is the account information will be deleted.
     * @return void if delete successful.
     * @throws AccountException – if Account is not exist.
     */
    @Transactional
    @Override
    public void deleteByUsername(String username) {
        Optional<AccountApp> checkAccount = accountRepository.findByUsername(username);
        if (checkAccount.isEmpty()){
            throw new AccountException(MessageUtils.Account.NOT_FOUND.getValue());
        }
        imageStorageService.delete(checkAccount.get().getUserApp().getAvatar().getPublicId());
        accountRepository.delete(checkAccount.get());
        log.info("Account with username :: {} has been deleted.", username);
    }

    //check it
    @Override
    public AccountApp updateAccount(String oldUsername, UserRequest userUpdate) {
        Optional<AccountApp> checkAccount = accountRepository.findByUsername(oldUsername);
        if (checkAccount.isEmpty()){
            throw new AccountException(MessageUtils.Account.NOT_FOUND.getValue());
        }
        AccountApp oldAccount = checkAccount.get();
//        BeanUtils.copyProperties(userUpdate, oldAccount, "id", "password");
//        oldAccount.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        if(userUpdate.getUsername() != null){
            oldAccount.setUsername(userUpdate.getUsername());
        }
        oldAccount.setUserApp(
                UserInfo.builder()
                        .fullName(userUpdate.getFullName())
                        .email(userUpdate.getEmail())
                        .fullName(userUpdate.getAvatar())
                        .build()
        );
        Optional<RoleApp> roleApp = roleService.findRole(userUpdate.getRole());
        if(roleApp.isEmpty()){
            throw new RoleException(MessageUtils.Role.NOT_FOUND.getValue());
        }else{
            Set<RoleApp> roles = new HashSet<>();
            roles.add(roleApp.get());
            oldAccount.setRoles(roles);
        }
        AccountApp accountUpdated = accountRepository.save(oldAccount);
        log.info("Account with username :: {} has been updated, new username is :: {}", oldUsername, accountUpdated.getUsername());
        return accountUpdated;
    }

    @Override
    public AccountApp updateAvatar(String username, MultipartFile avatarFile) {
        Optional<AccountApp> checkAccount = accountRepository.findByUsername(username);
        if (checkAccount.isEmpty()){
            throw new AccountException(MessageUtils.Account.NOT_FOUND.getValue());
        }
        ImageResponse imgResponse = storageService
                .upload(avatarFile, UploadFolder.ACCOUNT, String.valueOf(checkAccount.get().getId()));
        UserInfo userInfo = userInfoService.findByAccount(checkAccount.get());
        userInfo.setAvatar(imgResponse);
        userInfoService.save(userInfo);
        return checkAccount.get();
    }

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
                passwordEncoder.encode(userRequest.getPassword()),
                userRequest.getProvider()
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
                .account(newAccount)
                .build();
        newAccount.setUserApp(userInfo); // set user info for new Account
        accountRepository.save(newAccount);
        log.info("Created new user with username : {}", userRequest.getUsername());
        return newAccount;
    }
}
