
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Register extends HttpServlet {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ex4";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "samyuktha9111";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] details = { "name", "college_name", "college_address", "pincode", "age", "dob", "gender", "department",
                "contact", "email", "skills", "hobby1", "hobby2", "hobby3", "filename" };

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println(
                "<html>	<head>	<meta charset=\"utf-8\">  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">  <title>SkillTest</title>    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\" />  <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin />  <link href=\"https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@0,400;0,500;0,700;1,400&display=swap\"   rel=\"stylesheet\" /> <link href=\"./style.css\" rel=\"stylesheet\" type=\"text/css\" /> <style media=\"all\"> </style></head><body> <!-- this is the start of content -->  <header>    <h1>SkillTest</h1></header><section> ");

        try {
            // Register JDBC driver
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName(JDBC_DRIVER);

            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            PreparedStatement st = conn
                    .prepareStatement("insert into registrations values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (int i = 0; i < details.length; i++) {
                st.setString(i + 1, request.getParameter(details[i]));
            }

            st.executeUpdate();

            // Close all the connections
            st.close();
            conn.close();

            // Get a writer pointer
            // to display the successful result

            // out.println(
            // "<h2>Registration Successful!</h2><button class=\"button\"
            // onclick=\"window.location.href='templates/test.html'\">Start Skill
            // Test</button>");

            out.println("</section></body></html>");

            RequestDispatcher rd = request.getRequestDispatcher("Test");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
