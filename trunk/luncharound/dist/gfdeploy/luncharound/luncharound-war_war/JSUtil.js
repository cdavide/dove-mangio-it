function submitForm(complete){
    var geocoder;
    var results;
    if(!complete){
        var address = this.form.via.value+", "+ this.form.citta.value;
    }else{
        var address = this.form.indirizzo.value;
    }
    geocoder = new google.maps.Geocoder();
    geocoder.geocode( {
        'address': address
    }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            this.form.latitudine.value = results[0].geometry.location.lat().valueOf();
            this.form.longitudine.value = results[0].geometry.location.lng().valueOf();
            this.form.indirizzo.value= results[0].formatted_address.valueOf();
            alert()
            this.form.submit(this.form.indirizzo.value);
        } else {
            alert("Geocode was not successful for the following reason: " + status);
            return false;
        }
    } );
}