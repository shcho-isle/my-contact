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
    <title>Registration</title>
</head>
<body>

<div class="container" style="width: 300px;" align="center">
    <form:form action="registr" method="post" commandName="userForm">

        <h2 class="form-signin-heading">Введите данные для регистрации</h2>

        <input type="text" class="form-control" name="login"
               placeholder="Login"/>
        <input type="password" class="form-control" name="password"
               placeholder="Password"/>
        <input type="text" class="form-control" name="fullName"
               placeholder="Full Name"/>
        <button class="btn btn-lg btn-success btn-block" type="submit">Зарегистрировать</button>
        <c:if test="${not empty message}">
            <div class="alert alert-danger">
                    ${message}
            </div>
        </c:if>

    </form:form>
</div>
</body>
</html>