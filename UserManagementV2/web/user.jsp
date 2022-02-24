<%-- 
    Document   : user
    Created on : 24-02-2022, 23:18:41
    Author     : Thiep Ngo
--%>

<%@page import="sample.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet" href="style.css"/>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !"US".equals(loginUser.getRoleID())){
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        <%
            UserDTO loginUsers = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUsers == null) {
                loginUsers = new UserDTO();
            }
        %>
        <div class="header">
            <div class="header__img">
                <a href="home.jsp">
                    <img src="https://previews.123rf.com/images/iimages/iimages1301/iimages130101269/17443531-ilustraci%C3%B3n-de-un-hombre-y-una-tienda-de-verduras.jpg" alt="alt"/>
                </a>  
            </div>
            <div class="header__name">
                <p>Cửa Hàng Rau Củ Của Ngô Thiệp</p>
            </div>
            <form class="header__user" action="home.jsp" method="POST">
                <p>Welcome: <%=loginUser.getFullName() %></p>
                <input class="header__logout" type="submit" name="action" value="Logout" />
            </form>
        </div>
    </body>
</html>
