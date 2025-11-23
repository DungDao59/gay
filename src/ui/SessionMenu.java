package ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.Attendee;
import model.Event;
import model.Presenter;
import model.Session;
import service.EventManager;

/*
 * Dao Tien Dung - s4088577
 * Session menu file for UI
 */

public class SessionMenu {
    private EventManager manager;
    private final Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public SessionMenu(EventManager manager){
        this.manager = manager;
    }

    private void showAllSessions(){
        List<Session> sessions = manager.getAllSessions();

        if(sessions.isEmpty()){
            System.out.println("[Info] No session found");
            return;
        }

        int index = 1;
        for(var session: sessions){
            System.out.println("      Session " + (index ++));
            System.out.println("---------------------");
            printSession(session);
        }
    }

    private void printSession(Session session){
        System.out.println("+ ID: " + session.getSessionId());
        System.out.println("+ Title: " + session.getTitle());
        System.out.println("+ Description: " + session.getDescription());
        System.out.println("+ Venue: " + session.getVenue());
        System.out.println("+ Capacity: " + session.getCapacity());
        System.out.println("+ Start time: " + session.getStartDateTime());
        System.out.println("+ End time: " + session.getEndDateTime());
        System.out.println("+ Event: " + session.getEventId());
        System.out.println("+ Attendees:");
        if(session.getAttendees() == null || session.getAttendees().isEmpty()){
            System.out.println("    - [Info] No attendee available for this session");
        }
        else{
            for(Attendee attendee: session.getAttendees()){
                System.out.println("    - Attendee ID: " + attendee.getId() + " | Full Name: " + attendee.getFullName());
            }
        }

        System.out.println("+ Presenters");
        if(session.getPresenters() == null || session.getPresenters().isEmpty()){
            System.out.println("    - [Info] No presenter available for this session");
        }
        else{
            for(Presenter presenter: session.getPresenters()){
                System.out.println("    - Presenter ID: " + presenter.getId() + " | Full Name: " + presenter.getFullName());
            }
        }

        System.out.println();
        
    }

    private void addSession(){
        List<Event> events = manager.getAllEvents();
        if(events.isEmpty()){
            System.out.println("[Info] No current event to add more session.");
            return;
        }

        System.out.println("----- Add New Session -----");

        String title, description,venue;
        int eventId, capacity;
        LocalDateTime startDate, endDate;
        
        while(true){
            System.out.print("Enter session title: ");
            title = sc.nextLine().trim();

            if(!title.isEmpty()) break;
            
            System.out.println("[Error] Session title can't be empty");
        }

        while(true){
            System.out.print("Enter session description: ");
            description = sc.nextLine().trim();

            if(!description.isEmpty()) break;
            
            System.out.println("[Error] Session description can't be empty");
        }

        while(true){
            System.out.print("Enter session venue: ");
            venue = sc.nextLine().trim();

            if(!description.isEmpty()) break;
            
            System.out.println("[Error] Session description can't be empty");
            
        }

        while(true){
            System.out.print("Enter session start date & time (DD/MM/YYYY HH:MM): ");
            String input = sc.nextLine().trim();

            try{
                startDate = LocalDateTime.parse(input, formatter);
                if(startDate.isBefore(LocalDateTime.now())){
                    System.out.println("[Error] Session date cannot exist in the past");
                    continue;
                }
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid date. Please enter the correct format DD/MM/YYYY (Example: 31/12/2025)");
            }
        }

        while(true){
            System.out.print("Enter session end date & time (DD/MM/YYYY HH:MM): ");
            String input = sc.nextLine().trim();

            try{
                endDate = LocalDateTime.parse(input, formatter);
                if(endDate.isBefore(startDate)){
                    System.out.println("[Error] Session end date cannot exist before start date");
                    continue;
                }
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid date. Please enter the correct format DD/MM/YYYY (Example: 31/12/2025)");
            }
        }

        System.out.println("Available event: ");
        for (Event event : events) {
            System.out.println("     + Event ID: " + event.getEventId() + " | Name: " + event.getName());
        }

        while(true){
            try{
                System.out.print("Enter event ID for session: ");
                eventId = Integer.parseInt(sc.nextLine().trim());

                if(manager.getEventById(eventId) == null){
                    System.out.println("[Info] No event found");
                    continue;
                }

                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter an integer");
            }
        }

        while(true){
            try{
                System.out.print("Enter session capacity: ");
                capacity = Integer.parseInt(sc.nextLine().trim());
                
                if(capacity <= 0){
                    System.out.println("[Error] Session capacity must be an integer");
                }

                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter an integer");
            }
        }

        Session session = new Session(eventId,title, description, startDate, endDate, venue, capacity);

        try{
            manager.addSession(session);
            System.out.println("[Success] Session ID #" + session.getEventId() + " added");
        }catch(Exception e){
            System.out.println("[Error] " + e.getMessage());
        }

    }

    private void updateSession(){
        if(manager.getAllEvents().isEmpty()){
            System.out.println("[Info] No current session");
            return;
        }

        System.out.println("----- Update Session -----");
        for(Session session: manager.getAllSessions()){
            System.out.println("- ID: " + session.getSessionId() + " | Name: " + session.getTitle());
        }

        System.out.print("Enter session ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Session session = manager.getSessionById(id);
        if(session == null){
            System.out.println("[Error] No session found");
            return;
        }
        
        int choice = -1;
        while(choice != 0){
            System.out.println();
            System.out.println("====== Update Session #" + id + " ======");
            System.out.println("- ID: " + session.getSessionId() + " | Title: " + session.getTitle() + " | Description: " + session.getDescription() + " | Venue: " + session.getVenue() +" | Start time: " + session.getStartDateTime() + " | End time: " +  session.getEndDateTime() + " | Event ID: " + session.getEventId() + " | Capacity: " + session.getCapacity());
            System.out.println("0. Return");
            System.out.println("1. Update session title");
            System.out.println("2. Update session description");
            System.out.println("3. Update session venue");
            System.out.println("4. Update session start date ");
            System.out.println("5. Update session end date ");
            System.out.println("6. Update session event ID");
            System.out.println("7. Update session capacity");

            System.out.print("Enter your choice (0-6): ");
            try{
                choice = Integer.parseInt(sc.nextLine().trim());

                if(choice < 0 || choice > 7){
                    throw new Exception();
                } 
            }catch(Exception e){
                System.out.println("[Error] Invalid option. Please enter a number (0-4).");
                continue;
            }

            switch(choice){
                case 0:
                    System.out.println("[Returning] Returning ...");
                    break;
                case 1:{
                    while(true){
                        System.out.print("Enter new session title: ");
                        String title = sc.nextLine().trim();

                        if(title.isEmpty()){
                            System.out.println("[Error] Event name cannot be empty");
                        }

                        session.setTitle(title);
                        System.out.println("[Success] Session title updated.");
                        break;
                    }
                    break;
                }
                case 2:{
                    while(true){
                        System.out.print("Enter new session description: ");
                        String description = sc.nextLine().trim();

                        if(description.isEmpty()){
                            System.out.println("[Error] Session description cannot be empty");
                        }

                        session.setDescription(description);
                        System.out.println("[Success] Session description updated.");
                        break;
                    }
                    break;
                }
                case 3:{
                    while(true){
                        System.out.print("Enter new session venue: ");
                        String venue = sc.nextLine().trim();

                        if(venue.isEmpty()){
                            System.out.println("[Error] Session venue cannot be empty");
                        }

                        session.setVenue(venue);
                        System.out.println("[Success] Session venue updated.");
                        break;
                    }
                    break;
                }
                case 4:{
                    while(true){
                        System.out.print("Enter new session start date & time (DD/MM/YYYY HH:MM): ");
                        String input = sc.nextLine().trim();

                        try{
                            LocalDateTime date  = LocalDateTime.parse(input, formatter);
                            if(date.isBefore(LocalDateTime.now())){
                                System.out.println("[Error] Session schedule date cannot be in the past!");
                                continue;
                            }

                            session.setStartDateTime(date);
                            System.out.println("[Success] Update session start date.");
                            break;
                        }catch(Exception e){
                            System.out.println("[Error] Invalid date. Please enter the correct format DD/MM/YYYY HH:MM (Example: 31/12/2025 20:00)");
                        }
                    }
                    break;
                }
                case 5:{
                    while(true){
                        System.out.print("Enter update session end date & time (DD/MM/YYYY HH:MM): ");
                        String input = sc.nextLine().trim();

                        try{
                            LocalDateTime date  = LocalDateTime.parse(input, formatter);
                            if(date.isBefore(session.getStartDateTime())){
                                System.out.println("[Error] End date cannot exist before start date");
                                continue;
                            }

                            session.setEndDateTime(date);
                            System.out.println("[Success] Update session end date.");
                            break;
                        }catch(Exception e){
                            System.out.println("[Error] Invalid date. Please enter the correct format DD/MM/YYYY (Example: 31/12/2025)");
                        }
                    }
                    break;
                }
                case 6:
                    System.out.println("====== Available events ======");
                    for(Event event : manager.getAllEvents()){
                        System.out.println("+ Event ID: " + event.getEventId() + " | Name: " + event.getName());
                    }

                    try{
                        System.out.print("Enter event ID for session: ");
                        int eventId = Integer.parseInt(sc.nextLine().trim());

                        if(manager.getEventById(eventId) == null){
                            System.out.println("[Info] No event found");
                            continue;
                        }

                        session.setEventId(eventId);
                        break;
                    }catch(Exception e){
                        System.out.println("[Error] Invalid input. Please enter an integer");
                    }

                case 7:
                    while(true){
                        try{
                            System.out.print("Enter session capacity: ");
                            int capacity = Integer.parseInt(sc.nextLine().trim());
                            
                            if(capacity <= 0){
                                System.out.println("[Error] Session capacity must be an integer");
                            }

                            session.setCapacity(capacity);
                            break;
                        }catch(Exception e){
                            System.out.println("[Error] Invalid input. Please enter an integer");
                        }
                    }
            }
        }
        try{
            manager.updateSession(session);
            System.out.println("[Success] Session ID #" + session.getSessionId() + " updated");
        }catch(Exception e){
            System.out.println("[Error] "+ e.getMessage());
        }
    }
    
    private void deleteSession(){
        if(manager.getAllEvents().isEmpty()){
            System.out.println("[Info] No current event");
            return;
        }

        List<Session> sessions = manager.getAllSessions();

        System.out.println("----- Delete Session -----");
        for(Session session: sessions){
            System.out.println("- ID: " + session.getSessionId() + " | Title: " + session.getTitle());
        }
        int id = 0;
        while(true){
            System.out.print("Enter Session ID to delete: ");
            try{
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer" );
                continue;
            }
        }


        Session session = manager.getSessionById(id);
        if(session == null){
            System.out.println("[Error] No session found");
            return;
        }

        System.out.print("Confirmation for deleting session ID #" + id + " ? (Y for yes/ N for no): ");
        String input =  sc.nextLine().trim().toLowerCase();

        if(input.equalsIgnoreCase("y")){
            try{
                manager.deleteSession(id);
                System.out.println("[Success] Delete session with ID #" + id);
            }catch(Exception e){
                System.out.println("[Error] "+ e.getMessage());
            }
        }
        else if (input.equalsIgnoreCase("n")){
            System.out.println("[Denied] No deletion for session ID #" + id);
        }
        else{
            System.out.println("[Error] Please enter 'Y' or 'N'");
        }
    }

    private void getSessionInfoById(){
        if(manager.getAllSessions().isEmpty()){
            System.out.println("[Info] No current session");
            return;
        }

        System.out.println("----- Get Session Info by ID -----");
        for(Session session: manager.getAllSessions()){
            System.out.println("- ID: " + session.getSessionId() + " | Title: " + session.getTitle());
        }
        int id = 0;
        while(true){
            System.out.print("Enter session ID: ");
            try{
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer" );
                continue;
            }
        }


        Session session = manager.getSessionById(id);
        if(session == null){
            System.out.println("[Error] No session found");
            return;
        }

        System.out.println("\n------ Session ID #" + session.getSessionId() + " ------");
        System.out.println("+ Title: " + session.getTitle());
        System.out.println("+ Description: " + session.getDescription());
        System.out.println("+ Venue: " + session.getVenue());
        System.out.println("+ Capacity: " + session.getCapacity());
        System.out.println("+ Start time: " + session.getStartDateTime());
        System.out.println("+ End time: " + session.getEndDateTime());
        System.out.println("+ Event: " + session.getEventId());
        System.out.println("+ Attendees:");
        if(session.getAttendees() == null || session.getAttendees().isEmpty()){
            System.out.println("    - [Info] No attendee available for this session");
        }
        else{
            for(Attendee attendee: session.getAttendees()){
                System.out.println("    - Attendee ID: " + attendee.getId() + " | Full Name: " + attendee.getFullName());
            }
        }

        System.out.println("+ Presenters");
        if(session.getPresenters() == null || session.getPresenters().isEmpty()){
            System.out.println("    - [Info] No presenter available for this session");
        }
        else{
            for(Presenter presenter: session.getPresenters()){
                System.out.println("    - Presenter ID: " + presenter.getId() + " | Full Name: " + presenter.getFullName());
            }
        }
    }
 

    public void sessionMenu(){
        System.out.print("\n[Success] Welcome to Session Management");
        int userChoice = -1;
        while(userChoice != 0 ){
            System.out.println();
            System.out.println("========== Session Management ==========");
            System.out.println("0. Back to main menu");
            System.out.println("1. Show all sessions info");
            System.out.println("2. Add session");
            System.out.println("3. Update session");
            System.out.println("4. Delete session");
            System.out.println("5. Get session info by ID");

            try{
                System.out.print("Enter your choice (0-8): ");
                
                String input = sc.nextLine();
                userChoice = Integer.parseInt(input);

                if(userChoice < 0 || userChoice > 8) throw new Exception("Index out of range");
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer (0-8).");
                continue;
            }

            switch (userChoice) {
                case 0:
                    System.out.println("[Returning] Back to Main Menu ...");
                    break;
                case 1:
                    showAllSessions();
                    break;
                case 2:
                    addSession();
                    break;
                case 3:
                    updateSession();
                    break; 
                case 4:
                    deleteSession();
                    break;
                case 5:
                    getSessionInfoById();
                    break;

            }
        }
    }
}
