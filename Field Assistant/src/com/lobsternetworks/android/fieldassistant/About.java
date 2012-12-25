package com.lobsternetworks.android.fieldassistant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.lobsternetworks.android.fieldassistant.R;

public class About extends Activity{

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        TextView text = (TextView)findViewById(R.id.abouttext);
        text.setText("About:\n\n Written by George Moody\n georgemoody@gmail.com");
        Button back = (Button)findViewById(R.id.aboutback);
        back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}});
	}
	
	
}
