package com.android.pregnancycalculator;

//Import all necessary classes
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.android.pregnancycalculator.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.app.Dialog;

//Our class DroidActivity which runs the application
public class DroidActivity extends Activity {
	//Declare our TextBox Variables
	private EditText inputDateTextBox;
	
	//Declare our Date Select and Calculate Buttons
	private Button mPickDate;
	private Button calculateButton;
	
	//Declare our Date integers
	private int mYear;
    private int mMonth;
    private int mDay;
	
    //Declare DateDialog
    static final int DATE_DIALOG_ID=0;
    
    //Main layout view
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Initialize the EditText fields
        inputDateTextBox = (EditText)findViewById(R.id.inputDateTextBox);
        calculateButton = (Button)findViewById(R.id.calculateButton);
        mPickDate = (Button) findViewById(R.id.pickDate);
        
        //Listener for Select Date Button. Shows DatePicker Dialog
        mPickDate.setOnClickListener (new View.OnClickListener() {
        	public void onClick (View v) {
        		showDialog(DATE_DIALOG_ID);
        	}
        });
        
        //Initialize DatePicker Calendar and date integers
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        //Set the output fields so they cannot be selected or edited
//        outputDateTextBox.setFocusable(false);
//        weeksTextBox.setFocusable(false);
        
        //Create our alert dialog in case a date is not selected
        final AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
		errorDialog.setMessage("Date not selected");
		errorDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			//Dismiss the dialog when OK is clicked
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
			});

		//Listener for Calculate Button
        calculateButton.setOnClickListener(new View.OnClickListener() {
			
        	//onClick listener class when Calculate Button is pressed
			public void onClick(View v) {
				try
				{
					
					//Input string grabbed from first EditText Box
					String startDateInput = inputDateTextBox.getText().toString();
					
					
					//Create our 3 calendars to use to calculate due date and weeks along
					Calendar startCalendar = new GregorianCalendar();
					Calendar currentCalendar = new GregorianCalendar();
					Calendar dueCalendar = new GregorianCalendar();
					
					//Create a simple format for date input and output
					SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					
					//Convert the user input into a date
					Date date = (Date)df.parse(startDateInput);
					
					//Set the current date from input on due date and start calendar
					dueCalendar.setTime(date);
					startCalendar.setTime(date);
					
					//Add 280 days to the due date calendar to get the correct due date
					dueCalendar.add(Calendar.DAY_OF_YEAR, 280);
					
					//Set outputDate to system date
					Date outputDate = dueCalendar.getTime();
					
					//Convert to a String
					String dueDate="";
					
					//Determine weeks along by subtracting start date from current date
					int weeksAlong = currentCalendar.get(Calendar.WEEK_OF_YEAR) - startCalendar.get(Calendar.WEEK_OF_YEAR);
					int daysAlong = (currentCalendar.get(Calendar.DAY_OF_YEAR) - startCalendar.get(Calendar.DAY_OF_YEAR)) %7;
					int weeksToGo = dueCalendar.get(Calendar.WEEK_OF_YEAR) - currentCalendar.get(Calendar.WEEK_OF_YEAR);
					int daysToGo = (dueCalendar.get(Calendar.DAY_OF_YEAR) - currentCalendar.get(Calendar.DAY_OF_YEAR)) %7; 
					
					
					Bundle b=new Bundle();
					b.putString(dueDate, df.format(outputDate));
					b.putInt("weeksAlong", weeksAlong);
					b.putInt("daysAlong", daysAlong);
					b.putInt("weeksToGo", weeksToGo);
					b.putInt("daysToGo", daysToGo);
					Intent i=new Intent(DroidActivity.this, DroidResults.class);
					i.putExtras(b);

					startActivity(i);
					
				//Catch any exceptions and display error dialog
				}catch(Exception e){
					errorDialog.show();
				}
			}
		});
    }
    
    //Create Dialog DatePicker when pressing/clicking button
    @Override
    protected Dialog onCreateDialog(int id){
    	switch (id) {
    		case DATE_DIALOG_ID:
    			return new DatePickerDialog(this,
    					mDateSetListener,
    					mYear, mMonth, mDay);
        	}
    	return null;
    }
    
    //Function to update the TextBox with selected Date
    private void updateDisplay(){
    	inputDateTextBox.setText(
    			new StringBuilder()
    			//Month is 0 so add 1
    			.append(mMonth +1).append("/")
    			.append(mDay).append("/")
    			.append(mYear).append(""));
    	
    	//Clear previous fields when selecting a Date (Possible future use with 
//    	outputDateTextBox.setText("");
//    	weeksTextBox.setText(""); 			
    	}
    
    //DatePicker Dialog to set Date integers
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    
                    //updateDisplay function called when selecting Date
                    updateDisplay();
                }
            };
    
    }
