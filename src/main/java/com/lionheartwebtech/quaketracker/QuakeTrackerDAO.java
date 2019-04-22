package com.lionheartwebtech.quaketracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class QuakeTrackerDAO {

    private static Connection conn;
    private static final Logger logger = Logger.getLogger(QuakeTrackerDAO.class.getName());

    public static void init() {

        String connString = "jdbc:mysql://";
        connString += "lionheartwebtech-db.cv18zcsjzteu.us-west-2.rds.amazonaws.com:3306";
        connString += "/orcas";
        connString += "?user=orcas&password=orcas";
        //connString += "&useSSL=true&trustServerCertificate=true";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(connString);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private static int getRowCount(ResultSet rs) throws SQLException {
        int i = 0;
        while (rs.next()) {
            //System.out.println("Loop running");
            i++;
        }
        return i;
    }

    public static boolean userExists(Connection conn, String email) throws SQLException {
        String query = "SELECT * FROM users WHERE emails = ?";
        ResultSet rs = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, email);
            rs = stmt.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return (rs.next());
        /*String query = "SELECT * FROM users WHERE emails = ?";
        ResultSet rs = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            rs = stmt.executeQuery();
            return !(!rs.isBeforeFirst() && rs.getRow() == 0);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;*/
        

    }
    public static boolean userExists(int userID) throws SQLException {
        String query = "SELECT * FROM users WHERE userid = ?";
        ResultSet rs = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userID);

            rs = stmt.executeQuery();
            return !(!rs.isBeforeFirst() && rs.getRow() == 0);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;

    }

    
    public static Member getMember(int userID) throws SQLException {
        String query = "SELECT * FROM users WHERE userid = ?";
        ResultSet rs = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userID);

            rs = stmt.executeQuery();
            String email = rs.getString(1);
            String firstname = rs.getString(2);
            String lastname = rs.getString(3);
            double uLatitude = rs.getDouble(4);
            double uLongitude = rs.getDouble(5);
            double aLatitude = rs.getDouble(6);
            double aLongitude = rs.getDouble(7);
            double aRange = rs.getDouble(8);
            String ipAddress = rs.getString(9);
            boolean isMember = (rs.getInt(10) != 0);
            boolean isAdmin = (rs.getInt(11) != 0);
            String phoneNumber = rs.getString(12);
            if (isMember) {
                return new Member(isAdmin, email, firstname, lastname, uLatitude, uLongitude, aLatitude, aLongitude, aRange, ipAddress, phoneNumber);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static UsersPojo getUser(int userID) throws SQLException {
        String query = "SELECT * FROM users WHERE userid = ?";
        ResultSet rs = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userID);

            rs = stmt.executeQuery();
            String email = rs.getString(1);
            String firstname = rs.getString(2);
            String lastname = rs.getString(3);
            double uLatitude = rs.getDouble(4);
            double uLongitude = rs.getDouble(5);
            double aLatitude = rs.getDouble(6);
            double aLongitude = rs.getDouble(7);
            double aRange = rs.getDouble(8);
            String ipAddress = rs.getString(9);
            String phoneNumber = rs.getString(10);
           
                return new UsersPojo(email, firstname, lastname, uLatitude, uLongitude, aLatitude, aLongitude, aRange, ipAddress, phoneNumber);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void addUser(UsersPojo user) throws SQLException {
        String insertTableSQL = "INSERT INTO users"
                + "(emails, firstname, lastname, userlatitude, userlongitude, alertlatitude, alertlongitude, alertrange, ipaddress, ismember, isadmin, phonenumber) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(insertTableSQL);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getFirstName());
        ps.setString(3, user.getLastName());
        ps.setDouble(4, user.getUserLatitude());
        ps.setDouble(5, user.getUserLongitude());
        ps.setDouble(6, user.getAlertLatitude());
        ps.setDouble(7, user.getAlertLongitude());
        ps.setDouble(8, user.getAlertRange());
        ps.setString(9, user.getIpAddress());
        ps.setInt(10, 0);
        ps.setInt(11, 0);
        ps.setString(12, user.getPhoneNumber());
        ps.executeUpdate();
    }

    public static void addMember(Connection conn, Member member) throws SQLException {
        logger.debug("start adding member to db " + member);
       String insertTableSQL = "INSERT INTO users"
                + "(emails, firstname, lastname, userlatitude, userlongitude, alertlatitude, alertlongitude, alertrange, ipaddress, ismember, isadmin, phonenumber) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(insertTableSQL);
        ps.setString(1, member.getEmail());
        ps.setString(2, member.getFirstName());
        ps.setString(3, member.getLastName());
        ps.setDouble(4, member.getUserLatitude());
        ps.setDouble(5, member.getUserLongitude());
        ps.setDouble(6, member.getAlertLatitude());
        ps.setDouble(7, member.getAlertLongitude());
        ps.setDouble(8, member.getAlertRange());
        ps.setString(9, member.getIpAddress());
        ps.setInt(10, 1);
        ps.setInt(11, member.isAdministrator() ? 1 : 0);
        ps.setString(12, member.getPhoneNumber());
        
        ps.executeUpdate();
             
        
        /*String firstName = member.getFirstName();
        String email = member.getEmail();
        String lastName = member.getLastName();
        double userLatitude=member.getUserLatitude();
        double userLongitude=member.getUserLongitude();
        double alertLatitude=member.getAlertLatitude();
        double alertLongitude=member.getAlertLongitude();
        double alertRange =member.getAlertRange();
        String ipAddress   =member.getIpAddress();
        String phoneNumber  =member.getPhoneNumber();
        boolean isAdministrator =false;    */   
        /*
        int userid = executeSQLInsert(conn, insertTableSQL, firstName,
                email, lastName, userLatitude, userLongitude, alertLatitude,
                alertLongitude, alertRange, ipAddress, phoneNumber, isAdministrator);*/
    }
    
    
    /*private static int executeSQLInsert(Connection conn, String query, String firstName, 
            String email, String lastName, double userLatitude, double userLongitude, 
            double alertLatitude, double alertLongitude, double alertRange, 
            String ipAddress, String phoneNumber, boolean administrator) {

    return 1;
    }*/

        //logger.debug("Executing SQL Insert: " + query);
        
    
    /*public static void modifyUser(String userID, UsersPojo user) throws SQLException {
        String query = "UPDATE users SET userid = ?, passwords = ?, firstname = ?, lastname = ?, userlatitude = ?, userlongitude = ?, alertlatitude = ?, alertlongitude = ?, alertrange = ?, creationdate = ? WHERE userid = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        //ps.setString(1, user.getUserID()); //will be created automatically - chnage to email - it will be our primary key
        //add property email and methods checking if it exists 
        ps.setString(2, user.getEmail()); //remove it
        ps.setString(3, user.getFirstName()); //keep both
        ps.setString(4, user.getLastName());//keep both
        ps.setDouble(5, user.getUserLatitude());// get from Bree
        ps.setDouble(6, user.getUserLongitude());// get from Bree
        ps.setDouble(7, user.getAlertLatitude());// keep same us above user location
        ps.setDouble(8, user.getAlertLongitude());// keep same us above user location
        ps.setDouble(9, user.getAlertRange());// default value ~100ml
        //ps.setDouble(10, user.getCreationDate());//automatically or skip for a while 
        ps.setString(11, userID);
        ps.executeUpdate();
    }*/

    /*public static void modifyMember(String userID, Member member) throws SQLException {
        String query = "UPDATE users SET userid = ?, passwords = ?, firstname = ?, lastname = ?, userlatitude = ?, userlongitude = ?, alertlatitude = ?, alertlongitude = ?, alertrange = ?, creationdate = ?, isadmin = ? WHERE userid = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, member.getUserID());
        ps.setString(2, member.getEmail());
        ps.setString(3, member.getFirstName());
        ps.setString(4, member.getLastName());
        ps.setDouble(5, member.getUserLatitude());
        ps.setDouble(6, member.getUserLongitude());
        ps.setDouble(7, member.getAlertLatitude());
        ps.setDouble(8, member.getAlertLongitude());
        ps.setDouble(9, member.getAlertRange());
        //ps.setDouble(10, member.getCreationDate());
        ps.setDouble(11, member.isAdministrator() ? 1 : 0);
        ps.setString(12, userID);
        ps.executeUpdate();
    }

    public static void deleteUser(String userID) throws SQLException {
        String query = "DELETE FROM users WHERE userid = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, userID);
        ps.executeUpdate();
    }*/


}