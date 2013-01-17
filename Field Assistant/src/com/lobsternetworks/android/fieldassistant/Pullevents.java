package com.lobsternetworks.android.fieldassistant;
import java.io.BufferedReader;

import java.io.File;
 
import java.io.FileInputStream;
 
import java.io.FileNotFoundException;
 
import java.io.IOException;
 
import java.io.InputStreamReader;
import java.lang.reflect.Array;
 
import android.content.Context;
import android.os.Environment;
 
import android.util.Log;
import com.lobsternetworks.android.fieldassistant.R;
public class Pullevents {
private String[][] events;

public String[][] start(Context c, Integer eventid, Integer t){
	Integer currentevent = 0;
	Integer Sec = 0;
//	DataBaseHelper d = new DataBaseHelper(c);
//	   d.open();
	SchemaHelper sh = new SchemaHelper(c);
	try{	
//			Settings s = new Settings();
//			String path = s.getPreference("fielddroid:localpath");
		Functions fun = new Functions();
		   System.out.println("fun=" + fun.getLocalPath());
		   File f = new File(fun.getDBPath() +"Lynx.evt");
		 
		   FileInputStream IS = new FileInputStream(f);
		 
		   BufferedReader buf = new BufferedReader(new InputStreamReader(IS));
		 
		   String readString = new String();
		 
		   //just reading each line and pass it on the debugger
		 String events[][] = new String[64][5];
		 int i =0;
		 String dbeventid = "";
		   while((readString = buf.readLine())!= null){
			   String[] foo = readString.split(",");
			   Integer x = 0;
			   try{
				   x = Integer.parseInt(foo[0]);
				   
				   System.out.println(currentevent);
				   //System.out.println(eventid);
				   //System.out.println(foo[0]);
				   if(foo[0] != null){
				   currentevent = x;
				   }
				   if(eventid == Integer.parseInt(foo[0])){
					   
					   System.out.println("dbevent");
					   Sec = Integer.parseInt(foo[2]);
				   dbeventid = String.valueOf(sh.addEvent(Integer.parseInt(foo[0]), Integer.parseInt(foo[1]), Integer.parseInt(foo[2]), foo[3], t));
				   i=0;
				   events[i][0] = foo[0].toString();
				   events[i][1] = foo[1].toString();
				   events[i][2] = foo[2].toString();
				   events[i][3] = foo[3].toString();
				   events[i][4] = foo[4].toString();
				   }
			   }catch(Exception e){
				   
			   }

			   if(eventid.equals(currentevent)){
				   if(i>0){
					   System.out.println(readString + "+");
					   try{
						   String dbcompetitor_id = String.valueOf(sh.addCompetitor(Integer.parseInt(foo[1]),foo[3].toString() , foo[4].toString(), foo[5].toString()));
						   //Integer dbeventid = sh.getEvent_ID(eventid, Sec);
						   //Integer dbcompetitorid = sh.getCompetitor_ID(Integer.parseInt(foo[1]));
						   Long dbevent = sh.addCompetitorEvent(Integer.parseInt(dbeventid), Integer.parseInt(dbcompetitor_id), Integer.parseInt(foo[2]));
						   sh.addFlightOrder(Integer.parseInt(dbeventid.toString()), Integer.parseInt(dbcompetitor_id), 0, 0, "blank");
					   }catch(Exception e){
						   System.out.println(e);
					   }
				   }
				   i++;
			   }
		      //Log.d("line: ", readString);
		      i++;
		   }
		 
		} catch (Exception e) {
		 
		  // e.printStackTrace();
		 
		}
	
	sh.close();
		return events;
}
}
