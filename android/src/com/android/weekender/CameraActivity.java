package com.android.weekender;

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

public class CameraActivity extends ActionBarActivity {
	private ImageView pictureView;
	private EditText captionTitle;
	private TextView captionText;
	private Button publishButton;
	
	CameraPresenter mCameraPresenter;
	int CAMERA_REQUEST = 1888;
	LocationManager locManager = null;
	UserObject uObject;

	
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cam);
		
		// Interface Button
				pictureView = (ImageView) findViewById(R.id.pictureView);
				captionTitle = (EditText) findViewById(R.id.title);
				captionText = (TextView) findViewById(R.id.caption);
				publishButton = (Button) findViewById(R.id.publishButton);
	
		// Set font type
		Typeface showFont = Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");   
		captionText.setTypeface(showFont,Typeface.NORMAL);
		publishButton.setTypeface(showFont,Typeface.NORMAL);

		
		
		// get UserObject
		Bundle b = getIntent().getExtras();
		if (b != null){
			uObject = b.getParcelable(Constants.USER_OBJECT);
		} else {
			Log.e("FailedUserObjectPassCamera", "FAIL TO GET ID");
		}
		
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);

		// Interface Button
		pictureView = (ImageView) findViewById(R.id.pictureView);
		captionTitle = (EditText) findViewById(R.id.title);


		// Start Camera Intent
		mCameraPresenter = new CameraPresenter(this);
		Intent cameraIntent = mCameraPresenter.getCameraIntent();
		startActivityForResult(cameraIntent, 1888);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Set ImageView with the picture captured
		Bitmap image = mCameraPresenter.getImage();
		pictureView.setImageBitmap(image);
		
		// get GeoPoint
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mCameraPresenter.setGeoPoint(locManager);
		
	}

	public void publish(View v) {
		String title = captionTitle.getText().toString();
		if (title.matches("")) {
			Toast.makeText(getApplicationContext(),
					"Please enter a title name.", Toast.LENGTH_SHORT).show();
			return;
		}

		boolean res = mCameraPresenter.publish(title);
		if (res) {
			Toast.makeText(getApplicationContext(), "Publish Complete!",
					Toast.LENGTH_SHORT).show();
			goToGalleryActivity();
		} else {
			Toast.makeText(getApplicationContext(), "Unable to publish.",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void goToGalleryActivity() {
		Intent intent = new Intent(this, GalleryActivity.class);
		intent.putExtra(Constants.USER_OBJECT, uObject);
		startActivity(intent);
	}
}
