package dao;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.Presenter;
import model.PresenterRole;
import util.Database;

/*
 * Dao Tien Dung - s4088577
 * Presenter CRUD files
 */

public class PresenterDao {
    // Set default date format for every date
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Get connection to the database
    private Connection getConnection() throws SQLException {
        return Database.getConnection();
    }

    // Add presenter to database
    
    public void addPresenter(Presenter newPresenter){
        try(Connection conn = getConnection()){
            conn.setAutoCommit(false);

            String commandAddNewPresenterSQL = "INSERT INTO Person(fullName,dateOfBirth,contactInfo) VALUES (?,?,?)";
            // Excute adding new presenter to the Person table database and get the current id key
            try(PreparedStatement command = conn.prepareStatement(commandAddNewPresenterSQL,Statement.RETURN_GENERATED_KEYS)){
                command.setString(1, newPresenter.getFullName());
                command.setString(2, newPresenter.getDateOfBirth() != null ? newPresenter.getDateOfBirth().format(F) : null);
                command.setString(3, newPresenter.getContactInfomation());

                command.executeUpdate();

                ResultSet result = command.getGeneratedKeys();
                if(result.next())newPresenter.setId(result.getInt(1));
            }
            
            String commandAddPresenterToTableSQL = "INSERT INTO Presenter(presenterId,role) VALUES (?,?)";
            // Adding new presenter to Presenter table database
            try(PreparedStatement command = conn.prepareStatement(commandAddPresenterToTableSQL)){
                command.setInt(1, newPresenter.getId());
                command.setString(2, newPresenter.getRole().name());

                command.executeUpdate();
            }

            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Udpdate a presenter in the database
    
    public void updatePresenter(Presenter presenter){
        try(Connection conn = getConnection()){
            conn.setAutoCommit(false);
            
            String commandUpdatePresenterSQL = "UPDATE Person SET fullName=?, dateOfBirth=?, contactInfo=? WHERE id=?";
            // Updating a presenter info with current id
            try(PreparedStatement command = conn.prepareStatement(commandUpdatePresenterSQL)){
                command.setString(1, presenter.getFullName());
                command.setString(2, presenter.getDateOfBirth() != null ? presenter.getDateOfBirth().format(F): null);
                command.setString(3, presenter.getContactInfomation());
                command.setInt(4, presenter.getId());

                command.executeUpdate();
            }

            String commandUpdatePresenterRoleSQL = "UPDATE Presenter SET role=? WHERE presenterId=?";
            // Updating presenter role into Presenter table
            try(PreparedStatement command = conn.prepareStatement(commandUpdatePresenterRoleSQL)){
                command.setString(1, presenter.getRole().name());
                command.setInt(2, presenter.getId());

                command.executeUpdate();
            }

            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }
    };

    // Delete Presenter inside Database
    
    public void deletePresenter(int id){
        try(Connection conn = getConnection()){
            conn.setAutoCommit(false);
            
            String commandDeletePresenterSQL = "DELETE FROM Presenter WHERE presenterId=?";
            String commandDeletePersonSQL = "DELETE FROM Person WHERE id=?";
            // Delete Presenter by Id in both Person and Presenter table
            try(PreparedStatement command1 = conn.prepareStatement(commandDeletePersonSQL);
                PreparedStatement command2 = conn.prepareStatement(commandDeletePresenterSQL)){
                    command1.setInt(1, id);
                    command2.setInt(1, id);

                    command1.executeUpdate();
                    command2.executeUpdate();
                }

                conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Get all Presnters inside Database
    public List<Presenter> getAllPresenter(){
        List<Presenter> list = new ArrayList<>();
        try(Connection conn = getConnection()){
            
            String commandGetAllSQL = "SELECT p.*, pr.role FROM Person p JOIN Presenter pr ON p.id = pr.presenterId";
            // Join 2 table to get all the attributes needed
            try(Statement command = conn.createStatement();
                ResultSet result = command.executeQuery(commandGetAllSQL)){
                    while(result.next()){
                        Presenter newPresenter = new Presenter(result.getString("fullName"), result.getString("dateOfBirth") != null ? LocalDateTime.parse(result.getString("dateOfBirth"),F) : null, result.getString("contactInfo"), PresenterRole.valueOf(result.getString("role")));
                        newPresenter.setId(result.getInt("id"));
                        list.add(newPresenter);
                    }

                }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    // Get presenter by Id inside Database
    public Presenter getPresenterById(int id){
        try(Connection conn = getConnection()){

            String commandGetPresenterId = "SELECT p.*, pr.role FROM Person p join Presenter pr ON p.id = pr.presenterId WHERE p.id=?";
            // Execute SQL command to get all the info of the presenter based on Person and Presenter table.
            try(PreparedStatement command = conn.prepareStatement(commandGetPresenterId)){
                command.setInt(1, id);
                
                ResultSet result = command.executeQuery();
                // If there exist the role beneath the header
                if(result.next()){
                    Presenter newPresenter = new Presenter(result.getString("fullName"), result.getString("dateOfBirth") != null ? LocalDateTime.parse(result.getString("dateOfBirth"),F) : null, result.getString("contactInfo"), PresenterRole.valueOf(result.getString("role")));
                    newPresenter.setId(result.getInt("id"));

                    return newPresenter;
                }
                
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    // Get all presenters from Database by name
    public List<Presenter> getPresentersByName(String name){
        List<Presenter> list = new ArrayList<>();
        try(Connection conn = getConnection()){

            String commandGetPresenterId = "SELECT p.*, pr.role FROM Person p join Presenter pr ON p.id = pr.presenterId WHERE p.fullName LIKE ?";
            // Execute SQL command to get all the info of the presenter based on Person and Presenter table.
            try(PreparedStatement command = conn.prepareStatement(commandGetPresenterId)){
                command.setString(1, "%" + name + "%");
                
                ResultSet result = command.executeQuery();
                // If there exist the role beneath the header
                while(result.next()){
                    Presenter newPresenter = new Presenter(result.getString("fullName"), result.getString("dateOfBirth") != null ? LocalDateTime.parse(result.getString("dateOfBirth"),F) : null, result.getString("contactInfo"), PresenterRole.valueOf(result.getString("role")));
                    newPresenter.setId(result.getInt("id"));

                    list.add(newPresenter);
                }
                
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return list;
    }

    // Register a presenter to a session
    public void registerPresenterToSession(int presenterId, int sessionId){
        try(Connection conn = getConnection()){

            String commandRegistrationSQL = "INSERT INTO Session_Presenter (sessionId, presenterId) VALUES (?,?)";
            // Execute command for Session_Presnenter table
            try(PreparedStatement command = conn.prepareStatement(commandRegistrationSQL)){
                command.setInt(1, sessionId);
                command.setInt(2, presenterId);

                command.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Removing a presenter from a session
    
    public void removePresenterFromSession(int presenterId, int sessionId){
        try(Connection conn = getConnection()){
            
            String commandRemovePresenterSessionSQL = "DELETE FROM Session_Presenter WHERE sessionId=? AND presenterId=?";
            // Execute remove command SessionId and PresenterId  from Session_Presenter table
            try(PreparedStatement command = conn.prepareStatement(commandRemovePresenterSessionSQL)){
                command.setInt(1, sessionId);
                command.setInt(2, presenterId);

                command.executeUpdate();

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    };

    //Check if current presenter is inside session or not
    public boolean isPresenterRegistered(int presenterId, int sessionId) {
        try (Connection conn = getConnection()){

            String commandSelectAttendeeSessionSQL = "SELECT 1 FROM Session_Presenter WHERE presenterId = ? AND sessionId = ? LIMIT 1";
            // Execute command for selecting attendee if is currently joining a sesison
            try(PreparedStatement ps = conn.prepareStatement(commandSelectAttendeeSessionSQL)){

                ps.setInt(1, presenterId);
                ps.setInt(2, sessionId);

                ResultSet rs = ps.executeQuery();
                return rs.next();  // true if a row exists
            }
        }catch (SQLException e) {
            return false;
        }
    }

}
