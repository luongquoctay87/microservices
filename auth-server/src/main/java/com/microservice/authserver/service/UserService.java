package com.microservice.authserver.service;

import com.microservice.authserver.entity.Activity;
import com.microservice.authserver.entity.User;
import com.microservice.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Check user is valid or not
     * @param username
     * @param password
     * @return
     */
    public boolean isAuthenticated(String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            return encoder.matches(password, user.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get all roles by username
     * @param username
     * @return
     */
    public List<String> findAllRolesByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }

    /**
     * Get all activities by roles
     * @param roles
     * @return
     */
    public List<Activity> findAllActivitiesByRoles(String roles) {
        return userRepository.findActivitiesByRoles(roles);
    }
}
