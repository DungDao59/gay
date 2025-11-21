package ui;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.Event;
import model.EventType;
import model.Presenter;
import model.Session;
import service.EventManager;

/*
 * Dao Tien Dung - s4088577
 * Event Menu file for UI 
 */

public class EventMenu {
    private EventManager manager;
    private final Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public EventMenu(EventManager manager){
        this.manager = manager;
    }

    private void showAllEvents(){
        List<Event> events = manager.getAllEvents();

        if(events.isEmpty()){
            System.out.println("[Info] No event found");
            return;
        }

        int index = 1;
        for(var event: events){
            System.out.println("      Event " + (index ++));
            System.out.println("---------------------");
            printEvent(event);
        }
    }
    
    private void printEvent(Event event){
        System.out.println("+ ID: " + event.getEventId());
        System.out.println("+ Name: " + event.getName());
        System.out.println("+ Type: " + event.getType().name());
        System.out.println("+ Location: " + event.getLocation());
        System.out.println("+ Start date: " + event.getStartDate());
        System.out.println("+ End date: " + event.getEndDate());
        System.out.println("+ Session: ");
        if(event.getSessions() == null ||event.getSessions().isEmpty()){
            System.out.println("    - [Info] No session available for this event");
        }
        else{
            for(Session session: event.getSessions()){
                System.out.println("    - Session ID: " + session.getSessionId() + " | Title: " + session.getTitle() + " | Time: " + session.getScheduleDateTime() + " | Venue: " + session.getVenue());
            }
        }

        System.out.println();
    }

    private void addEvent(){
        System.out.println("----- Add New Event -----");

        String name, location;
        LocalDateTime startDate, endDate;
        EventType type;
        
        while(true){
            System.out.print("Enter event name: ");
            name = sc.nextLine().trim();

            if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("[Error] Name must contain only letters!");
                continue;
            }

            if(!name.isEmpty()) break;
            
            System.out.println("[Error] Event name can't be empty");
        }

        while(true){
            System.out.print("Enter event location: ");
            location = sc.nextLine().trim();

            if(!location.isEmpty()) break;
            
            System.out.println("[Error] Event location can't be empty");
        }

        while(true){
            System.out.print("Enter event start date & time (DD/MM/YYYY HH:MM): ");
            String input = sc.nextLine().trim();

            try{
                startDate = LocalDateTime.parse(input, formatter);
                if(startDate.isBefore(LocalDateTime.now())){
                    System.out.println("[Error] Start date cannot be in the past!");
                    continue;
                }
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid date. Please enter the correct format DD/MM/YYYY (Example: 31/12/2025)");
            }
        }

        while(true){
            System.out.print("Enter end date & time (DD/MM/YYYY HH:MM): ");
            String input = sc.nextLine().trim();

            try{
                endDate = LocalDateTime.parse(input, formatter);
                if(endDate.isBefore(startDate)){
                    System.out.println("[Error] End date cannot exist before start date!");
                    continue;
                }
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid date. Please enter the correct format DD/MM/YYYY (Example: 31/12/2025)");
            }
        }

        System.out.println("Event type: ");
        for (EventType type1 : EventType.values()) {
            System.out.println("    " +(type1.ordinal() + 1) + ". " + type1.name());
        }

        while(true){
            try{
                System.out.print("Choose a type: ");
                int choice = Integer.parseInt(sc.nextLine().trim());

                if(choice < 1 || choice > EventType.values().length){
                    System.out.println("[Error] Index out of range.");
                    continue;
                }
                type = EventType.values()[choice-1];
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter an integer");
            }
        }

        Event event = new Event(name, type, location, startDate, endDate);

        try{
            manager.addEvent(event);
            System.out.println("[Success] Event ID #" + event.getEventId() + " added");
        }catch(Exception e){
            System.out.println("[Error] " + e.getMessage());
        }

    }

    private void updateEvent(){
        if(manager.getAllEvents().isEmpty()){
            System.out.println("[Info] No current event");
            return;
        }

        System.out.println("----- Update Event -----");
        for(Event event: manager.getAllEvents()){
            System.out.println("- ID: " + event.getEventId() + " | Name: " + event.getName());
        }

        System.out.print("Enter event ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Event event = manager.getEventById(id);
        if(event == null){
            System.out.println("[Error] No event found");
            return;
        }
        
        int choice = -1;
        while(choice != 0){
            System.out.println();
            System.out.println("====== Update Event #" + id + " ======");
            System.out.println("- ID: " + event.getEventId() + " | Name: " + event.getName() + " | Type: " + event.getType().name() + " | Location: " + event.getLocation() +" | Start date: " + event.getStartDate() + " | End date: " + event.getEndDate());
            System.out.println("0. Return");
            System.out.println("1. Update event name");
            System.out.println("2. Update event location");
            System.out.println("3. Update event type");
            System.out.println("4. Update event start date ");
            System.out.println("5. Update event end date");


            System.out.print("Enter your choice (0-4): ");
            try{
                choice = Integer.parseInt(sc.nextLine().trim());

                if(choice < 0 || choice > 4){
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
                        System.out.print("Enter new event name: ");
                        String name = sc.nextLine().trim();

                        if(name.isEmpty()){
                            System.out.println("[Error] Event name cannot be empty");
                        }

                        event.setName(name);
                        System.out.println("[Success] Event name updated.");
                        break;
                    }
                    break;
                }
                case 2:{
                    while(true){
                        System.out.print("Enter new event location: ");
                        String location = sc.nextLine().trim();

                        if(location.isEmpty()){
                            System.out.println("[Error] Event location cannot be empty");
                        }

                        event.setLocation(location);
                        System.out.println("[Success] Event location updated.");
                        break;
                    }
                    break;
                }
                case 3:{
                    System.out.println("Event type:");
                    for (EventType type1 : EventType.values()) {
                        System.out.print(" " +(type1.ordinal() + 1) + ". " + type1.name() + " |");
                    }
                    while(true){
                        try{
                            System.out.print("\nChoose a number to update event type: ");
                            int userChoice = Integer.parseInt(sc.nextLine().trim());

                            if(userChoice < 1 || userChoice > EventType.values().length){
                                System.out.println("[Error] Index out of range.");
                                continue;
                            }
                            event.setEventType(EventType.values()[userChoice-1]);
                            System.out.println("[Success] Event type updated.");
                            break;
                        }catch(Exception e){
                            System.out.println("[Error] Invalid input. Please enter an integer");
                        }
                        
                    }
                    break;
                }
                case 4:{
                    while(true){
                        System.out.print("Enter update event start date & time (DD/MM/YYYY HH:MM): ");
                        String input = sc.nextLine().trim();

                        try{
                            LocalDateTime date  = LocalDateTime.parse(input, formatter);
                            if(date.isBefore(LocalDateTime.now())){
                                System.out.println("[Error] Start date cannot be in the past!");
                                continue;
                            }

                            event.setStartDate(date);
                            System.out.println("[Success] Update start date.");
                            break;
                        }catch(Exception e){
                            System.out.println("[Error] Invalid date. Please enter the correct format DD/MM/YYYY HH:MM (Example: 31/12/2025 20:00)");
                        }
                    }
                    break;
                }
                case 5:{
                    while(true){
                        System.out.print("Enter update event end date & time (DD/MM/YYYY HH:MM): ");
                        String input = sc.nextLine().trim();

                        try{
                            LocalDateTime date  = LocalDateTime.parse(input, formatter);
                            if(date.isBefore(event.getStartDate())){
                                System.out.println("[Error] End date cannot exist before start date");
                                continue;
                            }

                            event.setEndDate(date);
                            System.out.println("[Success] Update end date.");
                            break;
                        }catch(Exception e){
                            System.out.println("[Error] Invalid date. Please enter the correct format DD/MM/YYYY (Example: 31/12/2025)");
                        }
                    }
                    break;
                }

            }
        }
        try{
            manager.updateEvent(event);
            System.out.println("[Success] Event ID #" + event.getEventId() + " updated");
        }catch(Exception e){
            System.out.println("[Error] "+ e.getMessage());
        }
    }

    private void deleteEvent(){
        if(manager.getAllEvents().isEmpty()){
            System.out.println("[Info] No current event");
            return;
        }

        System.out.println("----- Delete Event -----");
        for(Event event: manager.getAllEvents()){
            System.out.println("- ID: " + event.getEventId() + " | Full Name: " + event.getName());
        }
        int id = 0;
        while(true){
            System.out.print("Enter Event ID to delete: ");
            try{
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer" );
                continue;
            }
        }


        Event event = manager.getEventById(id);
        if(event == null){
            System.out.println("[Error] No event found");
            return;
        }

        System.out.print("Confirmation for deleting event ID #" + id + " ? (Y for yes/ N for no): ");
        String input =  sc.nextLine().trim().toLowerCase();

        if(input.equalsIgnoreCase("y")){
            try{
                manager.deleteEvent(id);
                System.out.println("[Success] Delete event with ID #" + id);
            }catch(Exception e){
                System.out.println("[Error] "+ e.getMessage());
            }
        }
        else if (input.equalsIgnoreCase("n")){
            System.out.println("[Denied] No deletion for event ID #" + id);
        }
        else{
            System.out.println("[Error] Please enter 'Y' or 'N'");
        }
    }

    private void getEventInfoById(){
        if(manager.getAllEvents().isEmpty()){
            System.out.println("[Info] No current event");
            return;
        }

        System.out.println("----- Get Event Info by ID -----");
        for(Event event: manager.getAllEvents()){
            System.out.println("- ID: " + event.getEventId() + " | Name: " + event.getName());
        }
        int id = 0;
        while(true){
            System.out.print("Enter event ID: ");
            try{
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer" );
                continue;
            }
        }


        Event event = manager.getEventById(id);
        if(event == null){
            System.out.println("[Error] No event found");
            return;
        }

        System.out.println("\n------ Event ID #" + event.getEventId() + " ------");
        System.out.println("+ Name: " + event.getName());
        System.out.println("+ Type: " + event.getType().name());
        System.out.println("+ Location: " + event.getLocation());
        System.out.println("+ Start date: " + event.getStartDate());
        System.out.println("+ End date: " + event.getEndDate());
        System.out.println("+ Session registered: ");
        if ( event.getSessions() == null || event.getSessions().isEmpty()){
            System.out.println("    -[Info] No session registered currently");
        }
        else{
            for(Session s: event.getSessions()){
                System.out.println("    - Session ID: " + s.getSessionId() + " | Title: " + s.getTitle() + " | Date: " + s.getScheduleDateTime());
            }
        }
    }
    
    private void showAllEventsSortedByDate(){
        List<Event> events = manager.getEventsSortedByDate();

        if(events.isEmpty()){
            System.out.println("[Info] No current event");
            return;
        }

        int index = 1;
        System.out.println("====== Event Sorted By Date ======\n");
        for(var event: events){
            System.out.println("      Event " + (index ++));
            System.out.println("---------------------");
            printEvent(event);
        }
    }

    private void showAllEventsByPresenter(){
        if(manager.getAllPresenter().isEmpty()){
            System.out.println("[Info] Currently no presenter");
            return;
        }

        System.out.println("----- Get Event Info By Presenter -----\n");
        System.out.println("    Presenter");
        System.out.println("-----------------");
        for(Presenter presenter: manager.getAllPresenter()){
            System.out.println("+ ID: " + presenter.getId() + " | Full name: " + presenter.getFullName());
        }

        int id;
        while(true){
            System.out.print("Enter presenter ID: ");
            try{
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer" );
                continue;
            }
        }

        
        List<Event> events = manager.getEventsByPresenter(id);
        if(events.isEmpty()){
            System.out.println("[Info] No event found");
            return;
        }

        System.out.println("[Info] All events sorted by presenter name");
        int index = 1;
        for(var event: events){
            System.out.println("      Event " + (index ++));
            System.out.println("---------------------");
            printEvent(event);
        }

    }

    private void showAllEventsByType(){
        if(manager.getAllEvents().isEmpty()){
            System.out.println("[Info] No current event");
            return;
        }

        EventType type;

        System.out.println("Event type: ");
        for (EventType type1 : EventType.values()) {
            System.out.println("    " +(type1.ordinal() + 1) + ". " + type1.name());
        }

        while(true){
            try{
                System.out.print("Choose a type: ");
                int choice = Integer.parseInt(sc.nextLine().trim());

                if(choice < 1 || choice > EventType.values().length){
                    System.out.println("[Error] Index out of range.");
                    continue;
                }
                type = EventType.values()[choice-1];
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter an integer");
            }
        }

        List<Event> events = manager.getEventsByType(type);

        if(events.isEmpty()){
            System.out.println("[Info] No event found");
            return;
        }

        System.out.println("[Info] All events sorted by event type");
        int index = 1;
        for(var event: events){
            System.out.println("      Event " + (index ++));
            System.out.println("---------------------");
            printEvent(event);
        }

    }

    public void eventMenu(){
        System.out.print("\n[Success] Welcome to Event Management");
        int userChoice = -1;
        while(userChoice != 0 ){
            System.out.println();
            System.out.println("========== Event Management ==========");
            System.out.println("0. Back to main menu");
            System.out.println("1. Show all events info");
            System.out.println("2. Add event");
            System.out.println("3. Update event");
            System.out.println("4. Delete event");
            System.out.println("5. Get event info by ID");
            System.out.println("6. Get events sorted by date");
            System.out.println("7. Get event by presenter");
            System.out.println("8. Get event by type");

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
                    showAllEvents();
                    break;
                case 2:
                    addEvent();
                    break;
                case 3:
                    updateEvent();
                    break;
                case 4:
                    deleteEvent();
                    break;
                case 5:
                    getEventInfoById();
                    break;
                case 6:
                    showAllEventsSortedByDate();
                    break;
                case 7:
                    showAllEventsByPresenter();
                    break;
                case 8:
                    showAllEventsByType();
                    break;
            }
        }
    }
}

