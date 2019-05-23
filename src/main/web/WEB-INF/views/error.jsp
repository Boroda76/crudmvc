<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<body style="background: url(<spring:url value="/img/error.gif"/>); color: red;">
<sec:authorize access="isAuthenticated()">
<sec:authentication var="user" property="principal" />
<h1><c:out value="${message}"/></h1>
<c:choose>
    <c:when test="${user.rolesString.contains('ADMIN')}">
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
<a href="/index"><button>To main page</button></a>
</body>
</html>
