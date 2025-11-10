package model;
import java.util.*;

/*
 * @author Dao Tien Dung - s4088577
 */


public class Schedule {
    private int scheduleId;
    private List<Session> sessions;

    public Schedule(){};

    // GETTER 
    public int getScheduleId(){
        return scheduleId;
    }

    public List<Session> getSessions(){
        return sessions;
    }
}
