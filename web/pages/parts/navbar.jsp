<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.zasadnyy.task10.i18n.text"/>

<nav class="navbar navbar-inverse navbar-embossed" role="navigation">
    <%--logo with link button to tours--%>
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-01">
            <span class="sr-only">Toggle navigation</span>
        </button>
        <a class="navbar-brand logo-nav" href="<c:url value="/tours"/>">
            <img src="../img/logo.png">
        </a>
    </div>
    <%--drop down list => types of exurcion--%>
    <div class="collapse navbar-collapse" id="navbar-collapse-01">
        <ul class="nav navbar-nav navbar-left">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <fmt:message key="nav.dropdown.label"/>
                    <b class="caret"></b>
                </a>
                <span class="dropdown-arrow"></span>
                <ul class="dropdown-menu">
                    <li>
                        <a href="<c:url value="/tours?select=excursion"/>"><fmt:message key="nav.dropdown.excursion"/></a>
                    </li>
                    <li>
                        <a href="<c:url value="/tours?select=vacation"/>"><fmt:message key="nav.dropdown.vacation"/></a>
                    </li>
                    <li>
                        <a href="<c:url value="/tours?select=shopping"/>"><fmt:message key="nav.dropdown.shopping"/></a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="<c:url value="/tours"/>"><fmt:message key="nav.dropdown.all"/></a>
                    </li>
                </ul>
            </li>
        </ul>

        <%--Logged User info--%>
        <% if (request.getSession().getAttribute("user") != null) { %>
        <div class="navbar-right logged-user">
            <div class="profile-image flow-img" style="background-image:url(../img/users/${sessionScope.user.image})"></div>
            <p>${sessionScope.user.firstName} ${sessionScope.user.lastName}</p>
            <ul class="nav navbar-nav navbar-right">

                <%--Admin link--%>
                <c:if test="${sessionScope.user.admin}">
                    <li>
                        <a href="<c:url value="/admin/purchasestable"/>"><fmt:message key="nav.dropdown.admin"/></a>
                    </li>
                    <li class="divider"></li>
                </c:if>
                    <%--Log out--%>
                <li>
                    <a href="<c:url value="/tours?logout=${sessionScope.user.id}"/>"><fmt:message key="nav.dropdown.logout"/></a>
                </li>
                <li class="lang">
                    <a href="<%= request.getContextPath()%>?language=${language == 'uk' ? 'en' : 'uk'}">
                        ${language == 'uk' ? 'EN' : 'UKR'}
                    </a>
                </li>
            </ul>
        </div>
        <% } else {%>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="<c:url value="/login"/>"><fmt:message key="nav.dropdown.login"/></a>
            </li>
            <li class="divider"></li>
            <li>
                <a href="<c:url value="/registration"/>"><fmt:message key="nav.dropdown.singup"/></a>
            </li>
            <li class="lang">
                <a href="<%= request.getContextPath()%>?language=${language == 'uk' ? 'en' : 'uk'}">
                    ${language == 'uk' ? 'EN' : 'UKR'}
                </a>
            </li>
        </ul>
        <% } %>
    </div>
</nav>