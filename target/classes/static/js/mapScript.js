
// سكربت الخريطة الرئيسية لجلب الخرائط وتحميلها وضبط اعداداتها المبدئية

let baseMap = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
});

let options = {
    center: [33.3, 44.3],
    zoom: 10,
    layers: [baseMap],
    drawControls: true,
    fullscreenControl: true,
    selectArea: false,
};

var map = L.map('map', options);

// to disable double click zoom
map.doubleClickZoom.disable();

L.control.coordinates({
    position:"bottomleft",
    decimals:6,
    decimalSeparator:",",
    useLatLngOrder: true,
    labelTemplateLat:"Latitude: {y}",
    labelTemplateLng:"Longitude: {x}"
}).addTo(map);


let testLayer = L.tileLayer('https://clarity.maptiles.arcgis.com/arcgis/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {
    attribution: 'Test Layer'
});

// The tree containing the layers
var LayersTree = [
    {
        label: 'Base layers',
        children: [
            {label: 'OSM', layer: baseMap, name: 'OpenStreetMap'},
            {label: 'GeoServer', layer: testLayer, name: 'Geoserver test map'},
        ]
    },
];

L.control.layers.tree(LayersTree).addTo(map);


