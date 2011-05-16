package com.android.pregnancycalculator;

//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class DroidResults extends Activity{
	private EditText outputDateTextBox;
	//private EditText weeksTextBox;
	private TextView fDesc;
	private TextView farAlongTextView;
	private TextView toGoTextView;
		
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        outputDateTextBox = (EditText)findViewById(R.id.outputDateTextBox);
        //weeksTextBox = (EditText)findViewById(R.id.weeksTextBox);
        fDesc = (TextView)findViewById(R.id.fDesc);
        farAlongTextView = (TextView)findViewById(R.id.farAlongTextView);
        toGoTextView = (TextView)findViewById(R.id.toGoTextView);
        
        String dueDate = "";
        String fInfo = "";
        
        Bundle b = getIntent().getExtras(); 
        int weeksAlong = b.getInt("weeksAlong", 0);
        int daysAlong = b.getInt("daysAlong", 0);
        int weeksToGo = b.getInt("weeksToGo", 0);
        int daysToGo = b.getInt("daysToGo", 0);
        dueDate = b.getString(dueDate);
        
        
        ImageView fiv = (ImageView)findViewById(R.id.fiv);
        if (weeksAlong>=0 && weeksAlong<=3){
        	fiv.setImageResource(R.drawable.f0_3);
        	fInfo = "You are in the fertilization and implantation stage.";
        }
		if (weeksAlong>=4 && weeksAlong<=8){
			fiv.setImageResource(R.drawable.f4_8);
			fInfo = "Your baby is an embryo consisting of two layers of cells from which all her organs and body parts will develop.";
		}
		if (weeksAlong>=9 && weeksAlong<=12){
			fiv.setImageResource(R.drawable.f9_12);
			fInfo = "Your baby is now about the size of a kidney bean and is constantly moving. The baby has distinct, slightly webbed fingers.";
		}
		if (weeksAlong>=13 && weeksAlong<=17){
			fiv.setImageResource(R.drawable.f13_17);
			fInfo = "By now your baby is about 3 inches long and weighs nearly an ounce. The baby's tiny, unique fingerprints are now in place.";
		}
		if (weeksAlong>=18 && weeksAlong<=21){
			fiv.setImageResource(R.drawable.f18_21);
			fInfo = "Your baby is now about 5 inches long and weighs 5 ounces. The baby's skeleton is starting to harden from rubbery cartilage to bone.";
		}
		if (weeksAlong>=22 && weeksAlong<=25){
			fiv.setImageResource(R.drawable.f22_25);
			fInfo = "Eyebrows and eyelids are now in place. Your baby would now be more than 10 inches long if you stretched out its legs.";
		}
		if (weeksAlong>=26 && weeksAlong<=30){
			fiv.setImageResource(R.drawable.f26_30);
			fInfo = "Your baby weighs about a pound and a half. The baby's wrinkled skin is starting to smooth out as it puts on baby fat.";
		}
		if (weeksAlong>=31 && weeksAlong<=34){
			fiv.setImageResource(R.drawable.f31_34);
			fInfo = "By now, your baby weighs about 3 pounds and is more than 15 inches long. The baby can open and close its eyes and follow a light.";
		}
		if (weeksAlong>=35 && weeksAlong<=38){
			fiv.setImageResource(R.drawable.f35_38);
			fInfo = "Your baby now weighs about 4 3/4 pounds. The baby's layers of fat are filling it out, making it rounder, and its lungs are well developed.";
		}
		if (weeksAlong>=39){
			fiv.setImageResource(R.drawable.f39);
			fInfo = "The average baby is more than 19 inches long and weighs nearly 7 pounds now, but babies vary widely in size at this stage.";
		}
        
		//Output due date to second text box
		outputDateTextBox.setText(String.valueOf(dueDate));
		
		String farAlong = getFarAlong(weeksAlong, daysAlong);
		String toGo = getToGo(weeksToGo, daysToGo);
		
		//Output weeks along to third text box
		farAlongTextView.setText(String.valueOf(farAlong));
		toGoTextView.setText(toGo);
		
		
		updatefDescDisplay(fInfo);
		
		}
    
    private void updatefDescDisplay(String m) {
        fDesc.setText(m);
    }
    
    private String getFarAlong(int w, int d){
    	String farAlong = "";
    	if(w==0 && d==0){
    		farAlong = "You just got pregnant!";
    	}
	    	else{	
	    		if(w==0){
	    			farAlong = "You are "+d+" days along.";
	    		}
		    	if(d==0){
		    		farAlong = "You are "+w+" weeks along.";
		    	}
		    	else{
		    		farAlong = "You are "+w+" weeks and "+d+" days along.";
		    	}
    	}
    	return farAlong;
    }
    
    private String getToGo(int w, int d){
    	String toGo = "";
    	if(w==0 && d==0){
    		toGo = "You have 40 weeks to go!";
    	}
	    	else{	
	    		if(w==0){
	    			toGo = "You have "+d+" days to go.";
	    		}
		    	if(d==0){
		    		toGo = "You have "+w+" weeks to go.";
		    	}
		    	else{
		    		toGo = "You have "+w+" weeks and "+d+" days to go.";
		    	}
    	}
    	return toGo;
    }
			
    }
