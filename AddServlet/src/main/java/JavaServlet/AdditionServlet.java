package JavaServlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdditionServlet")
public class AdditionServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String str1 = request.getParameter("num1");
        String str2 = request.getParameter("num2");

        if (str1 == null || str2 == null || str1.isEmpty() || str2.isEmpty()) {
            out.println("<html><body>");
            out.println("<h3>Please enter both numbers!</h3>");
            out.println("<a href='addition.html'>Go Back</a>");
            out.println("</body></html>");
            return;
        }

        try {
            int num1 = Integer.parseInt(str1);
            int num2 = Integer.parseInt(str2);
            int sum = num1 + num2;

            out.println("<html><body>");
            out.println("<h2>Addition Result</h2>");
            out.println("<p>First Number: " + num1 + "</p>");
            out.println("<p>Second Number: " + num2 + "</p>");
            out.println("<p><b>Sum: " + sum + "</b></p>");
            out.println("<a href='addition.html'>Add Again</a>");
            out.println("</body></html>");
        } catch (NumberFormatException e) {
            out.println("<html><body>");
            out.println("<h3>Invalid input! Please enter numbers only.</h3>");
            out.println("<a href='addition.html'>Go Back</a>");
            out.println("</body></html>");
        } finally {
            out.close();
        }
    }
}
