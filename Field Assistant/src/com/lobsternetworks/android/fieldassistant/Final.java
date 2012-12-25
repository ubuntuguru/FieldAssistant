package com.lobsternetworks.android.fieldassistant;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.lobsternetworks.android.fieldassistant.R;

public class Final extends Activity {
	Integer Event = Functions.getActiveEvent();

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
    	Intent intent = new Intent(Final.this, Options.class);
    	startActivityForResult(intent,0);
    }
    public void settingsmenu(View v){
    	Intent intent = new Intent(Final.this, Settings.class);
    	startActivityForResult(intent,0);
    }
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalview);
        
    }
	
}
    