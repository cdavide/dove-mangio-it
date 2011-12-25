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
        
function submitEvForm(nextLink, formId) {
    evDlg.hide();
    clickLink(nextLink);
}

function submitNewsForm(nextLink, formId) {
    newsDlg.hide();
    clickLink(nextLink);
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

     function seleziona(form){
        
        
        if (document.getElementById(form+":scelta:0").checked==true) {
            document.getElementById(form+":salva").disabled = false;
            document.getElementById(form+":ind").disabled=false;
            document.getElementById(form+":ind").style.backgroundColor = "white";
        }
        else{
            document.getElementById(form+":ind").disabled = true;
            document.getElementById(form+":salva").disabled=true;
            document.getElementById(form+":ind").style.backgroundColor = "silver";
            document.getElementById(form+":ind").value="";
            
        }
     }
     
     function needGeoByAddr(){

        if(document.getElementById("formric:scelta:1").checked){
              navigator.geolocation.getCurrentPosition(ottieniPosizione,errorePosizione);
             return false;
         }
        if(document.getElementById("formric:scelta:0").checked && document.getElementById("formric:ind").value==""){
             alert("inserisci un indirizzo!");
             return false;
         }
         
        return true;
     }
    function ottieniPosizione(pos) {
        
        document.getElementById("formric:lat").value=pos.coords.latitude;
        document.getElementById("formric:lon").value=pos.coords.longitude;
        clickLink("formric:sub");
    }
     
     function errorePosizione(err) {
     
        if(err.code == 1) {
            alert("L'utente non ha autorizzato la geolocalizzazione");
        } else if(err.code == 2) {
            alert("Posizione non disponibile");
        } else if(err.code == 3) {
            alert("Timeout");
        } else {
            alert("ERRORE:" + err.message);
        }
        document.getElementById("formric:scelta:0").checked=true;
        seleziona("formric")
      }


function geoByAddr(){
    var geocoder;
    var address;
    address = document.getElementById("formric:ind").value;
    
    geocoder = new google.maps.Geocoder();
    geocoder.geocode( {
        'address': address
    } , callback);
}
//funzione sempre richiamata da geoByAddr
function callback(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
        document.getElementById("formric:lat").value = results[0].geometry.location.lat().valueOf();
        document.getElementById("formric:lon").value = results[0].geometry.location.lng().valueOf();
        document.getElementById("formric:ind").value= results[0].formatted_address.valueOf();
        clickLink("formric:sub");
    } else {
        alert("Geocode was not successful for the following reason: " + status);
        return false;
    }
}