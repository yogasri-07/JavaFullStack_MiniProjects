package JavaServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

    Connection con;

    // 1️⃣ init() – create DB connection
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companydb",
                "root",     // change if needed
                "root"      // change if needed
            );

            System.out.println("DB Connected");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // 2️⃣ service() – register + login
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // If servlet opened directly → redirect
        if (action == null) {
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // 🔹 REGISTER
            if (action.equals("Register")) {

                String name = request.getParameter("name");
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO employee(name, username, password) VALUES (?, ?, ?)"
                );

                ps.setString(1, name);
                ps.setString(2, username);
                ps.setString(3, password);
                ps.executeUpdate();

                // After register → go to login
                response.sendRedirect("login.html");
            }

            // 🔹 LOGIN
            else if (action.equals("Login")) {

                String username = request.getParameter("username");
                String password = request.getParameter("password");

                PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM employee WHERE username=? AND password=?"
                );

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h2>Welcome " + rs.getString("name") + "</h2>");
                    out.println("<h3>Attendance Logged Successfully</h3>");
                } else {
                    out.println("<h3>Invalid Username or Password</h3>");
                    out.println("<a href='login.html'>Try Again</a>");
                }
            }

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }

    // 3️⃣ destroy() – close DB connection
    @Override
    public void destroy() {
        try {
            con.close();
            System.out.println("DB Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
