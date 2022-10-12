<%--
  Created by IntelliJ IDEA.
  User: wissamkadim
  Date: 09/09/2022
  Time: 9:59 PM
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Signing up</title>
    <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<c:url value='css/colorComboBox.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/style.css'/>" crossorigin="anonymous">
</head>

<body>

<div class="container">
    <div id="login-box">
        <div class="controls">
            <form:form action="/create-user" method="post" modelAttribute="newUserModel">
                <div class="logo">
                    <img src="<c:url value='css/images/mgis2.png'/>" class="img img-responsive  center-block" style="" alt="image not found!"/>
                    <h1 class="logo-caption">
                        <span class="tweak">M</span><span class="tweak2">gis</span>
                        <span class="tweak3">  login</span>
                    </h1>
                </div><!-- /.logo -->

                <br>
                <form:input placeholder="Fullname.." path="userFullName" type="text" class="form-control" id="inputName"/>
                <form:input  placeholder="Username.." type="text" class="form-control" id="inputUsername" path="userName"/>
                <form:input placeholder="Password.." type="password" class="form-control" id="inputPassword" path="userPass"/>

                <br>
                <ul id="inputColor">
                    <li class="init"> select color..</li>
                    <li data-value="value 1"><span style="color:silver">&#xf0c8;</span> Silver</li>
                    <li data-value="value 2"><span style="color:orange">&#xf0c8;</span> Orange</li>
                    <li data-value="value 3"><span style="color:yellowgreen">&#xf0c8;</span> Grass</li>
                    <li data-value="value 3"><span style="color:black">&#xf0c8;</span> Black</li>
                    <li data-value="value 3"><span style="color:green">&#xf0c8;</span> Green</li>
                    <li data-value="value 3"><span style="color:red">&#xf0c8;</span> Red</li>
                    <li data-value="value 3"><span style="color:blue">&#xf0c8;</span> Blue</li>
                </ul>
                <form:hidden id="inputColorLabel" path="userColor"/>
                <%--                cssStyle="display: none"--%>
                <%--                <form:input type="color" class="form-select" id="inputColor" path="userColor"/>--%>
               <br>
                <hr>

                <label style="color: red"> *Note: </label>
                <label style="color: white">New user will not be allowed to</label><br><label style="color: white"> access map until the SUPERVISORS select his area.</label>

                <button type="submit" class="btn btn-default btn-block btn-custom">Register</button>
                <a  type="url" href="login" target="_self" class="btn btn-default btn-block btn-custom">Login</a>

            </form:form>
        </div>
    </div>
</div>
<div id="particles-js"></div>

<%--<script src="<c:url value='js/bootstrap.js'/>" crossorigin="anonymous"></script>--%>
<script src="<c:url value='js/jquery-3.6.0.min.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/colorComboBox.js'/>" crossorigin="anonymous"></script>
<script src="js/script.js"></script>

</body>
</html>
