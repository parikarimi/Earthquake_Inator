package earthquake.inator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuakeTrackerDAO {

    private static Connection conn;

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

    public static boolean userExists(String userID) throws SQLException {
        String query = "SELECT * FROM users WHERE userid = ?";
        ResultSet rs = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userID);

            rs = stmt.executeQuery();
            return !(!rs.isBeforeFirst() && rs.getRow() == 0);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;

    }

    public static User getUser(String userID) throws SQLException {
        String query = "SELECT * FROM users WHERE userid = ?";
        ResultSet rs = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userID);

            rs = stmt.executeQuery();
            User toReturn = null;
            if (getRowCount(rs) != 0) {
                String curuserID = rs.getString(1);
                String password = rs.getString(2);
                String firstname = rs.getString(3);
                String lastname = rs.getString(4);
                double uLatitude = rs.getDouble(5);
                double uLongitude = rs.getDouble(6);
                double aLatitude = rs.getDouble(7);
                double aLongitude = rs.getDouble(8);
                double aRange = rs.getDouble(9);
                double creationDate = rs.getDouble(10);
                toReturn = new User(curuserID, password, firstname, lastname, uLatitude, uLongitude, aLatitude, aLongitude, aRange, creationDate);
            }
            return toReturn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Member getMember(String userID) throws SQLException {
        String query = "SELECT * FROM users WHERE userid = ?";
        ResultSet rs = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userID);

            rs = stmt.executeQuery();
            String curuserID = rs.getString(1);
            String password = rs.getString(2);
            String firstname = rs.getString(3);
            String lastname = rs.getString(4);
            double uLatitude = rs.getDouble(5);
            double uLongitude = rs.getDouble(6);
            double aLatitude = rs.getDouble(7);
            double aLongitude = rs.getDouble(8);
            double aRange = rs.getDouble(9);
            double creationDate = rs.getDouble(10);
            boolean isMember = (rs.getInt(11) != 0);
            boolean isAdmin = (rs.getInt(12) != 0);
            if (isMember) {
                return new Member(isAdmin, curuserID, password, firstname, lastname, uLatitude, uLongitude, aLatitude, aLongitude, aRange, creationDate);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addUser(User user) throws SQLException {
        String insertTableSQL = "INSERT INTO users"
                + "(userid, passwords, firstname, lastname, userlatitude, userlongitude, alertlatitude, alertlongitude, alertrange, creationdate, ismember, isadmin) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(insertTableSQL);
        ps.setString(1, user.getUserID());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getFirstName());
        ps.setString(4, user.getLastName());
        ps.setDouble(5, user.getUserLatitude());
        ps.setDouble(6, user.getUserLongitude());
        ps.setDouble(7, user.getAlertLatitude());
        ps.setDouble(8, user.getAlertLongitude());
        ps.setDouble(9, user.getAlertRange());
        ps.setDouble(10, user.getCreationDate());
        ps.setInt(11, 0);
        ps.setInt(12, 0);
        ps.executeUpdate();
    }

    public static void addMember(Member member) throws SQLException {
        String insertTableSQL = "INSERT INTO users"
                + "(userid, passwords, firstname, lastname, userlatitude, userlongitude, alertlatitude, alertlongitude, alertrange, creationdate, ismember, isadmin) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(insertTableSQL);
        ps.setString(1, member.getUserID());
        ps.setString(2, member.getPassword());
        ps.setString(3, member.getFirstName());
        ps.setString(4, member.getLastName());
        ps.setDouble(5, member.getUserLatitude());
        ps.setDouble(6, member.getUserLongitude());
        ps.setDouble(7, member.getAlertLatitude());
        ps.setDouble(8, member.getAlertLongitude());
        ps.setDouble(9, member.getAlertRange());
        ps.setDouble(10, member.getCreationDate());
        ps.setInt(11, 1);
        ps.setInt(12, member.isAdministrator() ? 1 : 0);
        ps.executeUpdate();
    }

    public static void modifyUser(String userID, User user) throws SQLException {
        String query = "UPDATE users SET userid = ?, passwords = ?, firstname = ?, lastname = ?, userlatitude = ?, userlongitude = ?, alertlatitude = ?, alertlongitude = ?, alertrange = ?, creationdate = ? WHERE userid = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user.getUserID());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getFirstName());
        ps.setString(4, user.getLastName());
        ps.setDouble(5, user.getUserLatitude());
        ps.setDouble(6, user.getUserLongitude());
        ps.setDouble(7, user.getAlertLatitude());
        ps.setDouble(8, user.getAlertLongitude());
        ps.setDouble(9, user.getAlertRange());
        ps.setDouble(10, user.getCreationDate());
        ps.setString(11, userID);
        ps.executeUpdate();
    }

    public static void modifyMember(String userID, Member member) throws SQLException {
        String query = "UPDATE users SET userid = ?, passwords = ?, firstname = ?, lastname = ?, userlatitude = ?, userlongitude = ?, alertlatitude = ?, alertlongitude = ?, alertrange = ?, creationdate = ?, isadmin = ? WHERE userid = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, member.getUserID());
        ps.setString(2, member.getPassword());
        ps.setString(3, member.getFirstName());
        ps.setString(4, member.getLastName());
        ps.setDouble(5, member.getUserLatitude());
        ps.setDouble(6, member.getUserLongitude());
        ps.setDouble(7, member.getAlertLatitude());
        ps.setDouble(8, member.getAlertLongitude());
        ps.setDouble(9, member.getAlertRange());
        ps.setDouble(10, member.getCreationDate());
        ps.setDouble(11, member.isAdministrator() ? 1 : 0);
        ps.setString(12, userID);
        ps.executeUpdate();
    }

    public static void deleteUser(String userID) throws SQLException {
        String query = "DELETE FROM users WHERE userid = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, userID);
        ps.executeUpdate();
    }
}