<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ContactManager" tagdir="/WEB-INF/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2><spring:message code="${isNew ? 'contact.add' : 'contact.edit'}"/></h2>

            <div class="view-box">
                <form:form modelAttribute="contact" class="form-horizontal" method="post" action="${isNew ? 'new' : 'update'}"
                           charset="utf-8" accept-charset="UTF-8">

                    <form:input type="hidden" path="id" id="id"/>

                    <spring:message code="contact.lastName" var="lastName"/>
                    <ContactManager:inputField label='${lastName}' name="lastName"/>

                    <spring:message code="contact.firstName" var="firstName"/>
                    <ContactManager:inputField label='${firstName}' name="firstName"/>

                    <spring:message code="contact.middleName" var="middleName"/>
                    <ContactManager:inputField label='${middleName}' name="middleName"/>

                    <spring:message code="contact.mobilePhone" var="mobilePhone"/>
                    <ContactManager:inputField label='${mobilePhone}' name="mobilePhone"/>

                    <spring:message code="contact.homePhone" var="homePhone"/>
                    <ContactManager:inputField label='${homePhone}' name="homePhone"/>

                    <spring:message code="contact.address" var="address"/>
                    <ContactManager:inputField label='${address}' name="address"/>

                    <spring:message code="contact.email" var="email"/>
                    <ContactManager:inputField label='${email}' name="email"/>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-10">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                            <a class="btn btn-danger" type="button" onclick="window.location.href='contacts'">
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