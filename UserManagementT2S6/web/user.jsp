<%-- 
    Document   : user
    Created on : 16-02-2022, 19:54:52
    Author     : Thiep Ngo
--%>

<%@page import="sample.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !"US".equals(loginUser.getRoleID())) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        <h1 style="color: purple">Information User:</h1>
        <%
            UserDTO loginUsers = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUsers == null) {
                loginUsers = new UserDTO();
            }
        %>
        User ID: <%=loginUsers.getUserID()%></br>
        Full Name: <%=loginUsers.getFullName()%></br>
        Role ID: <%=loginUsers.getRoleID()%></br>
        Password: <%= "****"%>
    </body>
</html>
