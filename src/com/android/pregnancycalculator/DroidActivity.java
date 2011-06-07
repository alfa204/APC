/* 
 * Pregnancy Calculator
 * DroidActivity.java
 * Main activity which has user input last cycle start date using a Date Picker
 * and passes the variables to the DroidResults Activity to display the calculated
 * information.
 *  
 * Dylan Perales
 * Version 1.1
 */

package com.android.pregnancycalculator;

//Import all necessary imports
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
	// Declare our TextBox Variables
	private EditText inputDateTextBox;
	private EditText babyNameTextBox;

	// Declare our Date Select and Calculate Buttons
	private Button mPickDate;
	private Button calculateButton;

	// Declare our Date integers
	final Calendar cal = Calendar.getInstance();
	private int mYear = cal.get(Calendar.YEAR);
	private int mMonth = cal.get(Calendar.MONTH);
	private int mDay = cal.get(Calendar.DAY_OF_MONTH);

	// Declare DateDialog
	static final int DATE_DIALOG_ID = 0;

	//String startDateInput;

	// Main layout view adding main.xml
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Initialize the EditText fields and Buttons
		inputDateTextBox = (EditText) findViewById(R.id.inputDateTextBox);
		calculateButton = (Button) findViewById(R.id.calculateButton);
		mPickDate = (Button) findViewById(R.id.pickDate);
		babyNameTextBox = (EditText) findViewById(R.id.babyNameTextBox);

		// Create our alert dialog if user does not select a date
		final AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
		errorDialog.setMessage("Please select a Date");
		errorDialog.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {

					// Dismiss the dialog when OK is clicked
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		// FUTURE ERROR DIALOG FOR TESTING DATE RANGE
//		final AlertDialog.Builder inputDateError = new AlertDialog.Builder(this);
//		inputDateError.setMessage("You did not select a date");
//		inputDateError.setPositiveButton("Ok",
//				new DialogInterface.OnClickListener() {
//
//					// Dismiss the dialog when OK is clicked
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				});

		// Listener for Select Date Button. Shows DatePicker Dialog
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		// Listener for Calculate Button
		calculateButton.setOnClickListener(new View.OnClickListener() {

			// onClick listener class when Calculate Button is pressed
			public void onClick(View v) {
				try {
					// Input string grabbed from first EditText Box
					String startDateInput = inputDateTextBox.getText().toString();

					// Create our 3 calendars to use to calculate due date and weeks along
					Calendar startCalendar = new GregorianCalendar();
					Calendar currentCalendar = new GregorianCalendar();
					Calendar dueCalendar = new GregorianCalendar();

					// Create a simple format for date input and output
					SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

					// Convert the user input into a date
					Date date = (Date) df.parse(startDateInput);

					// Set the current date from input on due date and start calendar
					dueCalendar.setTime(date);
					startCalendar.setTime(date);
					if (startCalendar.getTimeInMillis() > currentCalendar
							.getTimeInMillis()) {
						throw new Exception("dateException");
					}

					// Add 280 days to the due date calendar to get the correct due date
					calcDue(dueCalendar);

					// Set outputDate to system date
					Date outputDate = dueCalendar.getTime();

					// Determine weeks along by subtracting start date from current date
					int weeksAlong = currentCalendar.get(Calendar.WEEK_OF_YEAR)- startCalendar.get(Calendar.WEEK_OF_YEAR);
					
					// If user is trying to determine future due date add 52 weeks from weeks along
					if (weeksAlong <= 0){
						weeksAlong += 52;
					}
					
					// Determine days along by subtracting start day from current day of year
					int daysAlong = (currentCalendar.get(Calendar.DAY_OF_YEAR) - startCalendar.get(Calendar.DAY_OF_YEAR)) % 7;
					
					// If user is trying to determine future due date add 7 days to days along 
					if (daysAlong < 0){ 
						daysAlong += 7;
					}
					
					// Get weeks to go by subtracting current week from due week of year
					int weeksTogo = dueCalendar.get(Calendar.WEEK_OF_YEAR) - currentCalendar.get(Calendar.WEEK_OF_YEAR);
					
					// If  user is trying to determine future due date add 52 weeks to weeks to go
					if (weeksTogo < 0){
						weeksTogo += 52;
					}
					
					// Get Days to go by subtracting current day from due day of year
					int daysTogo = (dueCalendar.get(Calendar.DAY_OF_YEAR) - currentCalendar.get(Calendar.DAY_OF_YEAR)) % 7;
					
					// If user is trying to determine future due date add 7 days to days to go
					if (daysTogo < 0){
						daysTogo += 7;
					}

					// Convert to a String
					String dueDate = df.format(outputDate);
					String babyName = babyNameTextBox.getText().toString();

					// Input information into SQLite Database (Experimental)
//					String pregcalcdb = "pregcalc.db";
//					PregCalcData pcDB = new PregCalcData(null, pregcalcdb, null, 1);
//					pcDB.insert(babyName, dueDate, weeksAlong,
//					daysAlong, weeksTogo, daysTogo);
//					pcDB.close();

					// Bundle to pass variables to DroidResults activity
					Bundle b = new Bundle();
					b.putString("babyNamePass", babyName);
					b.putString("dueDatePass", dueDate);
					b.putInt("weeksAlong", weeksAlong);
					b.putInt("daysAlong", daysAlong);
					b.putInt("weeksToGo", weeksTogo);
					b.putInt("daysToGo", daysTogo);
					

					// Intent to start new activity DroidResults
					Intent p = new Intent(DroidActivity.this,DroidResults.class);

					// Put bundle into new intent for new Activity
					p.putExtras(b);

					// Start new activity with intent "p" which contains the bundled variables
					startActivity(p);

				} 	
				// Catch any exceptions and display error dialog
				catch (Exception e) {
					errorDialog.show();
				}
				
			}

		});
	}

	//Calculate due date by adding 280 days from start date
	protected void calcDue(Calendar dueCalendar) {
		dueCalendar.add(Calendar.DAY_OF_YEAR, 280);
	}

	// Function to update the TextBox with selected Date
	private void updateDisplay() {
		inputDateTextBox.setText(new StringBuilder()
				// January is 0 so add 1
				.append(mMonth + 1).append("/").append(mDay).append("/")
				.append(mYear));
	}

	// Create Dialog DatePicker when pressing/clicking button
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	// DatePicker Dialog to set Date integers
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			// updateDisplay function called when selecting Date
			updateDisplay();
		}
	};

}