<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Homepage LunchAround</title>
	</head>
         <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
        <script type="text/javascript" src="JSUtil.js"></script>
	<body>
            
            <% if(request.getAttribute("errore")!=null){ %>
            
        <font color="red"> <%= request.getAttribute("errore")%> </font>
                
            <% } %>
            
            
		<h1>Welcome to LunchAround!</h1><br><br>
		inserimento nuovo locale:
		<form id="form" name=formlocale action="LocaliServlet" method="POST" >
			<br>nome locale:  <input type="text" name="nome">
			<br>Via e numero civico:  <input id="via" type="text" name="via">
			<br> città:   <input id="citta" type="text" name="citta">
			<br> proprietario   <input type="text" name="proprietario">
			<br> partita iva   <input type="text" name="pIVA">
			<input type="hidden"  name="azione" value="aggiungi_locale">
                        <input type="hidden" id="ind" name="indirizzo" value="">
                        <input type="hidden" id="lat" name="latitudine" value=0>
                        <input type="hidden" id="lon" name="longitudine" value=0>
			<input type="button" value="Aggiungi" onClick="submitForm(0);">
		</form>
                <br>
                <hr>
                <a href="LocaliServlet?azione=mostra_tutti">Elenco di tutti i locali presenti su LunchAround</a>
                <hr>
                <a href="LocaliServlet?azione=vai_a_ricerca">Cerca i locali nelle vicinanze</a>
                
                <hr>
                <jsp:include page="login.jsp"/>
                <hr>
</body>
</html>
