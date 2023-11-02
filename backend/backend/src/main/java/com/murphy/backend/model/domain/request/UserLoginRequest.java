package com.murphy.backend.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * user login request entity
 *
 * @author murphy
 */

@Data
public class UserLoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3242512671215495229L;
//    private static final long serialVersionUID = -8394212436205142983L;

    private String userAccount;

    private String userPassword;
}
