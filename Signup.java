
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Signup extends HttpServlet {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ex4";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "samyuktha9111";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("./templates/signup.html");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

            // Validate
            Statement stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM ex4.credentials";
            ResultSet rs = stmt.executeQuery(sql);

            Boolean flag = false;

            while (rs.next()) {

                if (request.getParameter("username").equals(rs.getString("username"))) {
                    out.println(
                            "<h2>Account already exists!</h2><button class=\"button\" onclick=\"window.location.href='templates/signup.html'\">Try Again</button>");

                    flag = true;
                    break;
                }

            }

            if (flag == false) {
                // Execute SQL query
                PreparedStatement st = conn
                        .prepareStatement("insert into credentials values(?, ?)");
                st.setString(1, request.getParameter("username"));
                st.setString(2, request.getParameter("password"));
                st.executeUpdate();

                // Close all the connections
                st.close();
                conn.close();

                // Get a writer pointer
                // to display the successful result

                out.println(
                        "<h2>Signup Successful!</h2><button class=\"button\" onclick=\"window.location.href='index.html'\">Login</button>");

            }

            out.println("</section></body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}