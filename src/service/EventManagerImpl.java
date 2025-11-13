package service;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import model.Attendee;
import model.Event;
import model.Presenter;
import model.PresenterRole;
import model.Session;
import model.StatusType;
import model.Ticket;
import util.Database;

/*
 * @author Dao Tien Dung - s4088577
 * Create fully concrete method for CRUD
 */
public class EventManagerImpl implements EventManager {
    // Set up DateFormatter for every field using LocalDateTime
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    //Calling service of each class
    private final AttendeeService attendeeService = new AttendeeService();
    private final PresenterService presenterService = new PresenterService();
    private final EventService eventService = new EventService();
    private final SessionService sessionService = new SessionService();
    private final TicketService ticketService = new TicketService();

    /*
     * ATTENDEE CRUD METHODS
     */

    // Add Attendee to Database
    @Override
    public void addAttendee(Attendee newAttendee){
        attendeeService.addAttendee(newAttendee);
    }

    // Update an Attendee inside Database
    @Override
    public void updateAttendee(Attendee attendee){
        attendeeService.updateAttendee(attendee);
    }

    // Delete an Attendee inside Database
    @Override
    public void deleteAttendee(int id){
        attendeeService.deleteAttendee(id);
    }

    // Get all attendees from database
    @Override
    public List<Attendee> getAllAttendees(){
        return attendeeService.getAllAttendees();
    }

    // Get attendee by id
    @Override
    public Attendee getAttendeeById(int id){
        return attendeeService.getAttendeeById(id);
    }

    // Create a registration for attendee to session
    @Override
    public void registerAttendeeToSession(int attendeeId, int sessionId){
        attendeeService.registerAttendeeToSession(attendeeId, sessionId);
    }

    // Attendee purchase ticket
    @Override
    public void attendeePurchaseTicket(int attendeeId, int ticketId){
        attendeeService.attendeePurchaseTicket(attendeeId, ticketId);
    }

    /*
     * PRESENTER CRUD METHODS
     */
    
    // Add presenter to Database
    @Override
    public void addPresenter(Presenter newPresenter){
        presenterService.addPresenter(newPresenter);
    }

    // Udpdate a presenter in the database
    @Override
    public void updatePresenter(Presenter presenter){
        presenterService.updatePresenter(presenter);
    };

    // Delete Presenter inside Database
    @Override
    public void deletePresenter(int id){
        presenterService.deletePresenter(id);        
    }

    // Get all Presnters inside Database
    @Override
    public List<Presenter> getAllPresenter(){
        return presenterService.getAllPresenter();
    }

    // Get presenter by Id inside Database
    @Override
    public Presenter getPresenterById(int id){
        return presenterService.getPresenterById(id);
    }

    // Register a presenter to a session
    @Override
    public void registerPresenterToSession(int presenterId, int sessionId){
        presenterService.registerPresenterToSession(presenterId, sessionId);
    }

    // Removing a presenter from a session
    @Override
    public void removePresenterFromSession(int presenterId, int sessionId){
        presenterService.removePresenterFromSession(presenterId, sessionId);
    };

    /*
     * EVENT CRUD METHODS
     */

    // Add Event to Database
    @Override
    public void addEvent(Event newEvent){
        eventService.addEvent(newEvent);
    };

    // Update an Event from Database
    @Override
    public void updateEvent(Event event){
        eventService.updateEvent(event);
    };

    // Delete an Event from Database by Id
    @Override
    public void deleteEvent(int id){
        eventService.deleteEvent(id);
    };

    // Get all Event from Database
    @Override
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    };

    // Get Event from Database by Id
    @Override
    public Event getEventById(int id){
        return eventService.getEventById(id);
    };

    /*
     * CRUD SESSION METHODS
     */

    // Add Session to Database
    @Override
    public void addSession(Session newSession){
        sessionService.addSession(newSession);
    };

    // Update a Session inside Database
    @Override
    public void updateSession(Session session){
        sessionService.updateSession(session);
    };

    // Delete a Session from Database
    @Override
    public void deleteSession(int id){
        sessionService.deleteSession(id);
    };

    // Get all Sessions from Database
    @Override
    public List<Session> getAllSessions(){
        return sessionService.getAllSession();
    };

    // Get Session from Database by Id
    @Override
    public Session getSessionById(int id){
        return sessionService.getSessionById(id);
    };

    //Add later
    // List<Session> getSessionsByDate(LocalDateTime time);
    // List<Session> getSessionsByPresenterName(String presenterName);

    // Check session capacity
    public boolean checkSessionCapacity(int id){
        return sessionService.checkSessionCapacity(id);
    };

    /*
     * CRUD TICKET METHODS
     */

    // Add Ticket to Database
    @Override
    public void addTicket(Ticket newTicket){
        ticketService.addTicket(newTicket);
    };
    
    // Update a Ticket inside Database
    @Override
    public void updateTicket(Ticket ticket){
        ticketService.updateTicket(ticket);
    };

    // Delete Ticket from Database
    @Override
    public void deleteTicket(int id){
        ticketService.deleteTicket(id);
    };
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    };
    public Ticket getTicketById(int id){
        return ticketService.getTicketById(id);
    };

    // Update ticket status
    @Override
    public void updateTicketStatus(int ticketId, StatusType status){
        ticketService.updateTicketStatus(ticketId, status);
    };

}
