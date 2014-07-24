package com.android.weekender;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.weekender.helper.Constants;
import com.android.weekender.helper.UserObject;
import com.parse.ParseCloud;
import com.parse.ParseException;

public class ItemActivity extends ActionBarActivity {
	// private ImageView pictureView;
	// private EditText captionTitle;
	// private TextView captionText;
	// private Button publishButton;

	// CameraPresenter mCameraPresenter;
	// int CAMERA_REQUEST = 1888;
	// LocationManager locManager = null;
	// UserObject uObject;

	UserObject uObject;
	String pictureId;
	String isToggled;

	TextView photoCaption;
	TextView showLikes ;
	ImageButton like_button;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		setContentView(R.layout.activity_item);
		
		//set font
		Typeface captionFont = Typeface.createFromAsset(getAssets(),
				"fonts/Pacifico.ttf");
		
		photoCaption = (TextView) findViewById(R.id.photo_caption);
		showLikes = (TextView) findViewById(R.id.like_view);
		like_button = (ImageButton) findViewById(R.id.like_button);
		// get UserObject
		Bundle b = getIntent().getExtras();
		if (b != null) {
			uObject = b.getParcelable(Constants.USER_OBJECT);
		} else {
			Log.e("FailedUserObjectPassCamera", "FAIL TO GET ID");
		}

		Toast.makeText(getApplicationContext(),
			uObject.getUserId(), Toast.LENGTH_SHORT).show();
		
		photoCaption.setTypeface(captionFont, Typeface.NORMAL);

		// Get Intent and Fetch Image
		Intent intent = getIntent();
		pictureId = intent.getStringExtra("imageId");
		ImageLoader.fetchImage(intent.getStringExtra("imageId"),
				(ImageView) this.findViewById(R.id.focusItem),
				getApplicationContext(), 1);

		// get number of likes

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pictureId", pictureId);

		try {
			String caption = ParseCloud.callFunction("getCaption", params);
			String numLikes = ParseCloud.callFunction("getLikesByPictureId",
					params).toString();
			photoCaption.setText(caption);
			showLikes.setText(numLikes);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// toggle like button
		params.put("userId", uObject.getUserId());

		try {
			isToggled = ParseCloud.callFunction("isLiked", params).toString();
			if (isToggled.equals("y")) {
				like_button.setBackgroundResource(R.drawable.fist_toggled);
			} else {
				like_button.setBackgroundResource(R.drawable.fist_untoggled);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		like_button.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggleLike();
			}
			
		});
	}

	public void toggleLike() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pictureId", pictureId);
		params.put("userId", uObject.getUserId());
		try {
			String numLikes = ParseCloud.callFunction("updateLike",
					params).toString();
			showLikes.setText(numLikes);
			if (isToggled.equals("y")) {
				isToggled = "n";
				like_button.setBackgroundResource(R.drawable.fist_untoggled);
			} else {
				isToggled = "y";
				like_button.setBackgroundResource(R.drawable.fist_toggled);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
