package JavaServlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TemperatureConverterServlet")
public class TemperatureConverterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String celsiusStr = request.getParameter("celsius");

        // If no input, redirect to the HTML form instead of showing error
        if (celsiusStr == null || celsiusStr.trim().isEmpty()) {
            response.sendRedirect("temperatureconverter.html");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        double celsius = Double.parseDouble(celsiusStr);
        double fahrenheit = (celsius * 9 / 5) + 32;

        out.println("<html><body>");
        out.println("<h2>Temperature Conversion Result</h2>");
        out.println("<p>" + celsius + " °C = " + fahrenheit + " °F</p>");
        out.println("<a href='temperatureconverter.html'>Convert Again</a>");
        out.println("</body></html>");
    }
}
