package ui;

import java.util.*;
/*
 * Dao Tien Dung - s4088577
 * Main menu file for UI
 */

import service.EventManager;
import service.EventManagerImpl;


public class MainMenu {
    private final EventManager manager = new EventManagerImpl();
    private final Scanner sc = new Scanner(System.in);

    public void mainMenu(){
        int userChoice = -1;
        while(userChoice != 0){
            System.out.println("===================================");
            System.out.println("      EVENT MANAGER SYSTEM");
            System.out.println("===================================");
            System.out.println("0. Exit the program");
            System.out.println("1. Manage Attendees");
            System.out.println("2. Manage Presenters");
            System.out.println("3. Manage Events");
            System.out.println("4. Manage Sessions");
            System.out.println("5. Manage Tickets");
            System.out.println("6. Export Reports");

            try{
                System.out.print("Enter your choice (0-6): ");

                String input = sc.nextLine();
                userChoice = Integer.parseInt(input);

                if(userChoice < 0 || userChoice > 6) throw new Exception("Index out of range");
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an Integer from (0-6)");
            }

            switch (userChoice) {
                case 0:
                    System.out.println("[Goodbye] Thanks for using Event Manager System.");
                    System.exit(userChoice);
                case 1:
                    new AttendeeMenu(manager).attendeeMenu();
                    break;
                case 2: 
                    new PresenterMenu(manager).presenterMenu();
                    break;
                case 3:
                    new EventMenu(manager).eventMenu();
                case 4: 
                    new SessionMenu(manager).sessionMenu();
                    break;
                case 5:
                    new TicketMenu(manager).ticketMenu();
                    break;
                case 6: 
                    new ReportMenu(manager).reportMenu();
                    break;
            
                default:
                    break;
            }
            System.out.println();
            System.out.println("[Success] Back to Main Menu");
        }
    }
}
