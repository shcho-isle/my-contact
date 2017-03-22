<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <c:if test="${not empty param.message}">
            <div class="message">
                <spring:message code="${param.message}" arguments="${param.lastname}"/>
            </div>
        </c:if>
        <div class="shadow">
            <h3><spring:message code="contacts.title"/> ${user.fullName}</h3>
            <div class="view-box">
                <div class="row">
                    <div class="col-sm-3 col-sm-offset-9">
                        <div class="panel panel-default">
                            <div class="panel-footer text-right">
                                <form action="searchContact">
                                    <div class="form-group">
                                        <input type="text" name="searchRequest" id="searchRequest" class="form-control"
                                               required
                                               placeholder="<spring:message code="contacts.search"/>"
                                               value="${searchRequest}"
                                        />
                                    </div>
                                    <div class="panel-footer text-right">
                                        <button type="submit" class="btn btn-primary">
                                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                        </button>
                                        <a class="btn btn-danger" type="button"
                                           onclick="window.location.href='contacts'">
                                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                        </a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <a class="btn btn-info" href="new">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                </a>

                <form action="contacts" method="post" id="contactForm">
                    <input type="hidden" id="idContact" name="idContact">
                    <input type="hidden" id="action" name="action">

                    <table class="table table-striped display" id="datatable">
                        <thead>
                        <tr>
                            <th><spring:message code="contacts.lastName"/></th>
                            <th><spring:message code="contacts.firstName"/></th>
                            <th><spring:message code="contacts.middleName"/></th>
                            <th><spring:message code="contacts.mobilePhone"/></th>
                            <th><spring:message code="contacts.homePhone"/></th>
                            <th><spring:message code="contacts.address"/></th>
                            <th><spring:message code="contacts.email"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <c:forEach var="contact" items="${contactList}">
                            <c:set var="classSucess" value=""/>
                            <c:if test="${idContact == contact.id}">
                                <c:set var="classSucess" value="info"/>
                            </c:if>
                            <tr class="${classSucess}">
                                <td>${contact.lastName}</td>
                                <td>${contact.firstName}</td>
                                <td>${contact.middleName}</td>
                                <td>${contact.mobilePhone}</td>
                                <td>${contact.homePhone}</td>
                                <td>${contact.address}</td>
                                <td>${contact.email}</td>
                                <td>
                                    <a class="btn btn-xs btn-primary" role="button" id="edit"
                                       onclick="window.location.href='/update-${contact.id}-contact'">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </a>
                                </td>
                                <td>
                                    <a class="btn btn-xs btn-danger" role="button"
                                       onclick="window.location.href='/delete-${contact.id}-contact'">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>