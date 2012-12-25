package com.lobsternetworks.android.fieldassistant;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.lobsternetworks.android.fieldassistant.R;

public class Results extends Activity{
	DataBaseHelper d = new DataBaseHelper(this);
ListView myList;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        String[] list = new String[] {"1 test", "2tester"};
        final Boolean finals = false;
       
        onUpdate();
        
        Button input = (Button)findViewById(R.id.inputbtn);
       	input.setOnClickListener(new View.OnClickListener() {
       		
       		@Override
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			try{
       				Intent myIntent;
       				myIntent = new Intent(v.getContext(), Input.class);
       				startActivityForResult(myIntent, 0);
       			}catch(Exception e){
       				System.out.println(e);
       			}
       		}
       	});
       	Button back = (Button)findViewById(R.id.backbtn);
       	back.setOnClickListener(new View.OnClickListener() {
       		
       		@Override
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			try{
       				d.close();
       				Functions.clearAttemptNum();
       				finish();
       			}catch(Exception e){
       				System.out.println(e);
       			}
       		}
       	});
       	Button clear = (Button)findViewById(R.id.clearbtn);
       	clear.setOnClickListener(new View.OnClickListener() {
       		
       		@Override
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			try{
       				d.open();
       				if(Functions.getAttemptNum() != null){
       					d.updateAttempt(Functions.getCompetitorID(),Functions.getActiveEvent(), " ", Functions.getAttemptNum());
       					Functions.clearAttemptNum();
       					onUpdate();
       				}
       				d.close();
       		}catch(Exception e){
       				System.out.println(e);
       			}
       		}
       	});
       	Button foul = (Button)findViewById(R.id.foulbtn);
       	foul.setOnClickListener(new View.OnClickListener() {
       		
       		@Override
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			try{
       				Integer n = 0;
       				d.open();
       				if(Functions.getAttemptNum() == null){
            	        Cursor c = d.getAttempts(Functions.getCompetitorID(), Functions.getActiveEvent());
            			
            			if(c != null){
            				c.moveToFirst();
            				n = c.getCount()+1;
            			}else{
            				n=1;
            			}
            			d.addAttempt(n, "F", Functions.getCompetitorID(),Functions.getActiveEvent());
           				}else{
           					d.updateAttempt(Functions.getCompetitorID(),Functions.getActiveEvent(), "F", Functions.getAttemptNum());
           					Functions.clearAttemptNum();
           					
           				}
        			d.close();
        			onUpdate();
       			}catch(Exception e){
       				System.out.println(e);
       			}
       		}
       	});
       	Button pass = (Button)findViewById(R.id.passbtn);
       	pass.setOnClickListener(new View.OnClickListener() {
       		
       		@Override
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			try{
       				Integer n = 0;
       				d.open();
       				if(Functions.getAttemptNum() == null){
        	        Cursor c = d.getAttempts(Functions.getCompetitorID(), Functions.getActiveEvent());
        			
        			if(c != null){
        				c.moveToFirst();
        				n = c.getCount()+1;
        			}else{        				n=1;
        			}
        			//build in a check to make sure we dont allow too many attempts
        			if(finals == true){
        				Toast.makeText(getApplicationContext(), "General FAilure", Toast.LENGTH_LONG).show();
        			}else{
        			Integer attempts = Integer.parseInt(d.getConf(Functions.getActiveEvent(), "ATTEMPTS")); 
        				if(n <= attempts){
        					d.addAttempt(n, "P", Functions.getCompetitorID(),Functions.getActiveEvent());
        					Toast.makeText(getApplicationContext(), "General FAilure", Toast.LENGTH_LONG).show();
        				}else{
        					Toast.makeText(getApplicationContext(), "Max Attempts Reached", Toast.LENGTH_SHORT).show();
        				}
        			}
        			
       				}else{
       					System.out.println("UPDATE PASS");
       					d.updateAttempt(Functions.getCompetitorID(),Functions.getActiveEvent(), "P", Functions.getAttemptNum());
       					Functions.clearAttemptNum();
       				}
        			d.close();
        			onUpdate();
       			}catch(Exception e){
       				System.out.println(e);
       			}
       		}
       	});
	}
	protected void onRestart(){
		super.onRestart();
		onUpdate();
      	System.out.println("onRestart");
		
	}
	public void onUpdate(){
		Integer competitorid = Functions.getCompetitorID();
        Integer eventid = Functions.getActiveEvent();
		try{
	        DataBaseHelper d = new DataBaseHelper(null);
	        d = new DataBaseHelper(this);
	        d.open();
	        Cursor c = d.getcompetitorbyid(competitorid);
	        c.moveToFirst();
	        TextView tv = (TextView) findViewById(R.id.competitorinfo);
	        tv.setText(c.getString(1) + " " + c.getString(2) + "\n" + c.getString(3));
	        c.close();
	        
	        Cursor attempts =d.getAttempts(competitorid, eventid);
	        Integer a = attempts.getCount();
	        final String[] competitorattempts = new String[a];
	        String e = "";
	        for(Integer i=0;i<a;i++){
	        	e = attempts.getString(2);
	        	e = e.replaceAll("//'", "'");
	        	e = e.replace("/", "");
	        	competitorattempts[i] = attempts.getString(1) + " " + e;
	        	attempts.moveToNext();
	        }
	        attempts.close();
	        myList  = (ListView)findViewById(R.id.listView1);
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, competitorattempts);
	        myList.setAdapter(adapter);
	        myList.setOnItemClickListener(new OnItemClickListener(){

  				@Override
  				public void onItemClick(AdapterView<?> v, View view,
  						int pos, long id) {
  					// TODO Auto-generated method stub
  					Integer attempt = pos + 1;
  					Functions.setAttemptNum(attempt);
  					Toast.makeText(getApplicationContext(), "Attempt #" + attempt.toString()+ " Selected", Toast.LENGTH_SHORT).show();
  					//System.out.println(Functions.getAttemptNum());
  				}
   	        	
   	        });
	        
	        
	        
	        d.close();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	}
	private Object listclick() {
		// TODO Auto-generated method stub
		return null;
	}
}
