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
     * CRUD ATTENDEE SERVICE METHODS
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
    boolean attendeePurchaseTicket(int attendeeId, int eventId, TicketType type);


    /*
     * CRUD PRESENTER SERVICE METHODS
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
     * CRUD EVENT SERVICE METHODS
     */
    void addEvent(Event newEvent) throws Exception;
    void updateEvent(Event event) throws Exception;
    void deleteEvent(int id) throws Exception;
    List<Event> getAllEvents();
    Event getEventById(int id);
    List<Event> getEventsByPresenter(int presentId);
    List<Event> getEventsSortedByDate();
    List<Event> getEventsByType(EventType type);



    /*
     * CRUD SESSION SERVICE METHODS
     */
    void addSession(Session newSession) throws Exception;
    void updateSession(Session session) throws Exception;
    void deleteSession(int id) throws Exception;
    List<Session> getAllSessions();
    Session getSessionById(int id);
    List<Session> getSessionsByDate(LocalDateTime time);
    List<Session> getSessionsByPresenterName(String presenterName);

    // Check session capacity
    boolean checkSessionCapacity(int id);


    /*
     * CRUD TICKET SERVICE METHODS
     */
    void addTicket(Ticket newTicket) throws Exception;
    void updateTicket(Ticket ticket) throws Exception;
    void deleteTicket(int id);
    List<Ticket> getAllTickets();
    Ticket getTicketById(int id);
    List<Ticket> getTicketsSortedByPrice();
    List<Ticket> getTicketsByType(TicketType type);

    // Update ticket status
    void updateTicketStatus(int ticketId, StatusType status);


    /*
     * CRUD SCHEDULE SERVICE METHODS
     */
    boolean checkScheduleConflictForPresenter(int presenterId, LocalDateTime time);
    boolean checkScheduleConflictForAttendee(int attendeeId, LocalDateTime time);
    boolean checkScheduleConflictForVenue(String venue, LocalDateTime time);

    List <Session> getScheduleForPresenter(int presenterId);
    List <Session> getScheduleForAttendee(int attendeeId);


    /*
     * CRUD REPORT SERVICE METHODS
     */
    void exportReport(String filename, List<?> data);
}
