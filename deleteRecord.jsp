<%-- 
    Document   : createRecord
    Created on : Nov 25, 2015
    Author     : Michaela Butz
--%>

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
        <h2>Delete Book Record</h2>
        <form action="delete" method="post">
            Record Number: <input type="number" name="id" placeholder="Record id to delete." required>
            <br><br>

            <input type="hidden" name="action" value="deleteRecord">
            
            <input type="submit" name="submit" value="Submit">
            <br><br>
        </form>
    </body>
</html>
