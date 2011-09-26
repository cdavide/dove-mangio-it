
    <% if(session.getAttribute("nome_utente")!=null) { %>
    
        Ciao  <%= session.getAttribute("nome_utente") %>! <br><br>
        
        <a href="UtentiServlet?azione=logout">Effettua il logout</a>
    
    <%} else { %>
    
        <form name=login action="UtentiServlet" method="POST">
            <br>Email:  <input type="text" name="mail">
            <br>Password:  <input type="text" name="password">
            <input type="hidden" name="azione" value="login">
            <input type="submit" value="Login">
        </form><br>
        oppure
        <a href="UtentiServlet?azione=registra_utente">Registrati come utente</a>
    <% }%>