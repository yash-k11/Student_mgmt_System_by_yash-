import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/javatask3";
        String username = "root";
        String password = "abc123";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.out.println("An error occurred while connecting to the database");
            e.printStackTrace();
        }
    }
}
