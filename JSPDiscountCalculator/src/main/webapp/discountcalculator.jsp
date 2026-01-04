<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Discount Calculator</title>
</head>
<body>

<h2>JSP Discount Calculator</h2>

<form method="get">
    Price:
    <input type="text" name="price" required>
    <br><br>
    Discount (%):
    <input type="text" name="discount" required>
    <br><br>
    <input type="submit" value="Calculate">
</form>

<hr>

<%
    String priceStr = request.getParameter("price");
    String discountStr = request.getParameter("discount");

    if (priceStr != null && discountStr != null) {
        try {
            double price = Double.parseDouble(priceStr);

            // Remove % if present
            discountStr = discountStr.replace("%", "").trim();
            double discount = Double.parseDouble(discountStr);

            double finalPrice = price - (price * discount / 100);
%>
            <h3>Original Price: <%= price %></h3>
            <h3>Discount: <%= discount %> %</h3>
            <h3>Final Price: <%= finalPrice %></h3>
<%
        } catch (Exception e) {
%>
            <h3 style="color:red;">Please enter valid numeric values!</h3>
<%
        }
    }
%>

</body>
</html>
