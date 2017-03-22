<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="phonebook" tagdir="/WEB-INF/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2><spring:message code="contact.edit"/></h2>

            <div class="view-box">
                <form:form modelAttribute="contact" class="form-horizontal" method="post"
                           charset="utf-8" accept-charset="UTF-8">

                    <form:input type="hidden" path="id" id="id"/>

                    <spring:message code="contacts.lastName" var="lastName"/>
                    <phonebook:inputField label='${lastName}' name="lastName"/>

                    <spring:message code="contacts.firstName" var="firstName"/>
                    <phonebook:inputField label='${firstName}' name="firstName"/>

                    <spring:message code="contacts.middleName" var="middleName"/>
                    <phonebook:inputField label='${middleName}' name="middleName"/>

                    <spring:message code="contacts.mobilePhone" var="mobilePhone"/>
                    <phonebook:inputField label='${mobilePhone}' name="mobilePhone"/>

                    <spring:message code="contacts.homePhone" var="homePhone"/>
                    <phonebook:inputField label='${homePhone}' name="homePhone"/>

                    <spring:message code="contacts.address" var="address"/>
                    <phonebook:inputField label='${address}' name="address"/>

                    <spring:message code="contacts.email" var="email"/>
                    <phonebook:inputField label='${email}' name="email"/>

                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-10">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                            <a class="btn btn-danger" role="button" onclick="window.location.href='contacts'">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                            </a>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>