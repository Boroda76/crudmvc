<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>User page</title>
</head>
<body>
<h1>Hello <c:out value="${user.login}"/>!</h1>
<h2 style="color:darkred"><c:out value="${message}"/></h2>
<c:choose>
    <c:when test="${user.rolesString.contains('ADMIN')}">
        <a href="/admin"><button>USERS LIST</button></a><br><br>
    </c:when>
</c:choose>
<table border="1" cellpadding="5">
    <thead>
    <tr>
        <td>login</td>
        <td>email</td>
        <td>sex</td>
        <td>age</td>
        <td>weight</td>
        <td>height</td>
        <td>role</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${user.login}"/></td>
        <td><c:out value="${user.email}"/></td>
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
    </tr>
    </tbody>
</table>
<br>
<a href="/logout"><button>LOGOUT</button></a>
</body>
</html>
