package com.lobsternetworks.android.fieldassistant;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.lobsternetworks.android.fieldassistant.R;

public class Preferences extends Activity{

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref);
        
        final String[] array_spinner = new String[]{"Phone", "Tablet 10IN"};

        SharedPreferences sharedPreferences = getSharedPreferences("fielddroid", MODE_PRIVATE);
	    String screen = sharedPreferences.getString("fielddroid:screen", "phone");
        final Spinner s = (Spinner) findViewById(R.id.screenspinner);
        ArrayAdapter adapter = new ArrayAdapter(this,
        android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
        Integer pos = adapter.getPosition(screen);
        s.setSelection(pos);
        TextView text = (TextView)findViewById(R.id.preftext);
        text.setText("Preferences\n\nRuleBook:\n\nEnglish/Metric\n\nUI Size\n\n");
        Button back = (Button)findViewById(R.id.prefback);
        back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				SharedPreferences sharedPreferences = getSharedPreferences("fielddroid", MODE_PRIVATE);
			    SharedPreferences.Editor editor = sharedPreferences.edit();
			    editor.putString("fielddroid:screen", array_spinner[s.getSelectedItemPosition()]);
			    editor.commit();
				finish();
			}});
	}
	
	
}
