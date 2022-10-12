
// سكربت تغيير ايقونه الماركر حسب لون المستخدم

let LeafIcon = L.Icon.extend({
    options: {
        shadowUrl: 'http://localhost:8080/css/markers/shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        // popupAnchor: [1, -34],
        // tooltipAnchor: [16, -28],
        shadowSize: [41, 41]
    }
});

let orangeIcon = new LeafIcon({
    iconUrl: 'http://localhost:8080/css/markers/orange.png',
})

let redIcon = new LeafIcon({
    iconUrl: 'http://localhost:8080/css/markers/red.png',
})

let silverIcon = new LeafIcon({
    iconUrl: 'http://localhost:8080/css/markers/silver.png',
})

let blackIcon = new LeafIcon({
    iconUrl: 'http://localhost:8080/css/markers/black.png',
})

let greenIcon = new LeafIcon({
    iconUrl: 'http://localhost:8080/css/markers/green.png',
})

let yellowgreenIcon = new LeafIcon({
    iconUrl: 'http://localhost:8080/css/markers/grass.png',
})

let blueIcon = new LeafIcon({
    iconUrl: 'http://localhost:8080/css/markers/blue.png',
})

function getMarkerIconObject(color) {
    switch (color) {
        case "blue":
            return blueIcon;
        case "red":
            return redIcon;
        case "green":
            return greenIcon;
        case "orange":
            return orangeIcon;
        case "black":
            return blackIcon;
        case "silver":
            return silverIcon;
        case "yellowgreen":
            return yellowgreenIcon;
    }
}