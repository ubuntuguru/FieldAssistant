package com.lobsternetworks.android.fieldassistant;
// This class is designed to map out a database table names

public class DBCompetitorEventTable {

    // UNIQUE ID OF EACH ROW - NO REAL MEANING HERE
    public static final String ID = "_id";

    // PLACE
    public static final String PLACE = "place";

    // POSITION
    public static final String POSITION = "position";

    // EVENT_ID
    public static final String EVENT_ID = "event_id";

    // COMPETITOR_ID
    public static final String COMPETITOR_ID = "competitor_id";

    // TABLE_NAME
    public static final String TABLE_NAME= "competitorevent";

    // SCHEMA
    public static final String SCHEMA = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PLACE + " INTEGER," + POSITION + " INTEGER, " + EVENT_ID + " INTEGER, " + COMPETITOR_ID + " INTEGER );";
}
