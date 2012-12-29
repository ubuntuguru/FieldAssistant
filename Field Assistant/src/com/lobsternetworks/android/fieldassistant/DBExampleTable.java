package com.lobsternetworks.android.fieldassistant;
// This class is designed to map out a database table names
// THIS TABLE ESSENTIALLY REPRESENTS A MAPPING FROM STUDENTS TO COURSES
public class DBExampleTable {

    // UNIQUE ID OF EACH ROW - NO REAL MEANING HERE
    public static final String ID = "_id";

    // THE ID OF THE STUDENT
    public static final String STUDENT_ID = "student_id";

    // THE ID OF ASSOCIATED COURSE
    public static final String COURSE_ID = "course_id";

    // THE NAME OF THE TABLE
    public static final String TABLE_NAME = "classes";
    // THE TABLE SCHEMA
    public static final String SCHEMA = "CREATE TABLE " + TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + STUDENT_ID + " INTEGER," + COURSE_ID
                + " INTEGER);";
}
