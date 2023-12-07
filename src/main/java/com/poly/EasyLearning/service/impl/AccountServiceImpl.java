package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.UpdateAccountRequest;
import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.dto.response.AccountResponse;
import com.poly.EasyLearning.dto.response.AuthResponse;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.ImageResponse;
import com.poly.EasyLearning.entity.RoleApp;
import com.poly.EasyLearning.entity.UserInfo;
import com.poly.EasyLearning.enums.RoleName;
import com.poly.EasyLearning.exception.AccountException;
import com.poly.EasyLearning.exception.RoleException;
import com.poly.EasyLearning.service.*;
import com.poly.EasyLearning.utils.MessageUtils;
import com.poly.EasyLearning.repository.AccountRepository;
import com.poly.EasyLearning.utils.UploadFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.util.stream.Collectors;

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
    private final JwtService jwtService;
    private final QuizService quizService;


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
        AccountApp accountDelete = checkAccount.get();
        if(checkAccount.get().getUserApp()!= null && checkAccount.get().getUserApp().getAvatar() != null) {
            imageStorageService.delete(checkAccount.get().getUserApp().getAvatar().getPublicId());
        }
        accountDelete.setLocked(true);
        accountRepository.save(accountDelete);
//        accountRepository.delete(checkAccount.get());
        log.info("Account with username :: {} has been blocked.", username);
    }

    //check it
    @Transactional
    @Override
    public AccountApp updateAccount(UpdateAccountRequest accountUpdate) {
        UserRequest userUpdate = accountUpdate.getUserUpdate();
        Optional<AccountApp> checkAccount = accountRepository.findByUsername(accountUpdate.getOldUsername());
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
        if(accountUpdate.getRoles() != null){
            Set<RoleApp> roles = new HashSet<>();
            accountUpdate.getRoles().forEach(role -> {
                Optional<RoleApp> roleApp = roleService.findRole(role);
                if(roleApp.isEmpty()){
                    throw new RoleException(MessageUtils.Role.NOT_FOUND.getValue());
                }else{
                    roles.add(roleApp.get());
                }
            });
            oldAccount.setRoles(roles);
        }

        AccountApp accountUpdated = accountRepository.save(oldAccount);
        log.info("Account with username :: {} has been updated, new username is :: {}",
                accountUpdate.getOldUsername(), accountUpdated.getUsername());
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

//    @Override
//    public AccountApp login(UserRequest user) {
//        Optional<AccountApp> account = accountRepository.findByUsername(user.getUsername());
//        if(account.isPresent()){
//            if(account.get().getUsername().equals(user.getUsername()) && account.get().getPassword().equals(user.getPassword())){
//                log.info(account.get().getUsername() + " " + account.get().getPassword());
//                log.info("Login success with username : {}", user.getUsername());
//                return account.get();
//            }
//            log.warn(MessageUtils.Account.WRONG_PASSWORD.getValue() + "|| username : " + user.getUsername());
//            //throw new AuthenticationFailException(MessageUtils.Account.WRONG_PASSWORD.getValue());
//        }
//        else {
//            log.warn(MessageUtils.Account.WRONG_USERNAME.getValue() + "|| username : " + user.getUsername());
//            //throw new AuthenticationFailException(MessageUtils.Account.WRONG_USERNAME.getValue());
//        }
//        return account.get();
//    }

    /**
     * Create new Account and UserInfo for this Account.
     * @param userRequest contains data fields to create a new Account.
     * @return new AuthResponse (containing jwt token) - if no error.
     * @throws AccountException – if Account already exist.
     */
    @Override
    public AuthResponse register(UserRequest userRequest) {
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
        String token = jwtService.generateToken(newAccount);
        return new AuthResponse(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  accountRepository.findByUsername(username)
                .orElseThrow(() ->  new UsernameNotFoundException(MessageUtils.Account.NOT_FOUND.getValue()));
    }

    @Override
    public Page<AccountResponse> findAllByUsername(String username, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountApp> pageAccount = accountRepository.findByUsernameContaining(username, pageable);
        List<AccountResponse> accountResList = new ArrayList<>();
        accountResList = pageAccount.stream()
                .map(account -> mapToAccountResponse(account))
                .collect(Collectors.toList());

        return new PageImpl( accountResList, pageable, pageAccount.getTotalElements());
    }

    private AccountResponse mapToAccountResponse(AccountApp account) {
        if(account.getUserApp() == null){
            return AccountResponse.builder()
                    .username(account.getUsername())
                    .provider(account.getProvider().getValue())
                    .enable(account.isEnabled())
                    .locked(account.isLocked())
                    .build();
        }

        return new AccountResponse(
                account.getId(),
                account.getUserApp().getFullName(),
                account.getUserApp().getEmail(),
                account.getUsername(),
                account.getProvider().getValue(),
                account.getRoles().stream().map( role -> role.getName().getValue()).collect(Collectors.toList()),
                account.isEnabled(),
                account.isLocked()
        );
    }

    @Override
    public Optional<AccountApp> findById(Integer id) {
        return accountRepository.findById(id);
    }
    // save account
    @Override
    public AccountApp save(AccountApp account) {
        return accountRepository.save(account);
    }
}
