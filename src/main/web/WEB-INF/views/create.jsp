<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User editor</title>
</head>
<body>
    <h1>Fill all fields to <c:if test="${userEdit != null}">
        edit
    </c:if>
        <c:if test="${userEdit == null}">
            create new
        </c:if> user</h1>
    <c:if test="${message!=null}">
        <h2 style="color:darkred"><c:out value="${message}"/></h2>
    </c:if>
    <a href="/logout"><button>LOGOUT</button></a><br><br>
    <div>
    <form action="/admin/update" method="post">
        <c:if test="${userEdit != null}">
            <input type="hidden" name="id" value="<c:out value='${userEdit.id}'/>" />
        </c:if>
        <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}"/>
        <input type="text" name="login" placeholder="login" value="<c:out value='${userEdit.login}'/>" required/>
        <br/>
        <input type="password" name="password" placeholder="password" value="<c:out value='${userEdit.password}'/>" required/>
        <br/>
        <input type="text" name="email" placeholder="email" value="<c:out value='${userEdit.email}'/>" required/>
        <br/>
        <select name="role" required>
            <option disabled>Choose role</option>
            <c:choose>
                <c:when test="${userEdit.rolesString.contains('ADMIN')}">
            <option  value="USER">user</option>
            <option selected value="ADMIN">admin</option>
                </c:when>
                <c:otherwise>
                    <option  selected value="USER">user</option>
                    <option  value="ADMIN">admin</option>
                </c:otherwise>
            </c:choose>
        </select>
        <br/>
        <input type="number" name="age" placeholder="age" value="<c:out value='${userEdit.age}'/>" required/>
        <p>Choose sex</p>
        <input type="radio" name="sex" value="true" <c:if test="${userEdit.sex}">checked</c:if> required/> Male
        <input type="radio" name="sex" value="false" <c:if test="${userEdit!=null&&userEdit.sex!=true}">checked</c:if>/> Female
        <br/>
        <input type="number" name="weight" placeholder="weight" step="0.01" value="<c:out value='${String.format(\"%.2f\",userEdit.weight).replace(\',\',\'.\')}'/>" required/>
        <br/>
        <input type="number" name="height" placeholder="height" step="0.01" value="<c:out value='${String.format(\"%.2f\",userEdit.height).replace(\',\',\'.\')}'/>" required/>
        <br/>
        <input type="submit" <c:choose>
            <c:when test="${userEdit!=null}">
                value="UPDATE"
            </c:when>
            <c:otherwise>
                value="CREATE"
            </c:otherwise>
        </c:choose>
               />
    </form>
        <a href="/admin/users">Back to users list</a>
    </div>
</body>
</html>
