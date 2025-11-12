package model;
import java.util.*;
import java.time.*;
/*
 * @author Dao Tien Dung - s4088577
 * Session class for session
 */

public class Session {
    private int sessionId;
    private String title;
    private String description;
    private LocalDateTime scheduleDateTime;
    private String venue;
    private int capacity;
    private List<Attendee> attendees;
    private List<Presenter> presenters;

    public Session(String title, String description, LocalDateTime scheduleDateTime, String venue, int capacity){
        this.title = title;
        this.description = description;
        this.scheduleDateTime = scheduleDateTime;
        this.venue = venue;
        this.capacity = capacity;
    }

    // GETTER
    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public LocalDateTime getScheduleDateTime(){
        return scheduleDateTime;
    }
    
    public String getVenue(){
        return venue;
    }

    public int getCapacity(){
        return capacity;
    }

    public List<Attendee> returnAttendees(){
        return attendees;
    }

    public List<Presenter> returnPresenters(){
        return presenters;
    }
}
