package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.Session;
import util.Database;

/*
 * Dao Tien Dung - s4088577
 * Session CRUD file
 */

public class SessionDao {
    // Set default date format for every date
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Get connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:assignment1.db");
    }

    /*
     * SESSION CRUD METHODS
     */

    // Add session to database
    public void addSession(Session newSession){
        try(Connection conn = getConnection()){
            
            String commandAddSessionSQL = "INSERT INTO Session(eventId, title, description, startDateTime, endDateTime, venue, capacity) VALUES (?,?,?,?,?,?)";
            // Execute add session to database
            try(PreparedStatement command = conn.prepareStatement(commandAddSessionSQL,Statement.RETURN_GENERATED_KEYS)){
                command.setInt(1, newSession.getEventId());
                command.setString(2, newSession.getTitle());
                command.setString(3, newSession.getDescription());
                command.setString(4, newSession.getStartDateTime().toString());
                command.setString(5, newSession.getEndDateTime().toString());
                command.setString(6, newSession.getVenue());
                command.setInt(7, newSession.getCapacity());

                command.executeUpdate();

                ResultSet result = command.getGeneratedKeys();

                if(result.next()){
                    newSession.setSessionId(result.getInt(1));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }    
    }

    // Update a session from database
    public void updateSession(Session session){
        try(Connection conn = getConnection()){

            String commandUpdateSQL = "UPDATE Session SET eventId=?, title=?, description=?, startDateTime=?, endDateTime=?, venue=?, capacity=? WHERE sessionId=?";
            // Execute update a session command from database
            try(PreparedStatement command = conn.prepareStatement(commandUpdateSQL)){
                command.setInt(1, session.getEventId());
                command.setString(2, session.getTitle());
                command.setString(3, session.getDescription());
                command.setString(4, session.getStartDateTime().toString());
                command.setString(5, session.getEndDateTime().toString());
                command.setString(6, session.getVenue());
                command.setInt(7,session.getCapacity());

                command.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Delete session from Database by Id
    public void deleteSession(int id){
        try(Connection conn = getConnection()){
            conn.setAutoCommit(false);
            
            conn.prepareStatement("DELETE FROM Session WHERE sessionId=" + id).executeUpdate(); // Delete Session from Session table
            conn.prepareStatement("DELETE FROM Session_Attendee WHERE sessionId=" + id).executeUpdate(); // Delete Session from Session_Attendee table
            conn.prepareStatement("DELETE FROM Session_Presenter WHERE sessionId=" + id).executeUpdate(); // Delete Session from Session_Presenter table
            conn.prepareStatement("DELETE FROM Schedule_Session WHERE sessionId=" + id).executeUpdate(); // Delete Session from Schedule_Session table

            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Get all Session from database
    public List<Session> getAllSessions(){
        List<Session> list = new ArrayList<>();
        try(Connection conn = getConnection()){
            
            String commandGetAllSQL = "SELECT * FROM Session";
            // Execute get all sessions from Session table
            try(
                Statement create = conn.createStatement();
                ResultSet result = create.executeQuery(commandGetAllSQL);
            ){
                while(result.next()){
                    Session session = new Session(result.getInt("eventId"), result.getString("title"), result.getString("description"), LocalDateTime.parse(result.getString("startDateTime"),F),LocalDateTime.parse(result.getString("endDateTime"),F), result.getString("venue"), result.getInt("capacity"));
                    session.setSessionId(result.getInt("sessionId"));

                    list.add(session);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    // Get Session from Database by Id
    public Session getSessionById(int id){
        try(Connection conn = getConnection()){
            
            String commandGetByIdSQL = "SELECT * FROM Session WHERE sessionId=?";
            try(PreparedStatement command = conn.prepareStatement(commandGetByIdSQL)){
                command.setInt(1, id);

                ResultSet result = command.executeQuery();
                if(result.next()){
                    Session session = new Session(result.getInt("eventId"), result.getString("title"), result.getString("description"), LocalDateTime.parse(result.getString("startDateTime"),F),LocalDateTime.parse(result.getString("endDateTime"),F), result.getString("venue"), result.getInt("capacity"));
                    session.setSessionId(result.getInt("sessionId"));

                    return session;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // Get the Session by date
    public List<Session> getSessionByDate(LocalDateTime time){
        List<Session> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            String commandGetByDateSQL = "SELECT * FROM Session WHERE startDateTime LIKE ?";
            // Execute get session by schedule date time inside SQL
            try(PreparedStatement command = conn.prepareStatement(commandGetByDateSQL)){
                command.setString(1, time.toLocalDate().toString() + "%");

                ResultSet result = command.executeQuery();
                while(result.next()){
                    Session session = new Session(result.getInt("eventId"), result.getString("title"), result.getString("description"), LocalDateTime.parse(result.getString("startDateTime"),F),LocalDateTime.parse(result.getString("endDateTime"),F), result.getString("venue"), result.getInt("capacity"));
                    session.setSessionId(result.getInt("sessionId"));

                    list.add(session);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // Get all Sessions from Database by Name
    public List<Session> getSessionByPresenterName(String presenterName){
        List<Session> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            String commandGetByDateSQL = "SELECT s.* FROM Session s JOIN Session_Presenter sp ON s.sessionId = sp.sessionId JOIN Presenter p ON sp.presenterId = p.presenterId JOIN Person per ON p.presenterId = per.id WHERE per.fullName = ?";
            // Execute get session by schedule date time inside SQL
            try(PreparedStatement command = conn.prepareStatement(commandGetByDateSQL)){
                command.setString(1, presenterName);

                ResultSet result = command.executeQuery();
                while(result.next()){
                    Session session = new Session(result.getInt("eventId"), result.getString("title"), result.getString("description"), LocalDateTime.parse(result.getString("startDateTime"),F),LocalDateTime.parse(result.getString("endDateTime"),F), result.getString("venue"), result.getInt("capacity"));
                    session.setSessionId(result.getInt("sessionId"));

                    list.add(session);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // Get session for attendee
    List<Session> getSessionsForAttendee(Connection conn,int attendeeId) throws SQLException{
        List<Session> list = new ArrayList<>();

        String commandGetByDateSQL = "SELECT s.* FROM Session s JOIN Session_Attendee sat ON s.sessionId = sat.sessionId WHERE sat.attendeeId = ?";
        // Execute get session by schedule date time inside SQL
        try(PreparedStatement command = conn.prepareStatement(commandGetByDateSQL)){
            command.setInt(1, attendeeId);

            ResultSet result = command.executeQuery();
            while(result.next()){
                Session session = new Session(result.getInt("eventId"), result.getString("title"), result.getString("description"), LocalDateTime.parse(result.getString("startDateTime"),F),LocalDateTime.parse(result.getString("endDateTime"),F), result.getString("venue"), result.getInt("capacity"));
                session.setSessionId(result.getInt("sessionId"));

                list.add(session);
            }
        }
        return list;
    }

    // Check session capacity if there is enough or not
    public boolean checkSessionCapacity(int sessionId){
        try(Connection conn = getConnection()){

            String commandGetCurrentAttendeeSQL =  "SELECT COUNT(attendeeId) AS total FROM Session_Attendee WHERE sessionId=?";
            // Get number of attendees from a session
            try(PreparedStatement command = conn.prepareStatement(commandGetCurrentAttendeeSQL)){
                command.setInt(1, sessionId);
                ResultSet result = command.executeQuery();
                if(result.next()){
                    int currentAttendee = result.getInt("total");

                    String commandGetCapacitySQL = "SELECT capacity from Session WHERE sessionId=?";
                    // Get capacity of session
                    try(PreparedStatement command1 = conn.prepareStatement(commandGetCapacitySQL)){
                        command1.setInt(1, sessionId);
                        ResultSet result1 = command1.executeQuery();

                        if(result1.next()){
                            int capacity = result1.getInt("capacity");
                            return currentAttendee < capacity;
                        }
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }
}
