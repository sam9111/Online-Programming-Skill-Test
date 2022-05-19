
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AdminDashboard extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Login login = new Login();

        PrintWriter out = response.getWriter();

        out.println(
                "<html>	<head>	<meta charset=\"utf-8\">  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">  <title>SkillTest</title>    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\" />  <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin />  <link href=\"https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@0,400;0,500;0,700;1,400&display=swap\"   rel=\"stylesheet\" /> <link href=\"./style.css\" rel=\"stylesheet\" type=\"text/css\" /> <style media=\"all\"> </style></head><body> <!-- this is the start of content -->  <header>    <h1>SkillTest</h1>     <nav><a href=\"http://localhost:8080/ex4/logout\">Logout</a></nav> </header><section> ");

        out.println("         <h2>Admin Dashboard</h2>");

        out.println("         <h3>Total Number of Sessions: " + login.getSessionCount() + "</h3>");

        out.println("</section></body></html>");

    }
}