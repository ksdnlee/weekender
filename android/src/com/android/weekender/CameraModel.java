package com.android.weekender;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.android.weekender.helper.Constants;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class CameraModel {
	private String photoDirectoryName = Constants.PHOTO_DIR_NAME;
	private String photoName = Constants.PHOTO_TEMP_NAME;
	File photoStorageName = null;
	File photoPath = null;

	// Parse Server Variables
	String parseImageClass = Constants.CLASS_IMAGES;
	String parseImageByteColumn = Constants.COLUMN_IMAGEBYTE;
	String parseTitleColumn = Constants.COLUMN_TITLE;
	String parseUserIdColumn = Constants.COLUMN_USERID;
	String parseGeoPoint = Constants.COLUMN_GEOPOINT;

	// Test strings
	String userId = "abcdefg";

	// GeoPoint
	Double longitude;
	Double latitude;

	public CameraModel(CameraActivity view) {
		Parse.initialize(view, "7rl4Da1XbvpuaKKDKb6VCMZseFZkwEuKyXT4QPDd",
				"2Rah9NP3dkgUhfToKZlCXT6YLOl4WQUNEMLyC8Ol");
	}

	public Intent getCameraIntent() {
		photoStorageName = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				photoDirectoryName);

		// start camera intent
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

		try {
			String folderpath = createImageDirectory();
			photoPath = new File(folderpath + "/" + photoName);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(photoPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cameraIntent;
	}

	public Bitmap getImage() {
		Log.i("getImage", "photo filepath: " + photoPath.getPath());
		Bitmap bitmap_photo = BitmapFactory.decodeFile(photoPath.getPath());
		return bitmap_photo;
	}

	public boolean publish(String title) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Bitmap bitmap_photo = getImage();
		bitmap_photo.compress(Bitmap.CompressFormat.PNG, 70, stream);
		byte[] photoData = stream.toByteArray();

		ParseFile parse_photo = new ParseFile(title, photoData);
		ParseObject post_data = new ParseObject(parseImageClass);
		post_data.put(parseUserIdColumn, userId);

		if (latitude != null || longitude != null) {
			post_data.put(parseGeoPoint, new ParseGeoPoint(latitude, longitude));
		}
		
		post_data.put(parseTitleColumn, title);
		post_data.put(parseImageByteColumn, parse_photo);

		try {
			post_data.saveInBackground(new SaveCallback() {
				public void done(ParseException e) {
					if (e == null) {
						Log.d("CameraModel publish", "Saved picture successfully");
					} else {
						Log.e("CameraModel publish",
								"Could not save picture: " + e.getMessage());
					}
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String createImageDirectory() throws IOException {
		if (!photoStorageName.exists()) {
			photoStorageName.mkdir();
		}
		return photoStorageName.getPath();
	}

	public Bitmap decodeFile(String path) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, o);
			// The new size we want to scale to
			final int REQUIRED_SIZE = 70;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeFile(path, o2);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setGeoPoint(LocationManager locManager) {
		if (locManager == null) {
			Log.e("[CameraModel] setGeoPoint", "getSystemService returned null");
			return;
		}

		Location location = locManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (location == null) {
			Log.e("[CameraModel] setGeoPoint",
					"getLastKnown Location returned null");
			return;
		}

		longitude = location.getLongitude();
		latitude = location.getLatitude();
	}

}
