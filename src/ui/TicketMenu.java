package ui;

import java.util.List;
import java.util.Scanner;

import model.Attendee;
import model.Event;
import model.Ticket;
import model.TicketType;
import service.EventManager;

/*
 * Dao Tien Dung - s4088577
 * Ticket menu file for UI
 */

public class TicketMenu{
    private EventManager manager;
    private Scanner sc = new Scanner(System.in);

    public TicketMenu(EventManager manager){
        this.manager = manager;
    }

    private void showAllTicketsInfo(){
        List<Ticket> tickets = manager.getAllTickets();

        if(tickets.isEmpty()){
            System.out.println("[Info] No ticket found");
            return;
        }

        int index = 1;
        for(var ticket: tickets){
            System.out.println("      Ticket " + (index ++));
            System.out.println("---------------------");
            printTicket(ticket);
        }
    }

    private void printTicket(Ticket ticket){
        System.out.println("+ ID: " + ticket.getTicketId());
        System.out.println("+ Type: " + ticket.getType().name());
        System.out.println("+ Price: $" + ticket.getPrice());
        System.out.println("+ Attendee name: " + manager.getAttendeeById(ticket.getAttendee()).getFullName());
        System.out.println("+ Event name: " + manager.getEventById(ticket.getEvent()).getName());
        System.out.println("+ Status: " + ticket.getStatus().name());
        
        System.out.println();
    }

    private void updateTicket(){
        if(manager.getAllTickets().isEmpty()){
            System.out.println("[Info] No current ticket");
            return;
        }

        System.out.println("----- Update Ticket -----");
        for(Ticket ticket: manager.getAllTickets()){
            System.out.println("- ID: " + ticket.getTicketId() + " | Event name: " + manager.getEventById(ticket.getEvent()).getName());
        }

        System.out.print("Enter ticket ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Ticket ticket = manager.getTicketById(id);
        if(ticket == null){
            System.out.println("[Error] No ticket found");
            return;
        }
        
        int choice = -1;
        while(choice != 0){
            System.out.println();
            System.out.println("====== Update Ticket #" + id + " ======");
            System.out.println("- ID: " + ticket.getTicketId() + " | Type: " + ticket.getType().name() + " | Price: $" + ticket.getPrice() + " | Status: " + ticket.getStatus() +" | Event name: " + manager.getEventById(ticket.getEvent()).getName() + " | Attendee name: " +  manager.getAttendeeById(ticket.getAttendee()).getFullName());
            System.out.println("0. Return");
            System.out.println("1. Update ticket type");
            System.out.println("2. Update ticket event");
            System.out.println("3. Update ticket attendee");

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
                    for(TicketType type : TicketType.values()){
                        System.out.println((type.ordinal() + 1)  + ". " + type.name());
                    }

                    while(true){
                        try{
                            System.out.print("Enter new ticket type (1-3): ");
                            int indexType = Integer.parseInt(sc.nextLine().trim());

                            if(indexType < 1 || indexType > 3){
                                System.out.println("[Error] Invalid index. Please enter a number in range (1-3)");
                                continue;
                            }

                            ticket.setType(TicketType.values()[indexType - 1]);
                            ticket.setPrice(manager.getPriceByType(TicketType.values()[indexType - 1]));
                            System.out.println("[Success] Ticket type updated.");
                            break;

                        }catch(Exception e){
                            System.out.println("[Error] Invalid input. Please enter an integer.");
                        }
                    }
                    break;
                }
                case 2:
                    System.out.println("====== Available events ======");
                    for(Event event : manager.getAllEvents()){
                        System.out.println("+ Event ID: " + event.getEventId() + " | Name: " + event.getName());
                    }
                    while(true){
                        try{
                            System.out.print("Enter event ID for ticket: ");
                            int eventId = Integer.parseInt(sc.nextLine().trim());

                            if(manager.getEventById(eventId) == null){
                                System.out.println("[Info] No event found");
                                continue;
                            }

                            ticket.setEventId(eventId);
                            System.out.println("[Success] Updated event for ticket");
                            break;
                        }catch(Exception e){
                            System.out.println("[Error] Invalid input. Please enter an integer");
                        }   
                    }
                    break;
                case 3:
                    while(true){
                        for(Attendee attendee: manager.getAllAttendees()){
                            System.out.println("+ ID: " + attendee.getId() + " | Full name: " + attendee.getFullName());
                        }
                        try{
                            System.out.print("Enter ticket attendee ID: ");
                            int attendeeId = Integer.parseInt(sc.nextLine().trim());
                            
                            if(attendeeId <= 0){
                                System.out.println("[Error] Attendee ID must be an integer");
                            }

                            ticket.setAttendeeId(attendeeId);
                            System.out.println("[Success] Updated attendee ID");
                            break;
                        }catch(Exception e){
                            System.out.println("[Error] Invalid input. Please enter an integer");
                        }
                    }
            }
        }
        try{
            manager.updateTicket(ticket);
            System.out.println("[Success] Ticket ID #" + ticket.getTicketId() + " updated");
        }catch(Exception e){
            System.out.println("[Error] "+ e.getMessage());
        }
    }

    private void deleteTicket(){
        if(manager.getAllTickets().isEmpty()){
            System.out.println("[Info] No current ticket");
            return;
        }

        List<Ticket> tickets = manager.getAllTickets();

        System.out.println("----- Delete Ticket -----");
        for(Ticket ticket: tickets){
            System.out.println("- ID: " + ticket.getTicketId() + " | Evet name: " + manager.getEventById(ticket.getEvent()).getName());
        }
        int id = 0;
        while(true){
            System.out.print("Enter Ticket ID to delete: ");
            try{
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer" );
                continue;
            }
        }


        Ticket ticket = manager.getTicketById(id);
        if(ticket == null){
            System.out.println("[Error] No ticket found");
            return;
        }

        System.out.print("Confirmation for deleting ticket ID #" + id + " ? (Y for yes/ N for no): ");
        String input =  sc.nextLine().trim().toLowerCase();

        if(input.equalsIgnoreCase("y")){
            try{
                manager.deleteTicket(id);
                System.out.println("[Success] Delete ticket with ID #" + id);
            }catch(Exception e){
                System.out.println("[Error] "+ e.getMessage());
            }
        }
        else if (input.equalsIgnoreCase("n")){
            System.out.println("[Denied] No deletion for ticket ID #" + id);
        }
        else{
            System.out.println("[Error] Please enter 'Y' or 'N'");
        }
    }

    private void showAllTicketsInfoByPrice(){
        List<Ticket> tickets = manager.getTicketsSortedByPrice();

        if(tickets.isEmpty()){
            System.out.println("[Info] No ticket found");
            return;
        }

        int index = 1;
        for(var ticket: tickets){
            System.out.println("      Ticket " + (index ++));
            System.out.println("---------------------");
            printTicket(ticket);
        }
    }

    private void showAllInfoSortedByType(){
        for(TicketType type : TicketType.values()){
            System.out.println((type.ordinal() + 1)  + ". " + type.name());
        }

        int indexType;

        while(true){
            try{
                System.out.print("Enter new ticket type (1-3): ");
                indexType = Integer.parseInt(sc.nextLine().trim());

                if(indexType < 1 || indexType > 3){
                    System.out.println("[Error] Invalid index. Please enter a number in range (1-3)");
                    continue;
                }

                break;

            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter an integer.");
            }
        }


        List<Ticket> tickets = manager.getTicketsByType(TicketType.values()[indexType-1]);

        if(tickets.isEmpty()){
            System.out.println("[Info] No ticket found");
            return;
        }

        int index = 1;
        for(var ticket: tickets){
            System.out.println("      Ticket " + (index ++));
            System.out.println("---------------------");
            printTicket(ticket);
        }
    }



    public void ticketMenu(){
        System.out.print("\n[Success] Welcome to Ticket Management");
        int userChoice = -1;
        while(userChoice != 0 ){
            System.out.println();
            System.out.println("========== Ticket Management ==========");
            System.out.println("0. Back to main menu");
            System.out.println("1. Show all tickets info");
            System.out.println("2. Update ticket");
            System.out.println("3. Delete ticket");
            System.out.println("4. Show all tickets sorted by price");
            System.out.println("5. Show all tickets info by type");


            try{
                System.out.print("Enter your choice (0-5): ");
                
                String input = sc.nextLine();
                userChoice = Integer.parseInt(input);

                if(userChoice < 0 || userChoice > 5) throw new Exception("Index out of range");
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer (0-5).");
                continue;
            }

            switch (userChoice) {
                case 0:
                    System.out.println("[Returning] Back to Main Menu ...");
                    break;
                case 1:
                    showAllTicketsInfo();
                    break;
                case 2:
                    updateTicket();
                    break;
                case 3:
                    deleteTicket();
                    break; 
                case 4:
                    showAllTicketsInfoByPrice();
                    break;
                case 5:
                    showAllInfoSortedByType();
                    break;
            }
        }
    }
}