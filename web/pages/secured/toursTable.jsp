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
    <title>Tours table</title>
    <jsp:include page="../parts/header.jsp"/>
</head>

<body>
<div class="container-fluid tours-table">
    <div class="row">
        <div class="col-md-10 col-xs-12 col-md-offset-1 table-nav">
            <jsp:include page="../parts/navbar.jsp"/>
        </div>
    </div>
    <%--arrow --%>
    <div class="row">
        <div class="col-md-10 col-sm-12 col-xs-12 col-md-offset-1 tables-menu">
            <h1><fmt:message key="toursTable.title"/></h1>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <fmt:message key="toursTable.title"/>
                        <b class="caret"></b>
                    </a>
                    <span class="dropdown-arrow"></span>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/admin/userstable"><fmt:message key="usersTable.title"/></a>
                        </li>
                        <li>
                            <a href="/admin/purchasestable"><fmt:message key="purchasesTable.title"/></a>
                        </li>
                    </ul>
                </li>
                <li class="divider"></li>
                <li>
                    <button id="trigger-overlay" type="button" class="btn btn-primary"><fmt:message
                            key="button.addTour"/></button>
                </li>
            </ul>
        </div>
    </div>

<form action="/admin/tourstable" enctype="multipart/form-data" method="post" id="tourtable-form">
    <div class="row">
        <div class="table-responsive tile col-md-10 col-sm-12 col-xs-12 col-md-offset-1">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>#</th>
                    <th><fmt:message key="tours.name"/></th>
                    <th><fmt:message key="tours.type"/></th>
                    <th><fmt:message key="tours.depart"/></th>
                    <th><fmt:message key="tours.arrival"/></th>
                    <th><fmt:message key="tours.price"/></th>
                    <th><fmt:message key="tours.discount"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${requestScope.toursTable}" var="tour">
                    <tr>
                        <td>
                            <label class="checkbox" for="checkbox${tour.id}">
                                <input type="checkbox" name="checkedTour" value="${tour.id}"
                                       id="checkbox${tour.id}" data-toggle="checkbox">
                            </label>
                        </td>
                        <td>${tour.id}</td>
                        <td>${tour.name}</td>
                        <td>${tour.type}</td>
                        <td>${tour.depart}</td>
                        <td>${tour.arrival}</td>
                        <td>${tour.price}</td>
                        <td>${tour.discount}</td>
                        <td>
                            <span class="fui-photo tourtable-image" data-toggle="modal"
                                  data-target="#myModal${tour.id}"></span>

                            <div class="modal fade" id="myModal${tour.id}" tabindex="-1" role="dialog"
                                 aria-labelledby="myModalLabel${tour.id}" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">${tour.name}</h4>
                                        </div>
                                        <div class="modal-body">
                                            <img src="../img/tours/${tour.image}">

                                            <p>${tour.image}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>
                            <span class="fui-new modal-icon" data-toggle="modal"
                                  data-target="#modalEdit${tour.id}"></span>

                            <div class="modal fade" id="modalEdit${tour.id}" tabindex="-1" role="dialog"
                                 aria-labelledby="modalEditLabel${tour.id}" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-hidden="true">&times;</button>
                                            <h4 class="modal-title">ID: ${tour.id}</h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <span><fmt:message key="tours.name"/></span>
                                                <input class="form-control"
                                                       placeholder="<fmt:message key="tours.name"/>"
                                                       name="name-${tour.id}"
                                                       value="${tour.name}"/>
                                            </div>
                                            <div class="form-group">
                                                <span><fmt:message key="tours.image"/></span>

                                                <div class="input-group">
                                                        <span class="input-group-btn">
                                                            <span class="btn btn-primary btn-file">
                                                                <fmt:message key="button.browse"/>
                                                                <input type="file" name="tour-image-${tour.id}"
                                                                       accept="image/*"/>
                                                            </span>
                                                        </span>
                                                    <input type="text" class="form-control" readonly="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <span><fmt:message key="tours.type"/> </span>
                                                <select name="typeslist-${tour.id}" class="select-block"
                                                        form="tourtable-form">
                                                    <c:choose>
                                                        <c:when test="${tour.type == 'Excursion'}">
                                                            <option value="Excursion" selected="selected">
                                                                <fmt:message key="tours.type.excursion"/>
                                                            </option>
                                                            <option value="Vacation">
                                                                <fmt:message key="tours.type.vacation"/>
                                                            </option>
                                                            <option value="Shopping">
                                                                <fmt:message key="tours.type.shopping"/>
                                                            </option>
                                                        </c:when>
                                                        <c:when test="${tour.type == 'Vacation'}">
                                                            <option value="Excursion">
                                                                <fmt:message key="tours.type.excursion"/>
                                                            </option>
                                                            <option value="Vacation" selected="selected">
                                                                <fmt:message key="tours.type.vacation"/>
                                                            </option>
                                                            <option value="Shopping">
                                                                <fmt:message key="tours.type.shopping"/>
                                                            </option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="Excursion">
                                                                <fmt:message key="tours.type.excursion"/>
                                                            </option>
                                                            <option value="Vacation">
                                                                <fmt:message key="tours.type.vacation"/>
                                                            </option>
                                                            <option value="Shopping" selected="selected">
                                                                <fmt:message key="tours.type.shopping"/>
                                                            </option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <span><fmt:message key="tours.depart"/> </span>
                                                <input class="form-control" type="date"
                                                       placeholder="<fmt:message key="tours.depart"/>"
                                                       name="depart-${tour.id}" value="${tour.depart}"/>
                                            </div>
                                            <div class="form-group">
                                                <span><fmt:message key="tours.arrival"/></span>
                                                <input class="form-control" type="date"
                                                       placeholder="<fmt:message key="tours.arrival"/>"
                                                       name="arrival-${tour.id}" value="${tour.arrival}"/>
                                            </div>
                                            <div class="form-group">
                                                <span><fmt:message key="tours.price"/></span>
                                                <input class="form-control" type="number"
                                                       placeholder="<fmt:message key="tours.price"/>"
                                                       name="price-${tour.id}" min="0" value="${tour.price}"/>
                                            </div>
                                            <div class="form-group">
                                                <span><fmt:message key="tours.discount"/></span>
                                                <input class="form-control" type="number"
                                                       placeholder="<fmt:message key="tours.discount"/>"
                                                       name="discount-${tour.id}" min="0" value="${tour.discount}"/>
                                            </div>
                                            <div class="form-group">
                                                <button class="btn btn-primary btn-lg btn-block" name="update"
                                                        type="submit" value="${tour.id}">
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
<div class="overlay overlay-slidedown login-screen">
    <button type="button" class="overlay-close">Close</button>
    <div class="login-part">
        <div class="login-ico col-md-1 col-sm-2 col-xs-12 col-md-offset-3">
            <img class="hidden-xs" src="${pageContext.request.contextPath}/img/clipboard.png" alt="Add tour"/>
            <h4>
                <fmt:message key="button.addTour"/>
            </h4>
        </div>
        <div class="login-form col-md-4 col-sm-8 col-xs-12 col-md-offset-1 col-sm-offset-1">
            <form class="overlay-form" action="${pageContext.request.contextPath}/admin/tourstable" method="post"
                  enctype="multipart/form-data" id="registration-tour-form">
                <div class="form-group">
                    <c:forEach items="${requestScope.registrationTourErrors}" var="error">
                        <p class="error">${error}</p>
                    </c:forEach>
                </div>
                <div class="form-group">
                    <input class="form-control" placeholder="<fmt:message key="tours.name"/>" name="name" required/>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-btn">
						    <span class="btn btn-primary btn-file">
							    <fmt:message key="button.browse"/>
								<input type="file" name="tour-image" accept="image/*"/>
							</span>
						</span>
                        <input type="text" class="form-control" readonly="">
                    </div>
                </div>
                <div class="form-group">
                    <%--@declare id="registration-tour-form"--%>
                    <select name="typeslist" class="select-block" form="registration-tour-form">
                        <option value="Excursion"><fmt:message key="tours.type.excursion"/></option>
                        <option value="Vacation" selected="selected"><fmt:message key="tours.type.vacation"/></option>
                        <option value="Shopping"><fmt:message key="tours.type.shopping"/></option>
                    </select>
                </div>
                <div class="form-group">
                    <input class="form-control" type="date" placeholder="<fmt:message key="tours.depart"/>"
                           name="depart" required/>
                </div>
                <div class="form-group">
                    <input class="form-control" type="date" placeholder="<fmt:message key="tours.arrival"/>"
                           name="arrival" required/>
                </div>
                <div class="form-group">
                    <input class="form-control" type="number" placeholder="<fmt:message key="tours.price"/>"
                           name="price" min="0" required/>
                </div>
                <div class="form-group">
                    <button class="btn btn-primary btn-lg btn-block" name="add-tour"
                            type="submit" value="add-tour">
                        <fmt:message key="button.addTour"/>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
<jsp:include page="../parts/scripts.jsp"/>
</body>
</html>
