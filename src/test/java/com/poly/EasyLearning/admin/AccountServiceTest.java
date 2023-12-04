package com.poly.EasyLearning.admin;

import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.service.AccountService;
import io.jsonwebtoken.lang.Assert;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @BeforeTestClass
    public void setUp() {

    }

    /**
     *  Tests the {@code findByUsername} method in {@code AccountService}
     *  The test ensures that the method returns a non-empty {@code Optional},
     *  indicating that an account with the specified username is found.
     */
    @Test
    public void testFindByUsername() {
        String username = "tuank";
        Optional<AccountApp> accountApp = accountService.findByUsername(username);
        Assertions.assertTrue(accountApp.isPresent());
    }


}
