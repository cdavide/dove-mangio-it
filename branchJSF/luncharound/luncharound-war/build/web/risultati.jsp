    
    <script type="text/javascript"
    src="http://maps.googleapis.com/maps/api/js?sensor=false">
    </script>

    <%= request.getAttribute("script")%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>LunchAround</title>
	</head>
        
	
        <body onload="initialize()">
        <center>
            <div id="map_canvas" style="width:60%; height:70%;" ></div>
         
            <%= request.getAttribute("contenuto")%>
            <hr>
            <jsp:include page="form_ricerca.jsp"/>
        </center>
            
            
        </body>
</html>
