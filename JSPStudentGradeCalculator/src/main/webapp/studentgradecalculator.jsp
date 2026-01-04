<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Student Grade Calculator</title>
</head>
<body>

<h2>Student Grade Calculator</h2>

<form method="post">
    Enter Marks:
    <input type="number" name="marks" required>
    <br><br>
    <input type="submit" value="Check Grade">
</form>

<br>

<%
    String m = request.getParameter("marks");

    if (m != null) {
        int marks = Integer.parseInt(m);
        String grade;

        if (marks >= 80) {
            grade = "A";
        } else if (marks >= 60) {
            grade = "B";
        } else if (marks >= 40) {
            grade = "C";
        } else {
            grade = "Fail";
        }
%>

<h3>Marks: <%= marks %></h3>
<h3>Grade: <%= grade %></h3>

<%
    }
%>

</body>
</html>
