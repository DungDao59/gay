package dao;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.StatusType;
import model.Ticket;
import model.TicketType;
import util.Database;

/*
 * Dao Tien Dung - s4088577
 * Ticket CRUD file
 */

public class TicketDao {
    // Set default date format for every date
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Get connection to the database
    private Connection getConnection() throws SQLException {
        return Database.getConnection();
    }

    /*
     * TICKET CRUD METHODS
     */

    // Add tickets to database
    public void addTicket(Ticket newTicket){
        try(Connection conn = getConnection()){
            
            String commandAddTicketSQL = "INSERT INTO Ticket(type,price, status, attendeeId, eventId) VALUES (?,?,?,?,?)";
            // Execute add ticket command to Database
            try(PreparedStatement command = conn.prepareStatement(commandAddTicketSQL)){
                command.setString(1, newTicket.getType().name());
                command.setDouble(2, newTicket.getPrice());
                command.setString(3, newTicket.getStatus().name());
                command.setInt(4, newTicket.getAttendee());
                command.setInt(5, newTicket.getEvent());

                command.executeUpdate();
                System.out.println("[Success] Add ticket to Database successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Update a ticket inside database
    public void updateTicket(Ticket ticket){
        try(Connection conn = getConnection()){
            
            String commandUpdateTicketSQL = "UPDATE Ticket SET type=?, price=?, status=?, attendeeId=?, eventId=? WHERE ticketId=?";
            // Execute update ticket command from database
            try(PreparedStatement command = conn.prepareStatement(commandUpdateTicketSQL)){
                command.setString(1, ticket.getType().name());
                command.setDouble(2, ticket.getPrice());
                command.setString(3, ticket.getStatus().name());
                command.setInt(4, ticket.getAttendee());
                command.setInt(5, ticket.getEvent());
                command.setInt(6,ticket.getTicketId());

                command.executeUpdate();
                System.out.println("[Success] Update ticket successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Delete a Ticket from Database by Id
    public void deleteTicket(int id){
        try(Connection conn = getConnection()){
            
            String commandDeleteTicketSQL = "DELETE FROM Ticket WHERE ticketId=?";
            try(PreparedStatement command = conn.prepareStatement(commandDeleteTicketSQL)){
                command.setInt(1, id);

                command.executeUpdate();
                System.out.println("[Success] Delete task successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Get all Ticket from Database
    public List<Ticket> getAllTickets(){
        List<Ticket> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            String commandGetAllSQL = "SELECT * FROM Ticket";
            try(Statement create = conn.createStatement();
                ResultSet result = create.executeQuery(commandGetAllSQL)){
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

                    System.out.println("[Success] Get all ticket successfully");
                }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // Get Ticket from Database by Id
    public Ticket getTicketById(int id){
        try(Connection conn = getConnection()){
            
            String commandGetById = "SELECT * FROM Ticket WHERE ticketId=?";
            // Execute get ticket by ID command from database
            try(PreparedStatement command = conn.prepareStatement(commandGetById)){
                command.setInt(1, id);

                ResultSet result = command.executeQuery();

                if(result.next()){
                    Ticket t = new Ticket(
                        TicketType.valueOf(result.getString("type")),
                        result.getDouble("price"), 
                        StatusType.valueOf(result.getString("status")),
                        result.getInt("attendeeId"), 
                        result.getInt("eventId")
                    );
                    t.setTicketId(result.getInt("ticketId"));

                    System.out.println("[Success] Get ticket by id successfully");
                    return t;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // Get all tickets sorted by Price from database
    public List<Ticket> getTicketsSortedByPrice(){
        List<Ticket> list = new ArrayList<>();
        try(Connection conn = getConnection()){
            
            String commandGetByPrice = "SELECT * FROM Ticket ORDER BY price ASC";
            // Execute get all ticket by price command from database
            try(PreparedStatement command = conn.prepareStatement(commandGetByPrice)){

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
                System.out.println("[Success] Get all ticket sorted by price successfully");

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // Update ticket status from database
    public void updateTicketStatus(int ticketId, StatusType status){
        try(Connection conn = getConnection()){
            
            String commandUpdateTicketStatus = "UPDATE Ticket SET status=? WHERE ticketId=?";
            // Execute udapte ticket status command from database
            try(PreparedStatement command = conn.prepareStatement(commandUpdateTicketStatus)){
                command.setString(1, status.name());
                command.setInt(2, ticketId);

                command.executeUpdate();
                System.out.println("[Success] Update ticket status successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
