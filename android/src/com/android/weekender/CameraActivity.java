package com.android.weekender;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends ActionBarActivity {
	private ImageView pictureView;
	private EditText captionTitle;
	CameraPresenter mCameraPresenter;
	int CAMERA_REQUEST = 1888;
	LocationManager locManager = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cam);

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

	public void cancel(View v) {
		goToGalleryActivity();
	}

	private void goToGalleryActivity() {
		Intent intent = new Intent(this, GalleryActivity.class);
		startActivity(intent);
	}
}
