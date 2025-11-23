package dao;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.Session;
import model.Ticket;
import model.Attendee;
import model.Event;
import model.Presenter;
import util.Database;

/*
 * Dao Tien Dung - s4088577
 * Report file
 */

public class ReportDao {
    // Set default date format for every date
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    // Export report to csv or txt file
    public void exportReport(String fileName, List<?> data){
        if(data == null || data.isEmpty()){
            System.out.println("[Error] No data to export");
            return;
        }

        try{
            Path folder = Paths.get("reports");
            if(!Files.exists(folder)){
                Files.createDirectories(folder);
            }

            Path pathfile = folder. resolve(fileName + ".txt");

            try(BufferedWriter writer = Files.newBufferedWriter(pathfile)){
                writer.write("===== Report Generated ===== \n\n");

                if(data == null || data.isEmpty()){
                    writer.write("[Empty] No data available");
                    return;
                }

                Object first = data.get(0);
                if(first instanceof Event){
                    writeEventReport(writer,(List<Event>)data);
                }
                else if(first instanceof Ticket){
                    writeTicketReport(writer,(List<Ticket>)data);
                }
                else if (first instanceof Session){
                    writeSessionReport(writer, (List<Session>)data);
                }
                else{
                    writer.write("[Error] Unsupported data type for export. Only Event or Ticket or Session are supported.\n");
                    System.out.println("[Error] Unsupported data type for export. Only Event or Session or Ticket are supported.");
                    return;
                }
                writer.flush();
                System.out.println("[Success] Report export to: " + folder.toAbsolutePath());

            }
        }catch(Exception e){
            System.out.println("[Error] Failed to export report: " + e.getMessage());
        }
    }

    private void writeEventReport(BufferedWriter writer, List<model.Event> events) throws IOException{
        writer.write("=== Event Report === \n");
        for(model.Event event:events){
            writer.write("+ ID: " + event.getEventId());
            writer.write("\n");
            writer.write("+ Name: " + event.getName());
            writer.write("\n");
            writer.write("+ Type: " + event.getType().name());
            writer.write("\n");
            writer.write("+ Location: " + event.getLocation());
            writer.write("\n");
            writer.write("+ Start date: " + event.getStartDate());
            writer.write("\n");
            writer.write("+ End date: " + event.getEndDate());
            writer.write("\n");
            writer.write("+ Session: ");
            writer.write("\n");
            if(event.getSessions() == null ||event.getSessions().isEmpty()){
                writer.write("    - [Info] No session available for this event");
                writer.write("\n");
            }
            else{
                for(Session session: event.getSessions()){
                    writer.write("    - Session ID: " + session.getSessionId() + " | Title: " + session.getTitle() + " | Start time: " + session.getStartDateTime() +  " | End time: " +  session.getEndDateTime() + " | Venue: " + session.getVenue());
                    writer.write("\n");
                }
            }

            writer.write("\n");
        }
    }

    private void writeTicketReport(BufferedWriter writer, List<Ticket> tickets) throws IOException{
        writer.write("=== Ticket Report === \n");
        for(Ticket ticket:tickets){
            writer.write("+ ID: " + ticket.getTicketId());
            writer.write("\n");
            writer.write("+ Type: " + ticket.getType().name());
            writer.write("\n");
            writer.write("+ Price: $" + ticket.getPrice());
            writer.write("\n");
            writer.write("+ Attendee ID: " + ticket.getAttendee());
            writer.write("\n");
            writer.write("+ Event ID: " + ticket.getEvent());
            writer.write("\n");
            writer.write("+ Status: " + ticket.getStatus().name());
            writer.write("\n");

            writer.write("\n");
        }
    }

    private void writeSessionReport(BufferedWriter writer, List<Session> sessions) throws IOException{
        writer.write("=== Session Report === \n");
        for(Session session:sessions){
            writer.write("+ ID: " + session.getSessionId());
            writer.write("\n");
            writer.write("+ Title: " + session.getTitle());
            writer.write("\n");
            writer.write("+ Description: " + session.getDescription());
            writer.write("\n");
            writer.write("+ Venue: " + session.getVenue());
            writer.write("\n");
            writer.write("+ Capacity: " + session.getCapacity());
            writer.write("\n");
            writer.write("+ Start time: " + session.getStartDateTime());
            writer.write("\n");
            writer.write("+ End time: " + session.getEndDateTime());
            writer.write("\n");
            writer.write("+ Event: " + session.getEventId());
            writer.write("\n");
            writer.write("+ Attendees:");
            writer.write("\n");
            if(session.getAttendees() == null || session.getAttendees().isEmpty()){
                writer.write("    - [Info] No attendee available for this session");
                writer.write("\n");
            }
            else{
                for(Attendee attendee: session.getAttendees()){
                    writer.write("    - Attendee ID: " + attendee.getId() + " | Full Name: " + attendee.getFullName());
                    writer.write("\n");
                }
            }

            writer.write("+ Presenters");
            writer.write("\n");
            if(session.getPresenters() == null || session.getPresenters().isEmpty()){
                writer.write("    - [Info] No presenter available for this session");
                writer.write("\n");
            }
            else{
                for(Presenter presenter: session.getPresenters()){
                    writer.write("    - Presenter ID: " + presenter.getId() + " | Full Name: " + presenter.getFullName());
                    writer.write("\n");
                }
            }

            writer.write("\n");
        }
    }

    public void exportEventsByType(String filename, List<Event> eventsForType){
        exportReport(filename, eventsForType);
    }

    public void exportEventsByDateSorted(String filename, List<Event> sortedEvents){
        exportReport(filename, sortedEvents);
    }

    public void exportTicketsByType(String filename, List<Ticket> ticketsForType){
        exportReport(filename, ticketsForType);
    }

    public void exportSessionByDate(String filename, List<Session> sessions){
        exportReport(filename, sessions);
    }

    public void exportSessionByPresenter(String filename, List<Session> sessions){
        exportReport(filename, sessions);
    }
}
