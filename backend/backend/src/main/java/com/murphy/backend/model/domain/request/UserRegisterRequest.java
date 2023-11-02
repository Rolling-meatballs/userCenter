package com.murphy.backend.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * user register request entity
 *
 * @author murphy
 */
@Data
public class UserRegisterRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = -8394212436205142983L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

}
