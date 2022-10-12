<%--
  Created by IntelliJ IDEA.
  User: wissamkadim
  Date: 09/09/2022
  Time: 5:09 PM
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="java.util.List" %>
<%@ page import="com.GisProjectAli.GisProjectMain.model.UserLogin" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Super Visor page</title>
    <link rel="stylesheet" href="<c:url value='css/bootstrap.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/leaflet.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/index.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/Leaflet.Coordinates-0.1.5.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/L.Control.Layers.Tree.css'/>" crossorigin="anonymous">

</head>
<body>
<div id="map"></div>

<form:form action="/do-logout" method="get">
    <button id="btnSignOut" style="top: 120px !important;" type="submit" class="btn btn-danger controlButtons" title="Signout">
        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-door-closed-fill" viewBox="0 0 16 16">
            <path d="M12 1a1 1 0 0 1 1 1v13h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V2a1 1 0 0 1 1-1h8zm-2 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"></path>
        </svg>
    </button>
</form:form>

<button type="button" style="top: 65px !important;" id="btnSelectArea" class="btn btn-info leaflet-select-control icon controlButtons">
    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
        <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path>
        <path fill-rule="evenodd" d="M5.216 14A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216z"></path>
        <path d="M4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5z"></path>
    </svg>
</button>

<div id="usersModal" data-backdrop="static" data-keyboard="false" class="modal fade bd-example-modal-sm" tabindex="-1"
     role="dialog" aria-labelledby="mySmallModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Users boundaries</h5>
            </div>
            <div class="modal-body">
                <form>
                    <div id="usersBoundriesLabels"></div>
                    <br>
                    <label for="usersListOption">Select User to set boundaries: </label>
                    <label for="usersListOption" style="color: red">*Use CTRL + Mouse dragging to select Area </label>
                    <select id="usersListOption" name="usersList" class="form-select"
                            aria-label="Default select example">
                        <option selected>Select user..</option>
                    </select>
                </form>
            </div>
            <div class="modal-footer">
                <button id="btnCloseModal" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button id="btnSelectUser" type="button" class="btn btn-primary">Select user</button>
            </div>
        </div>
    </div>
</div>

<div id="confirmModal" data-backdrop="static" data-keyboard="false" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <p style="font-weight: bold">Confirm area selection</p>
                <p style="color: red">*All selected user GIS data well be removed after
                    selecting new region </p>
            </div>
            <div class="modal-footer">
                <button id="btnSaveNewArea" type="button" class="btn btn-primary">Save area changes</button>
                <button id="btnCancelSavingArea" type="button" class="btn btn-secondary" data-dismiss="modal">Cancel
                </button>
            </div>
        </div>
    </div>
</div>


<script src="<c:url value='js/jquery-3.6.0.min.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/bootstrap.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/leaflet.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/Leaflet.Coordinates-0.1.5.min.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/map.SelectArea.min.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/L.Control.Layers.Tree.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/mapScript.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/supervisorindex.js'/>" crossorigin="anonymous"></script>

</body>
</html>
