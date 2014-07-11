package com.android.weekender;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class GalleryItem implements Parcelable {

	int mLikes;
	String mId;
	
	public GalleryItem (int likes, String id) {
		mLikes = likes;
		mId = id;
	}
	
	public String getId() {
		return mId;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
}
