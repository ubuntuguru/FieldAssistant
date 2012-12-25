package com.lobsternetworks.android.fieldassistant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import com.lobsternetworks.android.fieldassistant.R;

public class DataBaseHelper extends SQLiteOpenHelper{
	 
     //The Android's default system path of your application database.

	public static String DB_PATH = Functions.getDBPath();
 
    private static String DB_NAME = "fieldassistant.db";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 System.out.println("create");
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
        		System.out.println("nom nom nom");
    		} catch (Exception e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
    
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = Functions.getDBPath() + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 System.out.println("copy");
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = Functions.getDBPath() + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void open() throws SQLException{
    	System.out.println();
    	//Open the database
        String myPath = Functions.getDBPath() + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
	public void execute(String sql){
		myDataBase.execSQL(sql);
	}
	
	public void addEvent(int eventid, int round, int flight, String event, Integer eventtype){
		System.out.println("db " + eventid);
		myDataBase.execSQL("INSERT INTO events(`eventid`, `round`, `flight`, `event`) VALUES('" + eventid + "', '" + round + "', '" + flight + "', '" + event + "')");
	}

	public Cursor showEvents(){
		//System.out.println("a;dslk;asdfjlk;asjdf;klasdjfk'alks;dfjlk;asdjf;jklasdjf;lasdfjkl;asdjf;lljk" + DB_PATH);
		Cursor c = myDataBase.query("events", new String[] {"eventid", "id", "round", "flight", "event"}, null, null, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public Cursor showdistinctEvents(){
		//System.out.println("a;dslk;asdfjlk;asjdf;klasdjfk'alks;dfjlk;asdjf;jklasdjf;lasdfjkl;asdjf;lljk" + DB_PATH);
		Cursor c = myDataBase.query("events", new String[] {"distinct eventid", "event"}, null, null, null, null, null);
		c.moveToFirst();
		return c;
	}
	
	public Cursor getevents(Integer eventid){
		Cursor c = myDataBase.rawQuery("SELECT eventid, id, round, flight, event FROM events WHERE eventid=?", new String[] {eventid.toString()});
		c.moveToFirst();
		return  c;
	}
	
	public void addcompetitor(int competitorid, String Last, String First, String School){
		System.out.println("add");
		myDataBase.execSQL("INSERT INTO competitor(`competitorid`, `lastname`, `firstname`, `school`) VALUES('" + competitorid + "', '" + Last + "', '" + First + "', '" + School + "')");
	}
	public Cursor showcompetitors(){
		
		Cursor c = myDataBase.query("competitor", null, null, null, null, null, null);
		c.moveToFirst();
		return c;
	}
//	public Cursor showEvent(Integer eventid){
//		Cursor c = myDataBase.query("competitor", null, null, null, null, null, null);
//		c.moveToFirst();
//		return c;
//	}
//	public Integer geteventid()){
//		return null;	
//	}
	
	public Cursor getcompetitorbyid(Integer id){
	//Cursor c = myDataBase.rawQuery("SELECT id, firstname, lastname, school, competitorid FROM competitor WHERE id=?", new String[] {id.toString()});
	Cursor c = myDataBase.query("competitor", new String[] {"id", "firstname", "lastname", "school"}, "id='" + id + "'", null, null, null, null);
		c.moveToFirst();	
	return  c;
	}
	
	public void deleteall(){
		myDataBase.execSQL("DELETE FROM events");
		myDataBase.execSQL("DELETE FROM competitor");
		myDataBase.execSQL("DELETE FROM competitorevent");
		myDataBase.execSQL("DELETE FROM attempts");
	}
	
	public Cursor getCompetitorsfromeventid(Integer id){
		Cursor c = myDataBase.rawQuery("SELECT competitor.id, competitor.firstname, competitor.lastname, competitor.school, competitor.competitorid, competitorevent.position, events.round, events.flight, competitorevent.place FROM competitor, competitorevent, events WHERE competitor.id=competitorevent.competitorid  and competitorevent.eventid=events.id and events.id=?", new String[] {id.toString()});
		c.moveToFirst();
		return  c;
	}
	
	public void addcompetitorevent(Integer eventid, Integer competitorid, Integer position){
		myDataBase.execSQL("INSERT INTO competitorevent(`eventid`, `competitorid`, `position`) VALUES('" + eventid + "', '" + competitorid + "', '" + position + "' )");
	}

	public Integer getCompetitor_ID(Integer realid){
		//Cursor c = myDataBase.rawQuery("SELECT id FROM competitor ", null);
		Cursor c = myDataBase.query("competitor", new String[] {"id"}, "competitorid='" + realid + "'", null, null, null, null); 
		c.moveToLast();
		Integer id = c.getInt(0);
		return id;
	}

	public Integer getEvent_ID(Integer realid, Integer sec){
		//Cursor c = myDataBase.rawQuery("SELECT id FROM events", null);
		Cursor c = myDataBase.query("events" +
				"", new String[] {"id"}, "eventid='" + realid + "' and flight='" + sec + "'", null, null, null, "id ASC"); 
		c.moveToLast();
		Integer id = c.getInt(0);
		return id;
	}
	public String getEventname(Integer dbid){
		//Cursor c = myDataBase.rawQuery("SELECT id FROM events", null);
		Cursor c = myDataBase.query("events" +
				"", new String[] {"event"}, "id='" + dbid + "'", null, null, null, ""); 
		c.moveToLast();
		String name = c.getString(0);
		return name;
	}
	
	public Cursor getAttempts(Integer competitorid, Integer eventid){
		Cursor c = myDataBase.query("attempts" +
				"", new String[] {"id", "attemptnum", "attempt"}, "eventid='" + eventid + "' and competitorid='" + competitorid + "'", null, null, null, "attemptnum ASC");
		c.moveToFirst();
		return c;
	}
	
	public void updateAttempt(Integer competitorid, Integer eventid, String attempt, Integer attemptNum){
		System.out.println(competitorid + " " + eventid + " " + attempt + " " + attemptNum);
		myDataBase.execSQL("UPDATE attempts SET attempt = ? WHERE competitorid = ? and eventid = ? and attemptnum = ?", 
				new String[] {attempt, competitorid.toString(), eventid.toString(),  attemptNum.toString()});
	}
	public void addAttempt(Integer attemptnum, String attempt, Integer competitorid, Integer eventid) throws SQLException{
		attempt.replaceAll("'", "///'");
		//myDataBase.execSQL("INSERT INTO attempts ('attemptnum', 'attempt', 'competitorid','eventid') "+
		//		"VALUES('" + attemptnum.toString() + "', '" + attempt + "', '" + competitorid.toString() +"', '" + eventid.toString() +"')");
		myDataBase.execSQL("INSERT INTO attempts ('attemptnum', 'attempt', 'competitorid','eventid') VALUES(?, ?, ?, ?)", 
		new String[] {attemptnum.toString(), attempt, competitorid.toString(), eventid.toString()});
	}
	
	public Integer attemptnum(Integer competitorid, Integer eventid) throws SQLException{
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
	public String getConf(Integer Eventid, String conf){
		Cursor c = myDataBase.query("eventconf" +
				"", new String[] {"id", "event", "conf", "data"}, "event='" + Eventid + "' and conf='" + conf + "'", null, null, null, " ");
		c.moveToFirst();
		
		
		return c.getString(3);
		
	}
	
	public void addConf(Integer event, String Conf, String data){
		myDataBase.execSQL("INSERT INTO eventconf(`event`, `conf`, `data`) VALUES('" + event + "', '" + Conf + "', '" + data + "' )");
		
	}
	
	
		
	
	
}