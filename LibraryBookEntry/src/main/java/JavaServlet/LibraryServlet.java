package JavaServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LibraryServlet")
public class LibraryServlet extends HttpServlet {

    private Connection con;

    @Override
    public void init() throws ServletException {

        String url = "jdbc:mysql://localhost:3306/libraryDB";
        String user = "root";   // your MySQL username
        String password = "root";    // your MySQL password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully");
        } catch (Exception e) {
            throw new ServletException("DB connection failed: " + e.getMessage());
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        try {
            // ---------------- VIEW BOOKS ----------------
            if ("view".equalsIgnoreCase(action)) {

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM books");

                out.println("<h2>All Books</h2>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th></tr>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("title") + "</td>");
                    out.println("<td>" + rs.getString("author") + "</td>");
                    out.println("<td>" + rs.getDouble("price") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
                out.println("<br><a href='/LibraryBookEntry/addbooks.html'>Add More Books</a>");

            }
            // ---------------- ADD BOOK ----------------
            else {

                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String priceStr = request.getParameter("price");

                if (title == null || author == null || priceStr == null ||
                    title.isEmpty() || author.isEmpty() || priceStr.isEmpty()) {

                    response.sendRedirect("/LibraryBookEntry/addbooks.html");
                    return;
                }

                double price = Double.parseDouble(priceStr);

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO books(title, author, price) VALUES (?, ?, ?)");
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setDouble(3, price);

                ps.executeUpdate();

                out.println("<h3>Book Added Successfully!</h3>");
                out.println("<a href='/LibraryBookEntry/addbooks.html'>Add Another Book</a><br>");
                out.println("<a href='/LibraryBookEntry/LibraryServlet?action=view'>View Books</a>");
            }

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            out.println("<a href='/LibraryBookEntry/addbooks.html'>Go Back</a>");
        }
    }

    @Override
    public void destroy() {
        try {
            if (con != null)
                con.close();
            System.out.println("Database connection closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
