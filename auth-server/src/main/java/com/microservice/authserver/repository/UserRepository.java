package com.microservice.authserver.repository;

import com.microservice.authserver.entity.Activity;
import com.microservice.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User u WHERE u.username = :username")
    User findByUsername(String username);

    @Query(value = "SELECT DISTINCT r.name FROM tbl_role_permission_activities rpa " +
            "INNER JOIN tbl_roles r ON rpa.role_id = r.id " +
            "INNER JOIN tbl_user_roles usr ON usr.role_id = r.id " +
            "WHERE usr.user_id = (SELECT u.id FROM tbl_users u WHERE u.username = :username)", nativeQuery = true)
    List<String> findRoleByUsername(String username);

    @Query(value = "SELECT a.method, a.url FROM tbl_activities a " +
            "INNER JOIN tbl_role_permission_activities rpa ON rpa.activity_id = a.id " +
            "INNER JOIN tbl_roles pr ON pr.id = rpa.role_id " +
            "WHERE pr.name in (:roles) ORDER BY a.url", nativeQuery = true)
    List<Activity> findActivitiesByRoles(String roles);
}
