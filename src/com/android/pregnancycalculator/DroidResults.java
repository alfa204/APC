/* 
 * Pregnancy Calculator
 * DroidResults.java
 * This Activity is called when pressing calculate on DroidActivity
 * Pulls variables from Bundle B and displays the calculated data
 * Also displays images and description based on weeks along
 * 
 * Dylan Perales
 * Version 1.1
 */

package com.android.pregnancycalculator;

//Imports for this Activity
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//DroidResults Activity class which displays the information
public class DroidResults extends Activity {
	// Declare our view variables
	private TextView babyNameTextView;
	private EditText outputDateTextBox;
	private TextView fDesc;
	private TextView farAlongTextView;
	private TextView toGoTextView;
	private ImageView fiv;

	// @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.reset, menu);

		return true;
	}

	// Method to call when selecting preferences
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.resetInfo:
			// When selecting option show toast message confirming database is being cleared(NOT IMPLEMENTED)
			Toast.makeText(this, getString(R.string.db_cleared),
					Toast.LENGTH_SHORT).show();
			
			// Start Intent/Activity back to main class
			Intent resetIntent = new Intent(this, DroidActivity.class);
			startActivity(resetIntent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// onCreate method which is called within the Activity when calculate is pressed on DroidActivity
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set our view and declare view elements for this view
		setContentView(R.layout.info);
		babyNameTextView = (TextView) findViewById(R.id.babyNameTextView);
		outputDateTextBox = (EditText) findViewById(R.id.outputDateTextBox);
		fDesc = (TextView) findViewById(R.id.fDesc);
		farAlongTextView = (TextView) findViewById(R.id.farAlongTextView);
		toGoTextView = (TextView) findViewById(R.id.toGoTextView);

		// Declare our strings so we can pull the variables from the bundle
		String dueDate = "";
		String babyName = "";

		// Declare and get bundle with variables passed from DroidActivity
		Bundle b = getIntent().getExtras();
		babyName = b.getString("babyNamePass");
		dueDate = b.getString("dueDatePass");
		int weeksAlong = b.getInt("weeksAlong", 0);
		int daysAlong = b.getInt("daysAlong", 0);
		int weeksToGo = b.getInt("weeksToGo", 0);
		int daysToGo = b.getInt("daysToGo", 0);

		// If statements to set Image and Description based on weeks along
		setImageResource(weeksAlong);

		// Get time from conception and time until due date
		String farAlong = getFarAlong(weeksAlong, daysAlong);
		String toGo = getToGo(weeksToGo, daysToGo);

		// Call method to update display
		babyNameTextView.setText(babyName);
		updatefDescDisplay(dueDate, /* fInfo, */farAlong, toGo);

	}

	public void setImageResource(int weeksAlong) {
		fiv = (ImageView) findViewById(R.id.fiv);
		String fInfo = "";
		if (weeksAlong >= 0 && weeksAlong <= 3) {
			fiv.setImageResource(R.drawable.f0_3);
			fInfo = "You are in the fertilization and implantation stage.";
		}
		if (weeksAlong >= 4 && weeksAlong <= 8) {
			fiv.setImageResource(R.drawable.f4_8);
			fInfo = "Your baby is an embryo consisting of two layers of cells from which all her organs and body parts will develop.";
		}
		if (weeksAlong >= 9 && weeksAlong <= 12) {
			fiv.setImageResource(R.drawable.f9_12);
			fInfo = "Your baby is now about the size of a kidney bean and is constantly moving. The baby has distinct, slightly webbed fingers.";
		}
		if (weeksAlong >= 13 && weeksAlong <= 17) {
			fiv.setImageResource(R.drawable.f13_17);
			fInfo = "By now your baby is about 3 inches long and weighs nearly an ounce. The baby's tiny, unique fingerprints are now in place.";
		}
		if (weeksAlong >= 18 && weeksAlong <= 21) {
			fiv.setImageResource(R.drawable.f18_21);
			fInfo = "Your baby is now about 5 inches long and weighs 5 ounces. The baby's skeleton is starting to harden from rubbery cartilage to bone.";
		}
		if (weeksAlong >= 22 && weeksAlong <= 25) {
			fiv.setImageResource(R.drawable.f22_25);
			fInfo = "Eyebrows and eyelids are now in place. Your baby would now be more than 10 inches long if you stretched out its legs.";
		}
		if (weeksAlong >= 26 && weeksAlong <= 30) {
			fiv.setImageResource(R.drawable.f26_30);
			fInfo = "Your baby weighs about a pound and a half. The baby's wrinkled skin is starting to smooth out as it puts on baby fat.";
		}
		if (weeksAlong >= 31 && weeksAlong <= 34) {
			fiv.setImageResource(R.drawable.f31_34);
			fInfo = "By now, your baby weighs about 3 pounds and is more than 15 inches long. The baby can open and close its eyes and follow a light.";
		}
		if (weeksAlong >= 35 && weeksAlong <= 38) {
			fiv.setImageResource(R.drawable.f35_38);
			fInfo = "Your baby now weighs about 4 3/4 pounds. The baby's layers of fat are filling it out, making it rounder, and its lungs are well developed.";
		}
		if (weeksAlong >= 39) {
			fiv.setImageResource(R.drawable.f39);
			fInfo = "The average baby is more than 19 inches long and weighs nearly 7 pounds now, but babies vary widely in size at this stage.";
		}
		fDesc.setText(fInfo);
	}

	// Method to update display with output data
	private void updatefDescDisplay(String dD, String fA, String tG) {

		// Update display with information
		outputDateTextBox.setText(dD);
		farAlongTextView.setText(fA);
		toGoTextView.setText(tG);

	}

	// Method to get how far along user is based on weeks and days and return the string
	private String getFarAlong(int w, int d) {
		String farAlong = "";
		if (w == 0 && d == 0) {
			farAlong = "You just got pregnant!";
		} else {
			if (w == 0) {
				farAlong = "You are " + d + " days along.";
			}
			if (d == 0) {
				farAlong = "You are " + w + " weeks along.";
			} else {
				farAlong = "You are " + w + " weeks and " + d + " days along.";
			}
		}
		return farAlong;
	}

	// Method to get how long until due date based on weeks and days and return the string
	private String getToGo(int w, int d) {
		String toGo = "";
		if (w == 0 && d == 0) {
			toGo = "You have 40 weeks to go!";
		} else {
			if (w == 0) {
				toGo = "You have " + d + " days to go.";
			}
			if (d == 0) {
				toGo = "You have " + w + " weeks to go.";
			} else {
				toGo = "You have " + w + " weeks and " + d + " days to go.";
			}
		}
		return toGo;
	}

}
