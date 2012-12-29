package com.lobsternetworks.android.fieldassistant;
// This class is designed to map out a database table names
// THIS TABLE ESSENTIALLY REPRESENTS A MAPPING FROM STUDENTS TO COURSES
public class DBAttemptsTable {

    // TABLE_NAME
    public static final String TABLE_NAME = "attempts";

    // UNIQUE ID OF EACH ROW - NO REAL MEANING HERE
    public static final String ID = "_id";

    // ATTEMPT NUM
    public static final String ATTEMPTNUM = "attemptnum";

    // COMPETITORID
    public static final String COMPETITOR_ID = "competitor_id";

    // ATTEMPT
    public static final String ATTEMPT = "attempt";

    // EVENTID
    public static final String EVENT_ID = "event_id";

    // THE TABLE SCHEMA'
    public static final String SCHEMA = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ATTEMPTNUM + " INTEGER," + COMPETITOR_ID + " INTEGER, " + ATTEMPT +" STRING, " + EVENT_ID + " INTEGER);";
}
