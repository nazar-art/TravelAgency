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
    <title>Users table</title>
    <jsp:include page="../parts/header.jsp"/>
</head>

<body>
<div class="container-fluid users-table">
    <%--navbar--%>
    <div class="row">
        <div class="col-md-10 col-xs-12 col-md-offset-1 table-nav">
            <jsp:include page="../parts/navbar.jsp"/>
        </div>
    </div>
    <%-- ARROW --%>
    <div class="row">
        <div class="col-md-10 col-sm-12 col-xs-12 col-md-offset-1 tables-menu">
            <h1><fmt:message key="usersTable.title"/></h1>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <fmt:message key="usersTable.title"/>
                        <b class="caret"></b>
                    </a>
                    <span class="dropdown-arrow"></span>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/admin/tourstable"><fmt:message key="toursTable.title"/></a>
                        </li>
                        <li>
                            <a href="/admin/purchasestable"><fmt:message key="purchasesTable.title"/></a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
        <%--form --%>
    <form action="/admin/userstable" enctype="multipart/form-data" method="post" id="usertable-form">
        <div class="row">
            <div class="table-responsive tile col-md-10 col-sm-12 col-xs-12 col-md-offset-1">
                <table class="table table-striped table-hover">
                    <thead>
                    <%--Titles--%>
                    <tr>
                        <th></th>
                        <th>#</th>
                        <th><fmt:message key="users.firstName"/></th>
                        <th><fmt:message key="users.lastName"/></th>
                        <th><fmt:message key="users.email"/></th>
                        <th><fmt:message key="users.password"/></th>
                        <th><fmt:message key="users.discount"/></th>
                        <th><fmt:message key="users.admin"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${requestScope.users}" var="user">
                        <tr>
                            <td>
                                <label class="checkbox" for="checkbox${user.id}">
                                    <input type="checkbox" name="checkedUsers" value="${user.id}"
                                           id="checkbox${user.id}" data-toggle="checkbox">
                                </label>
                            </td>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.email}</td>
                            <td>${user.password}</td>
                            <td>${user.discount}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.admin}">
                                        <fmt:message key="statement.true"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="statement.false"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <%--edit photo--%>
                            <td>
                                <span class="fui-photo modal-icon" data-toggle="modal"
                                      data-target="#modalImage${user.id}"></span>

                                <div class="modal fade" id="modalImage${user.id}" tabindex="-1" role="dialog"
                                     aria-labelledby="modalImageLabel${user.id}" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-hidden="true">&times;</button>
                                                <h4 class="modal-title">${user.firstName} ${user.lastName}</h4>
                                            </div>
                                            <div class="modal-body">
                                                <img src="../img/users/${user.image}">

                                                <p>${user.image}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <%--edit profile icon--%>
                            <td>
                                <span class="fui-new modal-icon" data-toggle="modal"
                                      data-target="#modalEdit${user.id}"></span>

                                <div class="modal fade" id="modalEdit${user.id}" tabindex="-1" role="dialog"
                                     aria-labelledby="modalEditLabel${user.id}" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-hidden="true">&times;</button>
                                                <h4 class="modal-title">ID: ${user.id}</h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <span><fmt:message key="users.firstName"/></span>
                                                    <input class="form-control" placeholder="<fmt:message key="users.firstName"/>"
                                                           name="first_name-${user.id}"
                                                           value="${user.firstName}"/>
                                                </div>
                                                <div class="form-group">
                                                    <span><fmt:message key="users.lastName"/></span>
                                                    <input class="form-control" placeholder="<fmt:message key="users.lastName"/>"
                                                           name="last_name-${user.id}"
                                                           value="${user.lastName}"/>
                                                </div>
                                                <div class="form-group">
                                                    <span><fmt:message key="users.image"/></span>
                                                    <div class="input-group">
								                        <span class="input-group-btn">
									                        <span class="btn btn-primary btn-file">
										                        <fmt:message key="button.browse"/>
										                        <input type="file" name="userImage-${user.id}"
                                                                       accept="image/*"/>
									                        </span>
								                        </span>
                                                        <input type="text" class="form-control" readonly="">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <span><fmt:message key="login.email"/></span>
                                                    <input class="form-control" type="email" placeholder="<fmt:message key="login.email"/>"
                                                           name="email-${user.id}"
                                                           pattern="[^ @]*@[^ @]*\.[^ @]{2,}" value="${user.email}"/>
                                                </div>
                                                <div class="form-group">
                                                    <span><fmt:message key="users.discount"/></span>
                                                    <input class="form-control" type="number" placeholder="<fmt:message key="users.discount"/>"
                                                           name="discount-${user.id}"
                                                           value="${user.discount}"/>
                                                </div>
                                                <div class="form-group">
                                                    <span><fmt:message key="users.admin"/></span>
                                                    <select name="admin-${user.id}" class="select-block"
                                                            form="usertable-form">
                                                        <c:choose>
                                                            <c:when test="${user.admin}">
                                                                <option value="true" selected="selected"><fmt:message key="statement.true"/></option>
                                                                <option value="false"><fmt:message key="statement.false"/></option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="true"><fmt:message key="statement.true"/></option>
                                                                <option value="false" selected="selected"><fmt:message key="statement.false"/></option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <button class="btn btn-primary btn-lg btn-block" name="update"
                                                            type="submit" value="${user.id}">
                                                        <fmt:message key="button.submit"/>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-7 col-sm-7 col-xs-6 col-md-offset-1">
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
