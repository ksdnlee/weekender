package com.android.weekender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
	

public class GalleryPresenter {
	
	static final String EXTRA_MESSAGE = "com.android.weekender.MESSAGE";

	private IGalleryView mGV;
	private GalleryModel mGM;
	
	public GalleryPresenter( IGalleryView view) {
		mGV = view;
//		mGM = new GalleryModel();
	}

	// any methods on user input that handle the logic go here	
	public void capturePic(Context cntxt) {

	}
	
	View insertPhoto(String path, Context cntx) {
		Bitmap bm = decodeSampledBitmapFromUri(path, 220, 220);

		LinearLayout layout = new LinearLayout(cntx);
		layout.setLayoutParams(new LayoutParams(250, 250));
		layout.setGravity(Gravity.CENTER);

		ImageView imageView = new ImageView(cntx);
		imageView.setLayoutParams(new LayoutParams(220, 220));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bm);

		layout.addView(imageView);
		return layout;
	}

	private Bitmap decodeSampledBitmapFromUri(String path, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
}
