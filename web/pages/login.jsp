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
    <title>Login</title>
    <jsp:include page="parts/header.jsp"/>
</head>

<body>
<div class="container-fluid login-page">
    <div class="row">
        <div class="login-screen">
            <div class="login-part">
                <div class="login-ico col-md-1 col-sm-2 col-xs-12 col-md-offset-3">
                    <img class="hidden-xs" src="../img/watches.png" alt="Login"/>
                    <h4><fmt:message key="login.title"/></h4>
                </div>
                <div class="login-form col-md-4 col-sm-8 col-xs-12 col-md-offset-1 col-sm-offset-1">
                    <form action="login" method="post">
                        <div class="form-group">
                            <p class="error">${loginErrors}</p>
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="email" placeholder="<fmt:message key="login.email"/>" name="email"
                                   pattern="[^ @]*@[^ @]*\.[^ @]{2,}" required id="email"/>
                            <label class="login-field-icon fui-mail" for="email"></label>
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="password" placeholder="<fmt:message key="login.password"/>" name="password" required
                                   id="password"/>
                            <label class="login-field-icon fui-lock" for="password"></label>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary btn-lg btn-block" name="submit"
                                    type="submit" value="Submit">
                                <fmt:message key="button.submit"/>
                            </button>
                            <a class="login-link" href="<c:url value="/registration"/>"><fmt:message key="login.singup"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

