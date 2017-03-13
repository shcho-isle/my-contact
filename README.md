##### Настройки хранилица: `config/phonebook.properties`. Путь к нему должен передаваться в `VM options` например: `-Dlardi.conf=config/phonebook.properties`.

##### Для тестов существует отдельный файл с настройками: `src/test/resources/test.properties`

##### Скрипты для создания таблиц, и наполнения демонстрационными данными:
- `src\main\resources\db\initDB.sql`
- `src\main\resources\db\populateDB.slq`

##### После запуска приложение доступно по: [http://localhost:8080/](http://localhost:8080/)

##### SQL запрос для создания таблиц в mySQL:

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




##### Наполнение демонстрационным данными:
    
    DELETE FROM user_roles;
    DELETE FROM contacts;
    DELETE FROM users;
    
    ALTER TABLE users AUTO_INCREMENT = 1;
    ALTER TABLE user_roles AUTO_INCREMENT = 1;
    ALTER TABLE contacts AUTO_INCREMENT = 1;
    
    INSERT INTO users (login, password, full_name) VALUES
      ('Vano', '$2a$10$.JJyQ78f1MVbE9IbHB8d9ei2HSsl7Q1JCVAvoFYv7Mntu.NaGZQeS', 'Yakovenko Ivan Venediktovich'),
      ('Serg', '$2a$10$.JJyQ78f1MVbE9IbHB8d9ei2HSsl7Q1JCVAvoFYv7Mntu.NaGZQeS', 'Rubinov Sergey Nikolaevich');
    
    INSERT INTO user_roles (role, user_id) VALUES
      ('ROLE_ADMIN', 1),
      ('ROLE_ADMIN', 2);
    
    INSERT INTO contacts (last_name, first_name, middle_name, mobile_phone, home_phone, address, email, user_login) VALUES
      ('Dovbash', 'Sveta', 'Andriivna', '+380(66)1234567', '+380(44)1234567', 'Kyiv, Mechnikova str. 2', 'sveta@gmail.com', 'Vano'),
      ('Kushnir', 'Lena', 'Viktorivna', '+380(50)1234123', '+380(47)2661181', 'Cherkasy, Taraskova str. 16', 'lena@gmail.com', 'Vano'),
      ('Parasiuk', 'Sasha', 'Igorevych', '+380(97)9876543', '+380(44)0909098', 'Kyiv, Pushkinska str. 10', 'sasha@gmail.com', 'Vano'),
      ('Tiagnybok', 'Yulia', 'Volodymyrivna', '+380(67)8555855', '+380(47)2234567', 'Cherkasy, Rustavi str. 8', 'yulia@gmail.com', 'Vano'),
      ('Tsymbal', 'Vitia', 'Fedorovych', '+380(98)9234567', '+380(44)9234567', 'Kyiv, Sosninykh str. 38', 'vitia@gmail.com', 'Vano'),
      ('Tymoshenko', 'Grysha', 'Petrovych', '+380(93)1234555', '+380(47)2234555', 'Cherkasy, Gaidara str. 8', 'grysha@gmail.com', 'Vano'),
      ('Kukushkina', 'Dima', 'Grygorovych', '+380(63)3334567', '+380(44)3334567', 'Kyiv, Zhylanska str. 99', 'dima@gmail.com', 'Serg'),
      ('Kukushkina', 'Sveta', 'Andreevna', '+380(66)1234567', '+380(44)1234567', 'Kyiv, Mechnikova str. 2', 'sveta@gmail.com', 'Serg');