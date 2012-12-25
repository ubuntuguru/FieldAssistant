package com.lobsternetworks.android.fieldassistant;
//import com.lobsternetworks.android.fielddroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.lobsternetworks.android.fieldassistant.R;

public class FlightActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener{
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	int up = 0;
	ListView listView;
	int activeposition;
	
	ArrayList<HashMap<String,Object>> hashMapListForListView = new ArrayList<HashMap<String,Object>>();
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.listview);
		listView = (ListView)findViewById(R.id.listview01);
		
		
		DataBaseHelper myDbHelper = new DataBaseHelper(null);
        myDbHelper = new DataBaseHelper(this);
		try{
			myDbHelper.open();
			System.out.println("db open fa");
			System.out.println(Functions.getActiveEvent());
	 	 	Cursor c = myDbHelper.getCompetitorsfromeventid(Functions.getActiveEvent());
	 	 	System.out.println("did i die here?");
	 	 	Integer drawable;
	 	 	for(Integer i = 0;i<c.getCount();i++){
	 	 		System.out.println(c.getString(3));
	 	 		 //events[i]=  c.getInt(1) + "," + c.getInt(2) + "," + c.getInt(3) + "," + c.getString(4);

	 	 		Cursor a = myDbHelper.getAttempts(c.getInt(0), Functions.getActiveEvent());
	 	 		 addlvi(R.drawable.ic_launcher, R.drawable.pepper, c.getString(2) + ", " + c.getString(1), c.getString(3), c.getInt(0), a.getCount());
	 	 		 //i++;
	 	 		 c.moveToNext();
	 	 	}
	 	 	
	 	 	myDbHelper.close();
	 	 	
//			for(Integer i=0;i < 5; i++){
//			addlvi(Functions.ivleftswitcher(i),  R.drawable.ic_launcher, "Athlete, An");
//			}
	 	 	//whoisup();
		SimpleAdapter adapterForList = new SimpleAdapter(this,
                hashMapListForListView, R.layout.row_view,
                new String[] {"ivleft", "competitor", "text1", "ivright"},
                new int[] {R.id.left, R.id.text, R.id.text1, R.id.right}){
	         @Override
	            public View getView(int position, View convertView, ViewGroup parent) {
	                View row =  super.getView(position, convertView, parent);
	                View left = row.findViewById(R.id.left);
	                left.setTag(position);
	                left.setOnClickListener(FlightActivity.this);
	                View right = row.findViewById(R.id.right);
	                right.setTag(position);
	                right.setOnClickListener(FlightActivity.this);
	                
	                return row;
	            }
			
			
		};
		
		
        listView.setAdapter(adapterForList);
        listView.setOnItemClickListener(this);
//        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                return onLongListItemClick(v,pos,id);
            }
        });

		}catch(Exception e){
			System.out.println(e);
		}
		whoisup();
		rebuild();
	}

    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.left:
            Toast.makeText(this, "Left Accessory "+v.getTag(), Toast.LENGTH_SHORT).show();
            break;
        case R.id.right:
        	onClickright(v);
            Toast.makeText(this, "Right Accessory "+v.getTag(), Toast.LENGTH_SHORT).show();
            break;
        default:
            break;
        }
    }
    
    public void onClickleft(View v){
    	activeposition = Integer.parseInt(v.getTag().toString());
    	
    	setcompetitor();
    	//Functions.setActiveEvent(activeposition);
    	rebuild();
    }
    
    public void onClickright(View v){
    	activeposition = Integer.parseInt(v.getTag().toString());
    	
    	setcompetitor();
    	Functions.setActiveEvent(activeposition);
    	final String[] menu = getResources().getStringArray(R.array.rightclick);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Attempt");
    	builder.setItems(menu, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	
    	    	rebuild();
    	        Toast.makeText(getApplicationContext(), menu[item], Toast.LENGTH_SHORT).show();
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    public void addlvi(Integer ivleft, Integer ivright, String competitor, String School, Integer id, Integer attempts){
    	HashMap<String, Object> entitiesHashMap = new HashMap<String, Object>();
		entitiesHashMap.put("ivleft", ivleft);
		entitiesHashMap.put("competitor", competitor);
		entitiesHashMap.put("ivright", R.drawable.pepper);
		entitiesHashMap.put("text1", School);
		entitiesHashMap.put("id", id);
		entitiesHashMap.put("attempts", attempts);
		hashMapListForListView.add(entitiesHashMap);
    }
    
    public void updateivleft(Integer blah){
    	Map map = (Map)listView.getItemAtPosition(activeposition);
    	
    	map.put("ivleft", Functions.ivleftswitcher(blah));   
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	activeposition = position;
    	setcompetitor();
    	Intent myIntent;
    	myIntent = new Intent(v.getContext(), Results.class);
		startActivityForResult(myIntent, 0);
        Toast.makeText(this, "Item Click "+position, Toast.LENGTH_SHORT).show();
    }
    protected boolean onLongListItemClick(View v, int position, long id) {
    	//final CharSequence[] items = {"Red", "Green", "Blue"};
    	activeposition = position;
    	setcompetitor();
    	final String[] menu = getResources().getStringArray(R.array.flightlongclick);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Competitor");
    	builder.setItems(menu, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	if(item == 0){
    	    		//checkin();
    	    	}else if(item == 1){
    	    		updateivleft(3);
    	    	}else if(item == 2){
    	    		updateivleft(5);
    	    	}else if(item == 3){
    	    		updateivleft(0);
    	    	}
    	    	rebuild();
    	        Toast.makeText(getApplicationContext(), menu[item], Toast.LENGTH_SHORT).show();
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    	
    	Toast.makeText(this, "Long Item Click "+position, Toast.LENGTH_SHORT).show();
        return true;
    }
    protected void onStart(){
    	super.onStart();
    	System.out.println("onStart");
    }
    
    protected void onRestart(){
    	super.onRestart();
    	System.out.println("onRestart");
    	repull();
    	whoisup();
    	rebuild();
    }

    protected void onResume(){
    	super.onResume();
    	onRestart();
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
    
    public void setcompetitor(){
    	Map map = (Map)listView.getItemAtPosition(activeposition);
    	Integer cid = (Integer) map.get("id");
    	Functions.setCompetitorID(cid);
    }
        
    public void repull(){
    	hashMapListForListView.clear();
		DataBaseHelper myDbHelper = new DataBaseHelper(null);
        myDbHelper = new DataBaseHelper(this);
		try{
			myDbHelper.open();
			System.out.println("db open fa");
			System.out.println(Functions.getActiveEvent());
	 	 	Cursor c = myDbHelper.getCompetitorsfromeventid(Functions.getActiveEvent());
	 	 	System.out.println("did i die here?");
	 	 	Integer drawable;
	 	 	for(Integer i = 0;i<c.getCount();i++){
	 	 		System.out.println(c.getString(3));
	 	 		 //events[i]=  c.getInt(1) + "," + c.getInt(2) + "," + c.getInt(3) + "," + c.getString(4);

	 	 		Cursor a = myDbHelper.getAttempts(c.getInt(0), Functions.getActiveEvent());
	 	 		 addlvi(R.drawable.ic_launcher, R.drawable.pepper, c.getString(2) + ", " + c.getString(1), c.getString(3), c.getInt(0), a.getCount());
	 	 		 //i++;
	 	 		 c.moveToNext();
	 	 	}
	 	 	myDbHelper.close();
		}catch(Exception e){
			
		}
    }
    
    public void rebuild(){
    	

    	SimpleAdapter adapterForList = new SimpleAdapter(this,
                hashMapListForListView, R.layout.row_view,
                new String[] {"ivleft", "competitor", "text1", "ivright"},
                new int[] {R.id.left, R.id.text, R.id.text1, R.id.right}){
	         @Override
	            public View getView(int position, View convertView, ViewGroup parent) {
	                View row =  super.getView(position, convertView, parent);
	                View left = row.findViewById(R.id.left);
	                left.setTag(position);
	                left.setOnClickListener(FlightActivity.this);
	                View right = row.findViewById(R.id.right);
	                right.setTag(position);
	                right.setOnClickListener(FlightActivity.this);
	                
	                return row;
	            }
			
			
		};
		listView.setAdapter(adapterForList);
        listView.setOnItemClickListener(this);
//        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                return onLongListItemClick(v,pos,id);
            }
        });
    
    }
    public Map getmap(Integer m){
    	Map map = (Map)listView.getItemAtPosition(m);
    	return map;
    }
    public void whoisup(){
    	Integer j = listView.getCount();
    	
    	System.out.println("Blah Athletes "+ j);
    	Integer i;
    	for(i=1;i<j;i++){
    	Map a = (Map)listView.getItemAtPosition(i-1);
    	System.out.println("a"+(i-1)+"  " + a.get("attempts"));
    	Map b = (Map)listView.getItemAtPosition(i);
    	System.out.println("b"+(i)+"  " + b.get("attempts"));
    	if(Integer.parseInt(a.get("attempts").toString()) > Integer.parseInt(b.get("attempts").toString())){

    		System.out.println("a is up");
    		b.put("ivleft", R.drawable.up);
    		System.out.println("ath:" + i);
    		up=1;
    		Integer cpos = i+1;
    		Integer dpos = i+2;
    		if (cpos ==j){cpos=0;dpos=1;}
    		Map c = getmap(cpos);
    		c.put("ivleft", R.drawable.od);
    		
    		if (dpos ==j){dpos=0;}
    		Map d = getmap(dpos);
    		d.put("ivleft", R.drawable.ith);
    		i=j+10;
    		
    	}
    	}
    	if(up == 0){
    		//Map f = getmap(0);
    		Map f = (Map)listView.getItemAtPosition(0);
    		//try{
    		f.remove("ivleft");
    		f.put("ivleft", R.drawable.up);
    		Map b = getmap(1);
    		b.remove("ivleft");
    		b.put("ivleft", R.drawable.od);
    		Map c = getmap(2);
    		c.remove("ivleft");
    		c.put("ivleft", R.drawable.ith);
//    		}catch(Exception e){
//    			System.out.println(e);
//    			e.printStackTrace();
//    		}
    	
    	}
    }


}
