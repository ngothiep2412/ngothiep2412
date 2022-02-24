<%-- 
    Document   : home
    Created on : 24-02-2022, 22:23:50
    Author     : Thiep Ngo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet" href="style.css"/>
    </head>
    <body>
        <div class="header">
            <div class="header__img">
                <a href="home.jsp">
                    <img src="https://previews.123rf.com/images/iimages/iimages1301/iimages130101269/17443531-ilustraci%C3%B3n-de-un-hombre-y-una-tienda-de-verduras.jpg" alt="alt"/>
                </a>  
            </div>
            <div class="header__name">
                <p>Cửa Hàng Rau Củ Của Ngô Thiệp</p>
            </div>
            <form class="header__login-logout" action="login.jsp" method="POST">
                <input class="header__login" type="submit" name="action" value="Login" />
                <input class="header__create" type="submit" name="action" value="Create" />
            </form>
        </div>
    </body>
</html>
