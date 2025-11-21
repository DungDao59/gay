package ui;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.Presenter;
import model.PresenterRole;
import model.Session;
import service.EventManager;

/*
 * Dao Tien Dung - s4088577
 * Presenter Menu file for UI
 */

public class PresenterMenu {
    private EventManager manager;
    private final Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public PresenterMenu(EventManager manager){
        this.manager = manager;
    }

    private void printPrensenter(Presenter presenter){
        System.out.println("+ ID: " + presenter.getId());
        System.out.println("+ Full Name: " + presenter.getFullName());
        System.out.println("+ Date of birth: " + presenter.getDateOfBirth().toString());
        System.out.println("+ Contact information: " + presenter.getContactInfomation());
        System.out.println("+ Role: " + presenter.getRole().name());
        System.out.println("+ Session registered: ");
        if(presenter.getSessions().isEmpty()){
            System.out.println("    - No session registered currently");
        }
        else{
            for(Session s: presenter.getSessions()){
                System.out.println("    - Session ID: " + s.getSessionId() + " | Title: " + s.getTitle() + " | Start time: " + s.getStartDateTime() + " | End time: " + s.getEndDateTime());
            }
        }

        System.out.println();
    }

    private void showAllPresenters(){
        List<Presenter> presenters = manager.getAllPresenter();

        if(presenters.isEmpty()){
            System.out.println("[Info] No presenter found");
            return;
        }

        int index = 1;
        for(var presenter: presenters){
            System.out.println("      Presenter " + (index ++));
            System.out.println("---------------------");
            printPrensenter(presenter);
        }
    }

    private void addPresenter(){
        System.out.println("----- Add New Prensenter -----");

        String fullName;
        LocalDateTime dob = null;
        String contactInfo;
        PresenterRole role;


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

        System.out.println();
        for(PresenterRole role1 : PresenterRole.values()){
            System.out.println(role1.ordinal() + 1 + ". " + role1.name());
        }

        while(true){
            System.out.print("Choose presenter role: ");
            int roleChoice = Integer.parseInt(sc.nextLine().trim());

            if(roleChoice < 1 || roleChoice > PresenterRole.values().length){
                System.out.println("[Error] Invalid choice. Please enter a valid integer");
                continue;
            }

            role = PresenterRole.values()[roleChoice - 1];
            break;
        }

        Presenter presenter = new Presenter(fullName, dob, contactInfo,role);

        try{
            manager.addPresenter(presenter);
            System.out.println("[Success] Presenter #" + presenter.getId() + " added");
        }catch(Exception e){
            System.out.println("[Error] "+ e.getMessage());
        }
        
    }

    private void updatePresenter(){
        if(manager.getAllPresenter().isEmpty()){
            System.out.println("[Info] No presenter currently");
            return;
        }
        System.out.println("----- Update Presenter -----");
        for(Presenter presenter: manager.getAllPresenter()){
            System.out.println("- ID: " + presenter.getId() + " | Full Name: " + presenter.getFullName());
        }

        System.out.print("Enter presenter ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Presenter presenter = manager.getPresenterById(id);
        if(presenter == null){
            System.out.println("[Error] No presenter found");
            return;
        }
        
        int choice = -1;
        while(choice != 0){
            System.out.println();
            System.out.println("====== Update Presenter #" + id + " ======");
            System.out.println("- ID: " + presenter.getId() + " | Full Name: " + presenter.getFullName() + " | Date of birth: " + presenter.getDateOfBirth() + " | Contact infomation: " + presenter.getContactInfomation() + " | Role: " + presenter.getRole().name());
            System.out.println("0. Return");
            System.out.println("1. Update full name");
            System.out.println("2. Update date of birth");
            System.out.println("3. Update contact information");
            System.out.println("4. Update role");


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
                        System.out.print("Enter new full name: ");
                        String name = sc.nextLine().trim();

                        if (!name.matches("[a-zA-Z ]+")) {
                            System.out.println("[Error] Name must contain only letters!");
                            continue;
                        }

                        presenter.setFullName(name);
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
                            presenter.setDateOfBirth(date.atStartOfDay());
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

                        presenter.setContactInfomation(contact);
                        System.out.println("[Success] Contact info updated.");
                        break;
                    }
                    break;
                }
                case 4:
                    for(PresenterRole role1 : PresenterRole.values()){
                        System.out.println(role1.ordinal() + 1 + ". " + role1.name());
                    }

                    while(true){
                        System.out.print("Choose new presenter role: ");
                        int roleChoice = Integer.parseInt(sc.nextLine().trim());

                        if(roleChoice < 1 || roleChoice > PresenterRole.values().length){
                            System.out.println("[Error] Invalid choice. Please enter a valid integer");
                            continue;
                        }

                        presenter.setPresenterRole(PresenterRole.values()[roleChoice - 1]);
                        break;
                    }
            }
        }
        try{
            manager.updatePresenter(presenter);
            System.out.println("[Success] Presenter ID #" + presenter.getId() + " updated");
        }catch(Exception e){
            System.out.println("[Error] "+ e.getMessage());
        }
    }

    private void deletePresenter(){
        if(manager.getAllPresenter().isEmpty()){
            System.out.println("[Info] No presenter currently");
            return;
        }

        System.out.println("----- Delete Presenter -----");
        for(Presenter presenter: manager.getAllPresenter()){
            System.out.println("- ID: " + presenter.getId() + " | Full Name: " + presenter.getFullName());
        }
        int id = 0;
        while(true){
            System.out.print("Enter Presenter ID to delete: ");
            try{
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            }catch(Exception e){
                System.out.println("[Error] " + e.getMessage() + ". Please enter an integer" );
                continue;
            }
        }


        Presenter presenter = manager.getPresenterById(id);
        if(presenter == null){
            System.out.println("[Error] No presenter found");
            return;
        }

        System.out.print("Confirmation for deleting presenter ID #" + id + " ? (Y for yes/ N for no): ");
        String input =  sc.nextLine().trim().toLowerCase();

        if(input.equalsIgnoreCase("y")){
            try{
                manager.deletePresenter(id);
                System.out.println("[Success] Delete presenter with ID #" + id);
            }catch(Exception e){
                System.out.println("[Error] "+ e.getMessage());
            }
        }
        else if (input.equalsIgnoreCase("n")){
            System.out.println("[Denied] No deletion for presenter ID #" + id);
        }
        else{
            System.out.println("[Error] Please enter 'Y' or 'N'");
        }
    }

    private void getPresenterInfoById(){
        if(manager.getAllPresenter().isEmpty()){
            System.out.println("[Info] No presenter currently");
            return;
        }

        System.out.println("----- Get Presenter Info by ID -----");
        for(Presenter presenter: manager.getAllPresenter()){
            System.out.println("- ID: " + presenter.getId() + " | Full Name: " + presenter.getFullName());
        }
        int id = 0;
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


        Presenter presenter = manager.getPresenterById(id);
        if(presenter == null){
            System.out.println("[Error] No presenter found");
            return;
        }

        System.out.println("\n------ Presenter ID #" + presenter.getId() + " ------");
        System.out.println("+ Full Name: " + presenter.getFullName());
        System.out.println("+ Date of birth: " + presenter.getDateOfBirth().toString());
        System.out.println("+ Contact information: " + presenter.getContactInfomation());
        System.out.println("+ Role: " + presenter.getRole().name());
        System.out.println("+ Session registered: ");
        if (presenter.getSessions().isEmpty()){
            System.out.println("    - No session registered currently");
        }
        else{
            for(Session s: presenter.getSessions()){
                System.out.println("    - Session ID: " + s.getSessionId() + " | Title: " + s.getTitle() + " | Start time: " + s.getStartDateTime() + " | End time: " + s.getEndDateTime());
            }
        }
    }

    private void getPresenterInfoByName(){
        if(manager.getAllPresenter().isEmpty()){
            System.out.println("[Info] No presenter currently");
            return;
        }

        System.out.println("----- Get Presenter Info by name -----");

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
        
        List<Presenter> presenters = manager.getPresentersByName(fullName);

        if(presenters.isEmpty()){
            System.out.println("[Info] No presenter name '" + fullName + "' found");
            return;
        }

        int index = 1;
        for(var presenter: presenters){
            System.out.println("      Presenter " + (index ++));
            System.out.println("---------------------");
            printPrensenter(presenter);
        }
    }

    private void registeredSession(){
        if(manager.getAllPresenter().isEmpty()){
            System.out.println("[Info] No presenter currently");
            return;
        }

        List<Session> sessions = manager.getAllSessions();
        if(sessions.isEmpty()){
            System.out.println("[Info] Currently no sessions for registered");
            return;
        }
        System.out.println("----- Registered Presenter to Session -----\n");

        System.out.println("====== Available Sessions ======");
        for(Session session: sessions){
            System.out.println("ID: " +  session.getSessionId() + " | Title: " + session.getTitle() + " | Start time:  " + session.getStartDateTime() + " | End time: " + session.getEndDateTime() + " | Venue: " + session.getVenue());
        }

        int presenterId, sessisonId;

        while(true){
            System.out.print("Enter presenter ID: ");
            try {
                presenterId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("[Error] Invalid input. Please enter an integer.");
            }
        }

        if(manager.getPresenterById(presenterId) == null){
            System.out.println("[Info] No presenter found");
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
            manager.registerPresenterToSession(presenterId,sessisonId);
            System.out.println("[Success] Registered presenter ID #" + presenterId + " to session #" + sessisonId );
        }catch(Exception e){
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private void removeSession(){
        if(manager.getAllPresenter().isEmpty()){
            System.out.println("[Info] No presenter currently");
            return;
        }

        System.out.println("----- Remove Presenter from Session -----\n");

        int presenterId, sessisonId;
        while(true){
            System.out.print("Enter presenter ID: ");
            try {
                presenterId = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("[Error] Invalid input. Please enter an integer.");
            }
        }
        
        Presenter presenter = manager.getPresenterById(presenterId);
        
        if(presenter == null){
            System.out.println("[Info] No presenter found.");
            return;
        }

        System.out.println();
        if(presenter.getSessions().isEmpty()){
            System.out.println("[Info] No current registered session");
            return;
        }

        System.out.println("====== Current registered session ======");
        for(Session session: presenter.getSessions()){
            System.out.println("ID: " +  session.getSessionId() + " | Title: " + session.getTitle() + " | Start time:  " + session.getStartDateTime() + " | End time: " + session.getEndDateTime() + " | Venue: " + session.getVenue());
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

        System.out.print("Confirmation for removing presenter ID #" + presenterId + " from session ID # " + sessisonId + " ? (Y for yes/ N for no): ");
        String input =  sc.nextLine().trim().toLowerCase();

        if(input.equalsIgnoreCase("y")){
            try{
                manager.removePresenterFromSession(presenterId, sessisonId);
                System.out.println("[Success] Remove presenter with ID # " + presenterId + " from session ID #" + sessisonId);
            }catch(Exception e){
                System.out.println("[Error] "+ e.getMessage());
            }
        }
        else if (input.equalsIgnoreCase("n")){
            System.out.println("[Denied] No removing for presenter ID #" + presenterId);
        }
        else{
            System.out.println("[Error] Please enter 'Y' or 'N'");
        }

    }

    public void presenterMenu(){
        System.out.print("\n[Success] Welcome to Presenter Management");
        int userChoice = -1;
        while(userChoice != 0 ){
            System.out.println();
            System.out.println("========== Presenter Management ==========");
            System.out.println("0. Back to main menu");
            System.out.println("1. Show all presenters info");
            System.out.println("2. Add presenter");
            System.out.println("3. Update presenter");
            System.out.println("4. Delete presenter");
            System.out.println("5. Get presenter info by ID");
            System.out.println("6. Get presenter info by name");
            System.out.println("7. Register presenter to session");
            System.out.println("8. Remove presenter from session");

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
                    showAllPresenters();
                    break;
                case 2:
                    addPresenter();
                    break;
                case 3:
                    updatePresenter();
                    break;
                case 4:
                    deletePresenter();
                    break;
                case 5:
                    getPresenterInfoById();
                    break;
                case 6:
                    getPresenterInfoByName();
                    break;
                case 7:
                    registeredSession();
                    break;
                case 8:
                    removeSession();
                    break;
            }
        }
    }
}
