package com.lobsternetworks.android.fieldassistant;

//import com.lobsternetworks.android.fielddroid.R;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lobsternetworks.android.fieldassistant.R;

public class Options extends Activity {
    /** Called when the activity is first created. */
    

    

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        
        Button b = (Button)findViewById(R.id.deleteall);
    	b.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			
    				SchemaHelper d = new SchemaHelper(getApplicationContext());
    				d.deleteAll();
    				d.close();
    				finish();
    			
    		}
    	});
    	Button ok = (Button)findViewById(R.id.ok);
    	ok.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				finish();
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	
    	
    	
    	Button pref = (Button)findViewById(R.id.preferences);
    	pref.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), Preferences.class);
		    	startActivityForResult(intent,0);
			}});
    	Button about = (Button)findViewById(R.id.about);
    	about.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), About.class);
		    	startActivityForResult(intent,0);
			}});
    	
    	Button backup = (Button)findViewById(R.id.backup);
    	backup.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataBaseBackup b = new DataBaseBackup();
				try {
					b.backup();
					Toast.makeText(getApplicationContext(), "Backup Completed", Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), "Backup Failed", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}});
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
}
