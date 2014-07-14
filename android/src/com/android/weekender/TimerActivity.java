package com.android.weekender;

import java.util.concurrent.TimeUnit;

import android.os.Bundle;

import android.os.CountDownTimer;

import android.support.v7.app.ActionBarActivity;

import android.widget.TextView;

public class TimerActivity extends ActionBarActivity {

	TextView timeView;

	private static final String FORMAT = "%02d:%02d:%02d";

	int seconds, minutes;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_timer);

		timeView = (TextView) findViewById(R.id.timeView);

		String timeInMilliseconds = "1405058785773";

		String millisecondsInADay = "86400000";

		new CountDownTimer(Long.valueOf(timeInMilliseconds).longValue(), 1000) {

			public void onTick(long millisUntilFinished) {

				timeView.setText(""

				+ String.format(

				FORMAT,

				TimeUnit.MILLISECONDS

				.toHours(millisUntilFinished),

				TimeUnit.MILLISECONDS

				.toMinutes(millisUntilFinished)

				- TimeUnit.HOURS

				.toMinutes(TimeUnit.MILLISECONDS

				.toHours(millisUntilFinished)),

				TimeUnit.MILLISECONDS

				.toSeconds(millisUntilFinished)

				- TimeUnit.MINUTES

				.toSeconds(TimeUnit.MILLISECONDS

				.toMinutes(millisUntilFinished))));

			}

			public void onFinish() {

				timeView.setText("done!");

			}

		}.start();

	}

}
