package com.gecko71.m_game2;

import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class TCheckBox extends TWinPanelBase {

	Bitmap BmpDown;
	Bitmap BmpUP;
	Bitmap BmpNapis;
	int widthxCheck;
	int heightyCheck;
	TMainGameVariable _mainGameVariable;
	
	public TCheckBox(int left, int hsp, Bitmap newgameBmpDown,
			Bitmap newgameBmpUP, Bitmap newgameBmpNapis, TMainGameVariable mainGameVariable) {
		
		x=left;
		y=hsp;
		BmpDown=newgameBmpDown;
		BmpUP=newgameBmpUP;
		BmpNapis=newgameBmpNapis;
		widthx=BmpDown.getWidth()+BmpNapis.getHeight();
		heighty=BmpDown.getHeight()+BmpNapis.getHeight();
		widthxCheck=BmpDown.getWidth();
		heightyCheck=BmpDown.getHeight();
		_mainGameVariable=mainGameVariable;
	}

	
	@Override
	public void drawMainPanel(Canvas ca){
		try {
			if (GetVisible()){
				if(_mainGameVariable.sound_check==false){
					ca.drawBitmap(BmpDown,x,y,null);
					ca.drawBitmap(BmpNapis,x+BmpDown.getWidth(),y,null);					
				} else {
					ca.drawBitmap(BmpUP,x,y,null);
					ca.drawBitmap(BmpNapis,x+BmpDown.getWidth(),y,null);					
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
		  if(game_evant.x>x && game_evant.x<x+widthxCheck){
			  if(game_evant.y>y && game_evant.y<y+heightyCheck){
				  ok=true; 
				  game_evant.ok=true;
				  if(game_evant.MotionEvent_event==MotionEvent.ACTION_UP){
					  if(_mainGameVariable.sound_check){
						  _mainGameVariable.sound_check=false;
					  } else {
						  _mainGameVariable.sound_check=true;
					  }
				  }	  
			  }
		  }
		} catch (Exception e) {	ok=false;}
		return ok;
	}			
	
	
	
	
}
