package com.example.experimentation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
	private MainThread thread;
	private Bitmap square= BitmapFactory.decodeResource(getResources(), R.drawable.grey);
	private int width, height;
	private int direction= 1;
	private static final String TAG= MainGamePanel.class.getSimpleName();
	private Square s[]= new Square[2];
	private Square n;
	private int speed;
	public MainGamePanel(Context context, int speed){
		super(context);
		this.speed= 100+(100-speed)*4;
		getHolder().addCallback(this);
		WindowManager wm= (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display= wm.getDefaultDisplay();
		Point size= new Point();
		display.getSize(size);
		width= size.x;
		height= size.y;
		s[0]= new Square(square, width/2, height/2);
		s[1]= new Square(square, width/2, height/2-20);
		createNew();
		thread= new MainThread(getHolder(), this);
		setFocusable(true);
	}
	public int getSpeed(){
		return speed;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry= true;
		thread.setRunning(false);
		while(retry){
			try{
				thread.join();
				retry= false;
			}catch(InterruptedException e){
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		Log.d(TAG, "(X,Y)= ("+event.getX()+","+event.getY()+")");
		if(event.getY()>(height-300)){
			direction= 2;
		}
		else if(event.getY()<300){
			direction= 1;
		}
		else if(event.getX()>width/2){
			direction= 4;
		}
		else if(event.getX()<width/2){
			direction= 3;
		}
		return super.onTouchEvent(event);
	}
	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		for(Square i:s){
			i.draw(canvas);
		}
		n.draw(canvas);
	}
	public void increment(){
		if(checkForGameOver()){
			thread.setRunning(false);
		}
		else if(checkForCollision()){
			createNew();
			Square temp[]= new Square[s.length+1];
			for(int i= 1; i<=s.length; i++){
				temp[i]= new Square(square, s[i-1].getX(), s[i-1].getY());
			}
			s= temp;
			s[0]= new Square(square, 0, 0);
			switch(direction){
			case 1:
				s[0].setX(s[1].getX());
				s[0].setY(s[1].getY()-20);
				break;
			case 2:
				s[0].setX(s[1].getX());
				s[0].setY(s[1].getY()+20);
				break;
			case 3:
				s[0].setX(s[1].getX()-20);
				s[0].setY(s[1].getY());
				break;
			case 4:
				s[0].setX(s[1].getX()+20);
				s[0].setY(s[1].getY());
				break;
			}
		}
		else{
			for(int i= s.length-1; i>0; i--){
				s[i]= new Square(square, s[i-1].getX(), s[i-1].getY());
			}
			switch(direction){
			case 1:
				s[0].setX(s[1].getX());
				s[0].setY(s[1].getY()-20);
				break;
			case 2:
				s[0].setX(s[1].getX());
				s[0].setY(s[1].getY()+20);
				break;
			case 3:
				s[0].setX(s[1].getX()-20);
				s[0].setY(s[1].getY());
				break;
			case 4:
				s[0].setX(s[1].getX()+20);
				s[0].setY(s[1].getY());
				break;
			}
		}
	}
	public void createNew(){
		n= new Square(square, (int)(Math.random()*(width-20)), (int)(Math.random()*(height-20)));
	}
	public boolean checkForCollision(){
		boolean collision= false;
		if(s[0].getX()>n.getX()&&s[0].getX()<n.getX()+20){
			if(s[0].getY()>n.getY()&&s[0].getY()<n.getY()+20){
				collision= true;
			}
			else if(s[0].getY()+20>n.getY()&&s[0].getY()+20<n.getY()+20){
				collision= true;
			}
		}
		else if(s[0].getX()+20>n.getX()&&s[0].getX()+20<n.getX()+20){
			if(s[0].getY()>n.getY()&&s[0].getY()<n.getY()+20){
				collision= true;
			}
			else if(s[0].getY()+20>n.getY()&&s[0].getY()+20<n.getY()+20){
				collision= true;
			}
		}
		return collision;
	}
	public boolean checkForGameOver(){
		if(s[0].getX()<20||s[0].getX()>width-20){
			return true;
		}
		else if(s[0].getY()<20||s[0].getY()>height-20){
			return true;
		}
		for(int i= 1; i<s.length; i++){
			if(s[0].getX()==s[i].getX()&&s[0].getY()==s[i].getY()){
				return true;
			}
		}
		return false;
	}
}
