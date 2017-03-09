<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css" media="screen">
    <script src="../js/bootstrap.min.js"></script>
</head>

<body>
<nav class="navbar navbar-default navbar-static-top" role="navigation">
    <div align="center" class="container">
        <h2>Вы вошлки как: <span class="label label-default">${currentUser}</span></h2>
    </div>
    <div align="right" class="container">
        <form action="/logout" method="post">
            <button type="submit" class="btn btn-primary  btn-md">Выход</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</nav>
<div class="container">
    <h2>Контакты</h2>
    <!--Search Form -->
    <form action="/contact" method="get" id="seachContactForm" address="form">
        <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
        <div class="form-group col-xs-5">
            <input type="text" name="contactName" id="contactName" class="form-control" required="true"
                   placeholder="Напишите Имя или Фамилию"/>
        </div>
        <button type="submit" class="btn btn-info">
            <span class="glyphicon glyphicon-search"></span> Поиск
        </button>
        <br>
        <br>
    </form>

    <!--Contacts List-->
    <c:if test="${not empty message}">
        <div class="alert alert-success">
                ${message}
        </div>
    </c:if>
    <form action="/contact" method="post" id="contactForm" address="form">
        <input type="hidden" id="idContact" name="idContact">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty contactList}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td>Имя</td>
                        <td>Фамилия</td>
                        <td>Отчество</td>
                        <td>Адрес</td>
                        <td>Моб. телефон</td>
                        <td>Телефон</td>
                        <td>E-mail</td>
                        <td></td>
                    </tr>
                    </thead>
                    <c:forEach var="contact" items="${contactList}">
                        <c:set var="classSucess" value=""/>
                        <c:if test="${idContact == contact.id}">
                            <c:set var="classSucess" value="info"/>
                        </c:if>
                        <tr class="${classSucess}">
                            <td>
                                <a href="/contact?idContact=${contact.id}&searchAction=searchById">${contact.id}</a>
                            </td>
                            <td>${contact.firstName}</td>
                            <td>${contact.lastName}</td>
                            <td>${contact.middleName}</td>
                            <td>${contact.address}</td>
                            <td>${contact.mobilePhone}</td>
                            <td>${contact.homePhone}</td>
                            <td>${contact.email}</td>
                            <td><a href="#" id="remove"
                                   onclick="document.getElementById('action').value = 'remove';document.getElementById('idContact').value = '${contact.id}';

                                           document.getElementById('contactForm').submit();">
                                <input type="hidden" name="${_csrf.parameterName}"
                                       value="${_csrf.token}"/>
                                <span class="glyphicon glyphicon-trash"/>
                            </a>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <br>
                <div class="alert alert-info">
                    По заданым словам совпадений не найдено. <a href="contact">Назад</a>
                </div>
            </c:otherwise>
        </c:choose>
    </form>
    <form action="new-contact">
        <br>
        <button type="submit" class="btn btn-primary  btn-md">Создать контакт</button>
    </form>
</div>
</body>
</html>