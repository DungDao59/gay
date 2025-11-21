package dao;

import java.util.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import util.Database;
import model.Session;


/*
 * Dao Tien Dung - s4088577
 * Schedule CRUD file
 */

public class ScheduleDao {
    // Set default date format for every date
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Get connection to the database
    private Connection getConnection() throws SQLException {
        return Database.getConnection();
    }

    private boolean isTimeOverLap(LocalDateTime start1, LocalDateTime start2, LocalDateTime end1, LocalDateTime end2){
        if(start1 == null || start2 == null || end1 == null || end2 == null){
            return false;
        }
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    /*
     * SCHEDULE CHECK METHODS
     */
    public boolean checkScheduleConflictForPresenter(int presenterId, LocalDateTime newStart, LocalDateTime newEnd){
        try(Connection conn = getConnection()){

            String commandGetScheduleDateTimeSQL = "SELECT s.startDateTime, s.endDateTime FROM Session s JOIN Session_Presenter sp on s.sessionId = sp.sessionId WHERE sp.presenterId= ?";
            // Execute get scheduleDateTime command for Presenter from SQL 
            try(PreparedStatement command = conn.prepareStatement(commandGetScheduleDateTimeSQL)){
                command.setInt(1, presenterId);
                
                ResultSet result = command.executeQuery();
                while(result.next()){
                    LocalDateTime startDateTime = LocalDateTime.parse(result.getString("startDateTime"));
                    LocalDateTime endDateTime = LocalDateTime.parse(result.getString("endDateTime"));

                    if(isTimeOverLap(startDateTime, newStart, endDateTime, newEnd)) return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Check schedule for attendee
    public boolean checkScheduleConflictForAttendee(int attendeeId, LocalDateTime newStart, LocalDateTime newEnd){
        try(Connection conn = getConnection()){

            String commandGetScheduleDateTimeSQL = "SELECT s.startDateTime, s.endDateTime FROM Session s JOIN Session_Attendee sa on s.sessionId = sa.sessionId WHERE sa.attendeeId= ?";
            // Execute get scheduleDateTime command for Attendee from SQL 
            try(PreparedStatement command = conn.prepareStatement(commandGetScheduleDateTimeSQL)){
                command.setInt(1, attendeeId);
                
                ResultSet result = command.executeQuery();
                while(result.next()){
                    LocalDateTime startDateTime = LocalDateTime.parse(result.getString("startDateTime"));
                    LocalDateTime endDateTime = LocalDateTime.parse(result.getString("endDateTime"));

                    if(isTimeOverLap(startDateTime, newStart, endDateTime, newEnd)) return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Check confliction for venue
    public boolean checkScheduleConflictForVenue(String venue, LocalDateTime newStart, LocalDateTime newEnd){
        try(Connection conn = getConnection()){

            String commandGetScheduleDateTimeSQL = "SELECT startDateTime, endDateTime FROM Session WHERE venue = ?";
            // Execute get scheduleDateTie command for venue from SQL 
            try(PreparedStatement command = conn.prepareStatement(commandGetScheduleDateTimeSQL)){
                command.setString(1, venue);
                
                ResultSet result = command.executeQuery();
                while(result.next()){
                    LocalDateTime startDateTime = LocalDateTime.parse(result.getString("startDateTime"));
                    LocalDateTime endDateTime = LocalDateTime.parse(result.getString("endDateTime"));

                    if(isTimeOverLap(startDateTime, newStart, endDateTime, newEnd)) return true;               
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Get schedule for attendee
    public List<Session> getScheduleForAttendee(int attendeeId){
        List<Session> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            String commandGetScheduleDateTimeSQL = "SELECT s.* FROM Session s JOIN Session_Attendee sa ON s.sessionId = sa.sessionId WHERE sa.attendeeId=?";
            // Execute get scheduleDateTie command for venue from SQL 
            try(PreparedStatement command = conn.prepareStatement(commandGetScheduleDateTimeSQL)){
                command.setInt(1, attendeeId);
                
                ResultSet result = command.executeQuery();
                while(result.next()) {
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

    //Get schedule for presenter
    public List<Session> getScheduleForPresenter(int presentId){
        List<Session> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            String commandGetScheduleDateTimeSQL = "SELECT s.* FROM Session s JOIN Session_Presenter sp ON s.sessionId = sp.sessionId WHERE sp.presenterId=?";
            // Execute get scheduleDateTie command for venue from SQL 
            try(PreparedStatement command = conn.prepareStatement(commandGetScheduleDateTimeSQL)){
                command.setInt(1, presentId);
                
                ResultSet result = command.executeQuery();
                while(result.next()) {
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
}
