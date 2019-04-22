/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lionheartwebtech.quaketracker;

import java.util.Date;

/**
 *
 * @author Parichehr Karimi
 */
public class Quake {
    double mag;
    double longitude;
    double latitude;
    String place;
    Date date;


    public Quake(double mag, double longitude, double latitude, String place, Date date) {
        this.mag = mag;
        this.longitude = longitude;
        this.place = place;
        this.latitude = latitude;
        this.date = date;
    }

    public String getPlace() {
        return place;
    }
    

    public double getMag() {
        return mag;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Date getDate() {
        return date;
    }
    
    @Override
    public String toString() {
        return "Date: " + date + "\t\t\tPlace: " + place + "\t\t\tMagnitude: " + mag 
                + "\t\t\tLongitude: " + longitude + "\t\t\t\tLatitude: " + latitude + "\n";
    }
    
}
