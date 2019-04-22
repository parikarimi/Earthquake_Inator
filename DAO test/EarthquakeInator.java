package earthquake.inator;

import java.sql.SQLException;

public class EarthquakeInator {


    public static void main(String[] args) throws SQLException {
        QuakeTrackerDAO.init();
        System.out.println("user 0 exists:" +  QuakeTrackerDAO.userExists("0"));
        QuakeTrackerDAO.addUser(new User("test", "password", "bob", "joe", 47, -121, 47, -121, 50, 12345));
    }
    
}
