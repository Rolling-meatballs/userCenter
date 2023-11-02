package com.murphy.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.murphy.backend.common.ErrorCode;
import com.murphy.backend.exception.BusinessException;
import com.murphy.backend.model.domain.User;
import com.murphy.backend.service.UserService;
import com.murphy.backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.murphy.backend.contant.UserContant.USER_LOGIN_STATE;

/**
* @author murphy
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-10-19 09:19:27
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>

    implements UserService{

    /*
        * salt, used to encode userPassword
     */
    private static final String SALT = "123456";

    @Resource
    private UserMapper userMapper;


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. validate userAccount
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User info can not be blank");
        }
        if (userAccount.length() < 6 || userAccount.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User account length must be between 6 and 20");
        }
        if (userPassword.length() < 6 || userPassword.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User password length must be between 6 and 20");
        }

        // userAccount can not include special characters
        String regex = "^[a-zA-Z0-9_]+$";
        if (!userAccount.matches(regex)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User account can not include special characters");
        }

        // userPassword is equal to checkPassword
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User password is not equal to check password");
        }

        // userAccount is unique
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.USER_EXIST_ERROR, "User account already exists");
        }
//        User user = this.getOne(null);
//        if (user != null) {
//            return -1;
//        }
        // 2. encode userPassword

        String encodePassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes(StandardCharsets.UTF_8));
        System.out.println(encodePassword);
        // 3. save user
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encodePassword);
        boolean saveUser = userMapper.insert(user) > 0;

        // 4. return userId
        if (!saveUser) {
            throw new BusinessException(ErrorCode.USER_CREATE_ERROR, "User create failed");
        }

        return user.getId();
    }



    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. validate userAccount
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User info can not be blank");
        }
        if (userAccount.length() < 6 || userAccount.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User account length must be between 6 and 20");
        }
        if (userPassword.length() < 6 || userPassword.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "User password length must be between 6 and 20");
        }

        String encodePassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes(StandardCharsets.UTF_8));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encodePassword);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            log.info("user not found");
            throw new BusinessException(ErrorCode.USER_NOT_EXIST_ERROR, "User not found");
        }

        // 2. important userinfo can not return to frontend
        User safeUser = getSafeUser(user);

        // 3. Record user session
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);
        // log user login
        System.out.println(safeUser);
        System.out.println(safeUser.getId());
        return safeUser;
    }

    /**
     * get safe user
     *
     * @param user user
     * @return safe user
     */
    public User getSafeUser(User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST_ERROR, "User not found");
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setPhone(user.getPhone());
        safeUser.setUserRole(user.getUserRole());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        return safeUser;
    }

    /**
     * user logout
     *
     * @param request request
     * @return false
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }
}




