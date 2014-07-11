package com.android.weekender.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class UserObject implements Parcelable {
	private String objectId;
	private String fName;
	private String lName;

	public UserObject(String objectId, String fName, String lName) {
		this.objectId = objectId;
		this.fName = fName;
		this.lName = lName;
		this.objectId = "";
	}

	public void setUserId(String objectId) {
		this.objectId = objectId;
	}

	public UserObject(Parcel in) {
		readFromParcel(in);
	}

	public String getUserId() {
		return objectId;
	}

	public String getfName() {
		return fName;
	}

	public String lName() {
		return lName;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(objectId);
		dest.writeString(fName);
		dest.writeString(lName);
	}

	private void readFromParcel(Parcel in) {
		objectId = in.readString();
		fName = in.readString();
		lName = in.readString();
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public UserObject createFromParcel(Parcel in) {
			return new UserObject(in);
		}

		public UserObject[] newArray(int size) {
			return new UserObject[size];
		}
	};

}
