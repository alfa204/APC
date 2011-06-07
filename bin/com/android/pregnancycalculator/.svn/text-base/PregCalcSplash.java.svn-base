/* 
 * Pregnancy Calculator
 * PregCalcSplash.java
 * Splash Screen Activity that will be used as main startup screen and determine which
 * activity to be called depending on if the DB or Table has been created
 * This class has not been implemented yet
 *  
 * Dylan Perales
 * Version 1.1
 */

package com.android.pregnancycalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class PregCalcSplash extends Activity {

	// how long until we go to the next activity
	protected int _splashTime = 5000;
	protected PregCalcData pregCalcData;

	private Thread splashTread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		pregCalcData = new PregCalcData(this);

		final PregCalcSplash splash = this;

		// thread for displaying the SplashScreen
		splashTread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {

						// wait 5 sec
						wait(_splashTime);
					}

				} catch (InterruptedException e) {
				} finally {
					finish();
					// start a new activity
					if (pregCalcData.count() == 0) {
						Intent n = new Intent();
						n.setClass(splash, DroidActivity.class);
						startActivity(n);
						stop();
					} else {
						Intent p = new Intent();
						p.setClass(splash, DroidResults.class);
						startActivity(p);
						stop();
					}

				}
			}
		};

		splashTread.start();
	}

	// Function that will handle the touch
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (splashTread) {
				splashTread.notifyAll();
			}
		}
		return true;
	}

}
