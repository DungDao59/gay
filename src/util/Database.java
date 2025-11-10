package util;
import java.sql.*;

public class Database{
    private static final String DB_URL = "jdbc:sqlite:assignment1.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException{
        if(connection == null || connection.isClosed()) connection = DriverManager.getConnection(DB_URL);
        
        return connection;
    }

    public static void initiallize(){
        try(Statement smt =  getConnection().createStatement()){
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Person(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    fullname TEXT NOT NULL,
                    dateOfBirth TEXT,
                    contactInfo TEXT
                );        
            """);

            System.out.println("Database initiallized successfully");
        }
        catch(SQLException e){
            System.err.println("Failed to initiallized database: " + e.getMessage());
        }
    }
}
