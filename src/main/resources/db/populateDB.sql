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