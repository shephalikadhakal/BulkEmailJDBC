<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Search Contacts</title>
</head>
<body>
<form action="ContactSearch.jsp" method="POST">
    <label>Enter Name:</label>
    <input type="text" name="name" id="name">
    <input type="submit" value="Search">
</form>
<%
    String name = request.getParameter("name");
    if (name != null) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactinfo", "root", "")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contacts WHERE name LIKE '%" + name + "%'");
%>
<table border="1">
    <tr><th>CID</th><th>Name</th><th>Email</th></tr>
<%
            while (rs.next()) {
%>
    <tr>
        <td><%= rs.getInt("cid") %></td>
        <td><%= rs.getString("name") %></td>
        <td><%= rs.getString("email") %></td>
    </tr>
<%
            }
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
%>
</table>
</body>
</html>