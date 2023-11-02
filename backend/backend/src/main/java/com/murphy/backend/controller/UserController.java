package com.murphy.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.murphy.backend.common.BaseResponse;
import com.murphy.backend.common.ErrorCode;
import com.murphy.backend.common.ResultUtils;
import com.murphy.backend.exception.BusinessException;
import com.murphy.backend.model.domain.User;
import com.murphy.backend.model.domain.request.UserLoginRequest;
import com.murphy.backend.model.domain.request.UserRegisterRequest;
import com.murphy.backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.murphy.backend.contant.UserContant.DEFAULT_ROLE;
import static com.murphy.backend.contant.UserContant.USER_LOGIN_STATE;


/**
 * user interface
 *
 * @author murphy
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword);

        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        System.out.println("user login request: {}" + userLoginRequest);
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        User result = userService.userLogin(userAccount, userPassword, request);

        return ResultUtils.success(result);
    }


    @PostMapping("/logout")
    public BaseResponse<String> userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        try {
            userService.userLogout(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error(ErrorCode.NOT_LOGGED_ERROR);
        }

        return ResultUtils.successWithoutData();
    }

    @GetMapping("/currentUser")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        // get user login state
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser != null) {
            long userId = currentUser.getId();
            User result = userService.getSafeUser(userService.getById(userId));
            return ResultUtils.success(result);
        }
        //todo validate user authority
        return ResultUtils.error(ErrorCode.NOT_LOGGED_ERROR);

    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request) {
        // authority check
        if (!isSystemAdmin(request)) {
            return ResultUtils.error(ErrorCode.NO_AUTHORITY_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }

        List<User> userList = userService.list(queryWrapper);

        List<User> result = userList.stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());

        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        // authority check
        if (!isSystemAdmin(request)) {
            return ResultUtils.error(ErrorCode.NO_AUTHORITY_ERROR);
        }

        if (id <= 0) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        boolean res = userService.removeById(id);

        if (!res) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.successWithoutData();
    }

    /**
     * authority check
     *
     * @param request HttpServletRequest
     * @return true - admin, false - not admin
     */
    private boolean isSystemAdmin(HttpServletRequest request) {
        // authority check
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user.getUserRole() == DEFAULT_ROLE || user == null) {
            return false;
        } else {
            return true;
        }
    }
}
