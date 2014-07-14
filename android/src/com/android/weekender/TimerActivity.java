package com.android.weekender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import android.os.Bundle;

import android.os.CountDownTimer;

import android.support.v7.app.ActionBarActivity;

import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends ActionBarActivity {

	TextView timeView;
	TextView message;

	private static final String FORMAT = "%02d:%02d:%02d:%02d";

	int seconds, minutes;
	String timeLeftInMiliseconds, isWeekend;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_timer);
		
		timeView = (TextView) findViewById(R.id.timeView);
		message = (TextView) findViewById(R.id.message);
		
		//get time from parse
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			timeLeftInMiliseconds = ParseCloud.callFunction("getTimeLeft", params).toString();
			isWeekend = ParseCloud.callFunction("isWeekend", params).toString();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isWeekend.equals("true")) {
			message.setText("TIME LEFT TO POST YOUR FUN");
		} else {
			message.setText("TIME BEFORE PARTY TIME");
		}
		
		new CountDownTimer(Long.valueOf(timeLeftInMiliseconds).longValue(), 1000) {

			public void onTick(long millisUntilFinished) {
				
				timeView.setText(""	+ String.format(FORMAT,
				
				TimeUnit.MILLISECONDS.toDays(millisUntilFinished),
				
				TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)),

				TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),

				TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

			}

			public void onFinish() {

				timeView.setText("done!");

			}

		}.start();

	}

	
}
