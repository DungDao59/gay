package model;
import java.time.*;
import java.util.*;
/*
 * @author Dao Tien Dung - s4088577
 * Presenter inherits Person based class
 */


public class Presenter extends Person {
    private PresenterRole role;
    private List<Session> sessions = new ArrayList<>();

    public Presenter(String fullName, LocalDateTime dateOfBirth, String contactInformation, PresenterRole role){
        super(fullName, dateOfBirth, contactInformation);
        this.role = role;
    }

    // GETTER
    public PresenterRole getRole(){
        return role;
    }

    public List<Session> getSessions(){
        return sessions;
    }

    // SETTER 
    public void setPresenterRole(PresenterRole newRole){
        role = newRole;
    }

}
