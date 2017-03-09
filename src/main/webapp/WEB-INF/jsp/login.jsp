<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
    <script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" defer></script>
    <title>LARDI Phone book</title>
</head>
<body class="security-app">

<div class="container" style="width: 300px;">
    <form action="/login" method="post">

        <h2 class="form-signin-heading">Welcome to LARDI Phone Book!</h2>

        <input type="text" class="form-control" name="login"
               placeholder="Login"/>
        <input type="password" class="form-control" name="password"
               placeholder="Password"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
        Зарегистрироваться можно <a href="registr">здесь</a>
        <c:if test="${param.error ne null}">
            <div class="alert alert-danger">Неправильный логин или пароль:(</div>
        </c:if>
        <c:if test="${param.logout ne null}">
            <div class="alert alert-success">
                Вы успешно вышли:)
            </div>
        </c:if>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>