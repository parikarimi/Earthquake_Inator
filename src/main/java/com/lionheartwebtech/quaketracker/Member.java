package com.lionheartwebtech.quaketracker;

public class Member extends UsersPojo {

    private boolean isAdministrator;

    public Member(boolean isAdministrator, String email, String firstName, String lastName, 
            double userLatitude, double userLongitude, double alertLatitude, double alertLongitude, 
            double alertRange, String ipAddress, String phoneNumber) {
        super(email, firstName, lastName, userLatitude, userLongitude, alertLatitude,
                alertLongitude, alertRange, ipAddress, phoneNumber);
        this.isAdministrator = isAdministrator;
    }

    public boolean isAdministrator() {
        return this.isAdministrator;
    }
    
}
