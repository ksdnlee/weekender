package com.android.weekender;

import java.util.HashMap;
import com.android.weekender.helper.Constants;
import com.android.weekender.helper.UserObject;
import com.parse.ParseObject;

import android.support.v7.app.ActionBarActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;

import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.weekender.helper.Constants;
import com.android.weekender.helper.UserObject;
import com.parse.ParseCloud;
import com.parse.ParseException;

public class ItemActivity extends ActionBarActivity {

	
	final int size = 10;
	TextView[] all_comments = null;
	boolean visible = false; 
	

	UserObject uObject;
	String pictureId;
	String isToggled;

	TextView photoCaption;
	TextView showLikes ;
	ImageButton like_button;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		setContentView(R.layout.activity_item);
		
		
		
		//set font
		Typeface captionFont = Typeface.createFromAsset(getAssets(),
				"fonts/Pacifico.ttf");
		
		photoCaption = (TextView) findViewById(R.id.photo_caption);
		showLikes = (TextView) findViewById(R.id.like_view);
		like_button = (ImageButton) findViewById(R.id.like_button);
		// get UserObject
		Bundle b = getIntent().getExtras();
		if (b != null) {
			uObject = b.getParcelable(Constants.USER_OBJECT);
		} else {
			Log.e("FailedUserObjectPassCamera", "FAIL TO GET ID");
		}

		Toast.makeText(getApplicationContext(),
			uObject.getUserId(), Toast.LENGTH_SHORT).show();
		
		photoCaption.setTypeface(captionFont, Typeface.NORMAL);

		// Get Intent and Fetch Image
		Intent intent = getIntent();
		pictureId = intent.getStringExtra("imageId");
		ImageLoader.fetchImage(intent.getStringExtra("imageId"),
				(ImageView) this.findViewById(R.id.focusItem),
				getApplicationContext(), 1);

		// get number of likes

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pictureId", pictureId);
		String commentStr = null;
		try {
			String caption = ParseCloud.callFunction("getCaption", params);
			commentStr = ParseCloud.callFunction("getComments", params);
			
			String numLikes = ParseCloud.callFunction("getLikesByPictureId",
					params).toString();
			photoCaption.setText(caption);
			showLikes.setText(numLikes);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// toggle like button
		params.put("userId", uObject.getUserId());

		try {
			isToggled = ParseCloud.callFunction("isLiked", params).toString();
			if (isToggled.equals("y")) {
				like_button.setBackgroundResource(R.drawable.fist_toggled);
			} else {
				like_button.setBackgroundResource(R.drawable.fist_untoggled);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LinearLayout comments = (LinearLayout) findViewById(R.id.commentSection);
		
		
//		String employee = "MUekdI0faq@c@great picture!@s@MUekdI0faq@c@hellodsad sadas,dasdasdasdas@s@MUekdI0faq@c@sadasdasdas@s@MUekdI0faq@c@hello world,@s@";
        
        String delims = "[@]"+"[s]"+"[@]";
        String delims2 = "[@]"+"[c]"+"[@]";
        String[] completeString = commentStr.split(delims);
       
       //System.out.println("first" + Arrays.toString(completeString));
        
       for(String s: completeString){
           //user and comments array is made
           String[] userComments = s.split(delims2);
           
           if (userComments.length <= 1) break;
           
           String userId = userComments[0];
           String userComment = userComments[1];
           
           final TextView rowTextView = new TextView(this);

		    // set some properties of rowTextView or something
		    rowTextView.setText(userId + ": " + userComment);

		    // add the textview to the linearlayout
		    comments.addView(rowTextView);
       }
		
//		// Add all the comments to the commentSection layout
//		all_comments = new TextView[size];
//		for (int i = 0; i < size; i++) {
//			 // create a new textview
//		   
//
//		    // save a reference to the textview for later
//		    all_comments[i] = rowTextView;

		
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
	}

	
	public void toggleLike(View view) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pictureId", pictureId);
		params.put("userId", uObject.getUserId());
		try {
			String numLikes = ParseCloud.callFunction("updateLike",
					params).toString();
			showLikes.setText(numLikes);
			if (isToggled.equals("y")) {
				isToggled = "n";
				like_button.setBackgroundResource(R.drawable.fist_untoggled);
			} else {
				isToggled = "y";
				like_button.setBackgroundResource(R.drawable.fist_toggled);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Toggle comments
	public void openComments(View view) {
		ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.vFlipper);
		viewFlipper.showNext();
	}

	public void uploadComment(View view) {
		EditText commentText = (EditText)findViewById(R.id.getComment);
		String comments = commentText.getText().toString();

		ParseObject post_data = new ParseObject("Comments");
		post_data.put("pictureId", pictureId);
		post_data.put("commenter", uObject.getfName());
		post_data.put("comment", comments);
		post_data.saveInBackground();

		commentText.setText("");
		Toast.makeText(getApplicationContext(), "Comments Updated", Toast.LENGTH_SHORT).show();
	}

}
