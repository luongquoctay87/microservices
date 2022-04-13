package com.microservice.userservice.service.impl;

import com.microservice.userservice.VO.Department;
import com.microservice.userservice.VO.ResponseTemplateVO;
import com.microservice.userservice.api.form.UserForm;
import com.microservice.userservice.entity.User;
import com.microservice.userservice.repository.UserRepository;
import com.microservice.userservice.service.UserService;
import com.microservice.userservice.ultis.exception.BadRequestException;
import com.microservice.userservice.ultis.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User saveUser(UserForm form) {
        if (!StringUtils.hasText(form.getUsername())) {
            String mess = "Invalid argument";
            throw new BadRequestException(mess);
        }

        User user = userRepository.findByUsername(form.getUsername());

        if (!ObjectUtils.isEmpty(user)) {
            String mess = "user-not-exits";
            throw new NotFoundException(mess);
        }

        if (!StringUtils.hasText(form.getPassword())) {
            String mess = "Invalid argument";
            throw new BadRequestException(mess);
        }

        user = User.builder()
                .password(form.getPassword())
                .username(form.getUsername())
                .departmentId(form.getDepartmentId())
                .build();

        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        return save(user);
    }

    @Override
    public User updateDetails(String username, String password, long userId) {
        User user = userRepository.findById(userId).get();

        if(ObjectUtils.isEmpty(user)) {
            String mess = "user-not-exits";
            throw new NotFoundException(mess);
        }

        user.setPassword(password);
        user.setUsername(username);
        user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

        return save(user);
    }

    @Override
    public User findUserById(long userId) {
        User user = userRepository.findById(userId).get();

        if (ObjectUtils.isEmpty(user)) {
            String mess = "user-not-exits";
            throw new NotFoundException(mess);
        }

        return user;
    }

    @Override
    public List<User> getListUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        if (ObjectUtils.isEmpty(users)) {
            return Collections.EMPTY_LIST;
        }

        return users;

    }

    @Override
    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).get();

        if (ObjectUtils.isEmpty(user)) {
            String mess = "user-not-exits";
            throw new NotFoundException(mess);
        }

        userRepository.deleteById(userId);
    }

    @Override
    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);
        Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/api/v1/departments/" + user.getDepartmentId(),Department.class);
        vo.setUser(user);
        vo.setDepartment(department);

        return vo;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
