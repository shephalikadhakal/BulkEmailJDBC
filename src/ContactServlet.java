import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ContactServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.print("<h1>Contacts Table</h1>");
        out.print("<table border='1'><tr><th>CID</th><th>Name</th><th>Email</th></tr>");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactinfo", "root", "admin")) {
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contacts");

            while (rs.next()) {
                out.print("<tr><td>" + rs.getInt("cid") + "</td><td>" + rs.getString("name") + "</td><td>" + rs.getString("email") + "</td></tr>");
            }
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.print("</table>");
    }
}