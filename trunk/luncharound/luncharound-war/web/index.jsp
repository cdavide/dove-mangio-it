<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Homepage LunchAround</title>
	</head>
	<body>
		<h1>Welcome to LunchAround!</h1><br><br>
		inserimento nuovo locale:
		<form name=formlocale action="mainServlet" method="GET">
			<br>nome locale:  <input type="text" name="nome">
			<br>indirizzo:  <input type="text" name="indirizzo">
			<br> latit:   <input type="text" name="latit">
			<br> longit:   <input type="text" name="longit">
			<br> proprietario   <input type="text" name="propriet">
			<br> partita iva   <input type="text" name="piva">
			<input type="hidden" name="metodo" value="aggiungiLocale">
			<input type="submit" value="Aggiungi">
		</form>
		
    </body>
</html>
