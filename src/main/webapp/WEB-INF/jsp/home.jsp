<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
    <script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" defer></script>
    <title>LARDI Phone book</title>
</head>
<body class="security-app" bgcolor="#fff">
<div style="text-align: center;">
    <div class="jumbotron">
        <h1>Привет!</h1>
        <p>Вы находитесь на заглавной странице. Что бы перейти к записям нажмите на кнопку ниже</p>
        <p><a class="btn btn-primary btn-lg" role="button" onclick="window.location.href='contact'">Мои записи</a></p>
    </div>
</div>
</body>
</html>