<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Homepage LunchAround</title>
	</head>
	<body>
		<h1>Welcome to LunchAround!</h1><br><br>
		inserimento nuovo locale:
		<form name=formlocale action="MainServlet" method="GET">
			<br>nome locale:  <input type="text" name="nome">
			<br>indirizzo:  <input type="text" name="indirizzo">
			<br> latit:   <input type="text" name="latitudine">
			<br> longit:   <input type="text" name="longitudine">
			<br> proprietario   <input type="text" name="proprietario">
			<br> partita iva   <input type="text" name="pIVA">
			<input type="hidden" name="azione" value="aggiungi_locale">
			<input type="submit" value="Aggiungi">
		</form>
                <br>
                <a href="MainServlet?azione=mostra_tutti">Elenco di tutti i locali presenti su LunchAround</a>
    </body>
</html>
