package model;
import java.time.*;
import java.util.*;
/*
 * @author Dao Tien Dung - s4088577
 */

public class Attendee extends Person{
    private List<Session> registeredSession;
    private List<Ticket> tickets;

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
