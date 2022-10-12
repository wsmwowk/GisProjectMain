<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Login Screen</title>
    <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css'>
<%--    <link rel="stylesheet" href="<c:url value='css/bootstrap.css'/>" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="<c:url value='css/style.css'/>" crossorigin="anonymous">

</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
    <div id="login-box">
        <div class="logo">
            <img src="<c:url value='css/images/mgis2.png'/>" class="img img-responsive  center-block" style="" alt="image not found!"/>
            <h1 class="logo-caption">
                <span class="tweak">M</span><span class="tweak2">gis</span>
                <span class="tweak3">  login</span>
            </h1>
        </div><!-- /.logo -->

        <div class="controls">
            <%--            <form action="login" method="post">--%>
            <form:form action="/login" method="post" modelAttribute="checkUser">

                <form:input type="text"  class="form-control" id="inputUsername" path="userName" placeholder="username" />

<%--                <input type="text" name="username" placeholder="Username" class="form-control" required/>--%>

                <form:input type="password"  class="form-control" id="inputPassword" path="userPass" placeholder="Password"/>

<%--                <input type="password" name="password" placeholder="password" class="form-control" required/>--%>

                <button type="submit" class="btn btn-default btn-block btn-custom">Login</button>
                <a href="signup" target="_self" class="btn btn-default btn-block btn-custom">Register</a>
            </form:form>
        </div>
    </div>
</div>
<div id="particles-js"></div>
<!-- partial -->
<script src="<c:url value='js/jquery-3.6.0.min.js'/>" crossorigin="anonymous"></script>
<script src="js/script.js"></script>

</body>
</html>
