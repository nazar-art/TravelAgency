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
    <title>Purchase</title>
    <meta http-equiv="Refresh" content="2;url=<c:url value="/tours"/>">
    <jsp:include page="parts/header.jsp"/>
</head>

<body>
<div class="container-fluid login-page">
    <div class="row">
        <div class="login-screen">
            <div class="login-part">
                <div class="col-md-8 col-sm-8 col-xs-12 col-md-offset-2 col-sm-offset-2">
                    <h1><fmt:message key="purchase.thanks"/></h1>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

