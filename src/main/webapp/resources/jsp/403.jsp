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
    <title>Error</title>
</head>
<body class="security-app">
<div align="center" class="container" style="width: 600px;">
    <div class="alert alert-danger">
        <h3>У Вас нету прав для запрашиваемой страницы</h3>
    </div>
    <form action="/logout" method="post" style="width: 100px">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>