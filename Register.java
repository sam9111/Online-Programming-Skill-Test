
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

        doPost(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] details = { "name", "college_name", "college_address", "pincode", "age", "dob", "gender", "department",
                "contact", "email", "skills", "hobby1", "hobby2", "hobby3", "filename", "username" };

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Cookie name = new Cookie("name", request.getParameter("name"));
        Cookie contact = new Cookie("contact", request.getParameter("contact"));
        Cookie skills = new Cookie("skills", String.join("&", request.getParameter("skills").trim().split(", ", 0)));

        response.addCookie(name);
        response.addCookie(contact);
        response.addCookie(skills);

        try {
            // Register JDBC driver
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName(JDBC_DRIVER);

            // Open a connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            HttpSession session = request.getSession();

            // Checking if name in database

            String sql = "SELECT * FROM registrations WHERE name = ? AND username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, request.getParameter("name"));
            stmt.setString(2, (String) session.getAttribute("username"));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                out.println("exists");

                stmt.close();

            } else {

                // Execute SQL query
                PreparedStatement st = conn
                        .prepareStatement(
                                "insert into registrations values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                for (int i = 0; i < details.length; i++) {
                    st.setString(i + 1, request.getParameter(details[i]));
                }

                out.println("success");

                st.setString(16, (String) session.getAttribute("username"));

                st.executeUpdate();
                st.close();

            }

            // Close all the connections
            conn.close();

        } catch (Exception e) {
            out.println("failure");
            e.printStackTrace();
        }
    }
}
