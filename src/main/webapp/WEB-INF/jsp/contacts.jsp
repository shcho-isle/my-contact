<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<c:if test="${not empty message}">
    <div class="alert alert-success">
            ${message}
    </div>
</c:if>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><spring:message code="contacts.title"/> ${currentUser}</h3>
            <div class="view-box">
                <div class="row">
                    <div class="col-sm-5">
                        <div class="panel panel-default">
                            <div class="panel-footer text-right">
                                <form action="/contacts" method="get" id="seachContactForm" address="form">
                                    <input type="hidden" id="searchAction" name="searchAction"
                                           value="searchInFirstLastMobile">
                                    <div class="form-group col-xs-7">
                                        <input type="text" name="searchRequest" id="searchRequest" class="form-control"
                                               required="true"
                                               placeholder="<spring:message code="contacts.search"/>"/>
                                    </div>
                                    <button type="submit" class="btn btn-primary">
                                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                    </button>
                                    <a class="btn btn-danger" type="button" href="contacts">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    </a>
                                    <br>
                                    <br>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <a class="btn btn-info" href="new-contact">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                </a>

                <form action="/contacts" method="post" id="contactForm" address="form">
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
                                <td><a class="btn btn-xs btn-primary" href="/contacts?idContact=${contact.id}&searchAction=searchById" id="edit">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <span class="glyphicon glyphicon-pencil"/></a>
                                </td>
                                <td><a class="btn btn-xs btn-danger" href="<c:url value='/delete-${contact.id}-contact' />">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <span class="glyphicon glyphicon-remove"/></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>