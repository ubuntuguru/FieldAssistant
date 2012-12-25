package com.lobsternetworks.android.fieldassistant;

//import com.lobsternetworks.android.fielddroid.R;

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
    			try{
    				DataBaseHelper d = new DataBaseHelper(v.getContext());
    				d.open();
    				d.deleteall();
    				d.close();
    				finish();
    			}catch(Exception e){
    				System.out.println(e);
    			}
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
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
}
