package earthquake.inator;

public class Member extends User {

    private boolean isAdministrator;

    public Member(boolean isAdministrator, String userID, String password, String firstName, String lastName, double userLatitude, double userLongitude, double alertLatitude, double alertLongitude, double alertRange, double creationDate) {
        super(userID, password, firstName, lastName, userLatitude, userLongitude, alertLatitude, alertLongitude, alertRange, creationDate);
        this.isAdministrator = isAdministrator;
    }
    public boolean isAdministrator()
    {
    return this.isAdministrator;
    }
}
