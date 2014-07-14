package com.android.weekender;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.android.weekender.helper.Constants;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.android.weekender.helper.Constants;
import com.android.weekender.helper.UserObject;

public class GalleryActivity extends ActionBarActivity implements IGalleryView, OnClickListener {

	private GalleryPresenter mGP;
	static final String EXTRA_MESSAGE = "com.android.weekender.MESSAGE";
	GridView hor_gallery;
	UserObject uObject;
	private ArrayList<GalleryItem> mImages;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  

		setContentView(R.layout.activity_gallery);
		
		Bundle b = getIntent().getExtras();
		if (b != null){
			uObject = b.getParcelable(Constants.USER_OBJECT);
		} else {
			Log.e("FailedUserObjectPass", "FAIL TO GET ID");
		}
		
		// make Presenter
		mGP = new GalleryPresenter(this);
		final Context thisCtx = this;
		
		Parse.initialize(this, "7rl4Da1XbvpuaKKDKb6VCMZseFZkwEuKyXT4QPDd",
				"2Rah9NP3dkgUhfToKZlCXT6YLOl4WQUNEMLyC8Ol");

		// get Images

		HashMap<String, Object> params = new HashMap<String, Object>();
		final String imagesClass = Constants.CLASS_IMAGES;
		params.put("start", "0");
		params.put("end", "6");
		params.put("classname", imagesClass);

		// get all  the id's here in an array and then pass the array to Adapter
		String pIds = null;
		try {
			pIds = ParseCloud.callFunction("getAllPictureObjectId", params);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (pIds == null) {
			Log.d("Pictures", "Don't Exist");
			return;
		}
		String[] ListPictureIds = pIds.split(",");
		
		mImages = new ArrayList<GalleryItem>(ListPictureIds.length);
		for (int i=0; i < ListPictureIds.length; i++) {
			mImages.add(new GalleryItem(0, ListPictureIds[i]));
		}

	
		if (mImages.size() != 0) {
			// reference the Gallery Layout
			hor_gallery = (GridView) findViewById(R.id.hor_gallery);
			hor_gallery.setAdapter(new ImageAdapter(thisCtx, mImages));
		}
		
		ImageButton gallery_btn = (ImageButton) findViewById(R.id.action_gallery);
		gallery_btn.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
						  // nothing
			}
 
		});
		
		ImageButton camera_btn = (ImageButton) findViewById(R.id.action_camera);
		camera_btn.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
						   cameraActionPressed( arg0 );
			}
 
		});
		
		hor_gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				ImageView im = new ImageView(v.getContext());
			}
		});
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		
		return true;
	}

	// Called when the user clicks the Send button
	public void cameraActionPressed(View view) {
		// Do something in response to the button
		Intent intent = new Intent(this, CameraActivity.class);
		intent.putExtra(Constants.USER_OBJECT, uObject);
		startActivity(intent);
	}

	View insertPhoto(String path) {
		return mGP.insertPhoto(path, getApplicationContext());
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
		switch (v.getId()) {

		// case
		// whatever picture is clicked pass that onto Presenter mGP
		// case
		// break
		}

	}

}
