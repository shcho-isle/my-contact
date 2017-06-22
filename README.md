Web приложение “Контакты”
=============================
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/57d9f4d87c6c4c29bcb6363b47f950cd)](https://www.codacy.com/app/pavlo-plynko/contact-manager?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=shcho-isle/contact-manager&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/shcho-isle/contact-manager.svg?branch=master)](https://travis-ci.org/shcho-isle/contact-manager)

Настройки хранилища: `config/contactManager.properties`.
Путь к нему должен передаваться в `VM options` например: `-Dtelecom.conf=config/contactManager.properties`.

Настройки по умолчанию (если не указаны в `VM options`): `src/main/resources/application.properties`.

SQL запрос для создания таблиц в mySQL: `src/main/resources/schema.sql`.