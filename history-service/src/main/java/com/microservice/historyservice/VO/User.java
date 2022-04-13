package com.microservice.historyservice.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
        private Long userId;
        private Long departmentId;
        private String username;
        private String password;
        private Timestamp createdDate;
        private Timestamp updatedDate;
}
