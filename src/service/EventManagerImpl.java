package service;

import java.time.*;
import java.util.*;

import dao.AttendeeDao;
import dao.EventDao;
import dao.PresenterDao;
import dao.ReportDao;
import dao.ScheduleDao;
import dao.SessionDao;
import dao.TicketDao;
import model.Attendee;
import model.Event;
import model.EventType;
import model.Presenter;
import model.Session;
import model.StatusType;
import model.Ticket;
import model.TicketType;

/*
 * @author Dao Tien Dung - s4088577
 * Create fully concrete method for CRUD
 */
public class EventManagerImpl implements EventManager {
    //Calling service of each class
    private final AttendeeDao attendeeService = new AttendeeDao();
    private final PresenterDao presenterService = new PresenterDao();
    private final EventDao eventService = new EventDao();
    private final SessionDao sessionService = new SessionDao();
    private final TicketDao ticketService = new TicketDao();
    private final ScheduleDao scheduleService = new ScheduleDao();
    private final ReportDao reportService = new ReportDao();

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

    // Get Session by date from Database
    @Override
    public List<Session> getSessionsByDate(LocalDateTime time){
        return sessionService.getSessionByDate(time);
    };

    // List<Session> getSessionsByPresenterName(String presenterName);

    // Check session capacity
    @Override
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
    
    //Get all Ticket from Database
    @Override
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    };

    // Get Ticket by Id from Database
    public Ticket getTicketById(int id){
        return ticketService.getTicketById(id);
    };

    // Update ticket status
    @Override
    public void updateTicketStatus(int ticketId, StatusType status){
        ticketService.updateTicketStatus(ticketId, status);
    };

    /*
     * CRUD SCHEDULE METHODS
     */

    // Check conflict for Presenter
    @Override
    public boolean checkScheduleConflictForPresenter(int presenterId, LocalDateTime time){
        return scheduleService.checkScheduleConflictForPresenter(presenterId, time);
    };

    // Check conflict for Attendee
    @Override
    public boolean checkScheduleConflictForAttendee(int attendeeId, LocalDateTime time){
        return scheduleService.checkScheduleConflictForAttendee(attendeeId, time);
    };

    // Check conflict for venue 
    @Override
    public boolean checkScheduleConflictForVenue(String venue, LocalDateTime time){
        return scheduleService.checkScheduleConflictForVenue(venue, time);
    };

    // Get the schedule for Presenter by ID
    @Override
    public List <Session> getScheduleForPresenter(int presenterId){
        return scheduleService.getScheduleForPresenter(presenterId);
    };

    // Get the schedule for Attendee by ID
    @Override
    public List <Session> getScheduleForAttendee(int attendeeId){
        return scheduleService.getScheduleForAttendee(attendeeId);
    };

    /*
     * CRUD REPORT METHODS
     */

    // Get event sorted by date from database
    @Override
    public List<Event> getEventsSortedByDate(){
        return reportService.getEventSortedByDate();
    };

    // Get event by type from database
    @Override
    public List<Event> getEventsByType(EventType type){
        return reportService.getEventByType(type);
    };

    //Get tickets by type from database
    @Override
    public List<Ticket> getTicketsByType(TicketType type){
        return reportService.getTicketByType(type);
    };


}
