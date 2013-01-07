package com.lobsternetworks.android.fieldassistant;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lobsternetworks.android.fieldassistant.R;

public class Input extends Activity{
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		SharedPreferences sharedPreferences = getSharedPreferences("fieldassistant", MODE_PRIVATE);
	    String screen = sharedPreferences.getString("fieldassistant:screen", "phone");
	    if(screen.equals("phone")){
	    	setContentView(R.layout.input);
	    }else if(screen.equals("Tablet 10IN")){
	    	setContentView(R.layout.inputtablet10);
	    }else{
	    	setContentView(R.layout.input);
	    }
        Button one = (Button)findViewById(R.id.one);
    	one.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "1";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button two = (Button)findViewById(R.id.two);
    	two.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "2";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button three = (Button)findViewById(R.id.three);
    	three.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "3";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button feet = (Button)findViewById(R.id.feet);
    	feet.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "'";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button inches = (Button)findViewById(R.id.inches);
    	inches.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + '"';
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button four = (Button)findViewById(R.id.four);
    	four.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "4";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button five = (Button)findViewById(R.id.five);
    	five.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "5";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button six = (Button)findViewById(R.id.six);
    	six.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "6";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button seven = (Button)findViewById(R.id.seven);
    	seven.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "7";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button eight = (Button)findViewById(R.id.eight);
    	eight.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "8";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button nine = (Button)findViewById(R.id.nine);
    	nine.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "9";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button onequarter = (Button)findViewById(R.id.onequarter);
    	onequarter.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + ".25";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button half = (Button)findViewById(R.id.half);
    	half.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + ".5";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button threequarters = (Button)findViewById(R.id.threequarters);
    	threequarters.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + ".75";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button delete = (Button)findViewById(R.id.delete);
    	delete.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Functions.chop(Text, 1);
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button period = (Button)findViewById(R.id.period);
    	period.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + ".";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button zero = (Button)findViewById(R.id.zero);
    	zero.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "0";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button nm = (Button)findViewById(R.id.nm);
    	nm.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = Text + "NM";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	Button clear = (Button)findViewById(R.id.clear);
    	clear.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
    			Text = " ";
    			distance.setText(Text);
    			}catch(Exception e){
    				System.out.println(e);
    			}
    		}
    	});
    	
    	Button button = (Button)findViewById(R.id.imabutton);
    	button.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			SchemaHelper d = new SchemaHelper(v.getContext());
    			Integer n = 0;
    			//try{
    				TextView distance = (TextView)findViewById(R.id.distance);
    			String Text = distance.getText().toString();
//    			Text = " ";
//    			distance.setText(Text);

    			Integer e = Integer.parseInt(d.getEventConf(d.getEventID(Functions.getActiveEvent()).getInt(2), "ATTEMPTS"));

    	        Cursor c = d.getAttempts(Functions.getCompetitorID(), Functions.getActiveEvent());
    			
    			if(c != null){
    				c.moveToFirst();
    				n = c.getCount()+1;
    			}else{
    				n=1;
    			}
    			if(n<e+1){
    			System.out.println(Functions.getCompetitorID());
    			System.out.println(Functions.getActiveEvent());
    			Text = Text.replaceAll("'", "/'");
    			d.addAttempt(n, Text, Functions.getCompetitorID(),Functions.getActiveEvent());
    			d.close();
    			finish();
    			}else{
    				Toast.makeText(getApplicationContext(), "Max attempts reached\nAttempt not Recorded", Toast.LENGTH_LONG).show();
    				d.close();
    				
    				distance.setTextColor(Color.RED);
    			}
    			
    		//	}catch(Exception e){
    			//	e.printStackTrace();
    		//	}
//    			System.out.println("back to results");
//    			finish();
    		}
    	});
    	
	}
	
}
