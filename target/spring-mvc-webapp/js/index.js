/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var sightingArray = [];
$(document).ready(function () {

    loadSightings();

});

function loadSightings() {

  var sigContainer = $('#fadeHomeMainDiv');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/SuperSightings/sightings',
        success: function (data, status) {
            $.each(data, function (index, sighting) {
                sightingArray.push(sighting);
            });
            createMaps();

        },
        error: function () {
            sigContainer
                .attr({ class: 'container' })
                .append($('<ul>')
                    .attr({ class: 'list-group' })
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger', style: 'width: 92.5%, margin: auto;' })
                        .text('Error calling SuperSightings web service.  Please try again later.')))
        }
    });
}
// function testmaps1() {
//     console.log(sightingArray);

//     sightingArray.sort(function(a,b){
//         return new Date(b.sightingDateTime) - new Date(a.sightingDateTime);
//     });

//     console.log(sightingArray);
// }

// function testmaps2() {
//     console.log(new Date(sightingArray[0].sightingDateTime));

//     sightingArray.sightingDateTime.sort(function(a, b) {return b-a});

// // sightingArray.sort(dynamicSort('sightingDateTime')).sort(dynamicSort('sightingDateTime'));
// // console.log(sightingArray);
// }

function dynamicSort(property) {
    return function(a, b) {
        return (a[property] < b[property]) ? -1 : (a[property] > b[property]) ? 1 : 0;
    }
 }

function createMaps() {

    for (var i = 0; i < sightingArray.length; i++) {
        $('#mapRow')
            .append($('<div>')
                .attr({ id: 'gMap' + i, class: 'mapView col-4', style: 'border: solid 15px white;' }))
    }

    for (var i = 0; i < sightingArray.length; i++) {
        var sMap = 'gMap' + i;
        var location = sightingArray[i].location;
        var lat = location.locationLatitude;
        var long = location.locationLongitude;

        initMap(lat, long, sMap);
    }

}

function initMap(lat, long, sMap) {
    // var uluru = { lat: -77.529291, lng: 167.152311 };
    var uluru = { lat: lat, lng: long };
    var map = new google.maps.Map(document.getElementById(sMap), {
        zoom: 6,
        center: uluru,
        styles: [
            {
              "elementType": "geometry",
              "stylers": [
                {
                  "color": "#1d2c4d"
                }
              ]
            },
            {
              "elementType": "labels.text.fill",
              "stylers": [
                {
                  "color": "#8ec3b9"
                }
              ]
            },
            {
              "elementType": "labels.text.stroke",
              "stylers": [
                {
                  "color": "#1a3646"
                }
              ]
            },
            {
              "featureType": "administrative.country",
              "elementType": "geometry.stroke",
              "stylers": [
                {
                  "color": "#4b6878"
                }
              ]
            },
            {
              "featureType": "administrative.land_parcel",
              "elementType": "labels.text.fill",
              "stylers": [
                {
                  "color": "#64779e"
                }
              ]
            },
            {
              "featureType": "administrative.province",
              "elementType": "geometry.stroke",
              "stylers": [
                {
                  "color": "#4b6878"
                }
              ]
            },
            {
              "featureType": "landscape.man_made",
              "elementType": "geometry.stroke",
              "stylers": [
                {
                  "color": "#334e87"
                }
              ]
            },
            {
              "featureType": "landscape.natural",
              "elementType": "geometry",
              "stylers": [
                {
                  "color": "#023e58"
                }
              ]
            },
            {
              "featureType": "poi",
              "elementType": "geometry",
              "stylers": [
                {
                  "color": "#283d6a"
                }
              ]
            },
            {
              "featureType": "poi",
              "elementType": "labels.text.fill",
              "stylers": [
                {
                  "color": "#6f9ba5"
                }
              ]
            },
            {
              "featureType": "poi",
              "elementType": "labels.text.stroke",
              "stylers": [
                {
                  "color": "#1d2c4d"
                }
              ]
            },
            {
              "featureType": "poi.park",
              "elementType": "geometry.fill",
              "stylers": [
                {
                  "color": "#023e58"
                }
              ]
            },
            {
              "featureType": "poi.park",
              "elementType": "labels.text.fill",
              "stylers": [
                {
                  "color": "#3C7680"
                }
              ]
            },
            {
              "featureType": "road",
              "elementType": "geometry",
              "stylers": [
                {
                  "color": "#304a7d"
                }
              ]
            },
            {
              "featureType": "road",
              "elementType": "labels.text.fill",
              "stylers": [
                {
                  "color": "#98a5be"
                }
              ]
            },
            {
              "featureType": "road",
              "elementType": "labels.text.stroke",
              "stylers": [
                {
                  "color": "#1d2c4d"
                }
              ]
            },
            {
              "featureType": "road.highway",
              "elementType": "geometry",
              "stylers": [
                {
                  "color": "#2c6675"
                }
              ]
            },
            {
              "featureType": "road.highway",
              "elementType": "geometry.stroke",
              "stylers": [
                {
                  "color": "#255763"
                }
              ]
            },
            {
              "featureType": "road.highway",
              "elementType": "labels.text.fill",
              "stylers": [
                {
                  "color": "#b0d5ce"
                }
              ]
            },
            {
              "featureType": "road.highway",
              "elementType": "labels.text.stroke",
              "stylers": [
                {
                  "color": "#023e58"
                }
              ]
            },
            {
              "featureType": "transit",
              "elementType": "labels.text.fill",
              "stylers": [
                {
                  "color": "#98a5be"
                }
              ]
            },
            {
              "featureType": "transit",
              "elementType": "labels.text.stroke",
              "stylers": [
                {
                  "color": "#1d2c4d"
                }
              ]
            },
            {
              "featureType": "transit.line",
              "elementType": "geometry.fill",
              "stylers": [
                {
                  "color": "#283d6a"
                }
              ]
            },
            {
              "featureType": "transit.station",
              "elementType": "geometry",
              "stylers": [
                {
                  "color": "#3a4762"
                }
              ]
            },
            {
              "featureType": "water",
              "elementType": "geometry",
              "stylers": [
                {
                  "color": "#0e1626"
                }
              ]
            },
            {
              "featureType": "water",
              "elementType": "labels.text.fill",
              "stylers": [
                {
                  "color": "#4e6d70"
                }
              ]
            }
          ]
    });
    var marker = new google.maps.Marker({
        position: uluru,
        map: map
    });
}