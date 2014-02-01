package com.joshchorlton.snakeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MenuActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		final Button startButton= (Button)findViewById(R.id.startButton);
		final SeekBar speedBar= (SeekBar)findViewById(R.id.speedBar);
		speedBar.setProgress(50);
		startButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				Intent i= new Intent(MenuActivity.this, FullscreenActivity.class);
				i.putExtra("speed", speedBar.getProgress());
				MenuActivity.this.startActivity(i);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	

}
