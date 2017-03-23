<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body class="security-app">

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header navbar-brand"><spring:message code="app.title"/></div>
        <div class="navbar-collapse collapse">
            <form:form class="navbar-form navbar-right" role="form" action="spring_security_check" method="post">
                <div class="form-group">
                    <input type="text" placeholder="<spring:message code="users.login"/>" class="form-control"
                           name="login">
                </div>
                <div class="form-group">
                    <input type="password" placeholder="<spring:message code="users.password"/>"
                           class="form-control" name="password">
                </div>
                <button type="submit" class="btn btn-success">
                    <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                </button>
            </form:form>
        </div>
    </div>
</div>

<div class="jumbotron">
    <div class="container">
        <c:if test="${param.error ne null}">
            <div class="error">
                <spring:message code="login.badCredentials"/>
            </div>
        </c:if>
        <c:if test="${not empty param.message}">
            <div class="message">
                <spring:message code="${param.message}" arguments="${param.fullname}"/>
            </div>
        </c:if>
        <br/>
        <p>
            <a class="btn btn-lg btn-success" href="register"><spring:message code="app.register"/> &raquo;</a>
            <button type="submit" class="btn btn-lg btn-primary" onclick="setCredentials('Vano', 'password')">
                <spring:message code="login.enter"/> user1
            </button>
            <button type="submit" class="btn btn-lg btn-primary" onclick="setCredentials('Serg', 'password')">
                <spring:message code="login.enter"/> user2
            </button>
        </p>
        <br/>
        <p><spring:message code="login.powered"/>
            <a href="https://projects.spring.io/spring-boot/">Spring Boot</a>,
            <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,
            <a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
            <a href="http://projects.spring.io/spring-security/">Spring Security</a>,
            <a href="http://hibernate.org/orm/">Hibernate ORM</a>,
            <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
            <a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,
            <a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,
            <a href="http://tomcat.apache.org/">Apache Tomcat</a>,
            <a href="http://www.webjars.org/">WebJars</a>,
            <a href="http://junit.org/">JUnit</a>,
            <a href="http://getbootstrap.com/">Bootstrap</a>.</p>
    </div>
</div>
<div class="container">
    <div class="lead">
        A <a href="https://en.wikipedia.org/wiki/Telephone_directory">telephone directory</a> ,
        also known as a telephone book, telephone address book, phone book, or the white/yellow pages, is a listing
        of telephone subscribers in a geographical area or subscribers to services provided by the organization
        that publishes the directory. Its purpose is to allow the telephone number of a subscriber identified by name
        and address to be found.
        <br><br>
        The advent of the Internet and smart phones in the 21st century greatly reduced the need for a paper phone book.
        Some communities, such as Seattle and San Francisco, sought to ban their unsolicited distribution as wasteful,
        unwanted and harmful to the environment.
    </div>
</div>

<script type="text/javascript">
    <c:if test="${not empty param.login}">
    setCredentials("${param.login}", "");
    </c:if>
    function setCredentials(login, password) {
        $('input[name="login"]').val(login);
        $('input[name="password"]').val(password);
    }
</script>

</body>
</html>