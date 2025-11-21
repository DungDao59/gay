package ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.Attendee;
import model.Event;
import model.Session;
import model.Ticket;
import model.TicketType;
import service.EventManager;

/*
 * Dao Tien Dung - s4088577
 * Attendee Menu file for UI 
 */

public class AttendeeMenu {
    private EventManager manager;
    private final Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public AttendeeMenu(EventManager manager){
        this.manager = manager;
    }

    private void printAttendee(Attendee attendee){
        System.out.println("+ ID: " + attendee.getId());
        System.out.println("+ Full Name: " + attendee.getFullName());
        System.out.println("+ Date of birth: " + attendee.getDateOfBirth().toString());
        System.out.println("+ Contact information: " + attendee.getContactInfomation());
        System.out.println("+ Session registered: ");
        if(attendee.getRegisteredSession().isEmpty()){
            System.out.println("    - No session registered currently");
        }
        else{
            for(Session s: attendee.getRegisteredSession()){
                System.out.println("    - Session ID: " + s.getSessionId() + " | Title: " + s.getTitle() + " | Start date: " + s.getStartDateTime() + " | End date: " + s.getEndDateTime());
            }
        }

        System.out.println("+ Purchased ticket: ");
        if(attendee.getTickets().isEmpty()){
            System.out.println("    - No session registered currently");
        }else{
            for(Ticket ticket: attendee.getTickets()){
                System.out.println("    - Ticket ID: " + ticket.getTicketId() + " | Type: " + ticket.getType().name() + " | Status: " + ticket.getStatus().name());
            }
        }
        
        System.out.println();
    }

    private void showAllAttendees(){
        List<Attendee> attendees = manager.getAllAttendees();

        if(attendees.isEmpty()){
            System.out.println("[Info] No attendees found");
            return;
        }

        int index = 1;
        for(var attendee: attendees){
            System.out.println("      Attendee " + (index ++));
            System.out.println("---------------------");
            printAttendee(attendee);
            
        }
    }
    
    private void addAttendee(){
        System.out.println("----- Add New Attendee -----");

        String fullName;
        LocalDateTime dob = null;
        String contactInfo;


        while(true){
            System.out.print("Enter full name: ");
            fullName = sc.nextLine().trim();

            if (!fullName.matches("[a-zA-Z ]+")) {
                System.out.println("[Error] Name must contain only letters!");
                continue;
            }

            if(!fullName.isEmpty()) break;
            
            System.out.println("[Error] Full name can't be empty");
        }

        while(true){
            System.out.print("Enter date of birth (DD/MM/YYYY): ");
            String input = sc.nextLine().trim();

            try{
                LocalDate date = LocalDate.parse(input, formatter);
                if(date.isAfter(LocalDate.now())){
                    System.out.println("[Error] Date of birth cannot be in the future!");
                    continue;
                }
                dob = date.atStartOfDay();
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid date. Please enter the correct format DD/MM/YYYY (Example: 31/12/2025)");
            }
        }

        while(true){
            System.out.print("Enter contact information: ");
            contactInfo = sc.nextLine().trim();
            if (!contactInfo.isEmpty()) break;
            System.out.println("[Error] Contact information cannot be empty.");
        }

        Attendee attendee = new Attendee(fullName, dob, contactInfo);
        try{
            manager.addAttendee(attendee);
            System.out.println("[Success] Attendee #" + attendee.getId() + " added");
        }catch(Exception e){
            System.out.println("[Error] "+ e.getMessage());
        }
        
    }
    
    private void updateAttendee(){
        if(manager.getAllAttendees().isEmpty()){
            System.out.println("[Info] No current attendee");
            return;
        }

        System.out.println("----- Update Attendee -----");
        for(Attendee attendee : manager.getAllAttendees()){
            System.out.println("- ID: " + attendee.getId() + " | Full Name: " + attendee.getFullName());
        }
        System.out.print("Enter attendee ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Attendee attendee = manager.getAttendeeById(id);
        if(attendee == null){
            System.out.println("[Error] No attendee found");
            return;
        }
        
        int choice = -1;
        while(choice != 0){
            System.out.println("====== Update Attendee #" + id + " ======");
            System.out.println("- ID: " + attendee.getId() + " | Full Name: " + attendee.getFullName() + " | Date of birth: " + attendee.getDateOfBirth() + " | Contact infomation: " + attendee.getContactInfomation());
            System.out.println("0. Return");
            System.out.println("1. Update full name");
            System.out.println("2. Update date of birth");
            System.out.println("3. Update contact information");

            System.out.print("Enter your choice (0-3): ");
            try{
                choice = Integer.parseInt(sc.nextLine().trim());

                if(choice < 0 || choice > 3){
                    throw new Exception();
                } 
            }catch(Exception e){
                System.out.println("[Error] Invalid option. Please enter a number (0-3).");
                continue;
            }

            switch(choice){
                case 0:
                    System.out.println("[Returning] Returning ...");
                    break;
                case 1:{
                    while(true){
                        System.out.print("Enter new full name: ");
                        String name = sc.nextLine().trim();

                        if (!name.matches("[a-zA-Z ]+")) {
                            System.out.println("[Error] Name must contain only letters!");
                            continue;
                        }

                        attendee.setFullName(name);
                        System.out.println("[Success] Full name updated.");
                        break;
                    }
                    break;
                }
                case 2:{
                    while (true) {
                        System.out.print("Enter date of birth (DD/MM/YYYY): ");
                        String input = sc.nextLine().trim();

                        try {
                            LocalDate date = LocalDate.parse(input, formatter);
                            if (date.isAfter(LocalDate.now())) {
                                System.out.println("[Error] Date of birth cannot be in the future!");
                                continue;
                            }
                            attendee.setDateOfBirth(date.atStartOfDay());
                            System.out.println("[Success] Date of birth updated.");
                            break;

                        } catch (Exception e) {
                            System.out.println("[Error] Invalid date format. Example: 31/12/2020");
                        }
                    }
                    break;
                }
                case 3:{
                    while (true) {
                        System.out.print("Enter new contact info: ");
                        String contact = sc.nextLine().trim();

                        if (contact.isEmpty()) {
                            System.out.println("[Error] Contact info cannot be empty.");
                            continue;
                        }

                        attendee.setContactInfomation(contact);
                        System.out.println("[Success] Contact info updated.");
                        break;
                    }
                    break;
                }
            }
        }
        try{
            manager.updateAttendee(attendee);
            System.out.println("[Success] Attendee ID #" + attendee.getId() + " updated");
        }catch(Exception e){
            System.out.println("[Error] "+ e.getMessage());
        }
    }

    private void deleteAttendee(){
        if(manager.getAllAttendees().isEmpty()){
            System.out.println("[Info] No current attendee");
            return;
        }

        System.out.println("----- Delete Attendee -----");
        for(Attendee attendee : manager.getAllAttendees()){
            System.out.println("- ID: " + attendee.getId() + " | Full Name: " + attendee.getFullName());
        }
        int id = 0;
        while(true){
            System.out.print("Enter attendee ID to delete: ");
            try{
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer" );
                continue;
            }
        }


        Attendee attendee = manager.getAttendeeById(id);
        if(attendee == null){
            System.out.println("[Error] No attendee found");
            return;
        }

        System.out.print("Confirmation for deleting attendee ID #" + id + " ? (Y for yes/ N for no): ");
        String input =  sc.nextLine().trim().toLowerCase();

        if(input.equalsIgnoreCase("y")){
            try{
                manager.deleteAttendee(id);
                System.out.println("[Success] Delete attendee with ID #" + id);
            }catch(Exception e){
                System.out.println("[Error] "+ e.getMessage());
            }
        }
        else if (input.equalsIgnoreCase("n")){
            System.out.println("[Denied] No deletion for attendee ID #" + id);
        }
        else{
            System.out.println("[Error] Please enter 'Y' or 'N'");
        }
    }

    private void getAttendeeInfoById(){
        if(manager.getAllAttendees().isEmpty()){
            System.out.println("[Info] No current attendee");
            return;
        }

        System.out.println("----- Get Attendee Info by ID -----");
        int id = 0;
        while(true){
            System.out.print("Enter attendee ID: ");
            try{
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer" );
                continue;
            }
        }


        Attendee attendee = manager.getAttendeeById(id);
        if(attendee == null){
            System.out.println("[Error] No attendee found");
            return;
        }

        System.out.println("\n------ Attendee ID #" + attendee.getId() + " ------");
        System.out.println("+ Full Name: " + attendee.getFullName());
        System.out.println("+ Date of birth: " + attendee.getDateOfBirth().toString());
        System.out.println("+ Contact information: " + attendee.getContactInfomation());
        System.out.println("+ Session registered: ");
        if(attendee.getRegisteredSession().isEmpty()){
            System.out.println("    - No session registered currently");
        }
        else{
            for(Session s: attendee.getRegisteredSession()){
                System.out.println("    - Session ID: " + s.getSessionId() + " | Title: " + s.getTitle() + " | Start date: " + s.getStartDateTime() + " | End date: " + s.getEndDateTime());
            }
        }

        System.out.println("+ Purchased ticket: ");
        if(attendee.getTickets().isEmpty()){
            System.out.println("    - No session registered currently");
        }else{
            for(Ticket ticket: attendee.getTickets()){
                System.out.println("    - Ticket ID: " + ticket.getTicketId() + " | Type: " + ticket.getType().name() + " | Status: " + ticket.getStatus().name());
            }
        }
    }

    private void getAttendeesInfoByName(){
        if(manager.getAllAttendees().isEmpty()){
            System.out.println("[Info] No current attendee");
            return;
        }

        System.out.println("----- Get Attendee Info by name -----");

        String fullName;
        while(true){
            System.out.print("Enter full name: ");
            fullName = sc.nextLine().trim();

            if (!fullName.matches("[a-zA-Z ]+")) {
                System.out.println("[Error] Name must contain only letters!");
                continue;
            }

            if(!fullName.isEmpty()) break;
            
            System.out.println("[Error] Full name can't be empty");
        }
        
        List<Attendee> attendees = manager.getAttendeesByName(fullName);

        if(attendees.isEmpty()){
            System.out.println("[Info] No attendee name '" + fullName + "' found");
            return;
        }

        int index = 1;
        for(var attendee: attendees){
            System.out.println("      Attendee " + (index ++));
            System.out.println("---------------------");
            printAttendee(attendee);
        }
    }

    private void registeredSession(){
        if(manager.getAllAttendees().isEmpty()){
            System.out.println("[Info] No current attendee");
            return;
        }

        List<Session> sessions = manager.getAllSessions();
        if(sessions.isEmpty()){
            System.out.println("[Info] Currently no sessions for registered");
            return;
        }
        System.out.println("----- Registered Attendee to Session -----\n");

        System.out.println("====== Available Sessions ======");
        for(Session session: sessions){
            System.out.println("ID: " +  session.getSessionId() + " | Title: " + session.getTitle() + " | Start time:  " + session.getStartDateTime() + " | End time: "+ session.getEndDateTime() + " | Venue: " + session.getVenue());
        }

        int attendeeId, sessisonId;

        while(true){
            System.out.print("Enter attendee ID: ");
            try {
                attendeeId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("[Error] Invalid input. Please enter an integer.");
            }
        }

        if(manager.getAttendeeById(attendeeId) == null){
            System.out.println("[Info] No attendee found");
            return;
        }

        while(true){
            System.out.print("Enter session ID: ");
            try {
                sessisonId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("[Error] Invalid input. Please enter an integer.");
            }
        }

        if(manager.getSessionById(sessisonId) == null){
            System.out.println("[Info] No session found");
            return;
        }

        try{
            manager.registerAttendeeToSession(attendeeId,sessisonId);
            System.out.println("[Success] Registered attendee #" + attendeeId + " to session #" + sessisonId );
        }catch(Exception e){
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private void attendeePurchaseTicket(){
        if(manager.getAllAttendees().isEmpty()){
            System.out.println("[Info] No current attendee");
            return;
        }

        List<Event> events = manager.getAllEvents();
        if(events.isEmpty()){
            System.out.println("[Info] No events available");
            return;
        }

        System.out.println("------ Purchase Ticket ------ \n");

        System.out.print("====== Available Events ======");
        for(Event event: events){
            System.out.println("+ Event ID: "+ event.getEventId() + " | Name: " + event.getName() + " | Location: " + event.getLocation() + " | Start date: " + event.getStartDate() + " | End date: " + event.getEndDate());
        }

        int attendeeId, eventId;
        while(true){
            try{
                System.out.print("Enter attendee ID: ");
                attendeeId = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){    
                System.out.println("[Error] Invalid input. Please enter a number");
            }
        }

        if(manager.getAttendeeById(attendeeId) == null){
            System.out.println("[Info] No attendee found");
            return;
        }


        while(true){
            try{
                System.out.print("Enter event ID: ");
                eventId = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){    
                System.out.println("[Error] Invalid input. Please enter a number");
            }
        }

        if(manager.getEventById(eventId) == null){
            System.out.println("[Info] No event found");
            return;
        }

        TicketType type;
        System.out.println();
        for(TicketType t : TicketType.values()){
            System.out.println((t.ordinal() + 1 ) + ". " + t.name());
        }

        while (true){
            try{
                System.out.print("Choose ticket type: ");
                int typeChoice = Integer.parseInt(sc.nextLine().trim());

                if(typeChoice < 1 || typeChoice > TicketType.values().length){
                    System.out.println("[Error] Invalid choice. Please enter a valid integer");
                    continue;
                }

                type = TicketType.values()[typeChoice - 1];
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter an integer");
            }
        }

        boolean success = manager.attendeePurchaseTicket(attendeeId, eventId,type);

        if(success){
            System.out.println("[Success] Ticket purchased");
        }
        else{
            System.out.println("[Error] Failed to purchase ticket");
        }
    }

    public void attendeeMenu(){
        System.out.print("\n[Success] Welcome to Attendee Management");
        int userChoice = -1;
        while(userChoice != 0 ){
            System.out.println();
            System.out.println("========== Attendee Management ==========");
            System.out.println("0. Back to main menu");
            System.out.println("1. Show all attendees info");
            System.out.println("2. Add attendee");
            System.out.println("3. Update attendee");
            System.out.println("4. Delete attendee");
            System.out.println("5. Get attendee info by ID");
            System.out.println("6. Get attendee info by name");
            System.out.println("7. Register attendee to session");
            System.out.println("8. Purchase ticket");

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
                    System.out.println("[Returning] Back to main menu ...");
                    break;
                case 1:
                    showAllAttendees();
                    break;
                case 2:
                    addAttendee();
                    break;
                case 3:
                    updateAttendee();
                    break;
                case 4:
                    deleteAttendee();
                    break;
                case 5:
                    getAttendeeInfoById();
                    break;
                case 6:
                    getAttendeesInfoByName();
                    break;
                case 7: 
                    registeredSession();
                    break;
                case 8:
                    attendeePurchaseTicket();
                    break;
            }
        }
        
    }
}
