package com.akt.imsocial;

import java.util.ArrayList;
import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateService extends Service{
	List<Status> statuses;
	boolean isRunning = true;
	StatusDao statusDao;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("Social", "service created");
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		statusDao = ((ImSocialApp)getApplication()).getStatusDao();
	Thread t = 	new Thread(){

			@Override
			public void run() {
				prepareStatusList();
				while (isRunning) {
					for(Status status: statuses){
						Log.d("Social",status.getUser().getName() + " Says: " + status.getText());
						statusDao.insert(status);
						Log.d("Social","Inserted into DB");
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
		};
		
		t.start();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isRunning = false;
		super.onDestroy();
	}
	
	private void prepareStatusList(){
		Twitter twitter = ((ImSocialApp)getApplication()).getTwitter();
		statuses = twitter.getPublicTimeline(); 
	}
	
}
