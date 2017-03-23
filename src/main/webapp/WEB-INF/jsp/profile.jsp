<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="phonebook" tagdir="/WEB-INF/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2><spring:message code="app.register"/></h2>

            <div class="view-box">
                <form:form modelAttribute="user" class="form-horizontal" method="post" action="register"
                           charset="utf-8" accept-charset="UTF-8">

                    <spring:message code="users.fullName" var="userName"/>
                    <phonebook:inputField label='${userName}' name="fullName"/>

                    <spring:message code="users.login" var="userLogin"/>
                    <phonebook:inputField label='${userLogin}' name="login"/>

                    <spring:message code="users.password" var="userPassword"/>
                    <phonebook:inputField label='${userPassword}' name="password" inputType="password"/>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-10">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                            <a class="btn btn-danger" type="button" onclick="window.location.href='login'">
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