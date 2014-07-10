package com.android.weekender;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;

public class CameraPresenter {
	private CameraActivity camView;
	CameraModel cameraModel = null;
	
	public CameraPresenter(CameraActivity view) {
		camView = view;
	}
	
	public Intent getCameraIntent(){
		cameraModel = new CameraModel(camView);
		return cameraModel.getCameraIntent();
	}
	
	public Bitmap getImage() {
		return cameraModel.getImage();
	}
	
	public boolean publish(String title) {
		return cameraModel.publish(title);
	}

	public void setGeoPoint(LocationManager locManager) {
		cameraModel.setGeoPoint(locManager);	
	}

}
