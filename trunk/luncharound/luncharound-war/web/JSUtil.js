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
    var cortesia=0;
    var quantita=0;
    var pulizia=0;
    var qualita=0;
    var affollamento=0;
    for (i=0;i<5;i++){
        if(document.getElementsByName("myvelocita")[i].checked) document.getElementById("velocita").value=i+1;
        if(document.getElementsByName("mycortesia")[i].checked) document.getElementById("cortesia").value=i+1;
        if(document.getElementsByName("myquantita")[i].checked) document.getElementById("quantita").value=i+1;
        if(document.getElementsByName("mypulizia")[i].checked) document.getElementById("pulizia").value=i+1;
        if(document.getElementsByName("myqualita")[i].checked) document.getElementById("qualita").value=i+1;
        if(document.getElementsByName("myaffollamento")[i].checked) document.getElementById("affollamento").value=i+1;
    }
    document.getElementById("val").submit();
}