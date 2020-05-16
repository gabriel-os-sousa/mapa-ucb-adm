// # variável para instanciar o mapa
var map;

var locais;

var markers=[];

// # variável para controle dos limites do mapa
var UCB_LIMITES = {
  north: -15.844851,
  south: -15.888811,
  east: -47.983347,
  west: -48.077730
};

// ##### FUNÇÃO PRINCIPAL que é chamada na callback da API ##########
function initMap() {
  map = new google.maps.Map(document.getElementById('mapDiv'), {
    zoom: 16,
    center: {lat: -15.8660717, lng: -48.029686}, // Seta o centro do mapa
    mapTypeId: 'satellite', // Tipo de mapa
    mapTypeControl: false, // desabilita a opção de mudar o tipo do mapa
    streetViewControl: false, // desabilita opção da street view 
    rotateControl: false, // desabilita o botão de inclinar o mapa 
    tilt: 0, // Valor da inclinação do mapa
    minZoom: 16, // zoom mínimo
    maxZoom: 19, // zoom máximo
    restriction: {
      latLngBounds: UCB_LIMITES,
      strictBounds: false,
    }
  });

  // ########## Seta os limites do zoom correspondente #########
  var bounds = {
    16: [[24023, 24025], [35692, 35694]],
    17: [[48047, 48050], [71385, 71389]],
    18: [[96094, 96100], [142771, 142778]],
    19: [[192189, 192201], [285542,285557]]
  };

  // Sets the map on all markers in the array.
  function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(map);
    }
  }

  // Removes the markers from the map, but keeps them in the array.
  function clearMarkers() {
    setMapOnAll(null);
  }

  // escuta o evento zoom_changed
  map.addListener('click', function(mapsMouseEvent) {
    clearMarkers();
    var marker = new google.maps.Marker({
      position: mapsMouseEvent.latLng,
      map: map,
      title: 'Local selecionado'
    });
    markers.push(marker);
    document.getElementById("latitude").value = marker.getPosition().lat();
    document.getElementById("latitudeAux").value = marker.getPosition().lat();
    document.getElementById("longitude").value = marker.getPosition().lng();
    document.getElementById("longitudeAux").value = marker.getPosition().lng();
  });
  
}