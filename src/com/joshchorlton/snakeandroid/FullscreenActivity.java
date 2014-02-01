package com.joshchorlton.snakeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class FullscreenActivity extends Activity {
	private static final String TAG= FullscreenActivity.class.getSimpleName();
	private int speed;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i= getIntent();
		speed= i.getIntExtra("speed", 50);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new MainGamePanel(this, speed));
		Log.d(TAG, "View added");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
	
	@Override
	protected void onDestroy(){
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}
	
	@Override
	protected void onStop(){
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
}
