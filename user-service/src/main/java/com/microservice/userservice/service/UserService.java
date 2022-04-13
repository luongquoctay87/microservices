package com.microservice.userservice.service;

import com.microservice.userservice.VO.ResponseTemplateVO;
import com.microservice.userservice.api.form.UserForm;
import com.microservice.userservice.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(UserForm form);

    User updateDetails(String username, String password, long userId);

    User findUserById(long userId);

    List<User> getListUsers();

    void deleteUser(long userId);

    public ResponseTemplateVO getUserWithDepartment(Long userId);

    User save(User user);
}
