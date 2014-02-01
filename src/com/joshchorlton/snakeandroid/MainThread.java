package com.joshchorlton.snakeandroid;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	private static final String TAG= MainThread.class.getSimpleName();
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private Timer t;
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel){
		super();
		this.surfaceHolder= surfaceHolder;
		this.gamePanel= gamePanel;
	}
	public void setRunning(boolean running){
		this.running= running;
		if(running){
			t= new Timer();
			t.schedule(new TimerTask(){
				public void run(){
					gamePanel.increment();
				}
			}, gamePanel.getSpeed(), gamePanel.getSpeed());
		}
		else{
			t.cancel();
		}
	}
	@SuppressLint("WrongCall")
	@Override
	public void run(){
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while(running){
			canvas= null;
			try{
				canvas= this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder){
					this.gamePanel.onDraw(canvas);
				}
			}finally{
				if(canvas!=null){
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
