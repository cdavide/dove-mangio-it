<%-- 
    Document   : newjsp
    Created on : 16-set-2011, 15.19.48
    Author     : Dave
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina di test registrazione utenti</title>
    </head>
    <body>
        <form name=formlocale action="UtentiServlet" method="GET">
        <br>Nome: <input type="text" name="username">
        <br>E-Mail: <input type="text" name="mail">
        <br>Home Location: <input type="text" name="home">
        <br>Password: <input type="text" name="password">
        <br>News <input type="checkbox" name="news" value="true">
        <br>Eventi <input type="checkbox" name="news" value="true">
        <br>Foto<input type="text" name="foto">
        <input type="hidden" value="aggiungi_utente" name="azione">
        <input type="submit" value="Aggiungi">
        </form>
        <hr>
    </body>
</html>
