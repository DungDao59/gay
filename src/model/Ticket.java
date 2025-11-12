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
    private Attendee attendee;
    private Event event;

    public Ticket(TicketType type, double price, StatusType status, Attendee attendee, Event event){
        this.type = type;
        this.price = price;
        this.status = status;
        this.attendee = attendee;
        this.event = event;
    }

    // GETTER
    public TicketType getType(){
        return type;
    }

    public double getPrice(){
        return price;
    }

    public StatusType getStatus(){
        return status;
    }

    public Attendee getAttendee(){
        return attendee;
    }

    public Event getEvent(){
        return event;
    }
}
