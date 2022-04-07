package com.microservice.userservice.service;

import com.microservice.userservice.VO.ResponseTemplateVO;
import com.microservice.userservice.api.form.UserForm;
import com.microservice.userservice.entity.User;

import java.util.List;

public interface UserService {
    User createNewUser(UserForm form);

    User changePassword(String password, long userId);

    User changeUsername(String username, long userId);

    User findUserById(long userId);

    List<User> getListUsers();

    void deleteUser(long userId);

    public ResponseTemplateVO getUserWithDepartment(Long userId);

    User save(User user);
}
