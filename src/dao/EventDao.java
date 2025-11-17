package dao;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.Event;
import model.EventType;
import util.Database;

/*
 * Dao Tien Dung - s4088577
 * Event CRUD files
 */

public class EventDao {
    // Set default date format for every date
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Get connection to the database
    private Connection getConnection() throws SQLException {
        return Database.getConnection();
    }

    /*
     * EVENT CRUD METHODS
     */

    // Add Event to Database
    public void addEvent(Event newEvent){
        try(Connection conn = getConnection()){
            
            String commandAddEventSQL = "INSERT INTO Event(name,type,location,startDate,endDate) VALUES (?,?,?,?,?)";
            // Execute adding event command sql
            try(PreparedStatement command = conn.prepareStatement(commandAddEventSQL)){
                command.setString(1, newEvent.getName());
                command.setString(2, newEvent.getType().name());
                command.setString(3, newEvent.getLocation());
                command.setString(4, newEvent.getStartDate().toString());
                command.setString(5, newEvent.getEndDate().toString());

                command.executeUpdate();
                System.out.println("[Success] Add event to database successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Update an event from a database
    public void updateEvent(Event event){
        try(Connection conn = getConnection()){
            
            String commandEventSQL = "UPDATE Event set name=?, type=?, location= ?, startDate=? , endDate=? WHERE eventId=?";
            //Execute updating an event inside database by Id
            try(PreparedStatement command = conn.prepareStatement(commandEventSQL)){
                command.setString(1, event.getName());
                command.setString(2, event.getType().name());
                command.setString(3, event.getLocation());
                command.setString(4, event.getStartDate().toString());
                command.setString(5, event.getEndDate().toString());
                command.setInt(6, event.getEventId());

                command.executeUpdate();

                System.out.println("[Success] Update event successfully");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Delete an event from database by Id
    public void deleteEvent(int id){
        try(Connection conn = getConnection()){
            
            String commandDeleteEventSQL = "DELETE FROM Event WHERE eventId=?";
            // Execute deleting an event inside database by Id
            try(PreparedStatement command = conn.prepareStatement(commandDeleteEventSQL)){
                command.setInt(1, id);

                command.executeUpdate();
                System.out.println("[Success] Delete event successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Get all event inside database
    public List<Event> getAllEvents(){
        List<Event> list = new ArrayList<>();
        
        try(Connection conn = getConnection()){

            String commandGetAllSQL = "SELECT * FROM Event";
            // Execute get all event table from database
            try(Statement create = conn.createStatement();
                ResultSet result = create.executeQuery(commandGetAllSQL)){
                    while(result.next()){
                        Event e = new Event(result.getString("name"), EventType.valueOf(result.getString("type")), result.getString("location"), LocalDateTime.parse(result.getString("startDate"),F), LocalDateTime.parse(result.getString("endDate"),F));
                        e.setEventId(result.getInt("eventId"));

                        list.add(e);
                    }

                    System.out.println("[Success] Get all events successfully");
                }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    // Get event by Id from database
    public Event getEventById(int id){
        try(Connection conn = getConnection()){

            String commandGetEventById = "SELECT * FROM Event WHERE eventId=?";
            //Execute get event by id from database
            try(PreparedStatement command = conn.prepareStatement(commandGetEventById)){
                command.setInt(1, id);
           
                ResultSet result = command.executeQuery();
                if(result.next()){
                    Event e = new Event(result.getString("name"), EventType.valueOf(result.getString("type")), result.getString("location"), LocalDateTime.parse(result.getString("startDate"),F), LocalDateTime.parse(result.getString("endDate"),F));
                    e.setEventId(result.getInt("eventId"));

                    return e;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    // Get all events from database by presenter
    public List<Event> getEventsByPresenter(int presenterId){
        List<Event> list = new ArrayList<>();
        
        try(Connection conn = getConnection()){

            String commandGetAllSQL = "SELECT DISTINCT e.* FROM Event e JOIN Session s ON e.eventId = s.sessionId JOIN Session_Presenter sp ON s.sessionId = sp.sessionId WHERE sp.presenterId = ?";
            // Execute get all event table from database by Presenter
            try(PreparedStatement command = conn.prepareStatement(commandGetAllSQL)){
                command.setInt(1, presenterId);
           
                ResultSet result = command.executeQuery();
                while(result.next()){
                    Event e = new Event(result.getString("name"), EventType.valueOf(result.getString("type")), result.getString("location"), LocalDateTime.parse(result.getString("startDate"),F), LocalDateTime.parse(result.getString("endDate"),F));
                    e.setEventId(result.getInt("eventId"));

                    list.add(e);
                }
                System.out.println("[Success] Get all presenters by name successfully");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return list;
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
}
