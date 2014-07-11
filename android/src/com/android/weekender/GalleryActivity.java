package com.android.weekender;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.weekender.helper.Constants;
import com.android.weekender.helper.UserObject;

public class GalleryActivity extends ActionBarActivity implements IGalleryView, OnClickListener {

	private GalleryPresenter mGP;
	static final String EXTRA_MESSAGE = "com.android.weekender.MESSAGE";
	LinearLayout hor_gallery;
	UserObject uObject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		
		Bundle b = getIntent().getExtras();
		if (b != null){
			uObject = b.getParcelable(Constants.USER_OBJECT);
		} else {
			Log.e("FailedUserObjectPass", "FAIL TO GET ID");
		}
		
		
		mGP = new GalleryPresenter(this);

		hor_gallery = (LinearLayout) findViewById(R.id.hor_gallery);

		String External = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
		External = External + "/Pictures";
		
		File targetDirector = new File(External);

		File[] files = targetDirector.listFiles();
//		Toast.makeText(getApplicationContext(), Integer.toString(files.length), Toast.LENGTH_LONG).show();
		if (files != null) {
			for (File file : files) {

				hor_gallery.addView(insertPhoto(file.getAbsolutePath()));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Infalte the menu items for use rin the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.gallery_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Called when the user clicks the Send button
	public void capturePic(View view) {
		// Do something in response to the button
		Intent intent = new Intent(this, CameraActivity.class);
		String message = "hello";
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}

	View insertPhoto(String path) {
		Bitmap bm = decodeSampledBitmapFromUri(path, 220, 220);

		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(250, 250));
		layout.setGravity(Gravity.CENTER);

		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setLayoutParams(new LayoutParams(220, 220));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bm);

		layout.addView(imageView);
		return layout;
	}

	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth,
			int reqHeight) {
		Bitmap bm = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(path, options);

		return bm;
	}

	public int calculateInSampleSize(

	BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		
			//case
			// whatever picture is clicked pass that onto Presenter mGP
			//case
			//break
		}
		
	}

}
