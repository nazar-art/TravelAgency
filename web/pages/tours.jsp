<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.zasadnyy.task10.i18n.text"/>

<html lang="${language}">
<head>
    <title>Tours</title>
    <jsp:include page="parts/header.jsp"/>
</head>

<body>
<div class="container tours">
    <div class="col-md-10 col-xs-12 col-md-offset-1">
        <jsp:include page="parts/navbar.jsp"/>
    </div>

    <div class="row">
        <div class="col-md-10 col-xs-12 col-md-offset-1">
            <c:forEach items="${tours}" var="tour">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <div class="tile <c:if test="${tour.discount > 0}">tile-discount</c:if>">
                        <h5 class="tour-type text-left">
                            <c:choose>
                                <c:when test="${tour.type == 'Excursion'}">
                                    <fmt:message key="tours.type.excursion"/>
                                </c:when>
                                <c:when test="${tour.type == 'Vacation'}">
                                    <fmt:message key="tours.type.vacation"/>
                                </c:when>
                                <c:when test="${tour.type == 'Shopping'}">
                                    <fmt:message key="tours.type.shopping"/>
                                </c:when>
                            </c:choose>
                        <%--${tour.type}--%>
                        </h5>

                        <div class="tour-image flow-img" style="background-image:url(../img/tours/${tour.image})"></div>
                        <h3 class="tile-title">${tour.name}</h3>

                        <p class="text-left">
                            <strong><fmt:message key="tours.depart"/>:</strong> ${tour.depart} <br>
                            <strong><fmt:message key="tours.arrival"/>:</strong> ${tour.arrival} <br>
                            <c:choose>
                                <c:when test="${tour.discount > 0}">
                                    <strong><fmt:message key="tours.price"/>:</strong> <span
                                        style="color: #E74C3C">$${tour.price * (100 - tour.discount)/100}</span>
                                </c:when>

                                <c:otherwise>
                                    <strong><fmt:message key="tours.price"/>:</strong> $${tour.price}
                                </c:otherwise>
                            </c:choose>

                        </p>
                        <a class="btn btn-primary btn-large btn-block"
                           href="<c:url value="/purchase?tour=${tour.id}"/>"><fmt:message key="button.buy"/></a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <!-- /tiles -->
</div>
<jsp:include page="parts/scripts.jsp"/>
</body>
</html>
