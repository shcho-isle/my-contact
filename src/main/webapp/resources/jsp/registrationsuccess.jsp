<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css" media="screen">
    <script src="../js/bootstrap.min.js"></script>
    <title>Успешная регистрация</title>
</head>
<body>

<div class="container" style="width: 500px;" align="center">
    <div class="alert alert-success">Регистрация успешна!</div>
</div>
<div class="jumbotron" align="center">
    <h1>Поздравляем!</h1>
    <p>Теперь вы можете Войти под своими данными:</p>
    <h3>Login:<span class="label label-default">${userForm.login}</span></h3>
    <h3>Full Name: <span class="label label-default">${userForm.fullName}</span></h3>
    <p><a class="btn btn-primary btn-lg" role="button" onclick="window.location.href='contact'">Войти</a></p>
</div>
</body>
</html>