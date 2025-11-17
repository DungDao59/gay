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
    public void registerAttendeeToSession(int attendeeId, int sessionId){
        // Find session inside Session tabble by Id
        Session session = sessionDao.getSessionById(sessionId);

        // Check if sessions exist 
        if(session == null){
            throw new RuntimeException("Session not found");
        }

        // Check if attendee Schedule is conflicted with session or not
        if(checkScheduleConflictForAttendee(attendeeId, session.getScheduleDateTime())){
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
    public void attendeePurchaseTicket(int attendeeId, int ticketId){
        // Get existing ticket
        Ticket ticket = ticketDao.getTicketById(ticketId);

        // If ticket not exist throw exception
        if(ticket == null){
            throw new RuntimeException("Ticket not found");
        }

        // set ticket attendee ID 
        ticket.setAttendeeId(attendeeId);

        // set Ticket status to PAID
        ticket.setStatus(StatusType.PAID);

        // Update Ticket inside Database
        ticketDao.updateTicket(ticket);
    }

    /*
     * PRESENTER SERVICE METHODS
     */

    // Check validation and add Presenter to Database
    @Override
    public void addPresenter(Presenter newPresenter) throws Exception{
        // Check if there exist an Presenter
        if(newPresenter == null) throw new Exception("Attendee can't be null");

        // Check if name is valid
        if(newPresenter.getFullName() == null || newPresenter.getFullName().isBlank()){
            throw new Exception("Attendee name can't be empty");
        }

        // Add Presenter to database
        presenterDao.addPresenter(newPresenter);
    }    

    // Update Info of a Presenter inside Database
    @Override
    public void updatePresenter(Presenter presenter) throws Exception{
        // Check if there exist an Presenter
        if(presenter == null) throw new Exception("Attendee can't be null");

        // Check if ID exists
        if(presenterDao.getPresenterById(presenter.getId()) == null){
            throw new Exception("Attendee ID cannot be found");
        }

        // Update Presenter inside Database
        presenterDao.updatePresenter(presenter);
    }

    // Delete a Presenter inside Database by Id
    @Override
    public void deletePresenter(int id) throws Exception{
        // Check if ID exists
        if(presenterDao.getPresenterById(id) == null){
            throw new Exception("Attendee ID cannot be found");
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

        // Check Presenter and Schedule to see if there is confliction or not.
        if(checkScheduleConflictForPresenter(presenterId, session.getScheduleDateTime())){
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
     * EVENT CRUD METHODS
     */

    

    /*
     * CRUD SESSION METHODS
     */

    
    /*
     * CRUD TICKET METHODS
     */

    

    /*
     * CRUD SCHEDULE METHODS
     */
    

    /*
     * CRUD REPORT METHODS
     */
    
}
