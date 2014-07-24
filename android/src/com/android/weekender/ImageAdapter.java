package com.android.weekender;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
	private ArrayList<GalleryItem> mList;
	
    public ImageAdapter(Context c, ArrayList<GalleryItem> list) {
        mContext = c;
        mList = list;
    }

    public int getCount() {
    	return mList.size();
    }

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
    
    // create a new ImageView for each item referenced by the Adapter
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		ImageLoader.fetchImage(mList.get(position).getId(), imageView, mContext, 0);
//		imageView.setImageBitmap(mList.get(position).getBitmap());
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

		imageView.setPadding(20, 10, 20, 10);
		
		
		return imageView;
	}
	
	
//	CODE FOR SQUARE BITMAP
//	if (srcBmp.getWidth() >= srcBmp.getHeight()){
//
//		  dstBmp = Bitmap.createBitmap(
//		     srcBmp, 
//		     srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
//		     0,
//		     srcBmp.getHeight(), 
//		     srcBmp.getHeight()
//		     );
//
//		}else{
//
//		  dstBmp = Bitmap.createBitmap(
//		     srcBmp,
//		     0, 
//		     srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
//		     srcBmp.getWidth(),
//		     srcBmp.getWidth() 
//		     );
//		}
	
	//imgview.setScaleType(ScaleType.FIT_XY);

}