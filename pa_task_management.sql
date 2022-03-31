----------------------------------
-- DATABASE pa_task_management --
----------------------------------
CREATE DATABASE pa_task_management DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE pa_task_management;

----------------------------------
-- TABLE pa_activities --
----------------------------------
CREATE TABLE pa_activities (
	id INT(11) AUTO_INCREMENT,
    method ENUM('GET', 'POST', 'PUT', 'PATCH', 'DELETE') NOT NULL,
	url VARCHAR(1024) NOT NULL,
	PRIMARY KEY(id)
); 
INSERT INTO pa_activities (id, `method`,  url) VALUES (1, "GET", "/api/v1/departments");
INSERT INTO pa_activities (id, `method`,  url) VALUES (2, "GET", "/api/v1/departments/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (3, "POST", "/api/v1/departments");
INSERT INTO pa_activities (id, `method`,  url) VALUES (4, "PUT", "/api/v1/departments/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (5, "PATCH", "/api/v1/departments/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (6, "DELETE", "/api/v1/departments/{id}");

INSERT INTO pa_activities (id, `method`,  url) VALUES (7, "GET", "/api/v1/users");
INSERT INTO pa_activities (id, `method`,  url) VALUES (8, "GET", "/api/v1/users//{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (9, "POST", "/api/v1/users");
INSERT INTO pa_activities (id, `method`,  url) VALUES (10, "PUT", "/api/v1/users/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (11, "PATCH", "/api/v1/users/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (12, "DELETE", "/api/v1/users/{id}");

INSERT INTO pa_activities (id, `method`,  url) VALUES (13, "GET", "/api/v1/teams");
INSERT INTO pa_activities (id, `method`,  url) VALUES (14, "GET", "/api/v1/teams/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (15, "POST", "/api/v1/teams");
INSERT INTO pa_activities (id, `method`,  url) VALUES (16, "PUT", "/api/v1/teams/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (17, "PATCH", "/api/v1/teams/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (18, "DELETE", "/api/v1/teams/{id}");

INSERT INTO pa_activities (id, `method`,  url) VALUES (19, "GET", "/api/v1/projects");
INSERT INTO pa_activities (id, `method`,  url) VALUES (20, "GET", "/api/v1/projects/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (21, "POST", "/api/v1/projects");
INSERT INTO pa_activities (id, `method`,  url) VALUES (22, "PUT", "/api/v1/projects/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (23, "PATCH", "/api/v1/projects/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (24, "DELETE", "/api/v1/projects/{id}");

INSERT INTO pa_activities (id, `method`,  url) VALUES (25, "GET", "/api/v1/tasks");
INSERT INTO pa_activities (id, `method`,  url) VALUES (26, "GET", "/api/v1/tasks/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (27, "POST", "/api/v1/tasks");
INSERT INTO pa_activities (id, `method`,  url) VALUES (28, "PUT", "/api/v1/tasks/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (29, "PATCH", "/api/v1/tasks/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (30, "DELETE", "/api/v1/tasks/{id}");

INSERT INTO pa_activities (id, `method`,  url) VALUES (31, "GET", "/api/v1/comments");
INSERT INTO pa_activities (id, `method`,  url) VALUES (32, "GET", "/api/v1/comments/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (33, "POST", "/api/v1/comments");
INSERT INTO pa_activities (id, `method`,  url) VALUES (34, "PUT", "/api/v1/comments/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (35, "PATCH", "/api/v1/comments/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (36, "DELETE", "/api/v1/comments/{id}");

INSERT INTO pa_activities (id, `method`,  url) VALUES (37, "GET", "/api/v1/histories");
INSERT INTO pa_activities (id, `method`,  url) VALUES (38, "GET", "/api/v1/histories/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (39, "POST", "/api/v1/histories");
INSERT INTO pa_activities (id, `method`,  url) VALUES (40, "PUT", "/api/v1/histories/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (41, "PATCH", "/api/v1/histories/{id}");
INSERT INTO pa_activities (id, `method`,  url) VALUES (42, "DELETE", "/api/v1/histories/{id}");

----------------------------------
-- TABLE pa_permissions --
----------------------------------
CREATE TABLE pa_permissions (
	id INT(1) AUTO_INCREMENT,
	name ENUM('VIEW', 'EDIT', 'DELETE', 'APPROVE') NOT NULL,
    enabled BOOLEAN,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(id)
);
INSERT INTO pa_permissions (id, name, enabled) VALUES (1, "VIEW", true);
INSERT INTO pa_permissions (id, name, enabled) VALUES (2, "EDIT", true);
INSERT INTO pa_permissions (id, name, enabled) VALUES (3, "DELETE", true);
INSERT INTO pa_permissions (id, name, enabled) VALUES (4, "APPROVE", true);

----------------------------------
-- TABLE pa_roles --
----------------------------------
CREATE TABLE pa_roles (
	id INT(11) AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    enabled BOOLEAN,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (name),
	PRIMARY KEY(id)
);
INSERT INTO pa_roles (id, name, enabled) VALUES (1, "ROLE_ADMIN", true);
INSERT INTO pa_roles (id, name, enabled) VALUES (2, "ROLE_LEADER", true);
INSERT INTO pa_roles (id, name, enabled) VALUES (3, "ROLE_MANAGER", true);
INSERT INTO pa_roles (id, name, enabled) VALUES (4, "ROLE_MEMBER", true);

----------------------------------
-- TABLE pa_role_permission_activities --
----------------------------------
CREATE TABLE pa_role_permission_activities (
	role_id INT(11) NOT NULL,
	permission_id INT(11) NOT NULL,
	activity_id INT(11) NOT NULL,
	PRIMARY KEY(role_id, permission_id, activity_id),
	CONSTRAINT pa_role_permission_activities_fk1 FOREIGN KEY (role_id) REFERENCES pa_roles(id),
	CONSTRAINT pa_role_permission_activities_fk2 FOREIGN KEY (permission_id) REFERENCES pa_permissions(id),
	CONSTRAINT pa_role_permission_activities_fk3 FOREIGN KEY (activity_id) REFERENCES pa_activities(id)
);
-- [ ROLE ANDMIN ] -- 
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 1, 1);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 1, 2);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 1, 7);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 1, 8);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 2, 3);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 2, 4);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 2, 5);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 2, 9);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 2, 10);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 2, 11);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 3, 6);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (1, 3, 12);

-- [ ROLE LEADER ] --
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 1);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 2);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 7);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 8);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 13);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 14);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 19);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 20);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 25);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 26);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 31);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 32);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 37);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (2, 1, 38);

-- [ ROLE MANAGER ] --
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 1, 13);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 1, 14);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 1, 19);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 1, 20);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 1, 25);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 1, 26);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 1, 31);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 1, 32);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 15);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 16);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 17);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 21);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 22);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 23);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 27);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 28);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 29);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 33);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 34);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 35);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 18);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 24);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 30);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (3, 2, 36);

-- [ ROLE MEMBER ] --
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 1, 13);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 1, 14);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 1, 19);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 1, 20);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 1, 31);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 1, 32);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 1, 37);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 1, 38);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 2, 27);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 2, 28);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 2, 29);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 2, 33);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 2, 34);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 2, 35);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 2, 39);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 2, 40);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 2, 41);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 3, 30);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 3, 36);
INSERT INTO pa_role_permission_activities (role_id, permission_id, activity_id) VALUES (4, 3, 42);

----------------------------------
-- TABLE pa_departments --
----------------------------------
CREATE TABLE pa_departments (
	id INT(11) AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    enabled BOOLEAN,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (name),
	PRIMARY KEY(id)
);
INSERT INTO pa_departments (id, name, enabled) VALUES (1, "BOD", true);
INSERT INTO pa_departments (id, name, enabled) VALUES (2, "HR", true);
INSERT INTO pa_departments (id, name, enabled) VALUES (3, "Accouting", true);
INSERT INTO pa_departments (id, name, enabled) VALUES (4, "Marketing", true);
INSERT INTO pa_departments (id, name, enabled) VALUES (5, "IT", true);

----------------------------------
-- TABLE pa_users --
----------------------------------
CREATE TABLE pa_users (
	id INT(11) AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(255) NOT NULL,
-- 	email VARCHAR(50) NOT NULL,
-- 	phone VARCHAR(50),
-- 	address VARCHAR(50),
	department_id INT(11),
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(id),
	UNIQUE(username),
	CONSTRAINT pa_user_fk1 FOREIGN KEY (department_id) REFERENCES pa_departments(id)
);
INSERT INTO pa_users (id, username, password, department_id) VALUES (1, "admin", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 3);
INSERT INTO pa_users (id, username, password, department_id) VALUES (2, "leader", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 1);
INSERT INTO pa_users (id, username, password, department_id) VALUES (3, "manager", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 1);
INSERT INTO pa_users (id, username, password, department_id) VALUES (4, "member", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 4);
INSERT INTO pa_users (id, username, password, department_id) VALUES (5, "user1", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 4);
INSERT INTO pa_users (id, username, password, department_id) VALUES (6, "user2", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 4);
INSERT INTO pa_users (id, username, password, department_id) VALUES (7, "user3", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 4);
INSERT INTO pa_users (id, username, password, department_id) VALUES (8, "user4", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 4);
INSERT INTO pa_users (id, username, password, department_id) VALUES (9, "user5", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 4);
INSERT INTO pa_users (id, username, password, department_id) VALUES (10, "user6", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 4);

INSERT INTO pa_users (id, username, password, department_id) VALUES (11, "other1", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 5);
INSERT INTO pa_users (id, username, password, department_id) VALUES (12, "other2", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 5);
INSERT INTO pa_users (id, username, password, department_id) VALUES (13, "other3", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 5);
INSERT INTO pa_users (id, username, password, department_id) VALUES (14, "other4", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 5);
INSERT INTO pa_users (id, username, password, department_id) VALUES (15, "other5", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 5);
INSERT INTO pa_users (id, username, password, department_id) VALUES (16, "other6", "$2a$10$xGV9xSn3o.8E9hMdsJAZmeP6et3osbbb3wz2otzVX/Vm6Xqstwavm", 5);


----------------------------------
-- TABLE pa_user_roles --
----------------------------------
CREATE TABLE pa_user_roles (
	user_id INT(11) NOT NULL,
	role_id INT(11) NOT NULL,
    PRIMARY KEY(user_id, role_id),
	CONSTRAINT pa_user_role_fk1 FOREIGN KEY (user_id) REFERENCES pa_users(id),
	CONSTRAINT pa_user_role_fk2 FOREIGN KEY (role_id) REFERENCES pa_roles(id)
);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (3, 3);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (4, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (5, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (6, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (7, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (8, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (9, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (10, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (11, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (12, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (13, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (14, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (15, 4);
INSERT INTO pa_user_roles (user_id, role_id) VALUES (16, 4);
----------------------------------
-- TABLE pa_projects --
----------------------------------
CREATE TABLE pa_projects (
	id INT(11) AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(1000),
	type ENUM('Public','Private'), 
	enabled BOOLEAN,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (name),
	PRIMARY KEY(id)
);
INSERT INTO pa_projects (id, name, description, type, enabled) VALUES (1, 'Dự án Marketing', 'Mô tả dự án marketing', 'Public', true);
INSERT INTO pa_projects (id, name, description, type, enabled) VALUES (2, 'Dự án IT', 'Mô tả dự án IT', 'Public', true);
INSERT INTO pa_projects (id, name, description, type, enabled) VALUES (3, 'Dự án HR', 'Mô tả dự án HR', 'Public', true);

----------------------------------
-- TABLE pa_sections --
----------------------------------
CREATE TABLE pa_sections (
	id INT(11) AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(1000),
	enabled BOOLEAN,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (name),
	PRIMARY KEY(id)
);
INSERT INTO pa_sections (id, name, description, enabled) VALUES (1, 'Giai đoạn I', 'Mô tả giai đoạn I', true);
INSERT INTO pa_sections (id, name, description, enabled) VALUES (2, 'Giai đoạn II', 'Mô tả giai đoạn II', true);
INSERT INTO pa_sections (id, name, description, enabled) VALUES (3, 'Giai đoạn III', 'Mô tả giai đoạn III', true);
INSERT INTO pa_sections (id, name, description, enabled) VALUES (4, 'Giai đoạn IV', 'Mô tả giai đoạn IV', true);

----------------------------------
-- TABLE pa_tasks --
----------------------------------
CREATE TABLE pa_tasks (
	id INT(11) AUTO_INCREMENT,
	created_by INT(11) NOT NULL,
    assignee INT(11) NOT NULL,
    project_id INT(11),
    section_id INT(11),
	name VARCHAR(255) NOT NULL,
	description TEXT, 
	priority ENUM('Cao','Trung bình', 'Thấp'),
	status ENUM('Chưa làm','Đang làm', 'Chờ nghiệm thu', 'Đã hoàn thành'),
	estimate_time FLOAT,
	start_date TIMESTAMP,
	end_date TIMESTAMP,
    parent_id INT(11),
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(id),
	CONSTRAINT pa_task_fk1 FOREIGN KEY (created_by) REFERENCES pa_users(id),
    CONSTRAINT pa_task_fk2 FOREIGN KEY (assignee) REFERENCES pa_users(id),
    CONSTRAINT pa_task_fk3 FOREIGN KEY (project_id) REFERENCES pa_projects(id),
    CONSTRAINT pa_task_fk4 FOREIGN KEY (section_id) REFERENCES pa_sections(id)
);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (1, 3, 4, null, null, 'Marketing công việc 1', 'Mô tả công việc 1', 'Cao', 'Đã hoàn thành', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (2, 3, 4, null, null, 'Marketing công việc 2', 'Mô tả công việc 2', 'Cao', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (3, 3, 4, null, null, 'Marketing công việc 3', 'Mô tả công việc 3', 'Cao', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);

INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (4, 3, 4, 1, 1, 'Marketing công việc A', 'Mô tả công việc A', 'Trung bình', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (5, 3, 4, 1, 1, 'Marketing công việc B', 'Mô tả công việc B', 'Trung bình', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (6, 3, 4, 1, 1, 'Marketing công việc C', 'Mô tả công việc C', 'Trung bình', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);

INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (7, 3, 4, 1, 2, 'Marketing công việc X', 'Mô tả công việc X', 'Thấp', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (8, 3, 4, 1, 2, 'Marketing công việc Y', 'Mô tả công việc Y', 'Thấp', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);

INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (9, 3, 4, 2, null, 'Công việc của tháng tư', 'Mô tả tháng tư', 'Trung bình', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (10, 4, 1, 2, 1, 'Công việc tuần 1', 'Mô tả tuần 1', 'Trung bình', 'Chưa làm', 4.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 9);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (11, 4, 1, 2, 2, 'Công việc tuần 2', 'Mô tả tuần 2', 'Trung bình', 'Chưa làm', 4.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 9);

INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (12, 4, 1, 2, 1, 'Công việc tuần 1 > Thứ 2', 'Mô tả Thứ 2', 'Cao', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 10);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (13, 4, 1, 2, 1, 'Công việc tuần 1 > Thứ 3', 'Mô tả Thứ 3', 'Cao', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 10);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (14, 4, 1, 2, 1, 'Công việc tuần 1 > Thứ 4', 'Mô tả Thứ 4', 'Trung bình', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 10);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (15, 4, 1, 2, 1, 'Công việc tuần 1 > Thứ 5', 'Mô tả Thứ 5', 'Trung bình', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 10);
INSERT INTO pa_tasks (id, created_by, assignee, project_id, section_id, name, description, priority, status, estimate_time, start_date, end_date, parent_id) 
VALUES (16, 4, 1, 2, 1, 'Công việc tuần 1 > Thứ 6', 'Mô tả Thứ 6', 'Thấp', 'Chưa làm', 8.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 10);

----------------------------------
-- TABLE pa_comments --
----------------------------------
CREATE TABLE pa_comments (
	id INT(11) AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
	task_id INT(11) NOT NULL,
	reply_id INT(11),
    content TEXT NOT NULL,
	enabled BOOLEAN,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(id),
    CONSTRAINT pa_comment_fk1 FOREIGN KEY (user_id) REFERENCES pa_users(id),
	CONSTRAINT pa_comment_fk2 FOREIGN KEY (task_id) REFERENCES pa_tasks(id)
);
INSERT INTO pa_comments (id, user_id, task_id, reply_id, content, enabled) VALUES (1, 3, 1, null, 'Comment 1: Comment lần đầu', true);
INSERT INTO pa_comments (id, user_id, task_id, reply_id, content, enabled) VALUES (2, 4, 1, 1, 'Comment 2: Trả lời comment 1', true);
INSERT INTO pa_comments (id, user_id, task_id, reply_id, content, enabled) VALUES (3, 3, 1, 2, 'Comment 3: Trả lời comment 2', true);

----------------------------------
-- TABLE pa_histories --
----------------------------------
CREATE TABLE pa_histories (
	id INT(11) AUTO_INCREMENT,
    task_id INT(11) NOT NULL,
    user_id INT(11) NOT NULL,
	actions VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(id),
    CONSTRAINT pa_history_fk1 FOREIGN KEY (task_id) REFERENCES pa_tasks(id),
    CONSTRAINT pa_history_fk2 FOREIGN KEY (user_id) REFERENCES pa_users(id)
);
INSERT INTO pa_histories (id, task_id, user_id, actions) VALUES (1, 1, 4, "Manager đã tạo công việc này");
INSERT INTO pa_histories (id, task_id, user_id, actions) VALUES (2, 1, 4, "Manager gán công việc này cho Member");
INSERT INTO pa_histories (id, task_id, user_id, actions) VALUES (3, 1, 4, "Manager đã thay đổi ngày hoàn thành sang 5:30PM 11/3/2022");
INSERT INTO pa_histories (id, task_id, user_id, actions) VALUES (4, 1, 3, "Member đã hoàn thành công việc");

----------------------------------
-- TABLE pa_teams --
----------------------------------
CREATE TABLE pa_teams (
	id INT(11) AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(1000),
	enabled BOOLEAN,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (name),
	PRIMARY KEY(id)
);
INSERT INTO pa_teams (id, name, description, enabled) VALUES (1, 'Team Alpha', "Mô tả team alpha", true);
INSERT INTO pa_teams (id, name, description, enabled) VALUES (2, 'Team Beta', "Mô tả team beta", true);
INSERT INTO pa_teams (id, name, description, enabled) VALUES (3, 'Team Gamma', "Mô tả team gamma", true);

----------------------------------
-- TABLE pa_team_users --
----------------------------------
CREATE TABLE pa_team_users (
	team_id INT(11) NOT NULL,
	department_id INT(11),
	user_id INT(11) NOT NULL,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(team_id, user_id),
	CONSTRAINT pa_team_user_fk1 FOREIGN KEY (team_id) REFERENCES pa_teams(id),
	CONSTRAINT pa_team_user_fk2 FOREIGN KEY (department_id) REFERENCES pa_departments(id),
	CONSTRAINT pa_team_user_fk3 FOREIGN KEY (user_id) REFERENCES pa_users(id)
);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (1, 4, 5);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (1, 4, 6);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (1, 4, 7);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (1, 4, 8);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (1, 4, 9);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (1, 4, 10);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (1, 1, 3);

INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (2, 5, 11);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (2, 5, 12);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (2, 5, 13);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (2, 5, 14);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (2, 5, 15);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (2, 5, 16);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (2, 1, 3);

INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (3, 4, 4);
INSERT INTO pa_team_users (team_id, department_id, user_id) VALUES (3, 1, 3);

----------------------------------
-- TABLE pa_project_teams --
----------------------------------
CREATE TABLE pa_project_teams (
	project_id INT(11) NOT NULL,
	team_id INT(11) NOT NULL,
	created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(project_id, team_id),
	CONSTRAINT pa_project_team_fk1 FOREIGN KEY (project_id) REFERENCES pa_projects(id),
	CONSTRAINT pa_project_team_fk2 FOREIGN KEY (team_id) REFERENCES pa_teams(id)
);
INSERT INTO pa_project_teams (project_id, team_id) VALUES (1, 1);
INSERT INTO pa_project_teams (project_id, team_id) VALUES (1, 2);
INSERT INTO pa_project_teams (project_id, team_id) VALUES (2, 3);
INSERT INTO pa_project_teams (project_id, team_id) VALUES (3, 3);
