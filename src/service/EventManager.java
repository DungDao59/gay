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
    void addAttendee(Attendee newAttendee) throws Exception;
    void updateAttendee(Attendee attendee) throws Exception;
    void deleteAttendee(int id) throws Exception;
    List<Attendee> getAllAttendees();
    Attendee getAttendeeById(int id);
    List<Attendee> getAttendeesByName(String name);

    // Register attendee to session
    void registerAttendeeToSession(int attendeeId, int sessionId);

    // Purchase ticket by an attendee
    void attendeePurchaseTicket(int attendeeId, int ticketId);

    
    /*
     * CRUD PRESENTER METHODS
     */
    void addPresenter(Presenter newPresenter) throws Exception;
    void updatePresenter(Presenter presenter) throws Exception;
    void deletePresenter(int id) throws Exception;
    List<Presenter> getAllPresenter();
    Presenter getPresenterById(int id);
    List<Presenter> getPresentersByName(String name);

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
    List<Event> getEventsByPresenter(int presentId);

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
    List<Ticket> getTicketsSortedByPrice();

    // Update ticket status
    void updateTicketStatus(int ticketId, StatusType status);


    /*
     * CRUD SCHEDULE METHODS
     */
    boolean checkScheduleConflictForPresenter(int presenterId, LocalDateTime time);
    boolean checkScheduleConflictForAttendee(int attendeeId, LocalDateTime time);
    boolean checkScheduleConflictForVenue(String venue, LocalDateTime time);

    List <Session> getScheduleForPresenter(int presenterId);
    List <Session> getScheduleForAttendee(int attendeeId);


    /*
     * CRUD REPORT METHODS
     */
    List<Event> getEventsSortedByDate();
    List<Event> getEventsByType(EventType type);
    List<Ticket> getTicketsByType(TicketType type);
    void exportReport(String filename, List<?> data);
}
