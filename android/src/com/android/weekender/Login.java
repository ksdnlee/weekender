package com.android.weekender;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class Login extends ActionBarActivity {

	private EditText username = null;
	private EditText password = null;
	private Button login_button;
	static final String EXTRA_MESSAGE = "com.android.weekender.MESSAGE";

	
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_login);
	        if (savedInstanceState == null) {
	            getSupportFragmentManager().beginTransaction()
	                    .add(R.id.container, new PlaceholderFragment())
	                    .commit();
	        }
	        username = (EditText)findViewById(R.id.editText1);
	        password = (EditText)findViewById(R.id.editText2);
	        login_button = (Button)findViewById(R.id.button1);
	    }
		
		public void login(View view) {
			if (username.getText().toString().equals("admin@admin.com") &&
			password.getText().toString().equals("admin")) {
				Toast.makeText(getApplicationContext(), "Logging in!", Toast.LENGTH_SHORT).show();
				
				//Send to main gallery
				Intent intent = new Intent(this, Gallery.class);
				String message = "user";
				intent.putExtra(EXTRA_MESSAGE, message);
				startActivity(intent);
			}
			else {
				
			}
		}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            return rootView;
        }
    }

}
