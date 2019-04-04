<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Please introduce yourself</h1>
<c:if test="${message!=null}">
    <h2 style="color:darkred"><c:out value="${message}"/></h2>
</c:if>
<form action="" method="post">
    <input type="text" name="login" placeholder="login" required/>
    <br>
    <input type="password" name="password" placeholder="password" required/>
    <br>
    <input type="submit" value="LOGIN"/>
</form>
</body>
</html>
