package com.lobsternetworks.android.fieldassistant;

import java.io.File;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lobsternetworks.android.fieldassistant.R;

public class Settings extends Activity {

	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.settings);
	        //SavePreferences(null, null);
	        EditText serverip = (EditText)findViewById(R.id.serverip);
	        serverip.setText(getPreference("fieldassistant:serverip"));
	        EditText sharepath = (EditText)findViewById(R.id.sharepath);
	        sharepath.setText(getPreference("fieldassistant:sharepath"));
	        EditText localpath = (EditText)findViewById(R.id.localpath);
	        String lpath = getPreference("fieldassistant:localpath");
	        if(lpath.length() < 1){
	        	lpath="/mnt/sdcard/meet/";
	        	File file = new File("/mnt/sdcard/meet/");
	        	if(!file.exists()){
	        	 File dir = new File("/mnt/sdcard/meet/");
	        	 	dir.mkdir();
	        	}
	        }
	        localpath.setText(lpath);
	        CheckBox smb_autoconnect = (CheckBox)findViewById(R.id.smb_autoconnect);
	        smb_autoconnect.setChecked(Boolean.parseBoolean(getPreference("fielddroid:smb_autoconnect")));
	        TextView path = (TextView)findViewById(R.id.storagepath);
	        String pathview = path.getText().toString();
	        path.setText(pathview + Environment.getExternalStorageDirectory().toString());
	        System.out.println(Environment.getExternalStorageDirectory().toString());
	        
	        Button button = (Button) findViewById(R.id.btnsave); 
			  button.setOnClickListener(new View.OnClickListener() {
		          public void onClick(View v) {
		        	  Functions f = new Functions();
		        	  EditText serverip = (EditText)findViewById(R.id.serverip);
		        	  SavePreference("fieldassistant:serverip", serverip.getText().toString());
		        	  EditText sharepath = (EditText)findViewById(R.id.sharepath);
		        	  SavePreference("fieldassistant:sharepath", sharepath.getText().toString());
		        	  EditText localpath = (EditText)findViewById(R.id.localpath);
		        	  SavePreference("fieldassistant:localpath", localpath.getText().toString());	  
		        	  CheckBox smb_autoconnect = (CheckBox)findViewById(R.id.smb_autoconnect);
		        	  SavePreference("fieldassistant:smb_autoconnect", String.valueOf(smb_autoconnect.isChecked()));
		        	  f.setLocalPath(localpath.getText().toString());
		              
		        	  finish();
		        	  
		          }
		      });
			
	 }
	 public void onClick(View v){
		 Toast.makeText(this, "YOU CLICKED ME K THX BYE", Toast.LENGTH_SHORT).show();
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
