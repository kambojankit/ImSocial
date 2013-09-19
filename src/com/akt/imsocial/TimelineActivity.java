package com.akt.imsocial;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class TimelineActivity extends Activity {
	TextView statusList;
	StatusDao statusDao;
	Cursor cursor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		statusList = (TextView) findViewById(R.id.txtStatusList);
		statusDao = ((ImSocialApp)getApplication()).getStatusDao();

		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		cursor = statusDao.query();
		while (cursor.moveToNext()) {
			String name= cursor.getString(cursor.getColumnIndex(StatusDao.C_NAME));
			String text = cursor.getString(cursor.getColumnIndex(StatusDao.C_STATUS));
			statusList.append(name+": "+text); 
		}
		
	}
}
