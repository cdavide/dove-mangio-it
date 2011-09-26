<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
    
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
            
            <div id="map_canvas" style="width:100%; height:100%"></div>
            
            <%= request.getAttribute("contenuto")%>	
        </body>
</html>
