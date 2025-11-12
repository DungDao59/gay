package util;
import java.sql.*;
/*
 * @author Dao Tien Dung - s4088577
 */

public class Database{
    private static final String DB_URL = "jdbc:sqlite:assignment1.db";
    private static Connection connection;


    //Check for SQL connection
    public static Connection getConnection() throws SQLException{
        if(connection == null || connection.isClosed()) connection = DriverManager.getConnection(DB_URL);
        
        return connection;
    }

    // Creating the initial entities
    public static void initiallize(){
        try(Statement smt =  getConnection().createStatement()){
            
            // Create Person base table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Person(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    fullname TEXT NOT NULL,
                    dateOfBirth TEXT,
                    contactInfo TEXT
                );        
            """);

            // Create Attendee table inherits Person table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Attendee(
                    attendeeId INTEGER PRIMARY KEY,
                    FOREIGN KEY (attendeeId) REFERENCES Person(id)
                );
            """);

            // Create Presenter table inherits Person table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Presenter(
                    presenterId INTEGER PRIMARY KEY,
                    role TEXT NOT NULL,
                    FOREIGN KEY (presenterId) REFERENCES Person(id)
                );
            """);

            // Create Event table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Event(
                    eventId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    type TEXT NOT NULL,
                    location TEXT NOT NULL,
                    startDate TEXT NOT NULL,
                    endDate TEXT NOT NULL
                );
            """);

            // Create Session table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Session(
                    sessionId INTEGER PRIMARY KEY AUTOINCREMENT,
                    eventId INTEGER NOT NULL,
                    title TEXT NOT NULL,
                    description TEXT NOT NULL,
                    scheduleDateTime TEXT NOT NULL,
                    venue TEXT,
                    capacity INTEGER,
                    FOREIGN KEY (eventId) REFERENCES Event(eventId)
                );
            """);

            // Create Session contains Attendee table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Session_Attendee(
                    sessionId INTEGER NOT NULL,
                    attendeeId INTEGER NOT NULL,
                    PRIMARY KEY (sessionId, attendeeId),
                    FOREIGN KEY (sessionId) REFERENCES Session(sessionId),
                    FOREIGN KEY (attendeeId) REFERENCES Attendee(attendeeId)
                );
            """);

            // Create Session contains Presenter table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Session_Presenter(
                    sessionId INTEGER NOT NULL,
                    presenterId INTEGER NOT NULL,
                    PRIMARY KEY (sessionId, presenterId),
                    FOREIGN KEY (sessionId) REFERENCES Session(sessionId),
                    FOREIGN KEY (presenterId) REFERENCES Presenter(presenterId)
                );       
            """);

            // Create Ticket table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Ticket(
                    ticketId INTEGER PRIMARY KEY AUTOINCREMENT,
                    type TEXT NOT NULL,
                    price REAL NOT NULL,
                    status TEXT NOT NULL,
                    attendeeId INTEGER NOT NULL,
                    eventId INTEGER NOT NULL,
                    FOREIGN KEY (attendeeId) REFERENCES Attendee(attendeeId),
                    FOREIGN KEY (eventId) REFERENCES Event(eventId)
                );
            """);

            // Create Schedule table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Schedule(
                    scheduleId INTEGER PRIMARY KEY AUTOINCREMENT
                );
            """);

            // Create Schedule to manage Sessions table
            smt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS Schedule_Session(
                    scheduleId INTEGER NOT NULL,
                    sessionId INTEGER NOT NULL,
                    PRIMARY KEY (scheduleId, sessionId),
                    FOREIGN KEY (scheduleId) REFERENCES Schedule(scheduleId),
                    FOREIGN KEY (sessionId) REFERENCES Session(sessionId)
                );
            """);

            System.out.println("Database initiallized successfully");
        }
        catch(SQLException e){
            System.err.println("Failed to initiallized database: " + e.getMessage());
        }
    }
}
