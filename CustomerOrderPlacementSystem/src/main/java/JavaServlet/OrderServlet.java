package JavaServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    private Connection con;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/customerdb", // your DB
                    "root", // username
                    "root"  // password
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("❌ DB Connection failed! Details: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("customer_name");
        String item = request.getParameter("item");
        String quantityStr = request.getParameter("quantity");

        if (name == null || name.isEmpty() ||
            item == null || item.isEmpty() ||
            quantityStr == null || quantityStr.isEmpty()) {
            out.println("<h3>❌ Please fill in all fields!</h3>");
            out.println("<a href='order.html'>Go back</a>");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            out.println("<h3>❌ Quantity must be a number!</h3>");
            out.println("<a href='order.html'>Go back</a>");
            return;
        }

        try {
            // Insert order into DB
            String insertSQL = "INSERT INTO orders (customer_name, item, quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, item);
            ps.setInt(3, quantity);
            ps.executeUpdate();

            // Get the last inserted order
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);

                // Display only current order
                out.println("<h2>✅ Order placed successfully!</h2>");
                out.println("<h3>Current Order Summary:</h3>");
                out.println("<table border='1' cellpadding='5'>");
                out.println("<tr><th>ID</th><th>Customer Name</th><th>Item</th><th>Quantity</th></tr>");
                out.println("<tr>");
                out.println("<td>" + orderId + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + item + "</td>");
                out.println("<td>" + quantity + "</td>");
                out.println("</tr>");
                out.println("</table><br>");
                out.println("<a href='order.html'>Place another order</a>");
            }

        } catch (SQLException e) {
            out.println("<h3>❌ Database error: " + e.getMessage() + "</h3>");
            out.println("<a href='order.html'>Go back</a>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("order.html");
    }

    @Override
    public void destroy() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
