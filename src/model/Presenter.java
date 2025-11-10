package model;
import java.time.*;
import java.util.*;
/*
 * @author Dao Tien Dung - s4088577
 */


public class Presenter extends Person {
    private String role;
    private List<Session> sessions;

    public Presenter(String fullName, LocalDateTime dateOfBirth, String contactInformation){
        super(fullName, dateOfBirth, contactInformation);
    }

    // GETTER
    public String getRole(){
        return role;
    }

    public List<Session> getSessions(){
        return sessions;
    }

}
