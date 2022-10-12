//   سكربت الصفحة الرئيسية, اهم سكربت بالبرنامج بي اغلب الكود المطلوب

// متغيرات احتاجيناهن جوة
let currentUserLayer;
let currentUserArea;
let currentUserId;
let slopeLayerName;
let slopeLayerType;

// ادوات الصفحة الي مستخدمهن هواي خليتهن بمتغير افضل والي مستخدمهن مرة يكفي استخدام السلكتر مالتهن jquery
let slopeInfoModal = $('#slopeInfoModal');
let btnSlopeAnalysis = $("#btnSlopeAnalysis");

// اضبط اعدادات الخريطة اضيف المسطرة والادوات
map.addControl(new L.Control.LinearMeasurement({
    unitSystem: 'imperial',
    color: '#FF0080',
    type: 'line'
}));
map.pm.addControls({
    position: 'topleft',
    drawCircle: false,
    drawCircleMarker: false,
    drawText: false,
    editControls: true,
});
L.PM.initialize({optIn: true});

// استدعي ذن الدالتين بالبداية حته احصل اليوزر الحالي وقائمة اليوزرية
getCurrentUserIdFromDb();
getUsersList()

// تحديد منطقة بالخريطة يأدي لتحليل سلوب
map.on('areaselected', (e) => {
    currentRect = L.rectangle(e.bounds, {color: 'blue', weight: 1});
    console.log(currentRect);
    areaSelectToggled();
    let region = [e.bounds._northEast.lat, e.bounds._northEast.lng, e.bounds._southWest.lat, e.bounds._southWest.lng];
    if (slopeLayerName !== undefined && slopeLayerType !== undefined)
        setSlopeData(region);
});
// ادوات التحكم بالصفحة
btnSlopeAnalysis.on('click', function () {
    if (btnSlopeAnalysis.hasClass("active")) {
        btnSlopeAnalysis.removeClass("active");
        // areaSelectToggled();
    } else {
        slopeInfoModal.modal({backdrop: "static ", keyboard: false});
        slopeInfoModal.modal('show');
    }
});
$("#btnSelectSlopeArea").on('click', function () {
    slopeLayerName = $("#slopeLayerName").val();
    slopeLayerType = $('#slopeLayerType').find(":selected").text();

    console.log(slopeLayerName + " " + slopeLayerType);
    if (slopeLayerName !== "" && slopeLayerName !== " ") {
        slopeInfoModal.modal('hide');
        areaSelectToggled();
    }
})
$("#btnCloseModal").on('click', function () {
    slopeInfoModal.modal('hide');
});
$("#btnSaveData").on("click", function () {
    if (map.pm.globalDrawModeEnabled())
        map.pm.disableDraw();
    currentFeatureLayer();
    saveToDb();
})

$("#btnGetMyArea").on("click", function () {
    if (currentUserArea != null)
        map.fitBounds(currentUserArea.getBounds());
});

// GET جلب
// طلب الأيدي مال اليوزر الي مسجل دخول حاليا انوب جلب المساحة مالته
function getCurrentUserIdFromDb() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "getCurrentUserId",
        success: function (data) {
            currentUserId = data;
            getCurrentUserAreaFromDb();
        },
        error: function (err) {
            console.log(err);
        }
    });
}

// GET جلب
// طلب قائمة باليوزرية حته نجيب  بياناتهم الجغرافية ونحطهة بالخريطة
function getUsersList() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "getUsersFromDb",
        success: function (data) {
            getUsersFeatures(data);
            // getCurrentUserFeatureColor();
        },
        error: function (err) {
            console.log(err);
        }
    });
}

// POST ارسال
//  طلب ارسال بيانات السلوب بصيغة جيسون للجافا ورة ما حددنه المنطقة فوك
function setSlopeData(region) {
    let slopeInfo = {
        slopeTitle: slopeLayerName,
        slopeType: slopeLayerType,
        slopeRegion: region
    };

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: 'setSlopeInfo',
        data: JSON.stringify(slopeInfo),
        success: addNewSlopeLayer
    })
}

// GET جلب
// ورة ما دزينه بيانات السلوب نتصل بالجيوسيرفر ونجيب اللاير نعرضهة ع الخريطة
function addNewSlopeLayer() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "CurrentSlopeFileName",
        success: function (slopeName) {
            console.log(slopeName);
            if (slopeName !== undefined || null)
                map.addLayer(L.tileLayer("http://localhost:8070/geoserver/gwc/service/wmts?layer=cite%3A" + slopeName + "&style=&tilematrixset=EPSG%3A900913&Service=WMTS&Request=GetTile&Version=1.0.0&Format=image%2Fpng&TileMatrix=EPSG%3A900913%3A{z}&TileCol={x}&TileRow={y}"));
            console.log("Success");
        },
        error: function (err) {
            console.log(err);
        }
    });

}

// POST ارسال
// طلب حفظ الفيجرات مال مستخدم الحالية والتغييرات للمستخدم
function saveToDb() {
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: 'updateUserFeatures',
        data: JSON.stringify(currentUserLayer),
        success: () => {
            console.log("تم الحفظ success");
        }
    })
}

// Get جلب
// طلب مساحة اليوزر الخاصة من الداتابيس
function getCurrentUserAreaFromDb() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "getCurrentUserBoundsFromDb",
        success: function (data){
            currentUserArea = L.geoJSON(JSON.parse(data)).setStyle({
                color: 'white',
                fillOpacity: 0,
                pmIgnore: true,
                snapIgnore: true
            }).addTo(map).on('mouseover', function (e) {
                var layer = e.target;
                layer.bringToBack();
            });
            map.fitBounds(currentUserArea.getBounds());
        },
        error: function (err) {
            console.log(err);
        }
    });
}

// دالة عادية تجيب الفيجرات مال يوزر الحالي للخريطة
function currentFeatureLayer() {
    var allLayers = L.featureGroup();
    map.eachLayer(function (layer) {
        if ((layer instanceof L.Path || layer instanceof L.Marker) && layer.pm
            && !layer.options.pmIgnore) {
            allLayers.addLayer(layer);
        }
    });
    currentUserLayer = allLayers.toGeoJSON();
}

// جلب فيجرات اليوزر الحالي وباقي اليوزرية واضافتهة ع الخريطة
function getUsersFeatures(data) {
    var otherUsersLayers = L.featureGroup();
    $.each(data, function (key, val) {
        if (val.userId.toString() === currentUserId) {
            setCurrentUserFeatureColorFromLayer(val.userColor.toString());
        }
        if (val.userGisDataSet.length !== 0 && val.userGisDataSet[0].gisData !== "empty") {
            if (val.userId.toString() === currentUserId) {
                currentUserLayer = L.geoJSON(JSON.parse(val.userGisDataSet[0].gisData.toString()), {
                    onEachFeature: onEachFeature,
                    color: val.userColor.toString(),
                    pmIgnore: false,
                    snapIgnore: true,
                    pointToLayer: function (feature, latlng) {
                        return L.marker(latlng, {
                            icon: getMarkerIconObject(val.userColor.toString()),
                            pmIgnore: false,
                            snapIgnore: true
                        });
                    },
                }).addTo(map);

            } else {
                 L.geoJSON(JSON.parse(val.userGisDataSet[0].gisData.toString()), {
                    onEachFeature: function (feature, layer) {
                        let latLngOrLngs = layer._latlng || layer._latlngs;
                        const myTimeout = setTimeout(()=>{
                            if (currentUserArea.getBounds().contains(latLngOrLngs)) {
                                otherUsersLayers.addLayer(layer);
                            }
                        }, 300);
                    },
                    color: val.userColor.toString(),
                    snapIgnore: true,
                    pmIgnore: true,
                    pointToLayer: function (feature, latlng) {
                        return L.marker(latlng, {
                            icon: getMarkerIconObject(val.userColor.toString()),
                            pmIgnore: true
                        });
                    },
                });
            }
        }
    })

    otherUsersLayers.addTo(map);

}

// دالة تابعة للدالة الفوك شغلهة تضيف خصائص ع الفيجرات حته تمنع التعديل او تعرض المساحة
function onEachFeature(feature, layer) {
    let restorePosition;
    if (feature.geometry.type === "Point") {
        restorePosition = layer._latlng;
        layer.on('pm:dragend', (e) => {
            if (!currentUserArea.getBounds().contains(e.layer.getLatLng()))
                e.layer.setLatLng(restorePosition);
            else
                restorePosition = e.layer.getLatLng();
        });
    }
    if (feature.geometry.type === "Polygon" || feature.geometry.type === "Line") {
        restorePosition = layer._latlngs;
        layer.on('pm:dragend', (e) => {
            if (!currentUserArea.getBounds().contains(e.layer.getLatLngs()))
                e.layer.setLatLngs(restorePosition);
            else
                restorePosition = e.layer.getLatLngs();
        });

        layer.on("click", () => {
            let seeArea = L.GeometryUtil.geodesicArea(layer.getLatLngs()[0]);
            let readAbleArea = L.GeometryUtil.readableArea(seeArea.toFixed(), true);
            layer.bindTooltip(readAbleArea, {permanent: true, direction: 'center'}).openTooltip()
        })

    }
}

//نوضح المساحة مال مستخدم ع الخريطة بلون ابيض ونوجه الخارطة ع هاي المساحة
function assignCurrentUserArea(data) {
}


// ورة ما جبنه لون المستخدم بالدالة السابقة نجي نلون ادوات الرسم والماركر بلون المستخدم
function setCurrentUserFeatureColorFromLayer(color) {
    console.log(color);
    map.pm.Draw.Line.setPathOptions({color: color});
    map.pm.Draw.Rectangle.setPathOptions({color: color});
    map.pm.Draw.Polygon.setPathOptions({color: color});
    map.pm.Draw.CircleMarker.setPathOptions({color: color});

    map.on('pm:drawstart', function (e) {
        var layer = e.workingLayer;
        const shape = map.pm.Draw.getActiveShape();
        if (shape === "Marker") {
            if (color !== undefined || null) {
                layer.setIcon(getMarkerIconObject(color));
            }
        }
    });

    map.on('pm:create', (e) => {
        const shape = map.pm.Draw.getActiveShape()
        if (shape === "Marker") {
            if (color !== null || undefined) {
                console.log(color + "Icon");
                e.layer.setIcon(getMarkerIconObject(color))
            }
            if (!currentUserArea.getBounds().contains(e.layer.getLatLng())){
                e.layer.removeFrom(map);
            }
            else {
                let restorePosition = e.layer.getLatLng();
                e.layer.on('pm:dragend', (e) => {
                    if (!currentUserArea.getBounds().contains(e.layer.getLatLng()))
                        e.layer.setLatLng(restorePosition);
                    else
                        restorePosition = e.layer.getLatLng();
                });
            }
        } else if (shape === "Polygon" || shape === "Rectangle" || shape === "Line") {
            if (!currentUserArea.getBounds().contains(e.layer.getLatLngs())) {
                e.layer.removeFrom(map);
            } else {
                let restorePosition = e.layer.getLatLngs();
                e.layer.on('pm:dragend', (e) => {
                    if (!currentUserArea.getBounds().contains(e.layer.getLatLngs()))
                        e.layer.setLatLngs(restorePosition);
                    else
                        restorePosition = e.layer.getLatLngs();
                });
                e.layer.on("click", () => {
                    let seeArea = L.GeometryUtil.geodesicArea(e.layer.getLatLngs()[0]);
                    let readAbleArea = L.GeometryUtil.readableArea(seeArea.toFixed(), true);
                    e.layer.bindTooltip(readAbleArea, {permanent: true, direction: 'center'}).openTooltip();
                })
            }
        }
    });
}

// من ندوس ع زر تحليل السلوب يشتغل تحديد المساحة والزر يظهر كأنو فعال لحد ما نخلص تحديد المساحة مال مسلوب
function areaSelectToggled() {
    if (map.selectArea.enabled()) {
        btnSlopeAnalysis.removeClass('active');
        map.selectArea.disable();
    } else {
        btnSlopeAnalysis.addClass('active');
        map.selectArea.enable();
    }
}