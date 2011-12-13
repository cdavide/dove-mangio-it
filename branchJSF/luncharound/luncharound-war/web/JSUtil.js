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
    } , callback);
}

function callback(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
        document.getElementById("lat").value = results[0].geometry.location.lat().valueOf();
        document.getElementById("lon").value = results[0].geometry.location.lng().valueOf();
        document.getElementById("ind").value= results[0].formatted_address.valueOf();
        this.form.submit();
    } else {
        alert("Geocode was not successful for the following reason: " + status);
        return false;
    }
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


/*Questa funzione serve per sfruttare le funzioni Javascript con le JSF
 *Per farla funzionare è necessario aggiungere un link nascosto nella pagina
 *e come action mettere l'azione sul managed bean
 *lo script simula un click sul link
 **/

function clickLink(linkId){
    var fireOnThis = document.getElementById(linkId)
    fireOnThis.click();
}



function handleLoginRequest(xhr, status, args) {

    if(args.validationFailed || !args.loggedIn) {
        jQuery('#dialog').parent().effect("shake", {
            times:3
        }, 100);
    } else {

        dlg.hide();
        jQuery('#loginLink').fadeOut();
    }
}
        

function handleRegRequest(xhr, status, args) {

    if(args.validationFailed || !args.reg) {
        jQuery('#Registrati').parent().effect("shake", {
            times:3
        }, 100);
    } else {

        regDlg.hide();
        jQuery('#registerLink').fadeOut();

                
    }
}
        
function handleEvRequest() {

    evDlg.hide();
}        
        
function submitRegForm( nextLink, formId){
    var geoc = null;
    var results;
    var address;
    var gInd = formId+":indirizzo";
    var gCit= formId+":citta";
    var gLat= formId+":lat";
    var gLon= formId+":lon";
    address = document.getElementById(gInd).value +", " +
    document.getElementById(gCit).value;

    if (address == null) {
        alert("Attenzione, per continuare e` necessario inserire un indirizzo!");
    }
    while (geoc == null){
        geoc = new google.maps.Geocoder();
    }
    try{
        geoc.geocode( {
            'address': address
        }, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                document.getElementById(gLat).value = results[0].geometry.location.lat().valueOf();
                document.getElementById(gLon).value = results[0].geometry.location.lng().valueOf();
                document.getElementById(gInd).value= results[0].formatted_address.valueOf();
                clickLink(nextLink);
            } else {
                alert("Geocode was not successful for the following reason: " + status);
                return false;
            }
        } );
    }
    catch (exception){
        alert("Attenzione: "+exception.toString());
    }
        
}