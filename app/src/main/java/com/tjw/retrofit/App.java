package com.tjw.retrofit;

import android.app.Application;

/**
 * ^-^
 * Created by tang-jw on 9/9.
 */
public class App extends Application {
	public static App mApp;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
	}
}
