package com.lobsternetworks.android.fieldassistant;
// This class is designed to map out a database table names
// THIS TABLE ESSENTIALLY REPRESENTS A MAPPING FROM STUDENTS TO COURSES
public class DBEventConfTable {

    // UNIQUE ID OF EACH ROW - NO REAL MEANING HERE
    public static final String ID = "_id";

    // DATA
    public static final String DATA = "data";

    // EVENT
    public static final String EVENT = "event";

    // CONF
    public static final String CONF = "CONF";

    // THE NAME OF THE TABLE
    public static final String TABLE_NAME = "eventconf";

    // THE TABLE SCHEMA
    public static final String SCHEMA = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DATA + " STRING," + EVENT + " STRING," + CONF + " STRING);";
}
