package model;
import java.util.*;
import java.time.*;
/*
 * @author Dao Tien Dung - s4088577
 * Event class for event
 */
public class Event {
    private int eventId;
    private String name;
    private EventType type;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Session> sessions;

    public Event(String name, EventType type, String location, LocalDateTime startDate, LocalDateTime endDate){
        this.name = name;
        this.type = type;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // GETTER
    public int getEventId(){
        return eventId;
    }

    public String getName(){
        return name;
    }

    public EventType getType(){
        return type;
    }

    public String getLocation(){
        return location;
    }

    public LocalDateTime getStartDate(){
        return startDate;
    }

    public LocalDateTime getEndDate(){
        return endDate;
    }

    public List<Session> getSessions(){
        return sessions;
    }

    // SETTER
    public void setEventId(int id){
        eventId = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEventType(EventType type){
        this.type = type;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setStartDate(LocalDateTime newStartDate){
        this.startDate = newStartDate;
    }

    public void setEndDate(LocalDateTime newEndDate){
        this.endDate = newEndDate;
    }
}
