<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="phonebook" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <div class="view-box">
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
                    <h2><spring:message code="contact.edit"/></h2>
                    <div class="form-group col-xs-5">
                        <label for="firstName" class="control-label col-xs-5">
                            <spring:message code="contacts.firstName"/>:
                        </label>
                        <input type="text" name="firstName" id="firstName" class="form-control"
                               value="${contact.firstName}"/>

                        <label for="lastName" class="control-label col-xs-5">
                            <spring:message code="contacts.lastName"/>:
                        </label>
                        <input type="text" name="lastName" id="lastName" class="form-control"
                               value="${contact.lastName}"/>

                        <label for="middleName" class="control-label col-xs-5">
                            <spring:message code="contacts.middleName"/>:
                        </label>
                        <input type="text" name="middleName" id="middleName" class="form-control"
                               value="${contact.middleName}"/>

                        <label for="address" class="control-label col-xs-5">
                            <spring:message code="contacts.address"/>:
                        </label>
                        <input type="text" name="address" id="address" class="form-control" value="${contact.address}"/>

                        <label for="mobilePhone" class="control-label col-xs-5">
                            <spring:message code="contacts.mobilePhone"/>:
                        </label>
                        <input type="text" name="mobilePhone" id="mobilePhone" class="form-control"
                               value="${contact.mobilePhone}" placeholder="+380(66)1234567"/>

                        <label for="homePhone" class="control-label col-xs-5">
                            <spring:message code="contacts.homePhone"/>:
                        </label>
                        <input type="text" name="homePhone" id="homePhone" class="form-control"
                               value="${contact.homePhone}" placeholder="Не обязательно"/>

                        <label for="email" class="control-label col-xs-5">
                            <spring:message code="contacts.email"/>:
                        </label>
                        <input type="text" name="email" id="email" class="form-control" value="${contact.email}"
                               placeholder="hr@lardi-trans.com"/>

                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <br>
                        <button type="submit" class="btn btn-primary">
                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                        </button>
                        <a class="btn btn-danger" role="button" onclick="window.location.href='contacts'">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>