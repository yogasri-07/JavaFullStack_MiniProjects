package JavaServlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/simpleInterest")
public class simpleInterest extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String pStr = request.getParameter("principal");
        String rStr = request.getParameter("rate");
        String tStr = request.getParameter("time");

        out.println("<html><body>");
        out.println("<h2>Simple Interest Calculator</h2>");

        // If parameters are missing, show the form
        if (pStr == null || rStr == null || tStr == null) {
            out.println("<form action='simpleInterest' method='get'>");
            out.println("Principal (P): <input type='text' name='principal' required><br><br>");
            out.println("Rate (R): <input type='text' name='rate' required><br><br>");
            out.println("Time (T): <input type='text' name='time' required><br><br>");
            out.println("<input type='submit' value='Calculate'>");
            out.println("</form>");
        } else {
            // Parameters exist → calculate
            try {
                double principal = Double.parseDouble(pStr.trim());
                double rate = Double.parseDouble(rStr.trim());
                double time = Double.parseDouble(tStr.trim());

                double si = (principal * rate * time) / 100;

                out.println("Principal: " + principal + "<br>");
                out.println("Rate: " + rate + "<br>");
                out.println("Time: " + time + "<br>");
                out.println("<h3>Simple Interest: " + si + "</h3>");
                out.println("<a href='simpleInterest'>Calculate Again</a>");
            } catch (NumberFormatException e) {
                out.println("<h3 style='color:red;'>Please enter valid numeric values!</h3>");
                out.println("<a href='simpleInterest'>Go back</a>");
            }
        }

        out.println("</body></html>");
    }
}
