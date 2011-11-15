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
		<h1>Welcome to LunchAround!</h1>
                
                <iframe src="//www.facebook.com/plugins/like.php?locale=it_IT&app_id=241460472572920&amp;href=www.facebook.com/luncharound&amp;send=false&amp;layout=standard&amp;width=600&amp;show_faces=true&amp;action=like&amp;colorscheme=light&amp;font&amp;height=100" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:450px; height:80px;" allowTransparency="true"></iframe>

                <br>
                
		<% if(session.getAttribute("idUtente")!=null){ %>
                <a href="LocaliServlet?azione=pag_ins_locale">Aggiungi il tuo locale su LunchAround</a>
                
                <% } %>
                <% if(session.getAttribute("localipersonali")!=null){ %>
                
                </br><a href="LocaliServlet?azione=visualizza_locali_personali">Visualizza i tuoi locali su LunchAround</a>
                
                <% } %>
                <hr>
                <a href="LocaliServlet?azione=mostra_tutti">Elenco di tutti i locali presenti su LunchAround</a>
                <hr>
                <a href="LocaliServlet?azione=vai_a_ricerca">Cerca i locali nelle vicinanze</a>
                
                <hr>
                <jsp:include page="login.jsp"/>
                <hr>
</body>
</html>
