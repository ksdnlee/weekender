package com.android.weekender;



import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class CameraActivity extends ActionBarActivity {

	  private static final int CAMERA_REQUEST = 1888; 
	    private ImageView imageView;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.cam);
	        imageView = (ImageView)findViewById(R.id.imageView1);
	        Button photoButton = (Button)findViewById(R.id.button1);
	        Button publishButton = (Button)findViewById(R.id.button2);
	        photoButton.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
	                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
	            }
	        });
	        
	        publishButton.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	Toast.makeText(getApplicationContext(), "Photo Uploaded!", Toast.LENGTH_SHORT).show();
	            }
	        });
	    }

	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (requestCode == CAMERA_REQUEST) {  
	            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            imageView.setImageBitmap(photo);
	        }  

	    }
	    
	    public void publish() {
	    	Toast.makeText(getApplicationContext(), "Photo Uploaded!", Toast.LENGTH_SHORT).show();
	    }
}
