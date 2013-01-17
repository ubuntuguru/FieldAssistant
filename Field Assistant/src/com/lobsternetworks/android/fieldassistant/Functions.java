package com.lobsternetworks.android.fieldassistant;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import com.lobsternetworks.android.fieldassistant.R;

public class Functions extends Activity{
	String event, competitor;
	public static String localpath;
	static String sharepath;
	static String serverip;
	Integer eventid, competitorid;
	static Integer ext_event_id;
	
	
	
	
	public static void setExt_event_id(Integer event){
		ext_event_id = event;
	}
		
	public static Integer getExt_event_id(){
		return ext_event_id;
	}
	static Integer ActiveEvent, CompetitorID;
	static Integer attemptNum = null;
	public static Integer getAttemptNum() {
		return attemptNum;
	}

	public static void setAttemptNum(Integer attemptNum) {
		Functions.attemptNum = attemptNum;
	}
	public static void clearAttemptNum() {
		Functions.attemptNum = null;
	}

	public static Integer ivleftswitcher(String in){
		Integer myint = 0;
		if(in.equalsIgnoreCase("up")){
			myint = R.drawable.up;
		}else if(in.equalsIgnoreCase("ondeck")){
			myint = R.drawable.ondeck;
		}else if(in.equalsIgnoreCase("onhold")){
			myint = R.drawable.onhold;
		}else if(in.equalsIgnoreCase("checkedout")){
			myint = R.drawable.checkedout;
		}else if(in.equalsIgnoreCase("suspend")){
			myint = R.drawable.suspend;
		}else if(in.equalsIgnoreCase("blank")){
			myint = R.drawable.blank;
		}else if(in.equalsIgnoreCase("ic_launcher")){
			myint = R.drawable.ic_launcher;
		}else{
			myint = R.drawable.blank;
		}
		return myint;
	}
	
	public void selectEvent(String Event){
		
	}
	
	public void selectCompetitor(String Competitor){
		
	}
	public static void setCompetitorID(Integer ID){
		CompetitorID = ID;
	}
	public static Integer getCompetitorID(){
		return CompetitorID;
	}
	public Integer getEvent(){
		
		return eventid;
	}
	
	public Integer getCompetitor(){
		
		return competitorid;
	}
	
	public static void setActiveEvent(Integer event){
		ActiveEvent = event;
	}
		
	public static Integer getActiveEvent(){
		return ActiveEvent;
	}
	
	public static String chop(String s, int i)
	  {
		String eol = "EOL";
	      if ( i == 0 || s == null || eol == null )
	      {
	         return s;
	      }

	      int length = s.length();

	      /*
	       * if it is a 2 char EOL and the string ends with
	       * it, nip it off.  The EOL in this case is treated like 1 character
	       */
	      if ( eol.length() == 2 && s.endsWith(eol ))
	      {
	          length -= 2;
	          i -= 1;
	      }

	      if ( i > 0)
	      {
	          length -= i;
	      }

	      if ( length < 0)
	      {
	          length = 0;
	      }

	      return s.substring( 0, length);
	  }
	public static void setLocalPath(String Path){
		localpath = Path;
		System.out.println("my" + localpath);
	}
	public static String getLocalPath(){
		System.out.println("my" + localpath);
		
		return localpath;
	}
	
	public static String getDBPath(){
		System.out.println("my" + localpath);
		String Path = localpath;
	
		return Path; 
	}
				
	public static void setSharePath(String s){
		sharepath = s;
	}
	public static String getSharePath(){
		return sharepath;
	}
	public static void setServerIP(String s){
		serverip = s;
	}
	public static String getServerIP(){
		return serverip;
	}
	public static String parseResult(String r){
		String ret = "";
		if(r.equalsIgnoreCase("FOUL")){
			ret = r;
		}else if(r.equalsIgnoreCase("PASS")){
			ret = r;
		}else{
			String[] a = r.split("'");
			String[] b = (String[])a[1].split("\"");
			ret = a[0] + "-" + b[0];
		}
		return ret;
	}
	
	public static String attempt_comp(String rrr){
		String[] a = rrr.split("'");
		String[] b = (String[])a[1].split("\"");
		return  a[0] + "." + ((Integer.parseInt(b[0]))/12);
		
	}

}
