package model;
import java.time.LocalDateTime;

/*
 * @author Dao Tien Dung - s4088577
 */

public class Person{
    protected int id;
    protected String fullName;
    protected LocalDateTime dateOfBirth;
    protected String contactInfomation;

    public Person(String fullName, LocalDateTime dateOfBirth, String contactInfomation){
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.contactInfomation = contactInfomation;
    }

    // GETTER
    public int getId(){
        return id;
    }

    public String getFullName(){
        return fullName;
    }
    
    public LocalDateTime getDateOfBirth(){
        return dateOfBirth;
    }

    public String getContactInformation(){
        return contactInfomation;
    }

    // SETTER
    public void setFullName(String newFullName){
        this.fullName = newFullName;
    }

    public void setDateOfBirth(LocalDateTime newDateOfBirth){
        this.dateOfBirth = newDateOfBirth;
    }

    public void setContactInfomation(String newContactInformation){
        this.contactInfomation = newContactInformation;
    }
}