<%-- 
    Document   : login
    Created on : 24-02-2022, 23:15:54
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
        <form action="MainController" method="POST">
            userID <input type="text" name="userID" required="" placeholder="User ID"></br>
            password <input type="password" name="password" required="" placeholder="Password"></br>
            <input type="submit" name="action" value="Login">
            <input type="reset" value="Reset">
        </form>
        <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
        %>
        <%= error %>
        </br>
        <a href="create.jsp">Create User</a>
    </body>
</html>
