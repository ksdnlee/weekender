package com.android.weekender;


import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;


public class Login extends ActionBarActivity {
	
	private LoginButton loginBtn;
	private UiLifecycleHelper uiHelper;
	private final String classname = "UserProfile";
	private boolean loggedFlag = false;
	
	static final String EXTRA_MESSAGE = "com.android.weekender.MESSAGE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		Parse.initialize(this, "7rl4Da1XbvpuaKKDKb6VCMZseFZkwEuKyXT4QPDd", "2Rah9NP3dkgUhfToKZlCXT6YLOl4WQUNEMLyC8Ol");

		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
 
		setContentView(R.layout.activity_login);

		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(final GraphUser user) {
				if (user != null) {
					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("userId", user.getId());
					params.put("class", classname);

					ParseCloud.callFunctionInBackground("isNewUser", params, new FunctionCallback<Integer>() {
					   public void done(Integer result, ParseException e) {
					       if (e == null) {
					    	   if (result == 1) {
					    		   //insert into database
					    		   ParseObject post_data = new ParseObject("UserProfile");
					    		   post_data.put("userId", user.getId());
					    		   post_data.put("firstName", user.getFirstName());
					    		   post_data.put("lastName", user.getLastName());
					    		   post_data.saveInBackground();
					    	   }
					       }
					   }
					});
		    		loggedFlag = true;
					Toast.makeText(getApplicationContext(), "Logged in as " + user.getName(), Toast.LENGTH_SHORT).show();
				    if (loggedFlag) {
				    	moveToGallery();
				    }
					
				} else {
		    		loggedFlag = false;
					Toast.makeText(getApplicationContext(), "Not logged in", Toast.LENGTH_SHORT).show();
				}
			}
		});	
		
	}
	
	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				loggedFlag = true;
				moveToGallery();
				Log.i("FacebookSampleActivity", "Facebook session opened");
			} else if (state.isClosed()) {
				loggedFlag = false;
				Log.i("FacebookSampleActivity", "Facebook session closed");
			}
		}
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
 
	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}
	
	public void moveToGallery () {
		 //Send to main gallery
		Intent intent = new Intent(this, GalleryActivity.class);
		String message = "user";
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
}
