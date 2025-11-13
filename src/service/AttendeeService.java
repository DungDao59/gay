package service;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.*;

import model.Attendee;
import util.Database;

/*
 * Dao Tien Dung - s4088577
 * Attendee CRUD files
 */

public class AttendeeService {
    // Set default date format for every date
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Get connection to the database
    private Connection getConnection() throws SQLException {
        return Database.getConnection();
    }

    /*
     * ATTENDEE CRUD METHODS
     */

    // Add Attendee to Database
    public void addAttendee(Attendee newAttendee){
        try (Connection conn = getConnection()){
            conn.setAutoCommit(false);
            
            String personSQL = "INSERT INTO Person(fullName, dateOfBirth, contactInfo) VALUES (?, ?, ?);";
            //Execute add Attendee info to Person table and take id number
            try(PreparedStatement command = conn.prepareStatement(personSQL,Statement.RETURN_GENERATED_KEYS)){
                command.setString(1, newAttendee.getFullName());
                command.setString(2, newAttendee.getDateOfBirth() != null ? newAttendee.getDateOfBirth().format(F): null);
                command.setString(3, newAttendee.getContactInformation());

                command.executeUpdate();

                ResultSet result = command.getGeneratedKeys();
                if(result.next()){
                    int id = result.getInt(1);
                    newAttendee.setId(id);
                }
            }

            //Execute add Attendee id to Attendee table
            String attendeeSQL = "INSERT INTO Attendee(attendeeId) VALUES (?)";
            try (PreparedStatement command = conn.prepareStatement(attendeeSQL)){
                command.setInt(1, newAttendee.getId());
                command.executeUpdate();
            }

            conn.commit();
            System.out.println("[Success] Attendee added succesfully");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Update an Attendee inside Database
    
    public void updateAttendee(Attendee attendee){
        try(Connection conn = getConnection()){

            String commandSQL = "UPDATE Person SET fullname=?, dateOfBirth=?, contactInfo=? WHERE id = ?";
            //Execute update command sql
            try(PreparedStatement command = conn.prepareStatement(commandSQL)){
                command.setString(1, attendee.getFullName());
                command.setString(2, attendee.getDateOfBirth() != null ? attendee.getDateOfBirth().format(F) : null);
                command.setString(3, attendee.getContactInformation());
                command.setInt(4, attendee.getId());

                command.executeUpdate();
                
                System.out.println("[Success] Update Attendee information successfully.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Delete an Attendee inside Database
    
    public void deleteAttendee(int id){
        try(Connection conn = getConnection()){
            conn.setAutoCommit(false);

            //Deleting from Attendee table
            String commandDeleteAttendeeSQL = "DELETE FROM Attendee WHERE attendeeId=?";
            try(PreparedStatement command = conn.prepareStatement(commandDeleteAttendeeSQL)){
                command.setInt(1, id);
                command.executeUpdate();
            }

            //Deleting from Person table
            String commandDeletePersonSQL = "DELETE FROM Person WHERE id=?";
            try(PreparedStatement command = conn.prepareStatement(commandDeletePersonSQL)){
                command.setInt(1, id);
                command.executeUpdate();
            }

            conn.commit();
            System.out.println("[Success] Delete Attendee successfully");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Get all attendees from database
    
    public List<Attendee> getAllAttendees(){
        List<Attendee> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            //Get all the current attendee inside the database
            String commandGetAllSQL = "SELECT p.id, p.fullName, p.dateOfBirth, p.contactInfo FROM Person p JOIN Attendee a ON p.id = a.attendeeId";
            try(Statement command = conn.createStatement();
                ResultSet result = command.executeQuery(commandGetAllSQL)){
                    // Loop through each attendee
                    while(result.next()){
                        Attendee a = new Attendee(result.getString("fullName"), result.getString("dateOfBirth") != null ? LocalDateTime.parse(result.getString("dateOfBirth"),F) : null, result.getString("contactInfo"));
                        a.setId(result.getInt("id"));
                        list.add(a);
                    }
                }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // Get attendee by id
    
    public Attendee getAttendeeById(int id){
        try(Connection conn = getConnection()){

            String commandGetAttendeeById = "SELECT p.* FROM Person p JOIN Attendee a ON p.id = a.attendeeId WHERE p.id=?";
            //Execute get Attendee by id command database
            try(PreparedStatement command = conn.prepareStatement(commandGetAttendeeById)){
                command.setInt(1, id);
                ResultSet result = command.executeQuery();

                //Check if there is a next row for result
                if(result.next()){
                    Attendee a = new Attendee(result.getString("fullName"), result.getString("dateOfBirth") != null ? LocalDateTime.parse(result.getString("dateOfBirth"),F) : null, result.getString("contactInfo"));
                    a.setId(result.getInt("id"));

                    System.out.println("[Success] Get " + result.getString("fullName") + " attendee successfully");
                    return a;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    // Create a registration for attendee to session
    
    public void registerAttendeeToSession(int attendeeId, int sessionId){
        try(Connection conn = getConnection()){

            String commandRegistrationSQL = "INSERT INTO Session_Attendee(sessionId, attendeeId) VALUES (?,?)";
            // Execute a registration for attendee into session
            try(PreparedStatement command = conn.prepareStatement(commandRegistrationSQL)){
                command.setInt(1, sessionId);
                command.setInt(2, attendeeId);

                command.executeUpdate();
                System.out.println("[Success] Register attendee to session successfully.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Attendee purchase ticket
    
    public void attendeePurchaseTicket(int attendeeId, int ticketId){
        try(Connection conn = getConnection()){

            String commandPurchaseTicket = "UPDATE Ticket SET attendeeId=? WHERE ticketId=?";
            // Execute update purchase ticket for attendee command
            try(PreparedStatement command = conn.prepareStatement(commandPurchaseTicket)){
                command.setInt(1, attendeeId);
                command.setInt(2, ticketId);

                command.executeUpdate();
                System.out.println("[Success] Update ticket for attendee successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
