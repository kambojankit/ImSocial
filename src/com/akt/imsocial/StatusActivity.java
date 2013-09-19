package com.akt.imsocial;

import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity {

	EditText myStatus;
	Button myButton;
	String status;
	Twitter twitter;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		prepareTwitter();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		myStatus = (EditText) findViewById(R.id.edtStatus);
		myButton = (Button) findViewById(R.id.btnPostStatus);
		final PostToTwitter post = new PostToTwitter();		
		myButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				status = myStatus.getText().toString();
				if(status!=null && !status.isEmpty()){
					post.execute(status);
				}
			}
		});		
		
	}
	
	private void prepareTwitter(){
		twitter = ((ImSocialApp)getApplication()).getTwitter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, UpdateService.class);
		switch(item.getItemId()){
		case R.id.startService:
			startService(intent);
			break;
		case R.id.stopService:
			stopService(intent);
			break;
			
		case R.id.prefsActivity:
			Intent prefIntent = new Intent(this, PrefsActivity.class);
			startActivity(prefIntent);
			break;
		case R.id.timeline:
			Intent timelineIntent = new Intent(this, TimelineActivity.class);
			startActivity(timelineIntent);
			break;
		}
		return true;
	}
	
	class PostToTwitter extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			Twitter.Status s = twitter.setStatus(params[0]);
			
			return s.getText();
		}
		
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(StatusActivity.this, "Posted: "+result, Toast.LENGTH_SHORT).show();
			super.onPostExecute(result); 
		}
	}
}
