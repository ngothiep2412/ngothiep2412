<%-- 
    Document   : login
    Created on : 16-02-2022, 16:22:41
    Author     : Thiep Ngo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Input your information: </h1>
        <form action="MainController" method="POST">
            UserID <input type="text" name="userID" required="" placeholder="userID"/></br>
            Password <input type="password" name="password" required="" placeholder="password"/></br>
            <input type="submit" name="action" value="Login"/>
            <input type="reset" value="Reset"/>
        </form>
        
        <%
            String error = (String) request.getAttribute("ERROR");
            if(error == null) {
                error = "";
            }
        %>
        <%= error %>
        
        <a href="creeate.jsp">Create User</a>
    </body>
</html>
