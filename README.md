Настройки хранилица: `config/phonebook.properties`. Путь к нему должен передаваться в `VM options: -Dlardi.conf=config/phonebook.properties`.

Для тестов существует отдельный файл с настройками: `src/test/resources/test.properties`

Скрипты для создания таблиц, и наполнения демонстрационными данными:
- `src\main\resources\db\initDB.sql`
- `src\main\resources\db\populateDB.slq`

После запуска приложение доступно по: [http://localhost:8080/](http://localhost:8080/)

SQL запрос для создания таблиц в mySQL:

    DROP TABLE IF EXISTS user_roles;
    DROP TABLE IF EXISTS contacts;
    DROP TABLE IF EXISTS users;


    CREATE TABLE users
    (
        id        INT          NOT NULL AUTO_INCREMENT,
        login     VARCHAR(25)  NOT NULL,
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
        user_login    VARCHAR(25) NOT NULL,
        last_name     VARCHAR(25) NOT NULL,
        first_name    VARCHAR(25) NOT NULL,
        middle_name   VARCHAR(25) NOT NULL,
        mobile_phone  VARCHAR(15) NOT NULL,
        home_phone    VARCHAR(15),
        address       VARCHAR(50),
        email         VARCHAR(25),
        FOREIGN KEY (user_login) REFERENCES users (login) ON DELETE CASCADE,
        PRIMARY KEY (id)
    );
    CREATE UNIQUE INDEX contacts_unique_userlogin_mobilephone_idx
        ON contacts (user_login, mobile_phone);
