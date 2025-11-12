package model;
import java.time.*;
import java.util.*;
/*
 * @author Dao Tien Dung - s4088577
 * Attendee inherits Person base class 
 */

public class Attendee extends Person{
    private int attendeeId;
    private List<Session> registeredSession;
    private List<Ticket> tickets;

    public Attendee(String fullName, LocalDateTime dateOfBirth, String contactInformation){
        super(fullName,dateOfBirth,contactInformation);
    }

    // GETTER
    public int getAttendeeId(){
        return attendeeId;
    }

    public List<Session> getRegisteredSession(){
        return registeredSession; 
    }

    public List<Ticket> getTickets(){
        return tickets;
    }

    // SETTER 
    public void setAttendeeId(int id){
        attendeeId = id;
    }
    
}
