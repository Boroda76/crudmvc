<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
        <div class="col-lg-2 bg-light px-0">
            <ul class="nav nav-pills flex-column mt-3">
<sec:authorize access="hasAuthority('ADMIN')">
                <li class="nav-item">
                    <a class="nav-link active" data-toggle="pill" href="#admin">Admin</a>
                </li>
</sec:authorize>
                <li class="nav-item">
                    <a class="nav-link"  data-toggle="pill" href="#user">User</a>
                </li>
            </ul>
        </div>
        <div class="col-lg-10" style="background-color:rgba(0, 0, 0, 0.05);">
            <div class="tab-content mt-3">
<sec:authorize access="hasAuthority('ADMIN')">
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
                        <div class="tab-pane container active px-0" id="list">
                            <div class="card">
                                <div class="card-header">All users</div>
                                <div class="card-body">
                                    <table class="table table-striped table-responsive">
                                        <thead>
                                        <tr>
                                            <td>login</td>
                                            <td>email</td>
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
                                                <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                                                      value="${user.weight}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                                                      value="${user.height}"/></td>
                                                <td><c:out value="${user.rolesString}"/></td>
                                                <td>
                                                    <button type="button" class="btn btn-success" data-toggle="modal" <c:out value="data-target=#user${user.id}"/>>Edit</button>
                                                    <a type="button" class="btn btn-danger" <c:out value="href=/admin/delete?id=${user.id}"/>>Delete</a>
                                                </td>
                                            </tr>




                                            <div class="modal" <c:out value="id=user${user.id}"/>>
                                                <form action="/admin/update" method="post">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h4 class="modal-title">Edit user <c:out value="${user.login} ${user.rolesString}"/> </h4>
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                        </div>
                                                        <div class="modal-body">

                                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                                <input type="hidden" name="id" value="${user.id}"/>
                                                                <div class="form-group">
                                                                    <label for="loginE">Login:</label>
                                                                    <input class="form-control" type="text" id="loginE" name="login" placeholder="login" required value="${user.login}"/>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="passwordE">Password</label>
                                                                    <input class="form-control" type="password" id="passwordE" name="password" placeholder="password"
                                                                           required value="${user.password}"/>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="emailE">Email:</label>

                                                                    <input class="form-control" type="text" id="emailE" name="email" placeholder="email"
                                                                           required value="${user.email}"/>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="roleE">Role:</label>

                                                                    <select class="form-control" name="role" id="roleE" required>
                                                                        <c:choose>
                                                                            <c:when test="${user.rolesString.contains('ADMIN')}">
                                                                                <option  value="USER">user</option>
                                                                                <option selected value="ADMIN">admin</option>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <option  selected value="USER">user</option>
                                                                                <option  value="ADMIN">admin</option>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </select>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="ageE">Age:</label>

                                                                    <input class="form-control" type="number" id="ageE" name="age" placeholder="age" required value="${user.age}"/>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label>Sex:</label>
                                                                    <input type="radio" name="sex" value="true" required <c:if test="${user.sex}">checked</c:if>/> Male
                                                                    <input type="radio" name="sex" value="false" required <c:if test="${user.sex!=true}">checked</c:if>/> Female
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="weightE">Weight:</label>

                                                                    <input class="form-control" type="number" id="weightE" name="weight" placeholder="weight"
                                                                           step="0.01" required value="<c:out value='${String.format(\"%.2f\",user.weight).replace(\',\',\'.\')}'/>"/>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="heightE">Height:</label>

                                                                    <input class="form-control" type="number" id="heightE" name="height" placeholder="height"
                                                                           step="0.01" required value="<c:out value='${String.format(\"%.2f\",user.height).replace(\',\',\'.\')}'/>"/>
                                                                </div>

                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
                                                            <button type="submit" class="btn btn-primary">Update user</button>
                                                        </div>

                                                    </div>
                                                </div>
                                                </form>
                                            </div>







                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane container fade px-0" id="create">
                            <div class="card">
                                <div class="card-header">Add new user</div>
                                <div class="card-body mx-auto">
                                    <form action="/admin/update" method="post">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <div class="form-group">
                                            <label for="login">Login:</label>
                                            <input class="form-control" type="text" id="login" name="login" placeholder="login" required/>
                                        </div>
                                        <div class="form-group">
                                            <label for="password">Password</label>
                                            <input class="form-control" type="password" id="password" name="password" placeholder="password"
                                                    required/>
                                        </div>
                                        <div class="form-group">
                                            <label for="email">Email:</label>

                                            <input class="form-control" type="text" id="email" name="email" placeholder="email"
                                                   required/>
                                        </div>
                                        <div class="form-group">
                                            <label for="role">Role:</label>

                                            <select class="form-control" name="role" id="role" required>
                                                <option disabled>Choose role</option>
                                                <option value="USER">user</option>
                                                <option value="ADMIN">admin</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="age">Age:</label>

                                            <input class="form-control" type="number" id="age" name="age" placeholder="age" required/>
                                        </div>
                                        <div class="form-group">
                                            <label>Sex:</label>
                                            <input type="radio" name="sex" value="true" required/> Male
                                            <input type="radio" name="sex" value="false" required/> Female
                                        </div>
                                        <div class="form-group">
                                            <label for="weight">Weight:</label>

                                            <input class="form-control" type="number" id="weight" name="weight" placeholder="weight"
                                                   step="0.01" required/>
                                        </div>
                                        <div class="form-group">
                                            <label for="height">Height:</label>

                                            <input class="form-control" type="number" id="height" name="height" placeholder="height"
                                                   step="0.01" required/>
                                        </div>
                                        <button type="submit" class="btn btn-success">Add new user</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
</sec:authorize>
                    <div class="tab-pane container fade" id="user">
                        <h1>User page</h1>
                        <p>Hello <c:out value="${pageContext.request.userPrincipal.name} !"/></p>
                    </div>
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
