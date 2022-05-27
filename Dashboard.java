
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Dashboard extends HttpServlet {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ex4";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "samyuktha9111";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            Login login = new Login();

            PrintWriter out = response.getWriter();

            Cookie[] cookies = request.getCookies();

            HttpSession session = request.getSession();

            String username = session.getAttribute("username").toString();

            out.println(
                    "<html>	<head>	<meta charset=\"utf-8\">  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">  <title>SkillTest</title>    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\" />  <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin />  <link href=\"https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@0,400;0,500;0,700;1,400&display=swap\"   rel=\"stylesheet\" /> <link href=\"./style.css\" rel=\"stylesheet\" type=\"text/css\" /> <style media=\"all\"> </style></head><body> <!-- this is the start of content -->  <header>    <h1>SkillTest</h1>     <nav><a href=\"http://localhost:8080/ex4/logout\">Logout</a></nav> </header><section> ");

            out.println("<h2>Dashboard</h2>");
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("name")) {
                        String name = cookie.getValue();

                        out.println("<h3>" + name.toUpperCase() + " here are your results!</h3>");

                    }
                }
            }

            Statement stmt = conn.createStatement();
            String sql;
            ResultSet rs = null;
            sql = "SELECT * FROM ex4.tests WHERE `username` = '" + username + "'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {

                out.println("<table><tr><th>Skill</th><th>Marks</th></tr>");

                out.println("<tr><td>Java</td><td>" + rs.getInt("java_marks") + "</td></tr>");
                out.println("<tr><td>C</td><td>" + rs.getInt("c_marks") + "</td></tr>");
                out.println("<tr><td>Python</td><td>" + rs.getInt("python_marks") + "</td></tr>");

            }
            out.println("</section></body></html>");

        } catch (

        SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }

    }
}