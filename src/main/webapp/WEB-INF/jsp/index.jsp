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

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Welcome</title>
    <link rel="stylesheet" href="<c:url value='css/bootstrap.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/leaflet.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/Leaflet.LinearMeasurement.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/leaflet-geoman.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/index.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/Leaflet.Coordinates-0.1.5.css'/>" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='css/L.Control.Layers.Tree.css'/>" crossorigin="anonymous">


</head>
<body>

<div id="map">
</div>

<button id="btnSaveData" type="button" style="top: 65px" class="btn btn-success controlButtons" title="Save">
    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-save-fill"
         viewBox="0 0 16 16">
        <path d="M8.5 1.5A1.5 1.5 0 0 1 10 0h4a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h6c-.314.418-.5.937-.5 1.5v7.793L4.854 6.646a.5.5 0 1 0-.708.708l3.5 3.5a.5.5 0 0 0 .708 0l3.5-3.5a.5.5 0 0 0-.708-.708L8.5 9.293V1.5z"></path>
    </svg>
</button>

<form:form action="/do-logout" method="get">
    <button id="btnSignOut" type="submit" style="top: 230px" class="btn btn-danger controlButtons" title="Signout">
        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor"
             class="bi bi-door-closed-fill" viewBox="0 0 16 16">
            <path d="M12 1a1 1 0 0 1 1 1v13h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V2a1 1 0 0 1 1-1h8zm-2 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"></path>
        </svg>
    </button>
</form:form>


<button type="button" id="btnSlopeAnalysis" style="top: 120px"
        class="btn btn-info leaflet-select-control controlButtons">
    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-graph-down"
         viewBox="0 0 16 16">
        <path fill-rule="evenodd"
              d="M0 0h1v15h15v1H0V0Zm14.817 11.887a.5.5 0 0 0 .07-.704l-4.5-5.5a.5.5 0 0 0-.74-.037L7.06 8.233 3.404 3.206a.5.5 0 0 0-.808.588l4 5.5a.5.5 0 0 0 .758.06l2.609-2.61 4.15 5.073a.5.5 0 0 0 .704.07Z"></path>
    </svg>
</button>

<button id="btnGetMyArea" type="button" style="top: 175px" class="btn btn-info controlButtons" title="MyArea">
    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-pin-map-fill"
         viewBox="0 0 16 16">
        <path fill-rule="evenodd"
              d="M3.1 11.2a.5.5 0 0 1 .4-.2H6a.5.5 0 0 1 0 1H3.75L1.5 15h13l-2.25-3H10a.5.5 0 0 1 0-1h2.5a.5.5 0 0 1 .4.2l3 4a.5.5 0 0 1-.4.8H.5a.5.5 0 0 1-.4-.8l3-4z"></path>
        <path fill-rule="evenodd" d="M4 4a4 4 0 1 1 4.5 3.969V13.5a.5.5 0 0 1-1 0V7.97A4 4 0 0 1 4 3.999z"></path>
    </svg>
</button>

<div id="slopeInfoModal" data-backdrop="static" data-keyboard="false" class="modal fade bd-example-modal-sm"
     tabindex="-1"
     role="dialog" aria-labelledby="mySmallModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Set slope information</h5>
            </div>
            <div class="modal-body">
                <form>
                    <label for="slopeLayerName" style="color: red">*Use CTRL + Mouse dragging to select slope
                        Area </label>
                    <br>
                    <label for="slopeLayerName">Layer name:</label>
                    <input type="text" id="slopeLayerName" class="form-text mb-3">
                    <label for="slopeLayerType">Slope format method:</label>
                    <select id="slopeLayerType" class="form-select">
                        <option value="percent">percent</option>
                        <option value="degree">degree</option>
                    </select>
                </form>
            </div>
            <div class="modal-footer">
                <button id="btnCloseModal" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button id="btnSelectSlopeArea" type="button" class="btn btn-primary">Select area</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='js/jquery-3.6.0.min.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/bootstrap.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/leaflet.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/Leaflet.LinearMeasurement.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/Leaflet.Coordinates-0.1.5.min.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/L.Control.Layers.Tree.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/map.SelectArea.min.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/leaflet-geoman.min.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/mapScript.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/mapMarkerIcons.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/Leaflet.draw.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/GeometryUtil.js'/>" crossorigin="anonymous"></script>
<script src="<c:url value='js/index.js'/>" crossorigin="anonymous"></script>

</body>
</html>
