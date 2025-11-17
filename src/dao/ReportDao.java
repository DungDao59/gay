package dao;

import java.sql.*;
import java.util.*;
import java.time.format.DateTimeFormatter;

import util.Database;

/*
 * Dao Tien Dung - s4088577
 * Report CRUD file
 */

public class ReportDao {
    // Set default date format for every date
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Get connection to the database
    private Connection getConnection() throws SQLException {
        return Database.getConnection();
    }


    // Export report to csv or txt file
    public void exportReport(String fileName, List<?> data){
        return;
    }

}
