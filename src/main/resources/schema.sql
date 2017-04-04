DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
  id        INT          NOT NULL AUTO_INCREMENT,
  login     VARCHAR(30)  NOT NULL,
  password  VARCHAR(100) NOT NULL,
  full_name VARCHAR(50)  NOT NULL,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX users_unique_login_idx ON users (login);


CREATE TABLE user_roles
(
  id           INT NOT NULL AUTO_INCREMENT,
  user_id      INT NOT NULL,
  role         VARCHAR(25),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX user_roles_idx ON user_roles (user_id, role);


CREATE TABLE contacts
(
  id            INT         NOT NULL AUTO_INCREMENT,
  user_id       INT         NOT NULL,
  last_name     VARCHAR(30) NOT NULL,
  first_name    VARCHAR(30) NOT NULL,
  middle_name   VARCHAR(30) NOT NULL,
  mobile_phone  VARCHAR(15) NOT NULL,
  home_phone    VARCHAR(15),
  address       VARCHAR(50),
  email         VARCHAR(30),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX contacts_unique_userid_mobilephone_idx
  ON contacts (user_id, mobile_phone);