package earthquake.inator;

class User {

    private String userID;
    private String password;
    private String firstName;
    private String lastName;
    private double userLatitude;
    private double userLongitude;
    private double alertLatitude;
    private double alertLongitude;
    private double alertRange;
    private double creationDate;

    public User(String userID, String password, String firstName, String lastName, double userLatitude, double userLongitude, double alertLatitude, double alertLongitude, double alertRange, double creationDate) {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.alertLatitude = alertLatitude;
        this.alertLongitude = alertLongitude;
        this.alertRange = alertRange;
        this.creationDate = creationDate;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public double getAlertLatitude() {
        return alertLatitude;
    }

    public double getAlertLongitude() {
        return alertLongitude;
    }

    public double getAlertRange() {
        return alertRange;
    }

    public double getCreationDate() {
        return creationDate;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserLocation(double latitude, double longitude) {
        this.userLatitude = latitude;
        this.userLongitude = longitude;
    }

    public void setAlertLocation(double latitude, double longitude, double range) {
        this.alertLatitude = latitude;
        this.alertLongitude = longitude;
        this.alertRange = range;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
