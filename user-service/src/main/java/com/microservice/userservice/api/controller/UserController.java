package com.microservice.userservice.api.controller;


import com.microservice.userservice.VO.ResponseTemplateVO;
import com.microservice.userservice.api.form.UserForm;
import com.microservice.userservice.api.response.ApiResponse;
import com.microservice.userservice.dto.UserDto;
import com.microservice.userservice.entity.User;
import com.microservice.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> saveUser(@RequestBody UserForm _form) {
        User user = userService.saveUser(_form);
        UserDto dto = user.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Thêm người dùng thành công");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getListUsers() {
        List<User> users = userService.getListUsers();
        List<UserDto> dto = users.stream().map(User::toDto).collect(Collectors.toList());
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Danh sách các người dùng");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findUserById(@PathVariable(name = "id") long _id) {
        User user = userService.findUserById(_id);
        UserDto dto = user.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), String.format("Người dùng %d",_id));
        return ResponseEntity.ok(response);
    }

    @PatchMapping( "/update/{id}")
    public ResponseEntity<ApiResponse> updateDetails(@RequestBody UserForm _form, @PathVariable("id") Long _id) {
        User user = userService.updateDetails(_form.getUsername(), _form.getPassword(), _id);
        UserDto dto = user.toDto();
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Cập nhật thành công");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") long _id) {
        userService.deleteUser(_id);
        ApiResponse response = ApiResponse.success(null, HttpStatus.OK.value(), "Xóa người dùng thành công");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/userdepartment/{id}")
    public ResponseTemplateVO getUserWithDepartment(@PathVariable(name = "id") Long userId){
        log.info("UserController -> getUserWithDepartment");
        return userService.getUserWithDepartment(userId);
    }
}
