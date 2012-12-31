package com.lobsternetworks.android.fieldassistant;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SchemaHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "fieldAssistant.db";

	// TOGGLE THIS NUMBER FOR UPDATING TABLES AND DATABASE
	private static final int DATABASE_VERSION = 0;

	SchemaHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create tables
		//CREATE ATTEMPTS TABLE
		db.execSQL(DBAttemptsDistanceTable.SCHEMA);
		//CREATE COMPETITOREVENT TABLE
		db.execSQL(DBCompetitorEventTable.SCHEMA);
		//CREATE COMPETITOR TABLE
		db.execSQL(DBCompetitorTable.SCHEMA);
		//CREATE EVENTCONF TABLE
		db.execSQL(DBEventConfTable.SCHEMA);
		//CREATE EVENTS TABLE
		db.execSQL(DBEventsTable.SCHEMA);


	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("LOG_TAG", "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");

		// KILL PREVIOUS TABLES IF UPGRADED
		        db.execSQL("DROP TABLE IF EXISTS " + DBAttemptsDistanceTable.TABLE_NAME);
		        db.execSQL("DROP TABLE IF EXISTS " + DBCompetitorEventTable.TABLE_NAME);
		        db.execSQL("DROP TABLE IF EXISTS " + DBCompetitorTable.TABLE_NAME);
		        db.execSQL("DROP TABLE IF EXISTS " + DBEventConfTable.TABLE_NAME);
//		        db.execSQL("DROP TABLE IF EXISTS " + DBExampleTable.TABLE_NAME);
		        

		// CREATE NEW INSTANCE OF SCHEMA
		onCreate(db);
	}

	public void deleteAll(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM " + DBAttemptsDistanceTable.TABLE_NAME);
		db.execSQL("DELETE FROM " + DBCompetitorEventTable.TABLE_NAME);
		db.execSQL("DELETE FROM " + DBCompetitorTable.TABLE_NAME);
		db.execSQL("DELETE FROM " + DBEventConfTable.TABLE_NAME);
		db.execSQL("DELETE FROM " + DBEventsTable.TABLE_NAME);
	}
	
	public long addCompetitor(String first, String last, String team, Integer cid) {
		// CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBCompetitorTable.FIRSTNAME, first);
		cv.put(DBCompetitorTable.LASTNAME, last);
		cv.put(DBCompetitorTable.TEAM, team);
		cv.put(DBCompetitorTable.EXT_COMPETITOR_ID, cid);

		// RETRIEVE WRITEABLE DATABASE AND INSERT
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.insert(DBCompetitorTable.TABLE_NAME, DBCompetitorTable.FIRSTNAME, cv);
		return result;
	}

	public Cursor showCompetitors() {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBCompetitorTable.ID, DBCompetitorTable.TEAM, DBCompetitorTable.FIRSTNAME, DBCompetitorTable.LASTNAME, DBCompetitorTable.EXT_COMPETITOR_ID };

		// QUERY ALL EVENTS
		Cursor c = sd.query(DBCompetitorTable.TABLE_NAME, columns, null, null, null, null, null);

		return c;
	}
	
	public Cursor getCompetitorById(Integer id) {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBCompetitorTable.ID, DBCompetitorTable.TEAM, DBCompetitorTable.FIRSTNAME, DBCompetitorTable.LASTNAME, DBCompetitorTable.EXT_COMPETITOR_ID };

		String[] selectionArgs = new String[] { String.valueOf(id) };

		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		Cursor c = sd.query(DBCompetitorTable.TABLE_NAME, columns, DBCompetitorTable.ID + "= ?", selectionArgs, null, null, null);
		//Cursor c = sd.query(ClassTable.TABLE_NAME, columns,  + "= ? ", selectionArgs, null, null, null);
		return c;
	}

	public long addEvent(Integer exteventid, Integer round, Integer flight, String event, Integer eventtype) {
		// CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBEventsTable.EXT_EVENT_ID, exteventid);
		cv.put(DBEventsTable.ROUND, round);
		cv.put(DBEventsTable.FLIGHT, flight);
		cv.put(DBEventsTable.EVENT, event);
		cv.put(DBEventsTable.EVENT_TYPE, eventtype);

		// RETRIEVE WRITEABLE DATABASE AND INSERT
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.insert(DBEventsTable.TABLE_NAME, DBEventsTable.EXT_EVENT_ID, cv);
		return result;
	}

	public Cursor showEvents() {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBEventsTable.ID, DBEventsTable.EVENT_TYPE, DBEventsTable.EXT_EVENT_ID, DBEventsTable.ROUND, DBEventsTable.FLIGHT, DBEventsTable.EVENT };

		// QUERY ALL EVENTS
		Cursor c = sd.query(DBEventsTable.TABLE_NAME, columns, null, null, null, null, null);

		return c;
	}

	public Cursor getEvent(Integer eventid) {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBEventsTable.ID, DBEventsTable.EVENT_TYPE, DBEventsTable.EXT_EVENT_ID, DBEventsTable.ROUND, DBEventsTable.FLIGHT, DBEventsTable.EVENT };

		String[] selectionArgs = new String[] { String.valueOf(eventid) };

		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		Cursor c = sd.query(DBEventsTable.TABLE_NAME, columns, DBEventsTable.EXT_EVENT_ID + "= ?", selectionArgs, null, null, null);
		//Cursor c = sd.query(ClassTable.TABLE_NAME, columns,  + "= ? ", selectionArgs, null, null, null);
		return c;
	}
	
	public Cursor getAttempts(Integer competitorid, Integer eventid) {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBAttemptsDistanceTable.ID, DBAttemptsDistanceTable.ATTEMPTNUM, DBAttemptsDistanceTable.COMPETITOR_ID, DBAttemptsDistanceTable.ATTEMPT, DBAttemptsDistanceTable.EVENT_ID };

		String[] selectionArgs = new String[] { String.valueOf(competitorid), String.valueOf(eventid) };

		// QUERY ALL ATTEMPTS FOR THE SPECIFIED QUERY
		Cursor c = sd.query(DBAttemptsDistanceTable.TABLE_NAME, columns, DBAttemptsDistanceTable.COMPETITOR_ID + "= ? AND " + DBAttemptsDistanceTable.EVENT_ID + "= ?", selectionArgs, null, null, null);

		return c;
	}
	
	public Cursor getCompetitorFromEventId(Integer eventid) {
		SQLiteDatabase sd = getWritableDatabase();

		// SET SELECTION ARGS
		String[] selectionArgs = new String[] { String.valueOf(eventid) };

		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		Cursor c = sd.rawQuery("SELECT " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.ID + ", " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.FIRSTNAME + ", " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.LASTNAME + ", " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.TEAM + ", " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.EXT_COMPETITOR_ID + ", " + DBCompetitorEventTable.TABLE_NAME + "." + DBCompetitorEventTable.POSITION + ", " + DBEventsTable.TABLE_NAME + "." + DBEventsTable.ROUND + ", " + DBEventsTable.TABLE_NAME + "." + DBEventsTable.FLIGHT + ", " + DBCompetitorEventTable.TABLE_NAME + "." + DBCompetitorEventTable.PLACE+ "FROM " + DBCompetitorTable.TABLE_NAME + ", " + DBCompetitorEventTable.TABLE_NAME + ", " + DBEventsTable.TABLE_NAME + " WHERE " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.ID + "=" + DBCompetitorEventTable.TABLE_NAME + "." + DBCompetitorEventTable.COMPETITOR_ID + " AND " + DBCompetitorEventTable.TABLE_NAME + "." + DBCompetitorEventTable.EVENT_ID + "=" + DBEventsTable.TABLE_NAME + "." + DBEventsTable.ID + " and " + DBEventsTable.TABLE_NAME + "." + DBEventsTable.ID + "=?", selectionArgs);

		return c;
	}
	
	public Integer attemptnum(Integer competitorid, Integer eventid){
		Cursor c = getAttempts(competitorid, eventid);
		Integer n = 0;
		if(c != null){
			c.moveToFirst();
			n = c.getCount()+1;
		}else{
			n=1;
		}
		return n;
	}
	
	public long addCompetitorEvent(Integer event, Integer competitor, Integer position) {
		// CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBCompetitorEventTable.COMPETITOR_ID, competitor);
		cv.put(DBCompetitorEventTable.EVENT_ID, event);
		cv.put(DBCompetitorEventTable.POSITION, position);
		
		// RETRIEVE WRITEABLE DATABASE AND INSERT
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.insert(DBCompetitorEventTable.TABLE_NAME, DBCompetitorEventTable.PLACE, cv);
		return result;
	}
	
	public long addEventConf(Integer event, String conf, String data) {
		// CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBEventConfTable.EVENT, event);
		cv.put(DBEventConfTable.CONF, conf);
		cv.put(DBEventConfTable.DATA, data);
		
		// RETRIEVE WRITEABLE DATABASE AND INSERT
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.insert(DBEventConfTable.TABLE_NAME, DBEventConfTable.CONF, cv);
		return result;
	}

	public String getEventConf(Integer eventid, String conf) {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBEventConfTable.ID, DBEventConfTable.EVENT, DBEventConfTable.CONF, DBEventConfTable.DATA };

		String[] selectionArgs = new String[] { String.valueOf(eventid), conf };

		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		Cursor c = sd.query(DBEventConfTable.TABLE_NAME, columns, DBEventConfTable.EVENT + "= ? and " + DBEventConfTable.CONF + "= ? ", selectionArgs, null, null, null);
		//Cursor c = sd.query(ClassTable.TABLE_NAME, columns,  + "= ? ", selectionArgs, null, null, null);
		c.moveToFirst();
		
		return c.getString(3);
	}
	
	public long addAttempt(Integer attemptnum, String attempt, Integer competitorid, Integer eventid){
		// CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBAttemptsDistanceTable.ATTEMPTNUM, attemptnum);
		cv.put(DBAttemptsDistanceTable.ATTEMPT, attempt);
		cv.put(DBAttemptsDistanceTable.COMPETITOR_ID, competitorid);
		cv.put(DBAttemptsDistanceTable.EVENT_ID, eventid);
				
		// RETRIEVE WRITEABLE DATABASE AND INSERT
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.insert(DBAttemptsDistanceTable.TABLE_NAME, DBAttemptsDistanceTable.COMPETITOR_ID, cv);
		return result;
	}
	
	public long updateAttempt(Integer competitorid, Integer eventid, String attempt, Integer attemptnum){
//		System.out.println(competitorid + " " + eventid + " " + attempt + " " + attemptNum);
//		myDataBase.execSQL("UPDATE attempts SET attempt = ? WHERE competitorid = ? and eventid = ? and attemptnum = ?", 
//				new String[] {attempt, competitorid.toString(), eventid.toString(),  attemptNum.toString()});
		//CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBAttemptsDistanceTable.ATTEMPT, attempt);
		
		//SETUP WHERE PARAMS
		String where = DBAttemptsDistanceTable.COMPETITOR_ID + " = ? AND " + DBAttemptsDistanceTable.EVENT_ID + " = ?";
		
		//CREATE A CONTENTVALUE OBJECT FOR WHERE ARGS
		String[] whereArgs = {String.valueOf(competitorid), String.valueOf(eventid)};
		
		//RETRIEVE WRITEABLE DATABASE AND UPDATE
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.update(DBAttemptsDistanceTable.TABLE_NAME, cv, where, whereArgs);
		return result;
	}
	
	public Cursor showDistinctEvents() {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL DISTINCT EXTERNAL EVENT ID's
		String[] columns = new String[] {"distinct " + DBEventsTable.EXT_EVENT_ID, DBEventsTable.EVENT };

		// QUERY ALL EVENTS
		Cursor c = sd.query(DBEventsTable.TABLE_NAME, columns, null, null, null, null, null);

		return c;
	}

	//  public long addStudent(String name, String state, int grade) {
	//  // CREATE A CONTENTVALUE OBJECT
	//  ContentValues cv = new ContentValues();
	////  cv.put(StudentTable.NAME, name);
	////  cv.put(StudentTable.STATE, state);
	////  cv.put(StudentTable.GRADE, grade);
	//
	//  // RETRIEVE WRITEABLE DATABASE AND INSERT
	//  SQLiteDatabase sd = getWritableDatabase();
	//  long result = sd.insert(StudentTable.TABLE_NAME, StudentTable.NAME, cv);
	//  return result;
	//}
	//
	//    public long addCourse(String name) {
	//        ContentValues cv = new ContentValues();
	//        cv.put(CourseTable.NAME, name);
	//
	//        SQLiteDatabase sd = getWritableDatabase();
	//        long result = sd.insert(CourseTable.TABLE_NAME, CourseTable.NAME, cv);
	//        return result;
	//    }
	//
	//    public boolean enrollStudentClass(int studentId, int courseId) {
	//        ContentValues cv = new ContentValues();
	//        cv.put(ClassTable.STUDENT_ID, studentId);
	//        cv.put(ClassTable.COURSE_ID, courseId);
	//
	//        SQLiteDatabase sd = getWritableDatabase();
	//        long result = sd.insert(ClassTable.TABLE_NAME, ClassTable.STUDENT_ID, cv);
	//        return (result >= 0);
	//    }
	//
	//    public Cursor getStudentsForCourse(int courseId) {
	//        SQLiteDatabase sd = getWritableDatabase();
	//
	//        // WE ONLY NEED TO RETURN STUDENT IDS
	//        String[] columns = new String[] { ClassTable.STUDENT_ID };
	//
	//        String[] selectionArgs = new String[] { String.valueOf(courseId) };
	//
	//        // QUERY CLASS MAP FOR STUDENTS IN COURSE
	//        Cursor c = sd.query(ClassTable.TABLE_NAME, columns, ClassTable.COURSE_ID + "= ? ", selectionArgs, null, null,
	//                null);
	//
	//        return c;
	//    }
	//
	//    public Cursor getCoursesForStudent(int studentId) {
	//        SQLiteDatabase sd = getWritableDatabase();
	//
	//        // WE ONLY NEED TO RETURN COURSE IDS
	//        String[] columns = new String[] { ClassTable.COURSE_ID };
	//
	//        String[] selectionArgs = new String[] { String.valueOf(studentId) };
	//
	//        // QUERY CLASS MAP FOR STUDENTS IN COURSE
	//        Cursor c = sd.query(ClassTable.TABLE_NAME, columns, ClassTable.STUDENT_ID + "= ? ", selectionArgs, null, null,
	//                null);
	//
	//        return c;
	//    }
	//
	//    public Set<Integer> getStudentsByGradeForCourse(int courseId, int grade) {
	//        SQLiteDatabase sd = getWritableDatabase();
	//
	//        // WE ONLY NEED TO RETURN COURSE IDS
	//        String[] columns = new String[] { ClassTable.STUDENT_ID };
	//
	//        String[] selectionArgs = new String[] { String.valueOf(courseId) };
	//
	//        // QUERY CLASS MAP FOR STUDENTS IN COURSE
	//        Cursor c = sd.query(ClassTable.TABLE_NAME, columns, ClassTable.COURSE_ID + "= ? ", selectionArgs, null, null,
	//                null);
	//        Set<Integer> returnIds = new HashSet<Integer>();
	//        while (c.moveToNext()) {
	//            int id = c.getInt(c.getColumnIndex(ClassTable.STUDENT_ID));
	//            returnIds.add(id);
	//        }
	//
	//        // MAKE SECOND QUERY
	//        columns = new String[] { StudentTable.ID };
	//        selectionArgs = new String[] { String.valueOf(grade) };
	//
	//        c = sd.query(StudentTable.TABLE_NAME, columns, StudentTable.GRADE + "= ?", selectionArgs, null, null, null);
	//        Set<Integer> gradeIds = new HashSet<Integer>();
	//        while (c.moveToNext()) {
	//            int id = c.getInt(c.getColumnIndex(StudentTable.ID));
	//            gradeIds.add(id);
	//        }
	//
	//        // RETURN INTERSECTION OF ID SETS
	//        returnIds.retainAll(gradeIds);
	//        return returnIds;
	//    }
	//
	//    public boolean removeStudent(int studentId) {
	//        SQLiteDatabase sd = getWritableDatabase();
	//        String[] whereArgs = new String[] { String.valueOf(studentId) };
	//
	//        // MAKE SURE YOU DELETE ALL CLASSES STUDENT IS SIGNED UP FOR
	//        sd.delete(ClassTable.TABLE_NAME, ClassTable.STUDENT_ID + "= ? ", whereArgs);
	//
	//        // THEN DELETE STUDENT
	//        int result = sd.delete(StudentTable.TABLE_NAME, StudentTable.ID + "= ? ", whereArgs);
	//        return (result > 0);
	//    }
	//
	//    public boolean removeCourse(int courseId) {
	//        SQLiteDatabase sd = getWritableDatabase();
	//        String[] whereArgs = new String[] { String.valueOf(courseId) };
	//
	//        // MAKE SURE YOU REMOVE COURSE FROM ALL STUDENTS ENROLLED
	//        sd.delete(ClassTable.TABLE_NAME, ClassTable.COURSE_ID + "= ? ", whereArgs);
	//
	//        // THEN DELETE COURSE
	//        int result = sd.delete(CourseTable.TABLE_NAME, CourseTable.ID + "= ? ", whereArgs);
	//        return (result > 0);
	//    }
}
