package JavaServlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UsernameDisplayServlet")
public class UsernameDisplayServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Read the 'username' parameter
        String username = request.getParameter("username");

        out.println("<html><head><title>Username Display</title></head><body>");

        if (username == null || username.trim().isEmpty()) {
            // No username yet, show the form
            out.println("<h2>Enter your username</h2>");
            out.println("<form action='UsernameDisplayServlet' method='get'>");
            out.println("Username: <input type='text' name='username' required>");
            out.println("<input type='submit' value='Submit'>");
            out.println("</form>");
        } else {
            // Username provided, show greeting
            out.println("<h2>Welcome, " + username + "!</h2>");
        }

        out.println("</body></html>");
        out.close();
    }
}
