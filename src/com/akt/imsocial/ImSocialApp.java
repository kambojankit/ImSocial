package com.akt.imsocial;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class ImSocialApp extends Application implements
		OnSharedPreferenceChangeListener {
	private Twitter twitter;
	SharedPreferences preferences;
	String username;
	String password;
private StatusDao statusDao;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preparePrefs();

		preferences.registerOnSharedPreferenceChangeListener(this);
		statusDao = new StatusDao(this);
		super.onCreate();
	}

	public Twitter getTwitter() {
		if (twitter == null) {
			twitter = new Twitter(username, password);
			twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		}

		return twitter;
	}

	private void preparePrefs() {
		username = preferences.getString("username", "student");
		password = preferences.getString("password", "password");

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Log.d("Social", "Shared Preferences Changed");
		preferences = sharedPreferences;
		if (key.equalsIgnoreCase("username")) {
			Log.d("Social", "Username Changed");
			username = preferences.getString("username", "student");
		}
		if (key.equalsIgnoreCase("password")) {
			Log.d("Social", "Password Changed");
			password = preferences.getString("password", "password");
		}

		twitter = null;
	}

	public StatusDao getStatusDao() {
		return statusDao;
	}

}
