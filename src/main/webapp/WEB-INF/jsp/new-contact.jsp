<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
    <script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" defer></script>
</head>
<body>
<div class="container">
    <form action="/contacts" method="post" address="form" data-toggle="validator" commandName="newContact">
        <c:if test="${empty action}">
            <c:set var="action" value="add"/>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                    ${error}
            </div>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="idContact" name="idContact" value="${contact.id}">
        <h2>Создать контакт</h2>
        <div class="form-group col-xs-4">
            <label for="firstName" class="control-label col-xs-4">Имя:</label>
            <input type="text" name="firstName" id="firstName" class="form-control" value="${contact.firstName}"
                   required="true"/>

            <label for="lastName" class="control-label col-xs-4">Фамилия:</label>
            <input type="text" name="lastName" id="lastName" class="form-control" value="${contact.lastName}"
                   required="true"/>

            <label for="middleName" class="control-label col-xs-4">Отчество:</label>
            <input type="text" name="middleName" id="middleName" class="form-control" value="${contact.middleName}"
                   required="true"/>

            <label for="address" class="control-label col-xs-4">Адрес:</label>
            <input type="text" name="address" id="address" class="form-control" value="${contact.address}"/>

            <label for="mobilePhone" class="control-label col-xs-4">Моб. телефон:</label>
            <input type="text" name="mobilePhone" id="mobilePhone" class="form-control" value="${contact.mobilePhone}"
                   placeholder="+380(66)1234567" required="true"/>

            <label for="homePhone" class="control-label col-xs-4">Телефон:</label>
            <input type="text" name="homePhone" id="homePhone" class="form-control" value="${contact.homePhone}"
                   placeholder="Не обязательно"/>

            <label for="email" class="control-label col-xs-4">Email:</label>
            <input type="text" name="email" id="email" class="form-control" value="${contact.email}"
                   placeholder="hr@lardi-trans.com"/>

            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <br>
            <c:if test="${action == 'edit'}">
                <button type="submit" class="btn btn-primary  btn-md">Обновить</button>
            </c:if>
            <c:if test="${action == 'add'}">
                <button type="submit" class="btn btn-primary  btn-md">Создать</button>
            </c:if> <a class="btn btn-danger btn-md" role="button" onclick="window.location.href='contacts'">Отмена</a>
        </div>
    </form>
</div>
</body>
</html>