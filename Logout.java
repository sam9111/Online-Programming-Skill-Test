
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Logout extends HttpServlet {

    public void sessionDestroyed(HttpSessionEvent sessionEvent, int count) {
        count = count - 1;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Login login = new Login();

        int count = login.getSessionCount();

        HttpSession session = request.getSession();

        session.invalidate();

        sessionDestroyed(new HttpSessionEvent(session), count);

        response.sendRedirect("index.html");

    }

}
