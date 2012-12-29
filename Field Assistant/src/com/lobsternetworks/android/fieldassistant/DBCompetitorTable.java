package com.lobsternetworks.android.fieldassistant;
// This class is designed to map out a database table names
// THIS TABLE ESSENTIALLY REPRESENTS A MAPPING FROM STUDENTS TO COURSES
public class DBCompetitorTable {

    // UNIQUE ID OF EACH ROW - NO REAL MEANING HERE
    public static final String ID = "_id";

    // TEAM
    public static final String TEAM = "team";

    // FIRSTNAME
    public static final String FIRSTNAME = "firstname";

    // LASTNAME
    public static final String LASTNAME = "lastname";

    // COMPETITOR_ID
    public static final String COMPETITOR_ID = "competitor_id";

    // TABLE_NAME
    public static final String TABLE_NAME = "competitor";

    // THE TABLE SCHEMA
    public static final String SCHEMA = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TEAM + " TEXT," + FIRSTNAME + " TEXT," + LASTNAME + " TEXT," + COMPETITOR_ID + " INTEGER);";
}
