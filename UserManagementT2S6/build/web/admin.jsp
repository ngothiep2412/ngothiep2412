<%-- 
    Document   : admin
    Created on : 16-02-2022, 19:54:34
    Author     : Thiep Ngo
--%>

<%@page import="java.util.List"%>
<%@page import="sample.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !"AD".equals(loginUser.getRoleID())) {
                response.sendRedirect("login.jsp");
                return;
            }
            String search = request.getParameter("search");
            if (search == null) {
                search = "";
            }
        %>
        <h1 style="color: blue"> Welcome: <%=loginUser.getFullName()%></h1>
        <a style="text-decoration: none" href="MainController?action=Logout">Logout</a>
        <form action="MainController">
            <input style="margin-bottom: 18px" type="submit" name ="action" value="Logout"/>
        </form>
        <form action="MainController" method="POST">
            Search:<input type="text" name="search" placeholder="Search"/>
            <input type="submit" name="action" value="Search">
        </form>

        <%
            List<UserDTO> list = (List<UserDTO>) request.getAttribute("LIST_USER");
            String error_message = (String) request.getAttribute("ERROR");
            if (error_message == null) {
                error_message = "";
            }
        %>
        <h1 style="color: red"><%= error_message%></h1>
        <%
            if (list != null) {
                if (!list.isEmpty()) {

        %>   
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>User ID</th>
                    <th>Full Name</th>
                    <th>Role ID</th>
                    <th>Password</th>
                    <th>Delete</th>
                    <th>Update</th>
                </tr>
            </thead>
            <tbody>
                <%                    int count = 1;
                    for (UserDTO user : list) {
                %>
            <form action="MainController" method="POST">
                <tr>
                    <td><%=count++%></td>
                    <td>
                        <input type="text" name="userID" value="<%= user.getUserID() %>" readonly=""/>
                        
                    </td>
                    <td>
                        <input type="text" name="fullName" value="<%= user.getFullName()%>"/>
                    </td>
                    <td>
                        <% if (loginUser.getUserID().equals(user.getUserID())) {
                        %>
                        <input type="text" name="roleID" value="<%= user.getRoleID() %>" readonly="" required=""/>
                        <%} else {%>
                        <input type="text" name="roleID" value="<%= user.getRoleID() %>"required=""/>
                        <%}%>
                    </td>
                    
                    <td><%= user.getPassword()%></td>
                    <td><a style="text-decoration: none" href="MainController?action=Delete&userID=<%= user.getUserID()%>&search=<%=search%>">Delete</a></td>
                    <td>
                        <input type="submit" name="action" value="Update"/>
                        <input type="hidden" name="search" value="<%= search %>"/>
                    </td>
                </tr>
            </form>

            <%}%>
        </tbody>
    </table>

    <%          }

        }
    %>
</body>
</html>
