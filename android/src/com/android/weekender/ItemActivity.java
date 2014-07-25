package com.android.weekender;

import java.util.HashMap;

import com.android.weekender.helper.Constants;
import com.android.weekender.helper.UserObject;
import com.parse.ParseObject;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class ItemActivity extends ActionBarActivity {
//	private ImageView pictureView;
//	private EditText captionTitle;
//	private TextView captionText;
//	private Button publishButton;
	
//	CameraPresenter mCameraPresenter;
//	int CAMERA_REQUEST = 1888;
//	LocationManager locManager = null;
//	UserObject uObject;
	
	final int size = 10;
	TextView[] all_comments = null;
	boolean visible = false; 
	
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_item);
		
		// Get Intent and Fetch Image
		Intent intent = getIntent();
		ImageLoader.fetchImage(intent.getStringExtra("imageId"), (ImageView)this.findViewById(R.id.focusItem), getApplicationContext(), 1);
		
		LinearLayout comments = (LinearLayout) findViewById(R.id.commentSection);


		// Add all the comments to the commentSection layout
		all_comments = new TextView[size];
		for (int i = 0; i < size; i++) {
			 // create a new textview
		    final TextView rowTextView = new TextView(this);

		    // set some properties of rowTextView or something
		    rowTextView.setText("This is row #" + i);

		    // add the textview to the linearlayout
		    comments.addView(rowTextView);

		    // save a reference to the textview for later
		    all_comments[i] = rowTextView;

		}
		
		// Comments wrapper relativeLayout
		// 	Add a editTExt and post button to the bottom
		//  Make the linearLayout with all the comments scrollable
		
		
		
//		RelativeLayout cw = (RelativeLayout) findViewById(R.id.commentWrapper);
		

		
//		EditText myComment = new EditText(this);
		
//		LayoutParams lp = new LayoutParams(700, 150);
//		myComment.setLayoutParams(lp);
//		myComment.setGravity(Gravity.CENTER);
//		myComment.setLayoutParams(params)

//		ImageButton postButton = new ImageButton(this);
//		
//		postButton.setImageResource(R.drawable.ic_camera);
//		
//		post.addView(postButton);
		
//		cw.addView(myComment);
		
//		comments.setVisibility(View.GONE);
		visible = false;
	}
	
	public void toggleLike(View view) {
		Intent intent = getIntent();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", intent.getStringExtra("pictureId"));
		//userId
		
		
		//isLiked
	}
	
	// Toggle comments
	public void openComments(View view) {
		ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.vFlipper);

		viewFlipper.showNext();
		
//		LinearLayout comments = (LinearLayout) findViewById(R.id.commentSection);
//		if ( visible ==  true ) {
//			visible = false;
//			comments.setVisibility(View.GONE);
//		}
//		else {
//			visible = true;
//			comments.setVisibility(View.VISIBLE);
//		}
	}
	
	public void uploadComment(View view) {
		EditText commentText = (EditText)findViewById(R.id.getComment);
		String comments = commentText.getText().toString();


		ParseObject post_data = new ParseObject("Comments");
		post_data.put("pictureId", "Df6m2mShNN");
		post_data.put("commenter", "MUekdI0faq");
		post_data.put("comment", comments);
		post_data.saveInBackground();


		commentText.setText("");
		Toast.makeText(getApplicationContext(), "Comments Updated", Toast.LENGTH_SHORT).show();
	}

}
