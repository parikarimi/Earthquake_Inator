/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lionheartwebtech.quaketracker;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Parichehr Karimi
 */
public class QuakeList {
    private static final Logger LOGGER = Logger.getLogger(QuakeList.class.getName());
    List<Quake> events;
    
    
    public QuakeList() {
        this.events = buildEventList();
    }
    
    public String toJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(events);
    }
    
    public List<Quake> getEvents() {
        return new ArrayList<>(events);
    }
    
    private List<Quake> buildEventList() {
        JsonObject quakeEvents = convertFromUrlToJsonObject();
        JsonArray features = quakeEvents.get("features").getAsJsonArray();//getJsonObjectChildByName("features", quakeEvents).getAsJsonArray();
        JsonArray properties = getJsonArrayByName("properties", features);
        JsonArray geometry = getJsonArrayByName("geometry", features);
        
        List<Quake> events = new ArrayList<>();
        
        Iterator<JsonElement> itProp = properties.iterator();
        Iterator<JsonElement> itGeo = geometry.iterator();

        JsonObject currentProperty = null;
        JsonObject currentGeometry = null;
        long time;
        double mag;
        double longitude;
        double latitude;
        String place;
        Date date;
        JsonArray coordinates; 

        
        while (itProp.hasNext()) {
            currentProperty = itProp.next().getAsJsonObject();            
            time = currentProperty.get("time").getAsLong();
            mag = currentProperty.get("mag").getAsDouble();
            place = currentProperty.get("place").getAsString(); 
            date = toDate(time);
            
            currentGeometry = itGeo.next().getAsJsonObject();
            coordinates = currentGeometry.getAsJsonArray("coordinates");
            longitude = coordinates.get(0).getAsDouble();
            latitude = coordinates.get(1).getAsDouble();
            Quake event = new Quake(mag, longitude, latitude, place, date);
            events.add(event);
        }
        return events;
    }
    
    private JsonArray getJsonArrayByName(String name, JsonArray array) {
        Iterator<JsonElement> it = array.iterator();
        JsonArray result = new JsonArray();
        JsonElement current = null;

        while (it.hasNext()) {
            current = it.next();
            result.add(current.getAsJsonObject().getAsJsonObject(name));
        }
        if (result.size() > 0) {
            return result;
        }
        return null;
    }
    private JsonObject convertFromUrlToJsonObject() {
        String url = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson";
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
            LOGGER.error("Unable to process USGS data feed...\n" + e.getMessage());
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
    
    /*
    Time when the event occurred. Times are reported in milliseconds 
    since the epoch ( 1970-01-01T00:00:00.000Z), and do not include 
    leap seconds. In certain output formats, the date is formatted 
    for readability.
    Conversion from epoch time to pacific statndard time
    */
    private Date toDate(long time) {
        Date date = null;
        try {
          date = new Date(time);
        } catch (Exception ex) {
          ex.getStackTrace();
        }
        return date;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < events.size(); i++) {
            sb.append(events.get(i)).append("\n\n");
        }
        return sb.toString();
    }
}
