<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>List of users</title>
</head>
<body>
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
</body>
</html>
