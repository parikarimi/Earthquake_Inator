$(document).ready(function() {
    getJSONData();
});

function initalizeMap() {
    L.mapbox.accessToken =
        'pk.eyJ1IjoiYnJlZS1vbHNzb24iLCJhIjoiY2pzbWFtdnl0MnJkZzN5cWdmdGRrOGljdiJ9.Npp7wArgMC6h_zKMX7v6rA';
    var map = L.mapbox.map('map', 'mapbox.outdoors');
    return map;
};

function loadMap() {

    map = initalizeMap();

    $.getJSON('https://json.geoiplookup.io', function(data) {
        console.log(JSON.stringify(data, null, 4));

        var userLat = data.latitude;
        var userLng = data.longitude;

        console.log("User latitude: " + userLat);
        console.log("User longitude: " + userLng);

        map.setView([userLat, userLng], 7);
    });
};

var existingMarkers = [];
function addMarker(lat, lng, date, mag, place) {
    
    var currentLocation = (lat + "," + lng);
    var quakeInfo = "<p><b>Date:</b> " + date + "</p><p><b>Place:</b> " + place + "</p><p><b>Magnitude:</b> " + mag + "</p>";

    if ($.inArray(currentLocation, existingMarkers) === -1) {

        var quake_marker = L.marker([lat, lng]);
        quake_marker.addTo(map);
        quake_marker.bindTooltip(quakeInfo).openPopup();

        existingMarkers.push(lat + "," + lng);
        console.log("SUCCEEDED! Marker added to " + lat + " " + lng);
    } else console.log("FAILED! Marker exists");
};

function addJSONDataToMap() {
    
    $.get("tracker?api=quakeData", function(jsonResponse) {
        console.log(jsonResponse);
        for (var i = 0; i < jsonResponse.length; i++) {
            console.log("Attempting to add marker: " + jsonResponse[i].latitude + " " + jsonResponse[i].longitude);
            addMarker(jsonResponse[i].latitude, jsonResponse[i].longitude,
                jsonResponse[i].date, jsonResponse[i].mag, jsonResponse[i].place);
        }
    });
};

function getJSONData() {
    
    addJSONDataToMap();
    setInterval(function() {
        addJSONDataToMap();
    }, 30000); //every 30 seconds 
    console.log("--Call JSON quake data--");
};

function showAlert(){
    alert("Thank You!\nYou are now signed up to receive alerts.");
};