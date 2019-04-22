/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lionheartwebtech.quaketracker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;

/**
 *
 * @author Parichehr Karimi
 */
public class IPLocation {
    private static final Logger LOGGER = Logger.getLogger(IPLocation.class.getName());
    
    private double longitude;
    private double latitude;
    private String ipAddress;
    
    public IPLocation (String ipAddress) {
        this.ipAddress = ipAddress;
        setGeoLocation();
    }
    
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getIpAddress() {
        return ipAddress;
    }
    
    private void setGeoLocation() {
        if (this.ipAddress.equals("0:0:0:0:0:0:0:1")) { // server testing purpose
            this.latitude = 0;
            this.longitude = 0;
        } else {
            LOGGER.info("Reading location JSON data to get longitude and latitude..");
            JsonObject ipLocation = convertFromUrlToJsonObject();
            this.latitude = ipLocation.get("latitude").getAsDouble();
            this.longitude = ipLocation.get("longitude").getAsDouble();
        }
    }
    
    private JsonObject convertFromUrlToJsonObject() {
        
        LOGGER.info("Reading location data from URL..");

        String url = "http://api.ipstack.com/" + ipAddress + "?access_key=8a9dd25172ace2126024e7f68b4a1246&format=1";
        InputStream inputStream = null;
        JsonObject json = null;
        BufferedReader reader = null;
        JsonElement jsonElement = null;
        
        try {              
            inputStream = new URL(url).openStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonContent = readContents(reader);
            JsonParser jsonParser = new JsonParser();
            jsonElement = jsonParser.parse(jsonContent);
            if (jsonElement.isJsonObject()) {
                json = jsonElement.getAsJsonObject();
            }

        } catch (Exception e) {
            LOGGER.error("Unable to process IP geolocation...\n" + e.getMessage());
        }
        
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    LOGGER.error("Unable to close inputStream...\n" + ex.getMessage());
                }
            }
        }
        return json;
    }
    private String readContents(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int character;
        while((character = reader.read()) != -1) {
            sb.append((char)character);
        }
        return sb.toString();
    }
}
