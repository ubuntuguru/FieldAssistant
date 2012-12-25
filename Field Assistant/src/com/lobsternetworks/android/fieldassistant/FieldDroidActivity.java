package com.lobsternetworks.android.fieldassistant;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.lobsternetworks.android.fieldassistant.R;

public class FieldDroidActivity extends Activity implements View.OnClickListener{
    /** Called when the activity is first created. */
	ListView listview;
	Integer position;
	ArrayList<HashMap<String,Object>> hashMapListForListView = new ArrayList<HashMap<String,Object>>();
	@Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.optionsmenu:
            options(null);
            return true;
        case R.id.exitmenu:
        	finish();
        	return true;
        case R.id.settingsmenu:
        	settingsmenu(null);
        	return true;

   
        }
       return false;
    }
    public void options(View v){
    	Intent intent = new Intent(FieldDroidActivity.this, Options.class);
    	startActivityForResult(intent,0);
    }
    public void selectevent(View v){
    	Intent intent = new Intent(FieldDroidActivity.this, SelectEvent.class);
    	startActivityForResult(intent,0);
    }
    public void settingsmenu(View v){
    	Intent intent = new Intent(FieldDroidActivity.this, Settings.class);
    	startActivityForResult(intent,0);
    }
    public void onCreate(Bundle savedInstanceState) {
    	System.out.println(Environment.getExternalStorageDirectory().toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listview = (ListView)findViewById(R.id.lv);
        Settings s = new Settings();
        DataBaseHelper myDbHelper = new DataBaseHelper(null);
        SharedPreferences sharedPreferences = getSharedPreferences("fielddroid", MODE_PRIVATE);
	    String thekey = sharedPreferences.getString("fielddroid:localpath", "/data/data/com.lobsternetworks.android.fielddroid/");
        Functions.setLocalPath(thekey);
        
        try{
        System.out.println("my:" + Functions.getLocalPath());
        Functions.setSharePath(getPreference("fielddroid:sharepath"));
        Functions.setServerIP(getPreference("fielddroid:serverip"));
        System.out.println("path" + getPreference("fielddroid:localpath"));
        //System.out.println("path" + s.getPreference("fielddroid:localpath"));
        
        myDbHelper = new DataBaseHelper(this);
        }catch(Exception e){
        	
        }
        try {
 
        	myDbHelper.createDataBase();

 	} catch (IOException ioe) {
 		System.out.println(ioe);
 		throw new Error("Unable to create database");
 
 	}
        
        
        String[] events = null;
 	try {
 		System.out.println("moo");
 		myDbHelper.open();

 		System.out.println("success");
 
 	}catch(Exception sqle){
 		System.out.println("utoh");
 		
 		System.out.println(sqle);
 	}
 	
 	myDbHelper.close();
	

	Button b = (Button)findViewById(R.id.selectevent);
	b.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myIntent;
			myIntent = new Intent(v.getContext(), SelectEvent.class);
			startActivityForResult(myIntent, 0);
		}
	});
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, " " + " selected", Toast.LENGTH_LONG).show();
	}

	protected void onListItemClick(ListView l, View v, int pos, long id) {
		//position = pos;
		String item = (String) listview.getAdapter().getItem(pos);
		Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	}
	 protected boolean onLongListItemClick(View v, int pos, long id) {
	    	//final CharSequence[] items = {"Red", "Green", "Blue"};
		 position = pos;
	    	final String[] menu = getResources().getStringArray(R.array.homelongclick);
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle("Event");
	    	builder.setItems(menu, new DialogInterface.OnClickListener() {
	    	    public void onClick(DialogInterface dialog, int item) {
	    	    	Toast.makeText(getApplicationContext(), menu[item], Toast.LENGTH_SHORT).show();
	    	    	if(item == 0){//Final
	    	    		Map map = (Map)listview.getItemAtPosition(position);
	    	    		String dbid = map.get("id").toString();
	    	    		String eventid = map.get("eventid").toString();
	    	    		String round = map.get("text1").toString();
	    	    		String section = map.get("text2").toString();
	    	    		Functions.setActiveEvent(geteventID());
	    	    		Toast.makeText(getApplicationContext(), "Final Test", Toast.LENGTH_SHORT).show();
	    	    		Intent myIntent;
	    	    		myIntent = new Intent(getApplicationContext(), Final.class);
	    	    		startActivityForResult(myIntent, 0);
	    	    	}else if(item ==1){//delete event
	    	    		
	    	    		
	    	    		
	    	    	}else if(item == 2){//send event
	    	    		Map map = (Map)listview.getItemAtPosition(position);
	    	    		String dbid = map.get("id").toString();
	    	    		String eventid = map.get("eventid").toString();
	    	    		String round = map.get("text1").toString();
	    	    		String section = map.get("text2").toString();
	    	    		SendEvents s = new SendEvents();
	    	    		s.start(Integer.parseInt(dbid), Integer.parseInt(eventid), Integer.parseInt(round), Integer.parseInt(section));
	    	    		Toast.makeText(getApplicationContext(), "send results\n more", Toast.LENGTH_SHORT).show();
	    	    	}
	    	        
	    	    }
	    	});
	    	AlertDialog alert = builder.create();
	    	alert.show();
	    	
	    	Toast.makeText(this, "Long Item Click "+position, Toast.LENGTH_SHORT).show();
	        return true;
	    }
	 public void addlvi(Integer k, Integer l, Integer i, Integer j, String text3){
	    	HashMap<String, Object> entitiesHashMap = new HashMap<String, Object>();
			entitiesHashMap.put("text1", i.toString());
			entitiesHashMap.put("text2", j.toString());
			entitiesHashMap.put("text3", text3);
			entitiesHashMap.put("id", k.toString());
			entitiesHashMap.put("eventid", l.toString());
			hashMapListForListView.add(entitiesHashMap);
	    }
      public void onItemClick(AdapterView<?> arg0, View v, Integer pos,
				long arg3) {
			// TODO Auto-generated method stub
    	  position = pos;
    	  System.out.println(geteventID());
    	  Functions.setActiveEvent(geteventID());
    	  
		//	Intent intent = new Intent(v.getContext(), FlightActivity.class);
		//	startActivityForResult(intent, 0);
		}
      
      protected void onStart(){
    	super.onStart();
      	System.out.println("onStart");
      	rebuild();
      }
      
      protected void onRestart(){
      	super.onRestart();
      	System.out.println("onRestart");
      	rebuild();
      }

      protected void onResume(){
      	super.onResume();
      	rebuild();
      	System.out.println("onResume");
      }

      protected void onPause(){
      	super.onPause();
      	System.out.println("onPause");
      }

      protected void onStop(){
      	super.onStop();
      	System.out.println("onStop");
      }

      protected void onDestroy(){
      	super.onDestroy();
      	System.out.println("onDestroy");
      }
      public void rebuild(){
    	  hashMapListForListView = new ArrayList<HashMap<String,Object>>();
    	 try{ 
    	  DataBaseHelper myDbHelper = new DataBaseHelper(null);
          myDbHelper = new DataBaseHelper(this);
    	  
    	  myDbHelper.open();
   	 	Cursor c = myDbHelper.showEvents();
   	 	//c.moveToNext();
  		Integer count = c.getCount(); 		
  		System.out.println(count);
  		//events = new String[count-1];
//   	 	String Text = "";
  //
  		Integer i =0;
  		c.moveToFirst();
  		for(int f = 0;f<count; f++){
   	 		System.out.println(i);
   	 		 //events[i]=  c.getInt(1) + "," + c.getInt(2) + "," + c.getInt(3) + "," + c.getString(4);
   	 		 addlvi(c.getInt(1), c.getInt(0), c.getInt(2), c.getInt(3), c.getString(4));
   	 		 i++;
   	 		 c.moveToNext();
   	 	}
  		//myDbHelper.addConf(9999, "test", "3");
   	 	myDbHelper.close();
    	 }catch(Exception e){
    		 System.out.println(e);
    	 }
    	 try{
    	  SimpleAdapter adapterForList = new SimpleAdapter(this,
                  hashMapListForListView, R.layout.row,
                  new String[] {"text1", "text2", "text3"},
                  new int[] {R.id.text1, R.id.text2, R.id.text3});

   			listview.setAdapter(adapterForList);
   	        listview.setOnItemClickListener(new OnItemClickListener(){

  				@Override
  				public void onItemClick(AdapterView<?> v, View arg1,
  						int pos, long arg3) {
  					// TODO Auto-generated method stub
  		    	  position = pos;
  		    	  System.out.println(geteventID());
  		    	  Functions.setActiveEvent(geteventID());
  					Intent intent = new Intent(v.getContext(), FlightActivity.class);
  					startActivityForResult(intent, 0);
  				}
   	        	
   	        });
//   	        registerForContextMenu(listView);
   	        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
   	            @Override
   	            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
   	                return onLongListItemClick(v,pos,id);
   	            }
   	        });
    	 }catch(Exception e){
    		 
    	 }
      }
	   public void SavePreference(String key, String value){
		    SharedPreferences sharedPreferences = getSharedPreferences("fielddroid", MODE_PRIVATE);
		    SharedPreferences.Editor editor = sharedPreferences.edit();
		    editor.putString(key, value);
		    editor.commit();
		   }
		  
		   public  String getPreference(String key){
		    SharedPreferences sharedPreferences = getSharedPreferences("fielddroid", MODE_PRIVATE);
		    String thekey = sharedPreferences.getString(key, "");
		    return thekey;
		   }
		   public Integer geteventID(){
			   Map map = (Map)listview.getItemAtPosition(position);
		    	String name = map.get("id").toString();
		    	return Integer.parseInt(name);
		   }
	
}