package com.android.weekender;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.android.weekender.helper.Constants;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageLoader {
	private final static String TAG = "ImadeLoader";

	public static void fetchImage(final String mId, final ImageView mView,
			final Context mContext) {
		if (mView == null || mContext == null || mId == null) {
			return;
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message message) {
				final Bitmap image = (Bitmap) message.obj;
				mView.setImageBitmap(image);
			}
		};

		final Thread thread = new Thread() {
			@Override
			public void run() {
				final Bitmap image = downloadImage(mId, mContext);
				if (image != null) {
					final Message message = handler.obtainMessage(1, image);
					handler.sendMessage(message);
				} else {
					Log.d(TAG, "Did not get image by ID: " + mId);
				}
			}
		};

		mView.setImageResource(R.drawable.ic_launcher);
		thread.setPriority(3);
		thread.start();
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public static Bitmap downloadImage(String mId, Context ctx) {
		Parse.initialize(ctx, "7rl4Da1XbvpuaKKDKb6VCMZseFZkwEuKyXT4QPDd",
				"2Rah9NP3dkgUhfToKZlCXT6YLOl4WQUNEMLyC8Ol");

		// get Images

		HashMap<String, Object> params = new HashMap<String, Object>();
		final String imagesClass = Constants.CLASS_IMAGES;
		params.put("start", "0");
		params.put("end", "10");
		params.put("classname", imagesClass);

		// get all the id's here in an array and then pass the array to Adapter

		// String pictureIds =
		// (String)ParseCloud.callFunction("getAllPictureObjectId", params);
		Bitmap bitmap = null;
		// if (pictureIds != null) {
		// String[] ListPictureIds = pictureIds.split(",");
		try {

			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					imagesClass);
			ParseObject obj = (ParseObject) query.get(mId);
			ParseFile image_file = (ParseFile) obj
					.get(Constants.COLUMN_IMAGEBYTE);
			byte bytes[] = image_file.getData();
			Log.d("Data", "We have data successfully");
			
			// First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;

			BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
			
			// Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, 400, (int)1.3 * 400);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    
			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

			

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		// } else {
		// Log.d("Capture", "Could not get picture ids");
		// }
		return bitmap;
	}

}