package com.gecko71.m_game2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class TPanel extends  TWinPanelBase{
	Paint _paint;
	InOnClick onClickPanel=null;
	public TWinPanelStart parent=null;
	
	
	public TPanel (int XX,int YY, int left,int down){
		super();
		x=XX;
		y=YY;
		heighty=down;
		widthx=left;
		_paint=new Paint();
		_paint.setColor(Color.WHITE);
		_paint.setStrokeWidth(0);
		_paint.setStyle(Paint.Style.STROKE);
		//_paint.setAntiAlias(true);
	}
	
	@Override
	public void drawMainPanel(Canvas ca){
		try {
			if(GetVisible()){
				// 	ramka
				ca.drawRect(x, y, widthx, heighty, _paint);
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
		  if(game_evant.x>x && game_evant.x<widthx){
			  if(game_evant.y>y && game_evant.y<heighty){
				  ok=true; 
				  game_evant.ok=true;
				  if(game_evant.MotionEvent_event==MotionEvent.ACTION_UP){
					  if (onClickPanel!=null) {
						  onClickPanel.OnClick(this);
					  }
				  }
				  if(game_evant.MotionEvent_event==MotionEvent.ACTION_DOWN){
					  if (onClickPanel!=null) {
						  //onClickPanel.OnClick(this);
					  }
				  }
			  }
		  } 
		   
		} catch (Exception e) {	ok=false;}
		return ok;
	}			
}
