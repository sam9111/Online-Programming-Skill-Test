
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Test extends HttpServlet {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ex4";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "samyuktha9111";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            out.println(
                    "<html>	<head>	<meta charset=\"utf-8\">  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">  <title>SkillTest</title>    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\" />  <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin />  <link href=\"https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@0,400;0,500;0,700;1,400&display=swap\"   rel=\"stylesheet\" /> <link href=\"./style.css\" rel=\"stylesheet\" type=\"text/css\" /> <style media=\"all\"> </style></head><body> <!-- this is the start of content -->  <header>    <h1>Online Programming Skill Test</h1>  <nav><a href=\"http://localhost:8080/ex4/logout\">Logout</a></nav> </header><section> ");

            Statement stmt = conn.createStatement();
            String sql;
            ResultSet rs = null;

            int java_marks = 0;
            int cpp_marks = 0;
            int python_marks = 0;

            for (int i = 1; i <= 6; i++) {

                String answer = request.getParameter(Integer.toString(i));

                if (answer != null) {

                    sql = "SELECT * FROM ex4.questions WHERE id = " + i;
                    rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        if (rs.getString("answer").equals(answer)) {
                            if (rs.getString("skill").equals("Java")) {
                                java_marks++;
                            } else if (rs.getString("skill").equals("C++")) {
                                cpp_marks++;
                            } else if (rs.getString("skill").equals("Python")) {
                                python_marks++;
                            }

                        }

                    }
                }

            }

            out.println("<h2>" + request.getParameter("name").toUpperCase() + " here are your results!</h2>");

            out.println("<table><tr><th>Skill</th><th>Marks</th></tr>");

            String[] skills = request.getParameter("skills").split(", ", 0);

            if (Arrays.asList(skills)
                    .contains("Java")) {
                out.println("<tr><td>Java</td><td>" + java_marks + "</td></tr>");
            }
            if (Arrays.asList(skills)
                    .contains("C++")) {
                out.println("<tr><td>C++</td><td>" + cpp_marks + "</td></tr>");
            }
            if (Arrays.asList(skills)
                    .contains("Python")) {
                out.println("<tr><td>Python</td><td>" + python_marks + "</td></tr>");
            }

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            out.println(
                    "<html>	<head>	<meta charset=\"utf-8\">  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">  <title>SkillTest</title>    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\" />  <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin />  <link href=\"https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@0,400;0,500;0,700;1,400&display=swap\"   rel=\"stylesheet\" /> <link href=\"./style.css\" rel=\"stylesheet\" type=\"text/css\" /> <style media=\"all\"> </style></head><body> <!-- this is the start of content -->  <header>    <h1>Online Programming Skill Test</h1>  <nav><a href=\"http://localhost:8080/ex4/logout\">Logout</a></nav> </header><section> ");

            String[] skills = request.getParameter("skills").split(", ", 0);
            Statement stmt = conn.createStatement();
            String sql;
            ResultSet rs = null;

            out.println("<form action=\"http://localhost:8080/ex4/Test\" method=\"post\">");

            for (String skill : skills) {
                sql = "SELECT * FROM ex4.questions WHERE `skill` = '" + skill + "'";
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    out.println("<span >" + rs.getString("skill") + "</span><br>");
                    out.println("<div class=\"form-input\"><label>" + rs.getString("question") + "</label>");
                    out.println("<input name=\"" + rs.getString("id") + "\" type=\"text\" />");
                    out.println("</div>");
                }
            }

            out.println("<input type=\"hidden\" value=\"" + request.getParameter("skills") + "\" name=\"skills\" />");

            out.println("<input type=\"hidden\" value=\"" + request.getParameter("name") + "\" name=\"name\" />");

            out.println("<input type=\"submit\" value=\"Submit\" /></form>");

            out.println("</section> <!-- this is the end of content --> </body> </html>");

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }
}
