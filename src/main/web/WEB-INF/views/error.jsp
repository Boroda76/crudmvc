<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Error</title>
    <script>
        function trace() {
            let trace=document.getElementById("trace");
            let display=trace.style.display;
            display=="none"?trace.style.display="block":trace.style.display="none";
        }
    </script>
</head>
<body style="background: url('https://i.pinimg.com/originals/5f/05/b8/5f05b8e968388c1dca6d644087534f68.gif'); color: red;">
<sec:authorize access="isAuthenticated()">
<sec:authentication var="user" property="principal" />
<h1><c:out value="${message}"/></h1>
<c:choose>
    <c:when test="${user.rolesString.contains('ADMIN')}">
        <a href="/admin/users"><button>To users list</button></a>
        <c:if test="${error!=null}">
        <br><br>
        <button onclick="trace()">Show/hide stacktrace</button>
        <div id="trace" style="display: none">
            <c:forEach var="call" items="${error.getStackTrace()}">
                <c:out value="${call.toString()}"/>
            </c:forEach>
        </div>
        </c:if>
    </c:when>
</c:choose>
</sec:authorize>
<a href="/user"><button>To user page</button></a>
</body>
</html>
