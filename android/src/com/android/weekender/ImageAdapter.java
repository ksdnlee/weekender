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
		ImageLoader.fetchImage(mList.get(position).getId(), imageView, mContext);
//		imageView.setImageBitmap(mList.get(position).getBitmap());
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

//		final int imgSize = 400;
//		
//		imageView.setLayoutParams(new GridView.LayoutParams(imgSize, imgSize));
//		
//		final int dimen = mList.get(position).isSelected ?  (int) mContext.getResources().getDimension(R.dimen.step_1_2) :
//															(int) mContext.getResources().getDimension(R.dimen.step_0);
		imageView.setPadding(40, 80, 40, 10);
		
//		imageView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams(v.getLayoutParams());
//                 parms2.addRule(RelativeLayout.CENTER_VERTICAL);
//                 parms2.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                  v.setLayoutParams(parms2);
//                 
//            }
//        });
		
		return imageView;
	}
	
	//imgview.setScaleType(ScaleType.FIT_XY);

}