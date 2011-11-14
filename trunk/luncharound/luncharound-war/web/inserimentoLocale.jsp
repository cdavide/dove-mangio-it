<%-- 
    Document   : inserimentoLocale
    Created on : 28-ott-2011, 11.18.50
    Author     : Bovio Lorenzo, Bronzino Francesco, Concas Davide
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
        <script type="text/javascript" src="JSUtil.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <h1> Inserimento nuovo locale: </h1>
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
                        <input type="hidden" id="idUtente" name="idUtente" value="<%= session.getAttribute("id") %>">
			<input type="button" value="Aggiungi" onClick="submitForm();">
		</form>
    </body>
     <jsp:include page="login.jsp"/>
</html>
