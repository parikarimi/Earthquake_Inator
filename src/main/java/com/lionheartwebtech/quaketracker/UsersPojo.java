package com.lionheartwebtech.quaketracker;
public class UsersPojo {
    //private String userID;
    private  String email;
    private String firstName;
    private String lastName;
    private double userLatitude;
    private double userLongitude;
    private double alertLatitude;
    private double alertLongitude;
    private double alertRange;
    private String phoneNumber;
    //private double creationDate;
    private String ipAddress;

    public UsersPojo(String email, String firstName, String lastName, double userLatitude,
            double userLongitude, double alertLatitude, double alertLongitude, 
            double alertRange, String ipAddress, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.alertLatitude = alertLatitude;
        this.alertLongitude = alertLongitude;
        this.alertRange = alertRange;
        this.ipAddress = ipAddress;    
        this.phoneNumber = phoneNumber;
    }



    public String getEmail() {
        return email;
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
    public String getIpAddress()
    {
        return ipAddress;
    }
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    /*public double getCreationDate() {
        return creationDate;
    }*/

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

   // public void setCreationDate(long creationDate) {
    //    this.creationDate = creationDate;
    //}

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
    
    
}
