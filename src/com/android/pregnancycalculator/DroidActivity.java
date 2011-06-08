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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.android.pregnancycalculator.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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

		// **FUTURE ERROR DIALOG FOR TESTING DATABASE INPUT**
		final AlertDialog.Builder dbFail = new AlertDialog.Builder(this);
		dbFail.setMessage("Database input failed");
		dbFail.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {

					// Dismiss the dialog when OK is clicked
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

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
					
					// If due date is next year add 52 weeks from weeks along
					if (weeksAlong <= 0){
						weeksAlong += 52;
					}
					
					// Determine days along by subtracting start day from current day of year
					int daysAlong = (currentCalendar.get(Calendar.DAY_OF_YEAR) - startCalendar.get(Calendar.DAY_OF_YEAR)) % 7;
					
					// If due date is next year add 7 days to days along 
					if (daysAlong < 0){ 
						daysAlong += 7;
					}
					
					// Get weeks to go by subtracting current week from due week of year
					int weeksTogo = dueCalendar.get(Calendar.WEEK_OF_YEAR) - currentCalendar.get(Calendar.WEEK_OF_YEAR);
					
					// If due date is next year add 52 weeks to weeks to go
					if (weeksTogo < 0){
						weeksTogo += 52;
					}
					
					// Get Days to go by subtracting current day from due day of year
					int daysTogo = (dueCalendar.get(Calendar.DAY_OF_YEAR) - currentCalendar.get(Calendar.DAY_OF_YEAR)) % 7;
					
					// If due date is next year add 7 days to days to go
					if (daysTogo < 0){
						daysTogo += 7;
					}

					// Convert date to a String to output and pass in bundle
					String dueDate = df.format(outputDate);
					
					//Get babyname from babyname textbox
					String babyName = babyNameTextBox.getText().toString();

					// Input information into SQLite Database (Experimental)
					updateDB(babyName, dueDate, weeksAlong, daysAlong, weeksTogo, daysTogo);

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

	// Method to insert data into Database
	private void updateDB(String bN, String dD, int wA, int dA, int wT, int dT){
		try{
			PregCalcData pcDB = new PregCalcData(this);
			pcDB.insert(bN, dD, wA, dA, wT, dT);
			pcDB.close();
		}
		// Catch exception and show toast message with information
		catch (Exception e) {
			displayExceptionMessage(this, e.getMessage());
			
			// **Toast message to report failure (Future use)**
//			Toast.makeText(this, getString(R.string.dbInputFailed),
//					Toast.LENGTH_SHORT).show();
		}
	}
	// Method to show exception toast message
	public static void displayExceptionMessage(Context context, String msg) {
		 Toast.makeText(context, msg , Toast.LENGTH_LONG).show();
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