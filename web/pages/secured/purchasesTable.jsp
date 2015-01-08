<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.zasadnyy.task10.i18n.text"/>

<html lang="${language}">
<head>
    <title>Purchases table</title>
    <jsp:include page="../parts/header.jsp"/>
</head>

<body>
<div class="container-fluid purchases-table">
    <div class="row">
        <div class="col-md-10 col-xs-12 col-md-offset-1 table-nav">
            <jsp:include page="../parts/navbar.jsp"/>
        </div>
    </div>
    <%-- ARROW --%>
    <div class="row">
        <div class="col-md-10 col-sm-12 col-xs-12 col-md-offset-1 tables-menu">
            <h1><fmt:message key="purchasesTable.title"/></h1>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <fmt:message key="purchasesTable.title"/>
                        <b class="caret"></b>
                    </a>
                    <span class="dropdown-arrow"></span>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/admin/tourstable"><fmt:message key="toursTable.title"/></a>
                        </li>
                        <li>
                            <a href="/admin/userstable"><fmt:message key="usersTable.title"/></a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>

    <form action="/admin/purchasestable" method="post">
        <div class="row">
            <div class="table-responsive tile col-md-10 col-sm-12 col-xs-12 col-md-offset-1">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>#</th>
                        <th><fmt:message key="purchasesTable.user"/></th>
                        <th><fmt:message key="purchasesTable.tour"/></th>
                        <th><fmt:message key="purchasesTable.timeOfPurchase"/></th>
                        <th><fmt:message key="purchasesTable.price"/></th>
                        <th><fmt:message key="purchasesTable.checked"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.purchases}" var="purchase">
                        <tr>
                            <td>
                                <label class="checkbox" for="checkbox${purchase.id}">
                                    <input type="checkbox" name="checkedPurchases" value="${purchase.id}"
                                           id="checkbox${purchase.id}" data-toggle="checkbox">
                                </label>
                            </td>
                            <td>${purchase.id}</td>
                            <td>${purchase.user.email}</td>
                            <td>${purchase.tour.name}</td>
                            <td>${purchase.timeOfPurchase}</td>
                            <td>${purchase.price}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${purchase.checked}">
                                        true
                                    </c:when>
                                    <c:otherwise>
                                        false
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-7 col-sm-7 col-xs-6 col-md-offset-1">
                <button class="btn btn-info" name="check"
                        type="submit" value="Check">
                    <fmt:message key="button.check"/>
                </button>
                <button class="btn btn-danger" name="delete"
                        type="submit" value="Delete">
                    <fmt:message key="button.delete"/>
                </button>
            </div>
        </div>
    </form>
</div>
<jsp:include page="../parts/scripts.jsp"/>
</body>
</html>
