package com.murphy.backend.common;

import lombok.Getter;

/**
 * @author murphy
 */

@Getter
public enum ErrorCode {
    /**
     * error code
     */
    PARAMS_ERROR(40000, "params error", "参数错误"),
    NULL_ERROR(40001, "require data null error", "请求数据为空"),
    USER_EXIST_ERROR(40002, "user exist", "用户已存在"),
    USER_PASSWORD_ERROR(40004, "user password error", "用户密码错误"),
    USER_CREATE_ERROR(40005, "user create error", "用户创建失败"),
    USER_NOT_EXIST_ERROR(40003, "user not exist", "用户不存在"),
    NOT_LOGGED_ERROR(40100, "not logged in", "未登录"),
    NO_AUTHORITY_ERROR(40101, "no authority", "没有权限"),
    SUCCESS(20000, "success", "成功"),
    SYSTEM_ERROR(20001, "system error", "系统错误");

    /**
     * error code
     * <p>
     * -- GETTER --
     *  get code
     *
     */
    private final int code;

    /**
     * error message
     * <p>
     * -- GETTER --
     *  get message
     *
     */
    private final String message;

    /**
     * error description
     * <p>
     * -- GETTER --
     *  get description
     *
     */
    private final String description;

    /**
     * error code
     * @param code code
     *             -- GETTER --
     *             get code
     *
     */
    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}
