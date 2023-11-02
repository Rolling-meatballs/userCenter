package com.murphy.backend.service;
import java.util.Date;

import com.murphy.backend.model.domain.User;
import jakarta.annotation.Resource;
//import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
/**
    * user service test
    *
    * @author: murphy
 */


/*
    * @description: UserServiceTest
    * @param: null
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("murphy");
        user.setUserAccount("murphy_account");
        user.setAvatarUrl("dfsdf");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("1123");
        user.setEmail("123");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        boolean result = userService.save(user);
        System.out.println("test");
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "test1User";
        String userPassword = "<>";
        String checkPassword = "<PASSWORD>";
        long userId = userService.userRegister(userAccount, userPassword, checkPassword);
        System.out.println(userId);
        assertEquals(-1, userId);
        userPassword = "12";
        checkPassword = "12";
        userId= userService.userRegister(userAccount, userPassword, checkPassword);
        System.out.println(userId);
        assertEquals(-1, userId);
        userAccount = "murphy";
        userPassword = "<PASSWORD>";
        checkPassword = "<PASSWORD>";
        userId= userService.userRegister(userAccount, userPassword, checkPassword);
        System.out.println(userId);
        assertEquals(-1, userId);
        userAccount = "test User";
        userPassword = "<PASSWORD>";
        userId= userService.userRegister(userAccount, userPassword, checkPassword);
        System.out.println(userId);
        assertEquals(-1, userId);
        userAccount = "tes";
        userPassword = "<PASSWORD>";
        userId= userService.userRegister(userAccount, userPassword, checkPassword);
        System.out.println(userId);
        assertEquals(-1, userId);
        userAccount = "testUser";
        userPassword = "<PASSWORD>";

        userId= userService.userRegister(userAccount, userPassword, checkPassword);
        System.out.println("end");
        System.out.println(userId);
        assertTrue(userId > 0);
    }

    @Test
    void userLogin() {
        String userAccount = "murphy";
        String userPassword = "<PASSWORD>";
        User user = userService.userLogin(userAccount, userPassword, null );
        System.out.println(user.getId());
        assertEquals(-1, user.getId());
    }
}