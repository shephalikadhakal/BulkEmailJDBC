import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Import {
    public static void main(String[] args) {
        String filePath = "D:\\Java assignment\\qn1\\contacts.csv";
        String jdbcURL = "jdbc:mysql://localhost:3306/contactinfo";
        String username = "root";
        String password = "admin";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO contacts(cid, name, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line= br.readLine(); // Skip the header row;
            int batchSize = 20;
            int count = 0;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                statement.setInt(1, Integer.parseInt(data[0]));
                statement.setString(2, data[1]);
                statement.setString(3, data[2]);
                statement.addBatch();
                count++;
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}