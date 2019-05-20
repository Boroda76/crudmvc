<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>List of users</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/">CRUDMVC</a>


    <div id="navb" class="collapse navbar-collapse">
        <ul class="navbar-nav float-right">


            <li class="nav-item">
                <a class="nav-link disabled" href="javascript:logout()">Disabled</a>
            </li>
        </ul>

    </div>
</nav>
    <h1>All users for CRUD</h1>
    <a href="/logout"><button>LOGOUT</button></a>
    <a href="/admin/update"><button>CREATE NEW USER</button></a><br><br>
    <div>
    <table border="1" cellpadding="5">
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
            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${user.weight}" /></td>
            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${user.height}" /></td>
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
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
