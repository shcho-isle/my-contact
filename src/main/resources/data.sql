DELETE FROM contacts;
DELETE FROM users;

ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE contacts AUTO_INCREMENT = 1;

INSERT INTO users (login, password, full_name) VALUES
  ('user1', '$2a$10$.JJyQ78f1MVbE9IbHB8d9ei2HSsl7Q1JCVAvoFYv7Mntu.NaGZQeS', 'Yakovenko Ivan Venediktovich'),
  ('user2', '$2a$10$.JJyQ78f1MVbE9IbHB8d9ei2HSsl7Q1JCVAvoFYv7Mntu.NaGZQeS', 'Rubinov Sergey Nikolaevich');

INSERT INTO contacts (last_name, first_name, middle_name, mobile_phone, home_phone, address, email, user_id) VALUES
  ('Dovbash', 'Sveta', 'Andriivna', '+380(66)1234567', '+380(44)1234567', 'Kyiv, Mechnikova str. 2', 'sveta@gmail.com', 1),
  ('Kushnir', 'Lena', 'Viktorivna', '+380(50)1234123', '+380(47)2661181', 'Cherkasy, Taraskova str. 16', 'lena@gmail.com', 1),
  ('Parasiuk', 'Sasha', 'Igorevych', '+380(97)9876543', '+380(44)0909098', 'Kyiv, Pushkinska str. 10', 'sasha@gmail.com', 1),
  ('Tiagnybok', 'Yulia', 'Volodymyrivna', '+380(67)8555855', '+380(47)2234567', 'Cherkasy, Rustavi str. 8', 'yulia@gmail.com', 1),
  ('Tsymbal', 'Vitia', 'Fedorovych', '+380(98)9234567', '+380(44)9234567', 'Kyiv, Sosninykh str. 38', 'vitia@gmail.com', 1),
  ('Tymoshenko', 'Grysha', 'Petrovych', '+380(93)1234555', '+380(47)2234555', 'Cherkasy, Gaidara str. 8', 'grysha@gmail.com', 1),
  ('Kukushkin', 'Dima', 'Grygorovych', '+380(63)3334567', '+380(44)3334567', 'Kyiv, Zhylanska str. 99', 'dima@gmail.com', 2),
  ('Kukushkina', 'Sveta', 'Andreevna', '+380(66)1234567', '+380(44)1234567', 'Kyiv, Mechnikova str. 2', 'sveta@gmail.com', 2);