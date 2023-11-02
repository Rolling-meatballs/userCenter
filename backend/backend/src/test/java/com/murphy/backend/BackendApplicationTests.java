package com.murphy.backend;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
class BackendApplicationTests {
    MessageDigest md5 = null;

    @Test
    void testEncodePassword() throws NoSuchAlgorithmException {
        String password = "<PASSWORD>";
        String salt = "123456";

        md5 = MessageDigest.getInstance("MD5");
        String encodePassword = DigestUtils.md5DigestAsHex((password + salt).getBytes(StandardCharsets.UTF_8));
        System.out.println(encodePassword);

        System.out.println(password);
    }

}
