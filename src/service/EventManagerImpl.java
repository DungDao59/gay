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
 * Create fully concrete method for Event Manager CRUD service
 */
public class EventManagerImpl implements EventManager {
    //Calling service of each class
    private final AttendeeDao attendeeDao = new AttendeeDao();
    private final PresenterDao presenterDao = new PresenterDao();
    private final EventDao eventDao = new EventDao();
    private final SessionDao sessionDao = new SessionDao();
    private final TicketDao ticketDao = new TicketDao();
    private final ScheduleDao scheduleDao = new ScheduleDao();
    private final ReportDao reportDao = new ReportDao();

    /*
     * ATTENDEE SERVICE METHODS 
     */

    // Check validation for Attendee and add it to Database
    @Override
    public void addAttendee(Attendee newAttendee) throws Exception{
        // Check if there exist an Attendee
        if(newAttendee == null) throw new Exception("Attendee can't be null");

        // Check if name is valid
        if(newAttendee.getFullName() == null || newAttendee.getFullName().isBlank()){
            throw new Exception("Attendee name can't be empty");
        }

        // Add attendee to database
        attendeeDao.addAttendee(newAttendee);
    }

    // Update an Attendee from Database
    @Override
    public void updateAttendee(Attendee attendee) throws Exception{
        // Check if there exist an Attendee
        if(attendee == null) throw new Exception("Attendee can't be null");

        // Check if ID exists
        if(attendeeDao.getAttendeeById(attendee.getId()) == null){
            throw new Exception("Attendee ID cannot be found");
        }

        // Update attendee inside Database
        attendeeDao.updateAttendee(attendee);
    }

    // Delete an Attendee inside Database by ID
    @Override
    public void deleteAttendee(int id) throws Exception{
        // Check if ID exists
        if(attendeeDao.getAttendeeById(id) == null){
            throw new Exception("Attendee ID cannot be found");
        }
    
        // Delete attendee from Database
        attendeeDao.deleteAttendee(id);
    }

    // Get all Attendee from Database
    @Override
    public List<Attendee> getAllAttendees(){
        return attendeeDao.getAllAttendees();
    }

    // Get Attendee by Id from Database
    @Override
    public Attendee getAttendeeById(int id){
        return attendeeDao.getAttendeeById(id);
    }

    // Get Attendees by name from Database
    @Override
    public List<Attendee> getAttendeesByName(String name){
        return attendeeDao.getAttendeesByName(name);
    }

    // Register an Attendee to a Session
    @Override
    public void registerAttendeeToSession(int attendeeId, int sessionId){
        // Find session inside Session tabble by Id
        Session session = sessionDao.getSessionById(sessionId);

        // Check if sessions exist 
        if(session == null){
            throw new RuntimeException("Session not found");
        }

        if(attendeeDao.isAttendeeRegistered(attendeeId, sessionId)){
            throw new RuntimeException("Attendee already registered in this session");
        }

        // Check if attendee Schedule is conflicted with session or not
        if(scheduleDao.checkScheduleConflictForAttendee(attendeeId, session.getStartDateTime(),session.getEndDateTime())){
            throw new RuntimeException("Attendee schedule conflict.");
        }

        // Check session capacity
        if(!sessionDao.checkSessionCapacity(sessionId)){
            throw new RuntimeException("Session is already full");
        }

        // Registering new Attendee To Session
        attendeeDao.registerAttendeeToSession(attendeeId, sessionId);
    }
    
    // Attendee Purchase Ticket
    @Override
    public boolean attendeePurchaseTicket(int attendeeId, int eventId, TicketType type){
        // Set price for ticket (randomly)
        double price = switch(type){
            case GENERAL -> 25.00;
            case VIP -> 50.00;
            case EARLY_BIRD -> 15.00;
        };

        // Create existing ticket
        Ticket ticket = new Ticket(type, price, StatusType.PAID, attendeeId, eventId);

        // Add ticket to Database
        try{
            ticketDao.addTicket(ticket);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /*
     * PRESENTER SERVICE METHODS
     */

    // Check validation and add Presenter to Database
    @Override
    public void addPresenter(Presenter newPresenter) throws Exception{
        // Check if there exist an Presenter
        if(newPresenter == null) throw new Exception("Presenter can't be null");

        // Check if name is valid
        if(newPresenter.getFullName() == null || newPresenter.getFullName().isBlank()){
            throw new Exception("Presenter name can't be empty");
        }

        // Add Presenter to database
        presenterDao.addPresenter(newPresenter);
    }    

    // Update Info of a Presenter inside Database
    @Override
    public void updatePresenter(Presenter presenter) throws Exception{
        // Check if there exist an Presenter
        if(presenter == null) throw new Exception("Presenter can't be null");

        // Check if ID exists
        if(presenterDao.getPresenterById(presenter.getId()) == null){
            throw new Exception("Presenter ID cannot be found");
        }

        // Update Presenter inside Database
        presenterDao.updatePresenter(presenter);
    }

    // Delete a Presenter inside Database by Id
    @Override
    public void deletePresenter(int id) throws Exception{
        // Check if ID exists
        if(presenterDao.getPresenterById(id) == null){
            throw new Exception("Presenter ID cannot be found");
        }
    
        // Delete Presenter from Database
        presenterDao.deletePresenter(id);
    }

    // Get all Presenters from Database
    @Override
    public List<Presenter> getAllPresenter(){
        return presenterDao.getAllPresenter();
    }

    // Get Presenter from Database by ID
    @Override
    public Presenter getPresenterById(int id){
        return presenterDao.getPresenterById(id);
    }

    // Get Presenters by name from Database
    @Override
    public List<Presenter> getPresentersByName(String name){
        return presenterDao.getPresentersByName(name);
    }

    // Registerd a Presenter to a Session 
    @Override
    public void registerPresenterToSession(int presenterId, int sessionId){
        // Get existed session inside Database
        Session session = sessionDao.getSessionById(sessionId);

        // Check if there is a session or not
        if(session == null){
            throw new RuntimeException("Session not found");
        }

        // Check presenter existence in session
        if(presenterDao.isPresenterRegistered(presenterId, sessionId)){
            throw new RuntimeException("Presenter already registered in this session");
        }

        // Check Presenter and Schedule to see if there is confliction or not.
        if(scheduleDao.checkScheduleConflictForPresenter(presenterId, session.getStartDateTime(),session.getEndDateTime())){
            throw new RuntimeException("Presenter schedule conflict");
        }

        // Register Presenter to a Session
        presenterDao.registerPresenterToSession(presenterId, sessionId);
    }

    // Remove a Presenter from a Session
    public void removePresenterFromSession(int presenterId, int sessionId){
        presenterDao.removePresenterFromSession(presenterId, sessionId);
    }

    /*
     * EVENT SERVICE METHODS
     */

   
    // Check validation and add Event to Database
    @Override
    public void addEvent(Event newEvent) throws Exception{
        // Check if there exist an Event
        if(newEvent == null) throw new Exception("Attendee can't be null");

        // Check if name is valid
        if(newEvent.getName() == null || newEvent.getName().isBlank()){
            throw new Exception("Event name can't be empty");
        }

        // Add Event to database
        eventDao.addEvent(newEvent);
    }    

    // Update an Event inside Database
    @Override
    public void updateEvent(Event event) throws Exception{
        // Check event presentation
        if(event == null) throw new Exception("Event can't be null");

        // Check if ID exists
        if(eventDao.getEventById(event.getEventId()) == null){
            throw new Exception("Event ID cannot be found");
        }

        // Update Presenter inside Database
        eventDao.updateEvent(event);
    }

    // Delete an Event from Database by Id
    @Override
    public void deleteEvent(int id){
        // Check if event exist inside Database
        if(eventDao.getEventById(id) == null){
            throw new RuntimeException("Event ID cannot be found");
        }
    
        // Delete Presenter from Database
        eventDao.deleteEvent(id);
    }

    // Get all Events from Database
    @Override
    public List<Event> getAllEvents(){
        return eventDao.getAllEvents();
    }

    // Get all Events from Database by Id
    @Override
    public Event getEventById(int id){
        return eventDao.getEventById(id);
    }

    // Get all Events by Presenter name from Database
    @Override
    public List<Event> getEventsByPresenter(int presenterId){
        return eventDao.getEventsByPresenter(presenterId);
    }

    // Get all Events sorted by Date
    @Override
    public List<Event> getEventsSortedByDate(){
        return eventDao.getEventSortedByDate();
    }

    // Get all Events by Type
    public List<Event> getEventsByType(EventType type){
        return eventDao.getEventByType(type);
    }


    /*
     * CRUD SESSION SERVICE
     */

    // Check validation and add Session to Database
    @Override
    public void addSession(Session newSession) throws Exception{
        // Check if session existed or not
        if(newSession == null) throw new Exception("Session can't be null");

        // Check if have the same session in the same venue with the same time
        if(scheduleDao.checkScheduleConflictForVenue(newSession.getVenue(), newSession.getStartDateTime(), newSession.getEndDateTime())) throw new Exception("Venue have been booked.");

        // Add session to Database
        sessionDao.addSession(newSession);
    }

    // Update a Session inside Database
    @Override
    public void updateSession(Session session) throws Exception{
        // Check if session existed or not
        if(session == null) throw new Exception("Session can't be null");
    
        // Check if session start date is after end time or not
        if(session.getStartDateTime().isAfter(session.getEndDateTime()))throw new Exception("Start time must be before end time");

        // Check if have the same session in the same venue with the same time
        if(scheduleDao.checkScheduleConflictForVenue(session.getVenue(), session.getStartDateTime(), session.getEndDateTime())) throw new Exception("Venue have been booked.");

        // Update session for database
        sessionDao.updateSession(session);
    }

    // Delete a Session inside Database by Id
    @Override
    public void deleteSession(int id) throws Exception{
        // Check if Session exists inside Database
        if(sessionDao.getSessionById(id) == null) throw new RuntimeException("Session not found");

        sessionDao.deleteSession(id);
    }

    // Get all Sessions inside Database
    @Override
    public List<Session> getAllSessions(){
        return sessionDao.getAllSessions();
    }

    // Get Session by Id from Database
    @Override
    public Session getSessionById(int id){
        return sessionDao.getSessionById(id);
    }

    // Get all Sessions by date from Database
    @Override
    public List<Session> getSessionsByDate(LocalDateTime time){
        return sessionDao.getSessionByDate(time);
    }

    // Get all Sessions by Presenter name from Database
    @Override
    public List<Session> getSessionsByPresenterName(String presenterName){
        return sessionDao.getSessionByPresenterName(presenterName);
    }

    // Check session capacity for session by Id 
    @Override
    public boolean checkSessionCapacity(int id){
        return sessionDao.checkSessionCapacity(id);
    }
    
    /*
     * CRUD TICKET METHODS
     */

    // Check validation and add Ticket to Database
    @Override
    public void addTicket(Ticket newTicket) throws Exception{
        // Check ticket existence
        if(newTicket == null) throw new Exception("Ticket can't be null");

        // Add ticket to Database
        ticketDao.addTicket(newTicket);
    }

    // Update a Ticket inside Database
    @Override
    public void updateTicket(Ticket ticket) throws Exception{
        // Check ticket existence
        if(ticket == null) throw new Exception("Ticket can't be null");
    
        // Update ticket to Database
        ticketDao.updateTicket(ticket);
    }

    // Delete a ticket from Database by Id
    @Override
    public void deleteTicket(int id){
        // Check if the ticket id found inside Database
        if(ticketDao.getTicketById(id) == null) throw new RuntimeException("No ticket found");

        ticketDao.deleteTicket(id);
    }

    // Get all tickets inside Database
    @Override
    public List<Ticket> getAllTickets(){
        return ticketDao.getAllTickets();
    }

    // Get a Ticket from Database by Id
    @Override
    public Ticket getTicketById(int id){
        return ticketDao.getTicketById(id);
    }

    // Get all Tickets sorted by price
    @Override
    public List<Ticket> getTicketsSortedByPrice(){
        return ticketDao.getTicketsSortedByPrice();
    }

    // Get all Tickets by Type
    @Override
    public List<Ticket> getTicketsByType(TicketType type){
        return ticketDao.getTicketByType(type);
    }


    // Update ticket status
    public void updateTicketStatus(int ticketId, StatusType status){
        Ticket ticket= ticketDao.getTicketById(ticketId);
        if(ticket == null){
            throw new RuntimeException("Ticket not found");
        }

        ticket.setStatus(status);
        ticketDao.updateTicketStatus(ticketId, status);
    }

    /*
     * CRUD SCHEDULE SERVICE
     */

    // Check conflict for presenter and schedule
    @Override
    public boolean checkScheduleConflictForPresenter(int presenterId, LocalDateTime newStart, LocalDateTime newEnd){
        return scheduleDao.checkScheduleConflictForPresenter(presenterId, newStart,newEnd);
    }

    // Check conflict for attendee and schedule
    @Override
    public boolean checkScheduleConflictForAttendee(int attendeeId, LocalDateTime newStart, LocalDateTime newEnd){
        return scheduleDao.checkScheduleConflictForAttendee(attendeeId, newStart, newEnd);
    }

    // Check conflict for schedule and venue
    @Override
    public boolean checkScheduleConflictForVenue(String venue, LocalDateTime newStart, LocalDateTime newEnd){
        return scheduleDao.checkScheduleConflictForVenue(venue, newStart, newEnd);
    }

    // Get all schedule for presenter
    @Override
    public List <Session> getScheduleForPresenter(int presenterId){
        return scheduleDao.getScheduleForPresenter(presenterId);
    }

    // Get all schedule for attendee
    @Override
    public List <Session> getScheduleForAttendee(int attendeeId){
        return scheduleDao.getScheduleForAttendee(attendeeId);
    }
    
    /*
     * CRUD REPORT SERVICE
     */
    
    // Export a report in to txt file
    public void exportReport(String fileName, List<?> data){
        reportDao.exportReport(fileName, data);
    }
}
