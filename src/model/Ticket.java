package model;

/*
 * @author Dao Tien Dung - s4088577
 * Ticket class for ticket
 */

public class Ticket {
    private int ticketId;
    private TicketType type;
    private double price;
    private StatusType status;
    private int attendeeId;
    private int eventId;

    public Ticket(TicketType type, double price, StatusType status, int attendeeId, int eventId){
        this.type = type;
        this.price = price;
        this.status = status;
        this.attendeeId = attendeeId;
        this.eventId = eventId;
    }

    // GETTER
    public int getTicketId(){
        return ticketId;
    }

    public TicketType getType(){
        return type;
    }

    public double getPrice(){
        return price;
    }

    public StatusType getStatus(){
        return status;
    }

    public int getAttendee(){
        return attendeeId;
    }

    public int getEvent(){
        return eventId;
    }

    // SETTER
    public void setTicketId(int id){
        ticketId = id;
    }
}
