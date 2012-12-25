package com.lobsternetworks.android.fieldassistant;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.lobsternetworks.android.fieldassistant.R;

public class SelectEvent extends Activity{

	
	
	 protected static final int DOWNLOAD_FILES_REQUEST = 0;

	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.selectevent);
	        SharedPreferences sharedPreferences = getSharedPreferences("fieldassistant", MODE_PRIVATE);
		    Boolean SMB_AUTOCONNECT = Boolean.parseBoolean(sharedPreferences.getString("fieldassistant:smb_autoconnect", "false"));
	        if(SMB_AUTOCONNECT == true){
	        Intent intent = new Intent();
			intent.setAction(Intent.ACTION_PICK);
			Uri smbUri = Uri.parse("smb://"+ Functions.getServerIP());
			intent.setDataAndType(smbUri, "vnd.android.cursor.dir/lysesoft.andsmb.uri");
			intent.putExtra("command_type", "download");

			//You can add SMB credentials:
			intent.putExtra("smb_username", "guest");
			//intent.putExtra("smb_password", "yourpassword");
			//intent.putExtra("smb_domain", "YOURDOMAIN");

			//Setup optional SMB preferences:
			//intent.putExtra("smb_encoding", "UTF-8");

			//And define file(s) to download into a local folder:
			intent.putExtra("remote_file1", Functions.getSharePath()+"lynx.evt");
			//intent.putExtra("remote_file2", "/remotefolder/subfolder/file2.zip");
			// Target local folder where files will be downloaded.
			intent.putExtra("local_folder", Functions.getLocalPath()+"lynx.evt"); 

			//Finally start the Activity to be closed after transfer:
			intent.putExtra("close_ui", "true");
			startActivityForResult(intent, DOWNLOAD_FILES_REQUEST);

			//Transfer status will be returned in onActivityResult method:
			String status = intent.getStringExtra("TRANSFERSTATUS");
			String files = intent.getStringExtra("TRANSFERAMOUNT"); 
			String size = intent.getStringExtra("TRANSFERSIZE");
			String time = intent.getStringExtra("TRANSFERTIME");
	        }
	        String[] array_spinner = new String[5];
	        array_spinner[0] = "Select an Event";
	        array_spinner[1] = "Pole Vault";
	        array_spinner[2] = "High Jump";
	        array_spinner[3] = "Horizontal Jumps";
	        array_spinner[4] = "Throws";
	        
	        final Spinner s = (Spinner) findViewById(R.id.selectevent);
	        ArrayAdapter adapter = new ArrayAdapter(this,
	        android.R.layout.simple_spinner_item, array_spinner);
	        s.setAdapter(adapter);
	        Button b = (Button)findViewById(R.id.selecteventbutton);
	    	b.setOnClickListener(new View.OnClickListener() {
	    		
	    		@Override
	    		public void onClick(View v) {
	    			// TODO Auto-generated method stub
	    			
	    			EditText e = (EditText)findViewById(R.id.event);
	    			String round = e.getText().toString();
	    			if(round.length() > 0){
	    				
	    			Pullevents p = new Pullevents();
	    			p.start(v.getContext(), Integer.parseInt(round), s.getSelectedItemPosition());
	    			DataBaseHelper d = new DataBaseHelper(v.getContext());
	    			Spinner type =(Spinner)findViewById(R.id.selectevent);
	    			EditText attempts = (EditText)findViewById(R.id.attempts);
	    			EditText numfinals = (EditText)findViewById(R.id.finals);
	    			EditText finalattempts = (EditText)findViewById(R.id.finalattempts);
	    			System.out.println(Integer.parseInt(round));
	    			d.addConf(Integer.parseInt(round), "TYPE", String.valueOf(type.getSelectedItemPosition()));
	    			d.addConf(Integer.parseInt(round),"ATTEMPTS", attempts.getText().toString());
	    			d.addConf(Integer.parseInt(round),"NUMFINALS", numfinals.getText().toString());
	    			d.addConf(Integer.parseInt(round),"FINALATTEMPTS", finalattempts.getText().toString());
	    			d.close();
	    			
	    			if(attempts.getText().length() > 0){
	    					
	    			}
	    			}
	    			finish();
	    		}
	    	});
	        
	        
	 }



}
