package com.android.weekender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends ActionBarActivity {

	TextView timeView_days, timeView_hours, timeView_minutes, timeView_seconds;
	TextView message;

	private static final String FORMAT_DAY = "%02d:";
	private static final String FORMAT_HOURS = "%02d:";
	private static final String FORMAT_MINUTES = "%02d:";
	private static final String FORMAT_SECONDS = "%02d";
	
	int seconds, minutes;
	String timeLeftInMiliseconds, isWeekend;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_timer);

		// Image and Button Interface
		timeView_days = (TextView) findViewById(R.id.timeView_days);
		timeView_hours = (TextView) findViewById(R.id.timeView_hours);
		timeView_minutes = (TextView) findViewById(R.id.timeView_minutes);
		timeView_seconds = (TextView) findViewById(R.id.timeView_seconds);

		message = (TextView) findViewById(R.id.message);

		// Set Font type
		Typeface showFont = Typeface.createFromAsset(getAssets(),
				"fonts/Pacifico.ttf");
		Typeface timeFont = Typeface.createFromAsset(getAssets(),
				"fonts/DS-DIGIB.ttf");
		message.setTypeface(showFont, Typeface.NORMAL);
		timeView_days.setTypeface(timeFont, Typeface.NORMAL);
		timeView_hours.setTypeface(timeFont, Typeface.NORMAL);
		timeView_minutes.setTypeface(timeFont, Typeface.NORMAL);
		timeView_seconds.setTypeface(timeFont, Typeface.NORMAL);

		// get time from parse
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			timeLeftInMiliseconds = ParseCloud.callFunction("getTimeLeft",
					params).toString();
			isWeekend = ParseCloud.callFunction("isWeekend", params).toString();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isWeekend.equals("true")) {
			message.setText("Time left to post your fun");
		} else {
			message.setText("Time before the party starts !");
		}

		new CountDownTimer(Long.valueOf(timeLeftInMiliseconds).longValue(),
				1000) {

			public void onTick(long millisUntilFinished) {

				timeView_days.setText(String.format(FORMAT_DAY,
						TimeUnit.MILLISECONDS.toDays(millisUntilFinished)));

				timeView_hours.setText(String.format(
						FORMAT_HOURS,
						TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
								- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS
										.toDays(millisUntilFinished))));
				timeView_minutes
						.setText(String.format(
								FORMAT_MINUTES,
								TimeUnit.MILLISECONDS
										.toMinutes(millisUntilFinished)
										- TimeUnit.HOURS
												.toMinutes(TimeUnit.MILLISECONDS
														.toHours(millisUntilFinished))));
				timeView_seconds
						.setText(String.format(
								FORMAT_SECONDS,
								TimeUnit.MILLISECONDS
										.toSeconds(millisUntilFinished)
										- TimeUnit.MINUTES
												.toSeconds(TimeUnit.MILLISECONDS
														.toMinutes(millisUntilFinished))));

			}

			public void onFinish() {

				timeView_days.setText("00");
				timeView_hours.setText("00");
				timeView_minutes.setText("00");
				timeView_seconds.setText("00");

			}

		}.start();

	}

}
