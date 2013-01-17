package com.lobsternetworks.android.fieldassistant;
// This class is designed to map out a database table names
public class DBFlightOrderTable {
// design for the flightorder table - used to map out the order in a flight
    // UNIQUE ID OF EACH ROW - NO REAL MEANING HERE
    public static final String ID = "_id";

    // COMPETITOR_ID
    public static final String COMPETITOR_ID = "competitor_id";

    // EVENT_ID
    public static final String EVENT_ID = "event_id";

    // STATUS_ID
    public static final String STATUS_ID = "status_id";

    // ATTEMPTS_COMPLETED
    //for distance events this will tally total attempts completed for the round
    //for vert jump events this will tally attempts at the current height??? may change
    public static final String ATTEMPTS_COMPLETED = "attempts_completed";

    // ICON
    public static final String ICON = "icon";
    
    //ACTIVE 
    public static final String ACTIVE = "active";
    
    
    // THE NAME OF THE TABLE
    public static final String TABLE_NAME = "flightorder";
    
    // THE TABLE SCHEMA
    public static final String SCHEMA = "CREATE TABLE " + TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COMPETITOR_ID + " INTEGER, " + EVENT_ID
                + " INTEGER, " +  STATUS_ID + " INTEGER, " + ATTEMPTS_COMPLETED + " INTEGER, " + ICON + " TEXT, " + ACTIVE + "INTEGER );";
}
