<%-- 
    Document   : creeate
    Created on : 23-02-2022, 17:17:02
    Author     : Thiep Ngo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <form action="MainController" method="POST">
            UserID<input type="text" name="userID" required=""/></br>
            Full Name<input type="text" name="fullName" required=""/></br>
            Role ID<input type="text" name="roleID" readonly="" value="US"/></br>
            Password<input type="password" name="password" required=""/></br>
            Password Confirm<input type="password" name="confirm" required=""/></br>
            <input type="submit" name="action" value="Create" /></br>
            <input type="reset" value="Reset" /></br>
        </form>
    </body>
</html>
