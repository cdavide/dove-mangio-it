    
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="JSUtil.js"></script>
 <script type="text/javascript">
     function seleziona(op){
        if (op) {
            document.formricerca.indirizzo.disabled = false;
            document.formricerca.salva.disabled=false;
            document.formricerca.indirizzo.style.backgroundColor = "white";
        }
        else{
            document.formricerca.indirizzo.disabled = true;
            document.formricerca.salva.disabled=true;
            document.formricerca.indirizzo.style.backgroundColor = "silver";
            document.formricerca.indirizzo.value="";
            
        }
     }
     //controlla se è necessario chiamare la funzione x geoloc. l'indirizzo'
     function controlla(){

         if(document.formricerca.scelta[0].checked==true && document.formricerca.indirizzo.value==""){
             alert("inserisci un indirizzo!");
             return false;
         }
         if(document.formricerca.scelta[1].checked==true){
              navigator.geolocation.getCurrentPosition(ottieniPosizione,errorePosizione);
             return false;
         }
        
        return true;
     }
     
     function ottieniPosizione(pos) {
        
        document.formricerca.latitudine.value=pos.coords.latitude;
        document.formricerca.longitudine.value=pos.coords.longitude;
        document.formricerca.submit();
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
        seleziona(1);
        document.formricerca.scelta[0].checked=true;
      }
 
 
 
 </script>

        Cerca un posto dove pranzare!
<form name="formricerca" id="form" action="LocaliServlet" method="POST">

    <input type="radio" name="scelta" value="ind" onclick="seleziona(1)" checked="true"> ricerca per indirizzo...</input><br>
    indirizzo:  <input type="text" size=40 name="indirizzo" id="ind"
                       value="<% if(session.getAttribute("home")!=null){ %><%=session.getAttribute("home")%><% } %>"><br>
    ...oppure <input type="radio" name="scelta" value="pos" onclick="seleziona(0)"> ottieni la mia posizione</input><br>

    con distanza massima
    <select name="distanza">
            <option value="1">1 km</option>
            <option value="2">2 km</option>
            <option value="5">5 km</option>
            <option value="10">10 km</option>
            <option value="15">15 km</option>
            <option value="20">20 km</option>
    </select><br>

    <input type="checkbox" name="salva" value="true">Imposta questo indirizzo come predefinito</input><br>
    <input type="hidden" name="latitudine" id="lat" value="">
    <input type="hidden" name="longitudine" id="lon" value="">
    <input type="hidden" name="azione" value="ricerca_locali">
    <input type="button" value="Ricerca" onClick="if(controlla()){submitForm(1);}">
</form>
