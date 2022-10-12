// سكربت صفحة الادمن او السوبرفايسر

// الادوات الي نستخدمهة اكثر من مرة نخليهن بمتغير واذا  ما نحتاجهن غير مرة ميحتاج
let selectAreaBtn = $("#btnSelectArea");
let usersDialog = $('#usersModal');
let confirmModal = $('#confirmModal');
let selectedUser = $('select[name=usersList]');
let currentRect = null;
let usersList = null;
let selectedBounds = null;

getUsersList();

//تحديد مساحة المستخدم
map.on('areaselected', (e) => {
    currentRect = L.rectangle(e.bounds, {color: 'blue', weight: 1});
    areaSelectToggled();
    selectedBounds = e.bounds;
    confirmModal.modal({backdrop: "static ", keyboard: false});
    confirmModal.modal('show');
});
// ادوات التحكم بالصفحة
selectAreaBtn.on('click', function () {
    if (selectAreaBtn.hasClass("active")) {
        selectAreaBtn.removeClass("active");
        areaSelectToggled();
    } else {
        usersDialog.modal({backdrop: "static ", keyboard: false});
        usersDialog.modal('show');
    }
});
$("#btnSaveNewArea").on('click', function () {
    saveUserAreaToDb();
    confirmModal.modal('hide');
})
$("#btnCancelSavingArea").on('click', function () {
    selectedBounds = null;
    confirmModal.modal('hide');
})
$("#btnSelectUser").on('click', function () {
    selectedUser = $('select[name=usersList]');
    usersDialog.modal('hide');
    areaSelectToggled();
})
$("#btnCloseModal").on('click', function () {
    usersDialog.modal('hide');
});

//POST ارسال
// ارسال بيانات المساحة الى الجافا ليتم حفظها بالداتابيس
function saveUserAreaToDb() {
    var selectedAreaLayer = L.featureGroup();
    selectedAreaLayer.addLayer(currentRect);

    var userBoundsToSave = {
        userId: selectedUser.val(),
        userArea: JSON.stringify(selectedAreaLayer.toGeoJSON())
    };

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: 'updateUserBounds',
        data: JSON.stringify(userBoundsToSave),
        success: function (result) {
            map.eachLayer(function (layer) {
                if (layer instanceof L.Polygon)
                    map.removeLayer(layer);
            });
            getUsersList();
            console.log("Success");
        }
    })
}
// GET
// جلب قائمة اليوزرات
function getUsersList() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "getUsersFromDb",
        success: appendDataToUsersList,
        error: function (err) {
            console.log(err);
        }
    });
}
// تفعيل او الغاء تفعيل تحديد المساحة
function areaSelectToggled() {
    if (map.selectArea.enabled()) {
        selectAreaBtn.removeClass('active');
        map.selectArea.disable();
    } else {
        selectAreaBtn.addClass('active');
        map.selectArea.enable();
    }
}
// بعد جلب قائمة اليوزرات استخدام بياناتهم بتحديث قائمة اليوزرات ووضع مساحاتهم على الخارطة
function appendDataToUsersList(data) {
    usersList = data;
    setUsersAreasMap();
    setUsersOnPage();
}
// عرض المستخدمين في القوائم
function setUsersOnPage() {
    $("#usersBoundriesLabels").empty();
    $("#usersListOption").empty();
    $.each(usersList, function (key, val) {
        $("#usersBoundriesLabels").append(
            `<option style="color: ${val.userColor};  inline-size: 150px;
             "> ${val.userName}  -   
              ${val.userBoundaries === "empty" ? "Area Not selected" : "Area selected"} 
              </option>`
        )
        $("#usersListOption").append(
            "<option style=\"background-color: blue;\" value=" + val.userId + ">" + val.userName + "</option>"
        )
    })
}
// عرض مساحاتهم في الخارطة
function setUsersAreasMap() {
    let color = "";
    $.each(usersList, function (key, val) {
        if (val.userBoundaries !== "empty" && val.userBoundaries !== null) {
            color = val.userColor.toString();
            if (color === "grass")
                color = "yellowgreen"
            L.geoJSON(JSON.parse(val.userBoundaries.toString())).setStyle({color: color}).addTo(map);
        }
    });
}