<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>List of users</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/">CRUDMVC</a>
    <ul class="navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link" href="/logout">Logout</a>
        </li>
    </ul>

</nav>
<div class="container-fluid">
<div class="row h-100">
<div class="col-lg-2 bg-light pr-0">
    <ul class="nav nav-pills flex-column mt-3">
        <li class="nav-item">
            <a class="nav-link active" data-toggle="tab" href="#admin">Admin</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="tab" href="#user">User</a>
        </li>
    </ul>
</div>
<div class="col-lg-10" style="background-color:rgba(0, 0, 0, 0.05);">
    <div class="tab-content mt-3">
        <div class="tab-pane container active" id="admin">
            <h1>Admin panel</h1>
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link active" data-toggle="tab" href="#list">Users table</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#create">New user</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane container active pl-0" id="list">
            <div class="card">
                <div class="card-header">All users</div>
                <div class="card-body">
            <table class="table table-striped table-responsive">
                <thead>
                <tr>
                    <td>login</td>
                    <td>email</td>
                    <td>password</td>
                    <td>sex</td>
                    <td>age</td>
                    <td>weight</td>
                    <td>height</td>
                    <td>role</td>
                    <td>action</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td><c:out value="${user.login}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.password}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${user.sex}">
                                    Male
                                </c:when>
                                <c:when test="${not user.sex}">
                                    Female
                                </c:when>
                            </c:choose>
                        </td>
                        <td><c:out value="${user.age}"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${user.weight}"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${user.height}"/></td>
                        <td><c:out value="${user.rolesString}"/></td>
                        <td>
                            <a href="/admin/user/<c:out value='${user.id}'/>">Open</a>
                            /
                            <a href="/admin/update?id=<c:out value='${user.id}'/>">Edit</a>
                            /
                            <a href="/admin/delete?id=<c:out value='${user.id}'/>">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
                </div>
            </div>
            </div>
                <div class="tab-pane container fade pl-0" id="create">
                    <div class="card">
                        <div class="card-header">Add new user</div>
                        <div class="card-body">FORM HERE<br/><button class="btn button">CREATE</button></div>
                </div>
        </div>
        </div>
        <div class="tab-pane container fade" id="user">Hello USERNAME </div>
    </div>
</div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
