package com.lobsternetworks.android.fieldassistant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class SchemaHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "fieldAssistant.db";

	// TOGGLE THIS NUMBER FOR UPDATING TABLES AND DATABASE
	private static final int DATABASE_VERSION = 1;

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
		//CREATE FLIGHTORDER TABLE
		db.execSQL(DBFlightOrderTable.SCHEMA);


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
		        db.execSQL("DROP TABLE IF EXISTS " + DBEventsTable.TABLE_NAME);
		        db.execSQL("DROP TABLE IF EXISTS " + DBFlightOrderTable.TABLE_NAME);
		        

		// CREATE NEW INSTANCE OF SCHEMA
		onCreate(db);
	}

	public void close(){
		SQLiteDatabase db = getWritableDatabase();
		db.close();
	}
	
	public void deleteAll(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM " + DBAttemptsDistanceTable.TABLE_NAME);
		db.execSQL("DELETE FROM " + DBCompetitorEventTable.TABLE_NAME);
		db.execSQL("DELETE FROM " + DBCompetitorTable.TABLE_NAME);
		db.execSQL("DELETE FROM " + DBEventConfTable.TABLE_NAME);
		db.execSQL("DELETE FROM " + DBEventsTable.TABLE_NAME);
		db.execSQL("DELETE FROM " + DBFlightOrderTable.TABLE_NAME);
	}
	
	public long addCompetitor(Integer cid, String first, String last, String team) {
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
	
	public Cursor showCompetitorevent() {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBCompetitorEventTable.ID, DBCompetitorEventTable.COMPETITOR_ID, DBCompetitorEventTable.EVENT_ID };

		// QUERY ALL EVENTS
		Cursor c = sd.query(DBCompetitorEventTable.TABLE_NAME, columns, null, null, null, null, null);

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
		//System.out.println("DC"+ eventid);
		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBEventsTable.ID, DBEventsTable.EVENT_TYPE, DBEventsTable.EXT_EVENT_ID, DBEventsTable.ROUND, DBEventsTable.FLIGHT, DBEventsTable.EVENT };

		String[] selectionArgs = new String[] { String.valueOf(eventid) };

		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		Cursor c = sd.query(DBEventsTable.TABLE_NAME, columns, DBEventsTable.EXT_EVENT_ID + "=?", selectionArgs, null, null, null);
		//Cursor c = sd.query(ClassTable.TABLE_NAME, columns,  + "= ? ", selectionArgs, null, null, null);
		//System.out.println("db"+c.getCount());
		c.moveToFirst();
		return c;
	}

	public Cursor getEventID(Integer eventid) {
		SQLiteDatabase sd = getWritableDatabase();
		//System.out.println("DC"+ eventid);
		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBEventsTable.ID, DBEventsTable.EVENT_TYPE, DBEventsTable.EXT_EVENT_ID, DBEventsTable.ROUND, DBEventsTable.FLIGHT, DBEventsTable.EVENT };

		String[] selectionArgs = new String[] { String.valueOf(eventid) };

		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		Cursor c = sd.query(DBEventsTable.TABLE_NAME, columns, DBEventsTable.ID + "=?", selectionArgs, null, null, null);
		//Cursor c = sd.query(ClassTable.TABLE_NAME, columns,  + "= ? ", selectionArgs, null, null, null);
		//System.out.println("db"+c.getCount());
		c.moveToFirst();
		return c;
	}
	
	public Cursor getAttempts(Integer competitorid, Integer eventid) {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBAttemptsDistanceTable.ID, DBAttemptsDistanceTable.ATTEMPTNUM, DBAttemptsDistanceTable.ATTEMPT, DBAttemptsDistanceTable.EVENT_ID };

		String[] selectionArgs = new String[] { String.valueOf(competitorid), String.valueOf(eventid) };

		// QUERY ALL ATTEMPTS FOR THE SPECIFIED QUERY
		Cursor c = sd.query(DBAttemptsDistanceTable.TABLE_NAME, columns, DBAttemptsDistanceTable.COMPETITOR_ID + "= ? AND " + DBAttemptsDistanceTable.EVENT_ID + "= ?", selectionArgs, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public Cursor showAttempts() {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBAttemptsDistanceTable.ID, DBAttemptsDistanceTable.ATTEMPTNUM, DBAttemptsDistanceTable.ATTEMPT, DBAttemptsDistanceTable.EVENT_ID };

		//String[] selectionArgs = new String[] { String.valueOf(competitorid), String.valueOf(eventid) };

		// QUERY ALL ATTEMPTS FOR THE SPECIFIED QUERY
		Cursor c = sd.query(DBAttemptsDistanceTable.TABLE_NAME, columns, null, null, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public Cursor getCompetitorFromEventId(Integer eventid) {
		SQLiteDatabase sd = getWritableDatabase();

		// SET SELECTION ARGS
		String[] selectionArgs = new String[] { String.valueOf(eventid) };

		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		//String query ="SELECT " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.ID + ", " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.FIRSTNAME + ", " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.LASTNAME + ", " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.TEAM + ", " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.EXT_COMPETITOR_ID + ", " + DBCompetitorEventTable.TABLE_NAME + "." + DBCompetitorEventTable.POSITION +
		String query ="SELECT " + DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.ID + ", " + DBCompetitorTable.FIRSTNAME + ", " + DBCompetitorTable.LASTNAME + ", " + DBCompetitorTable.TEAM + ", " + DBCompetitorTable.EXT_COMPETITOR_ID + ", " + DBCompetitorEventTable.POSITION + ", " + DBFlightOrderTable.TABLE_NAME + "." + DBFlightOrderTable.ICON +
		" FROM " + DBCompetitorTable.TABLE_NAME + " LEFT OUTER JOIN " + DBCompetitorEventTable.TABLE_NAME + 
		" ON " + 
		DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.ID + "=" + DBCompetitorEventTable.TABLE_NAME + "." + DBCompetitorEventTable.COMPETITOR_ID+
		" JOIN " + DBFlightOrderTable.TABLE_NAME + 
		" ON " + 
		DBCompetitorTable.TABLE_NAME + "." + DBCompetitorTable.ID + "=" + DBFlightOrderTable.TABLE_NAME + "." + DBFlightOrderTable.COMPETITOR_ID+
		//+ "' AND '"
		" WHERE " + 
		DBCompetitorEventTable.TABLE_NAME + "." + DBCompetitorEventTable.EVENT_ID + "=" + DBFlightOrderTable.TABLE_NAME + "." + DBFlightOrderTable.EVENT_ID + " AND " +
		DBCompetitorEventTable.TABLE_NAME + "." + DBCompetitorEventTable.EVENT_ID + "= ?"; 
		//System.out.println(query);
		Cursor c = sd.rawQuery(query, selectionArgs);
		c.moveToFirst();
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
		//System.out.println("event" + eventid);
		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBEventConfTable.ID, DBEventConfTable.EVENT, DBEventConfTable.CONF, DBEventConfTable.DATA };

		String[] selectionArgs = new String[] { String.valueOf(eventid), conf };

		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		Cursor c = sd.query(DBEventConfTable.TABLE_NAME, columns, "" + DBEventConfTable.EVENT + "= ? and " + DBEventConfTable.CONF + "= ? ", selectionArgs, null, null, null);
		//Cursor c = sd.query(ClassTable.TABLE_NAME, columns,  + "= ? ", selectionArgs, null, null, null);
		c.moveToFirst();
		//System.out.println("getEventConf_count" + c.getCount());
		return c.getString(3);
	}

	public Long updateEventConf(Integer eventid, String conf, String data) {
		SQLiteDatabase sd = getWritableDatabase();
		//CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBEventConfTable.DATA, data);
		
		//SETUP WHERE PARAMS
		String where = DBEventConfTable.EVENT + " = ? AND " + DBEventConfTable.CONF + " = ?";
		
		//CREATE A CONTENTVALUE OBJECT FOR WHERE ARGS
		String[] whereArgs = {String.valueOf(eventid), String.valueOf(conf)};
		
		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		long result = sd.update(DBEventConfTable.TABLE_NAME, cv, where, whereArgs);
		return result;
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
		updateFlightOrderAttempts(competitorid, eventid, attemptnum);
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
		String where = DBAttemptsDistanceTable.COMPETITOR_ID + " = ? AND " + DBAttemptsDistanceTable.EVENT_ID + " = ? AND " + DBAttemptsDistanceTable.ATTEMPTNUM + "= ?";
		
		//CREATE A CONTENTVALUE OBJECT FOR WHERE ARGS
		String[] whereArgs = {String.valueOf(competitorid), String.valueOf(eventid), String.valueOf(attemptnum)};
		
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

	public Cursor showEventConf() {
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBEventConfTable.ID, DBEventConfTable.EVENT, DBEventConfTable.CONF, DBEventConfTable.DATA };

		//String[] selectionArgs = new String[] { String.valueOf(eventid), conf };

		// QUERY ALL EVENTS MATCHING EXT_EVENT_ID
		Cursor c = sd.query(DBEventConfTable.TABLE_NAME, columns, null, null, null, null, null);
		//Cursor c = sd.query(ClassTable.TABLE_NAME, columns,  + "= ? ", selectionArgs, null, null, null);
		c.moveToFirst();
		
		return c;
	}
	
	public long addFlightOrder(Integer eventid, Integer cid, Integer status_id, Integer attempts, String icon){
		// CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBFlightOrderTable.STATUS_ID, status_id);
		cv.put(DBFlightOrderTable.ATTEMPTS_COMPLETED, attempts);
		cv.put(DBFlightOrderTable.COMPETITOR_ID, cid);
		cv.put(DBFlightOrderTable.EVENT_ID, eventid);
		cv.put(DBFlightOrderTable.ICON, icon);
				//System.out.println(cid);
		// RETRIEVE WRITEABLE DATABASE AND INSERT
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.insert(DBFlightOrderTable.TABLE_NAME, DBFlightOrderTable.COMPETITOR_ID, cv);
		//System.out.println(result);
		return result;
	}
	
	public Cursor getFlightOrder(Integer eventid, Integer cid){
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBFlightOrderTable.ID, DBFlightOrderTable.COMPETITOR_ID, DBFlightOrderTable.EVENT_ID, DBFlightOrderTable.STATUS_ID, DBFlightOrderTable.ATTEMPTS_COMPLETED, DBFlightOrderTable.ICON };

		String[] selectionArgs = new String[] { String.valueOf(eventid), String.valueOf(cid) };

		// QUERY ALL ATTEMPTS FOR THE SPECIFIED QUERY
		Cursor c = sd.query(DBFlightOrderTable.TABLE_NAME, columns, DBFlightOrderTable.EVENT_ID + "= ? AND " + DBFlightOrderTable.COMPETITOR_ID + "= ?", selectionArgs, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public Cursor showFlightOrder(){
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBFlightOrderTable.ID, DBFlightOrderTable.COMPETITOR_ID, DBFlightOrderTable.EVENT_ID, DBFlightOrderTable.STATUS_ID, DBFlightOrderTable.ATTEMPTS_COMPLETED, DBFlightOrderTable.ICON };

		//String[] selectionArgs = new String[] { String.valueOf(eventid), String.valueOf(cid) };

		// QUERY ALL ATTEMPTS FOR THE SPECIFIED QUERY
		Cursor c = sd.query(DBFlightOrderTable.TABLE_NAME, columns, null, null, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public long updateFlightOrderStatus(Integer competitorid, Integer eventid, Integer status){
		//CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBFlightOrderTable.STATUS_ID, status);
		
		//SETUP WHERE PARAMS
		String where = DBFlightOrderTable.COMPETITOR_ID + " = ? AND " + DBFlightOrderTable.EVENT_ID + " = ? ";
		
		//CREATE A CONTENTVALUE OBJECT FOR WHERE ARGS
		String[] whereArgs = {String.valueOf(competitorid), String.valueOf(eventid)};
		
		//RETRIEVE WRITEABLE DATABASE AND UPDATE
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.update(DBFlightOrderTable.TABLE_NAME, cv, where, whereArgs);
		return result;
	}

	public long updateFlightOrderAttempts(Integer competitorid, Integer eventid, Integer attempts){
		//CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBFlightOrderTable.ATTEMPTS_COMPLETED, attempts);
		
		//SETUP WHERE PARAMS
		String where = DBFlightOrderTable.COMPETITOR_ID + " = ? AND " + DBFlightOrderTable.EVENT_ID + " = ?";
		
		//CREATE A CONTENTVALUE OBJECT FOR WHERE ARGS
		String[] whereArgs = {String.valueOf(competitorid), String.valueOf(eventid)};
		
		//RETRIEVE WRITEABLE DATABASE AND UPDATE
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.update(DBFlightOrderTable.TABLE_NAME, cv, where, whereArgs);
		return result;
	}
	
	public long updateFlightOrderIcon(Integer competitorid, Integer eventid, String icon){
		//CREATE A CONTENTVALUE OBJECT
		ContentValues cv = new ContentValues();
		cv.put(DBFlightOrderTable.ICON, icon);
		
		//SETUP WHERE PARAMS
		String where = DBFlightOrderTable.COMPETITOR_ID + " = ? AND " + DBFlightOrderTable.EVENT_ID + " = ?";
		
		//CREATE A CONTENTVALUE OBJECT FOR WHERE ARGS
		String[] whereArgs = {String.valueOf(competitorid), String.valueOf(eventid)};
		
		//RETRIEVE WRITEABLE DATABASE AND UPDATE
		SQLiteDatabase sd = getWritableDatabase();
		long result = sd.update(DBFlightOrderTable.TABLE_NAME, cv, where, whereArgs);
		return result;
	}

	public Cursor getFlightOrderByEvent(Integer eventid) {
		// TODO Auto-generated method stub
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBFlightOrderTable.ID, DBFlightOrderTable.COMPETITOR_ID, DBFlightOrderTable.EVENT_ID, DBFlightOrderTable.STATUS_ID, DBFlightOrderTable.ATTEMPTS_COMPLETED, DBFlightOrderTable.ICON };

		String[] selectionArgs = new String[] { String.valueOf(eventid)};

		// QUERY ALL ATTEMPTS FOR THE SPECIFIED QUERY
		Cursor c = sd.query(DBFlightOrderTable.TABLE_NAME, columns, DBFlightOrderTable.EVENT_ID + "= ? ", selectionArgs, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public Cursor getFlightOrderByEventSorted(Integer eventid) {
		// TODO Auto-generated method stub
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBFlightOrderTable.ID, DBFlightOrderTable.COMPETITOR_ID, DBFlightOrderTable.EVENT_ID, DBFlightOrderTable.STATUS_ID, DBFlightOrderTable.ATTEMPTS_COMPLETED, DBFlightOrderTable.ICON };

		String[] selectionArgs = new String[] { String.valueOf(eventid)};

		// QUERY ALL ATTEMPTS FOR THE SPECIFIED QUERY
		Cursor c = sd.query(DBFlightOrderTable.TABLE_NAME, columns, DBFlightOrderTable.STATUS_ID + " = 1 AND " + DBFlightOrderTable.EVENT_ID + "= ? ORDER BY " + DBFlightOrderTable.ATTEMPTS_COMPLETED + ", "+ DBFlightOrderTable.ID, selectionArgs, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public Cursor getFlightOrderByEventSortedNA(Integer eventid) {
		// TODO Auto-generated method stub
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBFlightOrderTable.ID, DBFlightOrderTable.COMPETITOR_ID, DBFlightOrderTable.EVENT_ID, DBFlightOrderTable.STATUS_ID, DBFlightOrderTable.ATTEMPTS_COMPLETED, DBFlightOrderTable.ICON };

		String[] selectionArgs = new String[] { String.valueOf(eventid)};

		// QUERY ALL ATTEMPTS FOR THE SPECIFIED QUERY
		Cursor c = sd.query(DBFlightOrderTable.TABLE_NAME, columns, "(" + DBFlightOrderTable.STATUS_ID + " = 0 OR " + DBFlightOrderTable.STATUS_ID + " = 2 ) AND " + DBFlightOrderTable.EVENT_ID + "= ? ORDER BY " + DBFlightOrderTable.ATTEMPTS_COMPLETED + ", "+ DBFlightOrderTable.ID, selectionArgs, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public Cursor standings(Integer eventid) {
		// TODO Auto-generated method stub
		SQLiteDatabase sd = getWritableDatabase();

		// WE NEED TO RETURN ALL FIELDS
		String[] columns = new String[] { DBFlightOrderTable.ID, DBFlightOrderTable.COMPETITOR_ID, DBFlightOrderTable.EVENT_ID, DBFlightOrderTable.STATUS_ID, DBFlightOrderTable.ATTEMPTS_COMPLETED, DBFlightOrderTable.ICON };

		String[] selectionArgs = new String[] { String.valueOf(eventid)};

		// QUERY ALL ATTEMPTS FOR THE SPECIFIED QUERY
		Cursor c = sd.query(DBFlightOrderTable.TABLE_NAME, columns, "(" + DBFlightOrderTable.STATUS_ID + " = 0 OR " + DBFlightOrderTable.STATUS_ID + " = 2 ) AND " + DBFlightOrderTable.EVENT_ID + "= ? ORDER BY " + DBFlightOrderTable.ATTEMPTS_COMPLETED + ", "+ DBFlightOrderTable.ID, selectionArgs, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	
//	@SuppressLint("SimpleDateFormat")
//	public void backup(){
//		try {
//	        File sd = new File(Functions.getLocalPath());
//	        File data = Environment.getDataDirectory();
//	        Date dateNow = new Date ();
//	        
//	        SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//	        StringBuilder dateStr = new StringBuilder( dateformatYYYYMMDD.format( dateNow ) );
//	        
//	        
//	        if (sd.canWrite()) {
//	            String currentDBPath = "/data/com.lobsternetworks.android.fieldassistant/" + DATABASE_NAME;
//	            String backupDBPath = dateStr + DATABASE_NAME;
//	            File currentDB = new File(data, currentDBPath);
//	            File backupDB = new File(sd, backupDBPath);
//
//	            if (currentDB.exists()) {
//	                FileChannel src = new FileInputStream(currentDB).getChannel();
//	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
//	                dst.transferFrom(src, 0, src.size());
//	                src.close();
//	                dst.close();
//	            }
//	        }
//	    } catch (Exception e) {
//	    	e.printStackTrace();
//	    }
//	}
}
