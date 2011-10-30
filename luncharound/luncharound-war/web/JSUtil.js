/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function submitForm(){
    var geocoder;
    var results;
    var address;

    if (document.getElementById("via") == null) {
        address = document.getElementById("ind").value;
    }
    else{
        address = document.getElementById("via").value+", "+ document.getElementById("citta").value;
    } 
    geocoder = new google.maps.Geocoder();
    geocoder.geocode( {
        'address': address
    }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            document.getElementById("lat").value = results[0].geometry.location.lat().valueOf();
            document.getElementById("lon").value = results[0].geometry.location.lng().valueOf();
            document.getElementById("ind").value= results[0].formatted_address.valueOf();
            this.form.submit();
        } else {
            alert("Geocode was not successful for the following reason: " + status);
            return false;
        }
    } );
}

/* crea valori, li sbatte dento i campi hidden e poi sottomette il form
 */

function submitValutazione(){
    var velocita = 0;
    
    alert(document.getElementById("myvelocita").value);

}