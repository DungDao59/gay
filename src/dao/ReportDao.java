package dao;

import java.sql.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import model.Event;
import model.EventType;
import model.StatusType;
import model.Ticket;
import model.TicketType;
import util.Database;

/*
 * Dao Tien Dung - s4088577
 * Report CRUD file
 */

public class ReportDao {
    // Set default date format for every date
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Get connection to the database
    private Connection getConnection() throws SQLException {
        return Database.getConnection();
    }

    // Get event by sorted date
    public List<Event> getEventSortedByDate(){
        List<Event> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            String commandGetByDateSQL = "SELECT * FROM Event ORDER BY startDate ASC";
            // Execute get event by start date time inside SQL
            try(PreparedStatement command = conn.prepareStatement(commandGetByDateSQL)){

                ResultSet result = command.executeQuery();
                while(result.next()){
                    Event event = new Event(
                        result.getString("name"),
                        EventType.valueOf(result.getString("type")),
                        result.getString("location"),
                        LocalDateTime.parse(result.getString("startDate"), F),
                        LocalDateTime.parse(result.getString("endDate"), F)
                    );

                    event.setEventId(result.getInt("eventId"));
                    list.add(event);
                }

                System.out.println("[Success] Get all event by sorted date successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Get event by type
    public List<Event> getEventByType(EventType type){
        List<Event> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            String commandGetByDateSQL = "SELECT * FROM Event WHERE type = ?";
            // Execute get event by type command inside SQL
            try(PreparedStatement command = conn.prepareStatement(commandGetByDateSQL)){
                command.setString(1, type.name());

                ResultSet result = command.executeQuery();
                while(result.next()){
                    Event event = new Event(
                        result.getString("name"),
                        EventType.valueOf(result.getString("type")),
                        result.getString("location"),
                        LocalDateTime.parse(result.getString("startDate"), F),
                        LocalDateTime.parse(result.getString("endDate"), F)
                    );

                    event.setEventId(result.getInt("eventId"));
                    list.add(event);
                }

                System.out.println("[Success] Get all event by type successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // Get Ticket by type 
    public List<Ticket> getTicketByType(TicketType type){
        List<Ticket> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            String commandGetByDateSQL = "SELECT * FROM Ticket WHERE type = ?";
            // Execute get ticket by type command inside SQL
            try(PreparedStatement command = conn.prepareStatement(commandGetByDateSQL)){
                command.setString(1, type.name());

                ResultSet result = command.executeQuery();
                while(result.next()){
                    Ticket t = new Ticket(
                        TicketType.valueOf(result.getString("type")),
                            result.getDouble("price"), 
                            StatusType.valueOf(result.getString("status")),
                            result.getInt("attendeeId"), 
                            result.getInt("eventId")
                        );
                        t.setTicketId(result.getInt("ticketId"));
                        
                        list.add(t);
                }

                System.out.println("[Success] Get all tickets by type successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

}
