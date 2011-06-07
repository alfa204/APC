/* 
 * Pregnancy Calculator
 * ResetActivity.java
 * Reset Activity which will be used to take user back to main layout to reinput data
 * This activity is called from the preferences menu on DroidResults class
 * 
 * Dylan Perales
 * Version 1.1
 */

package com.android.pregnancycalculator;

import android.app.Activity;
import android.os.Bundle;

//ResetActivity class takes user back to main activity to reinput information
public class ResetActivity extends Activity {
	
	//Set content view back to main layout
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
}
