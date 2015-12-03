<%-- 
    Document   : createRecord
    Created on : Nov 25, 2015
    Author     : Michaela Butz
--%>

<%@page import="java.util.List, model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Butz' Book Rating System</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="mystyle.css">
    </head>
    <body>
        <h1><a href="home.html">Rate a Book!</a></h1>
        <h2>Book Report</h2>
        <%
            List<User> mydata = (List<User>) request.getAttribute("mydata");
            out.println("<table>");
            for (User user : mydata) {
                out.println(user.inHTMLRowFormat());
            }
            out.println("</table>");
        %>
    </body>
</html>
