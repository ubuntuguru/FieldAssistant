package com.lobsternetworks.android.fieldassistant;

import java.io.FileWriter;
import java.io.PrintWriter;

import android.database.Cursor;
import com.lobsternetworks.android.fieldassistant.R;

//public class SendEvents {
//public void start(Integer dbid, Integer eventid, Integer round, Integer section){
//	DataBaseHelper d = null;
//	String path = "/mnt/sdcard/meet/" + zero(eventid.toString()) + "-" + zero(round.toString()) + "-" + zero(section.toString()) + ".LFF";
//	try{
//		d = new DataBaseHelper(null);
//		d.open();
//	PrintWriter out = new PrintWriter(new FileWriter(path)); 
//	out.println(eventid.toString() + "," + round.toString() + "," + section.toString() + ","+d.getEventname(dbid));
//	
//	Cursor competitor = d.getCompetitorsfromeventid(dbid);
//	Integer count = competitor.getCount();
//	for(Integer i=0;i<count;i++){
//		out.print(competitor.getString(8) + ","+competitor.getString(4)+","+competitor.getString(2)+","+competitor.getString(1) + ",");
//		Cursor attempts = d.getAttempts(competitor.getInt(0), dbid);
//		Integer acount = attempts.getCount();
//		for(Integer j=0;j<acount;j++){
//		out.print(","+ attempts.getString(2).replaceAll("/'", "'"));
//		attempts.moveToNext();
//		}
//		//out.print("place,competitorid, ");
//		out.println(",,,");
//		
//		competitor.moveToNext();
//	}
////	out.print("Hello "); 
////	out.println("world"); 
//	out.close();
//	d.close();
//	}catch(Exception e){
//		System.out.println(e);
//		d.close();
//		
//	}
//	
//}
//public String zero(String a){
//
//	while(a.toString().length() < 3){
//		a = "0" + a;
//	}
//	return a;
//}
//}
