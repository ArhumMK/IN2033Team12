package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {
    public static void main(String[] args) {
        String url = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t12";
        String username = "in2033t12_d";
        String password = "MhftnbMWQLk";

        try {
            // Load driver (optional in modern Java, but safe)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connection successful!");

            // Simple test query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1");

            if (rs.next()) {
                System.out.println("✅ Query succeeded. Result: " + rs.getInt(1));
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
