package model;
import java.time.*;
import java.util.*;
/*
 * @author Dao Tien Dung - s4088577
 * Attendee inherits Person base class 
 */

public class Attendee extends Person{
    private List<Session> registeredSession = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();

    public Attendee(String fullName, LocalDateTime dateOfBirth, String contactInformation){
        super(fullName,dateOfBirth,contactInformation);
    }

    // GETTER
    public List<Session> getRegisteredSession(){
        return registeredSession; 
    }

    public List<Ticket> getTickets(){
        return tickets;
    }

    
}
