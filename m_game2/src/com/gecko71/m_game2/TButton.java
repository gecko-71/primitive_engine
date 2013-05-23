package com.gecko71.m_game2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
//import android.graphics.Color;
import android.view.MotionEvent;

public class TButton extends TWinPanelBase {

	Bitmap buttonUP ;
	Bitmap buttonDown ;
	boolean pushDown=false;
	InOnClick onClickAbout=null;
	int ile=0;
	public TWinPanelStart parent=null;
	
	
	public TButton(int xx, int yy, Bitmap bmpUP,Bitmap bmpDown) {
		super();
		x=xx;
		y=yy;
		onClickAbout=null;
		buttonUP=bmpUP;
		buttonDown=bmpDown; 	
		widthx=bmpUP.getWidth();
		heighty=bmpUP.getHeight();
		//SetDown();
		SetUp();
		parent=null;
		pushDown=false;
	}

	private void SetDown() {
		if (pushDown==false){
			pushDown=true;			
		}
	}

	private void SetUp() {
		if (pushDown){		
			pushDown=false;
		}
	}

	@Override
	public void drawMainPanel(Canvas ca){
		try {
			//paint.setColor(Color.BLACK);
			//ca.drawRect(new Rect(0,0,50,50), paint);
			//paint.setColor(Color.WHITE);
			//ca.drawText(Integer.toString(ile),10,10,paint);
			//paint.setColor(Color.BLACK);
			if (GetVisible()){
				if (pushDown==false) {
					ca.drawBitmap(buttonUP,x,y,null);
					//Log.v("m_game2", "buttonUp");
				} else{
					ca.drawBitmap(buttonDown,x,y,null);
					//Log.v("m_game2", "buttonDown");
				}
			}
		} catch (Exception e) {	}		
	}
	
	@Override
	public boolean OnEvantGame(Game_Evant game_evant) {
		boolean ok=false; 
		try {
		  if(GetVisible()==false){
			  game_evant.ok=true;	
			  return false;
		  }
		  if(game_evant.x>x && game_evant.x<x+widthx){
			  if(game_evant.y>y && game_evant.y<y+heighty){
				  ok=true; 
				  game_evant.ok=true;
				  if(game_evant.MotionEvent_event==MotionEvent.ACTION_DOWN){
					 SetDown(); 
				  }
				  if(game_evant.MotionEvent_event==MotionEvent.ACTION_UP){
					  if (onClickAbout!=null) {
						  onClickAbout.OnClick(this);
					  }
					  SetUp();
				  }
			  } else {
				  SetUp();				  
			  }
		  } else {
			  SetUp();			  
		  }
		} catch (Exception e) {	ok=false;}
		return ok;
	}		
	
}
