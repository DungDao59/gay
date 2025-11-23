package ui;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import model.EventType;
import model.Presenter;
import model.Session;
import model.Ticket;
import model.TicketType;
import model.Event;
import service.EventManager;

/*
 * Dao Tien Dung - s4088577
 * Ticket menu file for UI
 */

public class ReportMenu {
    private EventManager manager;
    private final Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ReportMenu(EventManager manager){
        this.manager = manager;
    }

    private void exportEventByType(){
        System.out.println("----- Export event by type -----");
        for(EventType type : EventType.values()){
            System.out.println((type.ordinal() + 1) + ". " + type.name());
        }
        int index;
        String filename;
        while(true){
            try{
                System.out.print("Enter type index (1-4): ");
                index = Integer.parseInt(sc.nextLine().trim());

                if(index < 1 || index > 4){
                    System.out.println("[Error] Index out of range. Please enter a valid number");
                    continue;
                }

                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter an integer");
                continue;
            }
        }

        List<Event> events = manager.getEventsByType(EventType.values()[index-1]);

        if(events.isEmpty()){
            System.out.println("[Error] No event found !");
            return;
        }

        while(true){
            System.out.print("Enter file name: ");
            filename = sc.nextLine().trim();
            
            if(filename.matches(".*[\\\\/:*?\"<>|].*")){
                System.out.println("[Error] File name contains invalid characters.");
                continue;
            }

            if(!filename.isEmpty())break;

            System.out.println("[Error File name can't be empty");
        }

        try {
            manager.exportEventsByType(filename, events);
            System.out.println("[Success] Exported events by type complete");
        } catch(Exception e) {
            System.out.println("[Error] Failed to export events: " + e.getMessage());
        }    
    }

    private void exportEventSortedByDate(){
        List<Event> events = manager.getEventsSortedByDate();

        if(events.isEmpty()){
            System.out.println("[Info] No current event for export");
        }

        System.out.println("----- Export event sorted by date -----");

        String filename;
        while(true){
            System.out.print("Enter file name: ");
            filename = sc.nextLine().trim();
            
            if(filename.matches(".*[\\\\/:*?\"<>|].*")){
                System.out.println("[Error] File name contains invalid characters.");
                continue;
            }

            if(!filename.isEmpty())break;

            System.out.println("[Error File name can't be empty");
        }

        try {
            manager.exportEventsByDateSorted(filename, events);
            System.out.println("[Success] Exported events sorted by date complete");
        } catch(Exception e) {
            System.out.println("[Error] Failed to export events: " + e.getMessage());
        }    
    }

    private void exportTicketsByType(){
        System.out.println("----- Export ticket by type -----");
        for(TicketType type : TicketType.values()){
            System.out.println((type.ordinal() + 1) + ". " + type.name());
        }
        int index;
        String filename;
        while(true){
            try{
                System.out.print("Enter type index (1-4): ");
                index = Integer.parseInt(sc.nextLine().trim());

                if(index < 1 || index > 4){
                    System.out.println("[Error] Index out of range. Please enter a valid number");
                    continue;
                }

                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter an integer");
                continue;
            }
        }

        List<Ticket> tickets = manager.getTicketsByType(TicketType.values()[index-1]);

        if(tickets.isEmpty()){
            System.out.println("[Error] No ticket found !");
            return;
        }

        while(true){
            System.out.print("Enter file name: ");
            filename = sc.nextLine().trim();
            
            if(filename.matches(".*[\\\\/:*?\"<>|].*")){
                System.out.println("[Error] File name contains invalid characters.");
                continue;
            }

            if(!filename.isEmpty())break;

            System.out.println("[Error File name can't be empty");
        }

        try {
            manager.exportTicketsByType(filename, tickets);
            System.out.println("[Success] Exported tickets by type complete");
        } catch(Exception e) {
            System.out.println("[Error] Failed to export ticket: " + e.getMessage());
        }    
    }

    private void exportSessionByDate(){
        System.out.println("----- Export session by date -----");
        LocalDateTime startDate,endDate;
        String filename;

        while(true){
            System.out.print("Enter start date (DD/MM/YYYY): ");
            String input = sc.nextLine().trim();

            try{
                startDate = LocalDateTime.parse(input);
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter valid date (01/12/2023");
            }
        }

        while(true){
            System.out.print("Enter end date (DD/MM/YYYY): ");
            String input = sc.nextLine().trim();

            try{
                endDate = LocalDateTime.parse(input);
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter valid date (01/12/2023)");
            }
        }
        

        List<Session> sessions = manager.getSessionsByDate(startDate,endDate);

        if(sessions.isEmpty()){
            System.out.println("[Error] No session found !");
            return;
        }

        while(true){
            System.out.print("Enter file name: ");
            filename = sc.nextLine().trim();
            
            if(filename.matches(".*[\\\\/:*?\"<>|].*")){
                System.out.println("[Error] File name contains invalid characters.");
                continue;
            }

            if(!filename.isEmpty())break;

            System.out.println("[Error File name can't be empty");
        }

        try {
            manager.exportSessionByDate(filename, sessions);
            System.out.println("[Success] Exported sessions by date complete");
        } catch(Exception e) {
            System.out.println("[Error] Failed to export session: " + e.getMessage());
        }    
    }
    
    private void exportSessionByPresenter(){
        System.out.println("----- Export session by presenter -----");
        int presenterId;
        String filename;

        for(Presenter presenter : manager.getAllPresenter()){
            System.out.println("+ ID:" + presenter.getId() + " | Full name: " + presenter.getFullName());
        }

        while(true){
            try{
                System.out.print("Enter presenter ID: ");
                presenterId = Integer.parseInt(sc.nextLine().trim());

                if(presenterId <= 0) {
                    System.out.println("[Error] Presenter ID must be postive");
                    continue;
                }
                break;
            }catch(Exception e){
                System.out.println("[Error] Invalid input. Please enter an integer");
            }
        }

        List<Session> sessions = manager.getSessionsByPresenter(presenterId);

        if(sessions.isEmpty()){
            System.out.println("[Info] No sessions found for this presenter");
            return;
        }


        while(true){
            System.out.print("Enter file name: ");
            filename = sc.nextLine().trim();
            
            if(filename.matches(".*[\\\\/:*?\"<>|].*")){
                System.out.println("[Error] File name contains invalid characters.");
                continue;
            }

            if(!filename.isEmpty())break;

            System.out.println("[Error File name can't be empty");
        }

        try {
            manager.exportSessionByPresenter(filename, sessions);
            System.out.println("[Success] Exported session by presenter complete");
        } catch(Exception e) {
            System.out.println("[Error] Failed to export sessions: " + e.getMessage());
        }    
    }



    public void reportMenu(){
        System.out.print("\n[Success] Export information to file");
        int userChoice = -1;
        while(userChoice != 0 ){
            System.out.println();
            System.out.println("========== Export Info ==========");
            System.out.println("0. Back to main menu");
            System.out.println("1. Export event by type");
            System.out.println("2. Export event sorted by date");
            System.out.println("3. Export tickets by type");
            System.out.println("4. Export sessions by date");
            System.out.println("5. Export sessions by presenter");

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
                    exportEventByType();
                    break;
                case 2:
                    exportEventSortedByDate();
                    break;
                case 3:
                    exportTicketsByType();
                    break;
                case 4:
                    exportSessionByDate();
                    break;
                case 5:
                    exportSessionByPresenter();
                    break;
            }
        }
    }
}
