package com.lobsternetworks.android.fieldassistant;
// This class is designed to map out a database table names
// THIS TABLE ESSENTIALLY REPRESENTS A MAPPING FROM STUDENTS TO COURSES
public class DBEventsTable {

    // UNIQUE ID OF EACH ROW - NO REAL MEANING HERE
    public static final String ID = "_id";

    // EVENT_TYPE
    public static final String EVENT_TYPE = "event_type";

    // EVENT_ID
    public static final String EXT_EVENT_ID = "ext_event_id";

    // ROUND
    public static final String ROUND = "round";

    // FLIGHT
    public static final String FLIGHT = "flight";

    // EVENT
    public static final String EVENT = "event";

    // TABLE_NAME
    public static final String TABLE_NAME = "events";

    // THE TABLE SCHEMA
    public static final String SCHEMA = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + EVENT_TYPE + " INTEGER," + EXT_EVENT_ID + " INTEGER," + ROUND + " INTEGER," + FLIGHT + " INTEGER," + EVENT + " STRING);";
}
