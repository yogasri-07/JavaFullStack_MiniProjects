<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Addition in JSP</title>
</head>
<body>
    <h2>JSP Addition Program</h2>

    <!-- HTML Form -->
    <form method="get" action="">
        Number 1: <input type="text" name="num1" required>
        <br><br>
        Number 2: <input type="text" name="num2" required>
        <br><br>
        <input type="submit" value="Add">
    </form>

    <br>

    <!-- Java Scriptlet to calculate sum -->
    <%
        // Read parameters from the request
        String n1 = request.getParameter("num1");
        String n2 = request.getParameter("num2");

        if (n1 != null && n2 != null) {
            try {
                int number1 = Integer.parseInt(n1);
                int number2 = Integer.parseInt(n2);
                int sum = number1 + number2;

                // Display result
                out.println("<h3>Result: " + number1 + " + " + number2 + " = " + sum + "</h3>");
            } catch (NumberFormatException e) {
                out.println("<h3 style='color:red;'>Please enter valid integers!</h3>");
            }
        }
    %>
</body>
</html>
