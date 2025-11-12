package service;
import java.util.*;
import java.time.*;
import model.Attendee;
import model.Event;
import model.EventType;
import model.Presenter;
import model.Session;
import model.Ticket;
import model.TicketType;
import model.StatusType;

/*
 * @author Dao Tien Dung - s4088577
 * Interface for Event Manager implementation
 */

public interface EventManager {
    /*
     * CRUD ATTENDEE METHODS
     */
    void addAttendee(Attendee newAttendee);
    void updateAttendee(Attendee attendee);
    void deleteAttendee(int id);
    List<Attendee> getAllAttendees();
    Attendee getAttendeeById(int id);

    // Register attendee to session
    void registerAttendeeToSession(int attendeeId, int sessionId);

    // Purchase ticket by an attendee
    void attendeePurchaseTicket(int attendeeId, int ticketId);

    
    /*
     * CRUD PRESENTER METHODS
     */
    void addPresenter(Presenter newPresenter);
    void updatePresenter(Presenter presenter);
    void deletePresenter(int id);
    List<Presenter> getAllPresenter();
    Presenter getPresenterById(int id);

    // Register presenter to session
    void registerPresenterToSession(int presenterId, int sessionId);

    // Remove presenter from a session
    void removePresenterFromSession(int presenterId, int sessionId);


    /*
     * CRUD EVENT METHODS
     */
    void addEvent(Event newEvent);
    void updateEvent(Event event);
    void deleteEvent(int id);
    List<Event> getAllEvents();
    Event getEventById(int id);


    /*
     * CRUD SESSION METHODS
     */
    void addSession(Session newSession);
    void updateSession(Session session);
    void deleteSession(int id);
    List<Session> getAllSessions();
    Session getSessionById(int id);
    List<Session> getSessionsByDate(LocalDateTime time);
    List<Session> getSessionsByPresenterName(String presenterName);

    // Check session capacity
    boolean checkSessionCapacity(int id);


    /*
     * CRUD TICKET METHODS
     */
    void addTicket(Ticket newTicket);
    void updateTicket(Ticket ticket);
    void deleteTicket(int id);
    List<Ticket> getAllTickets();
    Ticket getTicketById(int id);

    // Update ticket status
    void updateTicketStatus(int ticketId, StatusType status);


    /*
     * CRUD SCHEDULE METHODS
     */
    // Check presenter if there is conflict for another session or not
    boolean checkScheduleConflictForPresenter(int presenterId, LocalDateTime time);

    List <Session> getScheduleForPresenter(int presenterId);
    List <Session> getScheduleForAttendee(int attendeeId);


    /*
     * CRUD REPORT METHODS
     */
    List<Event> getEventSortedByDate();
    List<Event> getEventsByType(EventType type);
    List<Ticket> getTicketsByCategory(TicketType type);
}
