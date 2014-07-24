package com.android.weekender;

import java.util.HashMap;

import com.android.weekender.helper.Constants;
import com.android.weekender.helper.UserObject;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ItemActivity extends ActionBarActivity {
//	private ImageView pictureView;
//	private EditText captionTitle;
//	private TextView captionText;
//	private Button publishButton;
	
//	CameraPresenter mCameraPresenter;
//	int CAMERA_REQUEST = 1888;
//	LocationManager locManager = null;
//	UserObject uObject;
	
	
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_item);
		
		// Get Intent and Fetch Image
		Intent intent = getIntent();
		ImageLoader.fetchImage(intent.getStringExtra("imageId"), (ImageView)this.findViewById(R.id.focusItem), getApplicationContext(), 1);
		
	}
	
	public void toggleLike() {
		Intent intent = getIntent();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", intent.getStringExtra("pictureId"));
		//userId
	}
}
