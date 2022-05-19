
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Login extends HttpServlet {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/ex4";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "samyuktha9111";

	static int count = 0;

	public void sessionCreated(HttpSessionEvent sessionEvent) {
		count = count + 1;
	}

	public int getSessionCount() {
		return count;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("index.html");
		rd.forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
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

			// Execute SQL query
			Statement stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM ex4.credentials";
			ResultSet rs = stmt.executeQuery(sql);

			Boolean flag = false;

			out.println(
					"<html>	<head>	<meta charset=\"utf-8\">  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">  <title>SkillTest</title>    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\" />  <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin />  <link href=\"https://fonts.googleapis.com/css2?family=DM+Sans:ital,wght@0,400;0,500;0,700;1,400&display=swap\"   rel=\"stylesheet\" /> <link href=\"./style.css\" rel=\"stylesheet\" type=\"text/css\" /> <style media=\"all\"> </style></head><body> <!-- this is the start of content -->  <header>    <h1>SkillTest</h1>  </header><section> ");

			// Extract data from result set
			while (rs.next()) {

				if (request.getParameter("username").equals(rs.getString("username"))
						&& request.getParameter("password").equals(rs.getString("password"))) {

					HttpSession session = request.getSession();
					session.setAttribute("username", request.getParameter("username"));

					sessionCreated(new HttpSessionEvent(session));

					sql = "SELECT * FROM ex4.registrations WHERE username='" + request.getParameter("username") + "'";

					ResultSet rs1 = stmt.executeQuery(sql);

					if (rs1.next()) {
						response.sendRedirect("templates/dashboard.html");
					} else {
						response.sendRedirect("templates/form.html");

					}

					flag = true;
					break;
				}

			}

			if (flag == false) {
				out.println(
						"<h2>Login Failed</h2><button class=\"button\" onclick=\"window.location.href='index.html'\">Try Again</button>");
			}

			out.println("</section></body></html>");

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