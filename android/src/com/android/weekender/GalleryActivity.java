package com.android.weekender;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Gallery extends ActionBarActivity {
	
	static final String EXTRA_MESSAGE = "com.android.weekender.MESSAGE";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }
	
	// Called when the user clicks the Send button
	public void capturePic(View view){
		//Do something in response to the button
		Intent intent = new Intent(this, Capture.class);
		String message = "hello";
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}

}
