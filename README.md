Web приложение “Контакты”
=============================
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/57d9f4d87c6c4c29bcb6363b47f950cd)](https://www.codacy.com/app/pavlo-plynko/ContactManager?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=shcho-isle/ContactManager&amp;utm_campaign=Badge_Grade)
[![Dependency Status](https://dependencyci.com/github/shcho-isle/ContactManager/badge)](https://dependencyci.com/github/shcho-isle/ContactManager)
[![Build Status](https://travis-ci.org/shcho-isle/ContactManager.svg?branch=master)](https://travis-ci.org/shcho-isle/ContactManager)

Настройки хранилища: `config/contactManager.properties`.
Путь к нему должен передаваться в `VM options` например: `-Dtelecom.conf=${PB_ROOT}/config/contactManager.properties`.

SQL запрос для создания таблиц в mySQL:

```sql
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
 ```