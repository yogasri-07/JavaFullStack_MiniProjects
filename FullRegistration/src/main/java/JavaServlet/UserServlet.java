package JavaServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private Connection con;

    // 1️⃣ init() → Establish DB connection
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/userdb", "root", "root"
            );
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("DB connection problem.");
        }
    }

    // 2️⃣ service() → Handles both registration & login
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // If user accessed servlet directly → redirect to login
        if (action == null) {
            response.sendRedirect("login.html");
            return;
        }

        // Validate input after form submission
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            out.println("<h3>Please enter both username and password!</h3>");
            out.println("<a href='login.html'>Go Back</a>");
            return;
        }

        try {
            if ("register".equalsIgnoreCase(action)) {
                // Registration
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO users(username, password) VALUES(?, ?)"
                );
                ps.setString(1, username);
                ps.setString(2, password);

                int i = ps.executeUpdate();
                if (i > 0) {
                    out.println("<h3>Registration successful! <a href='login.html'>Login Here</a></h3>");
                } else {
                    out.println("<h3>Registration failed!</h3>");
                }

            } else if ("login".equalsIgnoreCase(action)) {
                // Login
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=?"
                );
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    out.println("<h3>Login Successful! Welcome, " + username + ".</h3>");
                } else {
                    out.println("<h3>Invalid username or password! <a href='login.html'>Try Again</a></h3>");
                }
            } else {
                out.println("<h3>Invalid action!</h3>");
            }
        } catch (SQLException e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }

    // 3️⃣ destroy() → Close DB connection
    @Override
    public void destroy() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
