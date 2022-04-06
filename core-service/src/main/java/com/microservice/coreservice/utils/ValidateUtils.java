package com.microservice.coreservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

public class ValidateUtils {

    public static void validateNullOrBlankString(HashMap<String, String> data) {
        for (String key : data.keySet()) {
            if (StringUtils.isNullOrBlank(data.get(key))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Không được để trống %s", key));
            }
        }
    }

    public static void validateNullOrBlankString(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Không được để trống %s", str));
        }
    }

    public static void isPassword(String password) {
        if (password.trim().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mật khẩu phải dài hơn 6 ký tự!");
        }
    }

    public static void isEmail(String email) {
        ValidateUtils.validateNullOrBlankString(email);
        String reg = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        if (!email.trim().matches(reg)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email không hợp lệ!");
        }
    }

    public static void isPhoneNumber(String phone) {
        ValidateUtils.validateNullOrBlankString(phone);
        String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
        if (!phone.matches(reg)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số điện thoại không hợp lệ!");
        }
    }
}
