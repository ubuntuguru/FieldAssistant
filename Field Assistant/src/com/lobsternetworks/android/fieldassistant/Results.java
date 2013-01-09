package com.lobsternetworks.android.fieldassistant;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.lobsternetworks.android.fieldassistant.R;

public class Results extends Activity{
	//SchemaHelper d = new SchemaHelper(getApplicationContext());
ListView myList;
//CountDownTimer cdt;
CountDownTimer cdt = new CountDownTimer(30000, 1000) {
	
    public void onTick(long millis) {
//        mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
    	TextView tv = (TextView)findViewById(R.id.timer);
    	tv.setText(String.valueOf((millis/1000)));
    }

    public void onFinish() {
  //      mTextField.setText("done!");
    	TextView tv = (TextView)findViewById(R.id.timer);
    	tv.setTextColor(Color.RED);
    	tv.setText("0");
    }
 }.start();
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
       				//d.close();
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
       			SchemaHelper d = new SchemaHelper(getApplicationContext());
       			try{
       				
       				if(Functions.getAttemptNum() != null){
       					d.updateAttempt(Functions.getCompetitorID(),Functions.getActiveEvent(), " ", Functions.getAttemptNum());
       					Functions.clearAttemptNum();
       					onUpdate();
       				}
       				
       		}catch(Exception e){
       				System.out.println(e);
       			}
       		d.close();
       		}
       	});
       	Button foul = (Button)findViewById(R.id.foulbtn);
       	foul.setOnClickListener(new View.OnClickListener() {
       		
       		@Override
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			SchemaHelper d = new SchemaHelper(getApplicationContext());
       			
   				Integer n = 0;
   				Integer e = 100;
   				Integer f = 0;
   				Cursor z = d.showEventConf();
   				for(Integer i=0;i<z.getCount();i++){
   					System.out.println(z.getInt(0)+" "+z.getInt(1)+" "+z.getString(2)+" " +z.getString(3));
   					z.moveToNext();
   				}
   				try{
   				System.out.println("exe"+d.getEventID(Functions.getActiveEvent()).getInt(2));			
   				e = Integer.parseInt(d.getEventConf(d.getEventID(Functions.getActiveEvent()).getInt(2), "ATTEMPTS"));
   				System.out.println(e);
   				f = d.attemptnum(Functions.getCompetitorID(), Functions.getActiveEvent());
   				
   				}catch(Exception ex){
   					ex.printStackTrace();
   				}
   				
   				if(Functions.getAttemptNum() == null){
   				if(f < e+1){
        	        Cursor c = d.getAttempts(Functions.getCompetitorID(), Functions.getActiveEvent());
        			
        			if(c != null){
        				c.moveToFirst();
        				n = c.getCount()+1;
        			}else{
        				n=1;
        			}
        			d.addAttempt(n, "F", Functions.getCompetitorID(),Functions.getActiveEvent());
   				}else{
   					Toast.makeText(getApplicationContext(), "Max attempts reached", Toast.LENGTH_SHORT).show();
   				}
       			}else{
       					d.updateAttempt(Functions.getCompetitorID(),Functions.getActiveEvent(), "F", Functions.getAttemptNum());
       					Functions.clearAttemptNum();
       					
       			}Functions.clearAttemptNum();
   				
    			
    			onUpdate();
   			
   			d.close();
   		}
       	});
       	Button pass = (Button)findViewById(R.id.passbtn);
       	pass.setOnClickListener(new View.OnClickListener() {
       		
       		@Override
       		public void onClick(View v) {
       			// TODO Auto-generated method stub
       			SchemaHelper d = new SchemaHelper(getApplicationContext());
       			
       				Integer n = 0;
       				Integer e = 100;
       				Integer f = 0;
       				Cursor z = d.showEventConf();
       				for(Integer i=0;i<z.getCount();i++){
       					System.out.println(z.getInt(0)+" "+z.getInt(1)+" "+z.getString(2)+" " +z.getString(3));
       					z.moveToNext();
       				}
       				try{
       				System.out.println("exe"+d.getEventID(Functions.getActiveEvent()).getInt(2));			
       				e = Integer.parseInt(d.getEventConf(d.getEventID(Functions.getActiveEvent()).getInt(2), "ATTEMPTS"));
       				System.out.println(e);
       				f = d.attemptnum(Functions.getCompetitorID(), Functions.getActiveEvent());
       				
       				}catch(Exception ex){
       					ex.printStackTrace();
       				}
       				
       				if(Functions.getAttemptNum() == null){
       					if(f < e+1){
            	        Cursor c = d.getAttempts(Functions.getCompetitorID(), Functions.getActiveEvent());
            			
            			if(c != null){
            				c.moveToFirst();
            				n = c.getCount()+1;
            			}else{
            				n=1;
            			}
            			d.addAttempt(n, "P", Functions.getCompetitorID(),Functions.getActiveEvent());
       					}else{
           					Toast.makeText(getApplicationContext(), "Max attempts reached", Toast.LENGTH_SHORT).show();
           				}
           			}else{
           					System.out.println(Functions.getCompetitorID()+Functions.getActiveEvent()+"P"+ Functions.getAttemptNum());
           					d.updateAttempt(Functions.getCompetitorID(),Functions.getActiveEvent(), "P", Functions.getAttemptNum());
           					Functions.clearAttemptNum();
           					
           			}Functions.clearAttemptNum();
       				
        			
        			onUpdate();
       			
       			d.close();
       		}
       	});
       	//d.close();
	}
	protected void onRestart(){
		super.onRestart();
		onUpdate();
      	System.out.println("onRestart");
		
	}
	
	protected void onPause(){
		super.onPause();
		cdt.cancel();
	}
	
	public void onUpdate(){
		Integer competitorid = Functions.getCompetitorID();
        Integer eventid = Functions.getActiveEvent();
        SchemaHelper d = new SchemaHelper(getApplicationContext());
		try{
	        
	        Cursor c = d.getCompetitorById(competitorid);
	        c.moveToFirst();
	        TextView tv = (TextView) findViewById(R.id.competitorinfo);
	        tv.setText(c.getString(2) + " " + c.getString(3) + "\n" + c.getString(1));
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
	        //Functions.setAttemptNum(a+2);
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
	        
	        
	        
	        
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        d.close();
	}
	private Object listclick() {
		// TODO Auto-generated method stub
		return null;
	}
}
