package com.lobsternetworks.android.fieldassistant;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class main extends ExpandableListActivity
{

    String[][] mListStringArray;
    String[] groups;
    String[][] childs;
	
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
    	Intent intent = new Intent(main.this, Options.class);
    	startActivityForResult(intent,0);
    }
    public void selectevent(View v){
    	Intent intent = new Intent(main.this, SelectEvent.class);
    	startActivityForResult(intent,0);
    }
    public void settingsmenu(View v){
    	Intent intent = new Intent(main.this, Settings.class);
    	startActivityForResult(intent,0);
    }
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.eventevl);
        Settings s = new Settings();
        //DataBaseHelper myDbHelper = new DataBaseHelper(null);
        SchemaHelper sh = new SchemaHelper(this);
        Cursor c = sh.showEvents();
        c.moveToFirst();
        Integer count = c.getCount();
        
        System.out.println("eventcount"+ count);
        for (int i=0;i<count;i++){
        	System.out.println("eventid"+c.getInt(2));
        }
        sh.close();
        SharedPreferences sharedPreferences = getSharedPreferences("fieldassistant", MODE_PRIVATE);
	    String thekey = sharedPreferences.getString("fieldassistant:localpath", "/data/data/com.lobsternetworks.android.fieldassistant/");
        Functions.setLocalPath(thekey);
        

        try{
        System.out.println("my:" + Functions.getLocalPath());
        Functions.setSharePath(getPreference("fieldassistant:sharepath"));
        Functions.setServerIP(getPreference("fieldassistant:serverip"));
        System.out.println("path" + getPreference("fieldassistant:localpath"));
        //System.out.println("path" + s.getPreference("fielddroid:localpath"));
        
        //myDbHelper = new DataBaseHelper(this);
        
        }catch(Exception e){
        	
        }
        
        
        String[] events = null;
 	
 	
        
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

      public void  rebuild(){
          //create the expandable list
    	  try{
          SimpleExpandableListAdapterWithEmptyGroups expListAdapter =
  			new SimpleExpandableListAdapterWithEmptyGroups(
  				this,
  				createGroupList(),	
  				R.layout.group_row,	
  				new String[] { "eventid", "eventname" },	
  				new int[] { R.id.eventid, R.id.eventname },		
  				createChildList(),	
  				R.layout.child_row,	
  				new String[] { "round", "flight" },	
  				new int[] { R.id.childname, R.id.rgb }	
  			);
  		setListAdapter( expListAdapter );
  		getExpandableListView().setOnItemLongClickListener(new OnItemLongClickListener() {
  		    @Override
  		    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
  		    	if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
  		            int groupPosition = ExpandableListView.getPackedPositionGroup(id);
  		            int childPosition = ExpandableListView.getPackedPositionChild(id);
  		          Toast.makeText(getApplicationContext(), "LONG" + groupPosition, Toast.LENGTH_SHORT).show();
  		          
  		          SchemaHelper sh = new SchemaHelper(getApplicationContext());
  		          Cursor c=sh.showDistinctEvents();
  		          c.moveToPosition(groupPosition);
  		          Functions.setExt_event_id(c.getInt(0));
  		        sh.close();
  		            		            // You now have everything that you would as if this was an OnChildClickListener() 
  		            // Add your logic here.
  		        final String[] menu = getResources().getStringArray(R.array.homelongclick);
  		    	AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
  		    	builder.setTitle("Event");
  		    	builder.setItems(menu, new DialogInterface.OnClickListener() {
  		    	    public void onClick(DialogInterface dialog, int item) {
  		    	    	Toast.makeText(getApplicationContext(), menu[item], Toast.LENGTH_SHORT).show();
  		    	    	if(item == 3){
  		    	    		Intent intent = new Intent(main.this, SettingsDistanceActivity.class);
  		    				startActivityForResult(intent, 0);
  		    	    	}
  		    	    }
  		    	});
  		    	AlertDialog alert = builder.create();
  		    	alert.show();
  		    	
  		    	Toast.makeText(getApplicationContext(), "Long Item Click ", Toast.LENGTH_SHORT).show();
  		            // Return true as we are handling the event.
  		            return true;
  		        }
  		    	if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
  		            int groupPosition = ExpandableListView.getPackedPositionGroup(id);
  		            int childPosition = ExpandableListView.getPackedPositionChild(id);
  		          Toast.makeText(getApplicationContext(), "LONG" + childPosition, Toast.LENGTH_SHORT).show();
  		            // You now have everything that you would as if this was an OnChildClickListener() 
  		            // Add your logic here.

  		            // Return true as we are handling the event.
  		            return true;
  		        }
  		    	
  		        return false;
  		    }
  		});
    	  }catch(Exception e){
  			System.out.println(e);
  		}
      }
      public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
    	  Functions.setActiveEvent(Integer.parseInt(childs[groupPosition][childPosition]));
  		Toast.makeText(getApplicationContext(), "Short" + groupPosition + childPosition, Toast.LENGTH_SHORT).show();
  		Intent intent = new Intent(view.getContext(), FlightActivity.class);
			startActivityForResult(intent, 0);
      	    //implement logic to start the appropriate activity for this child.
  		return true;
      	}
      public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {

  		Toast.makeText(getApplicationContext(), "Short" + groupPosition, Toast.LENGTH_SHORT).show();
      	    //implement logic to start the appropriate activity for this child.
  		return true;
      	}


      
      /* This function is called on expansion of the group */
      public void  onGroupExpand  (int groupPosition) {
          try{
               System.out.println("Group exapanding Listener => groupPosition = " + groupPosition);
          }catch(Exception e){
              System.out.println(" groupPosition Errrr +++ " + e.getMessage());
          }
      }  
	private List<HashMap<String, String>> createGroupList() {
	  ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
	  SchemaHelper sh = new SchemaHelper(this);
	  try{ 
    	  //DataBaseHelper myDbHelper = new DataBaseHelper(null);
          //myDbHelper = new DataBaseHelper(this);
		  
    	  
    	  //myDbHelper.open();
   	 	Cursor c = sh.showDistinctEvents();
   	 	c.moveToFirst();
  		Integer count = c.getCount();
  		//System.out.println();
  		System.out.println("group"+count);
  		//events = new String[count-1];
//   	 	String Text = "";
  		groups = new String[count];
  		Integer i =0;
  		c.moveToFirst();
  		for(int f = 0;f<count; f++){
   	 		//System.out.println(i);
   	 		 //events[i]=  c.getInt(1) + "," + c.getInt(2) + "," + c.getInt(3) + "," + c.getString(4);
   	 		//"eventid", "id", "round", "flight", "event"
   	 	HashMap<String, String> map = new HashMap<String, String>();
   	 	groups[f] = c.getString(1);
		map.put("eventid", String.valueOf(c.getInt(0)));
	    map.put("eventname", c.getString(1) );
		result.add(map);
   	 		
   	 		 c.moveToNext();
   	 	}
  		//myDbHelper.addConf(9999, "test", "3");
   	 	//myDbHelper.close();
  		
    	 }catch(Exception e){
    		 System.out.println(e);
    	 }
    	 sh.close();
	  return (List<HashMap<String, String>>)result;
    }

  private List<ArrayList<HashMap<String, String>>> createChildList() {
		ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();
		 SchemaHelper sh = new SchemaHelper(this);
	  try{ 
//    	  DataBaseHelper myDbHelper = new DataBaseHelper(null);
//          myDbHelper = new DataBaseHelper(this);
//    	  
//    	  myDbHelper.open();
		 
    	  Cursor c = sh.showDistinctEvents();
     	 	c.moveToFirst();
    		Integer count_c = c.getCount();
    		System.out.println("child"+count_c);
    		childs = new String[count_c][100];
	for( int i = 0 ; i < count_c ; ++i ) {
	  ArrayList<HashMap<String, String>> secList = new ArrayList<HashMap<String, String>>();
	  System.out.println("Event " + c.getInt(0));
	  Cursor d = sh.getEvent(c.getInt(0));
	  Integer count_d = d.getCount();
	  System.out.println(count_d);
	  d.moveToFirst();
	  for( int n = 0 ; n < count_d; n++ ) {
	    HashMap<String, String> child = new HashMap<String, String>();
		child.put( "round", "Round " + String.valueOf(d.getInt(2)) );
	    child.put( "flight", "Flight " + String.valueOf(d.getInt(3)) );
	    childs[i][n] = String.valueOf(d.getInt(1)); 
		secList.add( child );
		d.moveToNext();
	  }
	  result.add( secList );
	  c.moveToNext();
	}
	
	//myDbHelper.close();
 	 }catch(Exception e){
 		 System.out.println(e);
 	 }
 	sh.close();
	return result;
  }
  public void SavePreference(String key, String value){
	    SharedPreferences sharedPreferences = getSharedPreferences("fieldassistant", MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putString(key, value);
	    editor.commit();
	   }
	  
	   public  String getPreference(String key){
	    SharedPreferences sharedPreferences = getSharedPreferences("fieldassistant", MODE_PRIVATE);
	    String thekey = sharedPreferences.getString(key, "");
	    return thekey;
	   }
}