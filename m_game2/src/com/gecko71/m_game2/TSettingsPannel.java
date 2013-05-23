package com.gecko71.m_game2;

//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class TSettingsPannel extends TWinPanelBase {

    Rect r;
    Paint _paint;
	public TMainGameVariable MainGameVariable;
	//public ArrayList<THighScore> HighScore = null;
	
	public TSettingsPannel(TMainGameVariable mainGameVariable,int w,int h){
		super();
		active=false;
		widthx=w;
		heighty=h;
		MainGameVariable=mainGameVariable;
		
		float wx= (( (float) widthx)/100)*10; // wyliczam 15 % 
		int wxB=  widthx- (int) wx;
		float hx= (( (float) heighty)/100)*10; // wyliczam 15 % 
		int hxB=  heighty- (int) hx;
		r = new Rect((int) wx,(int) hx,wxB,hxB);
		
		_paint=new Paint();
		_paint.setColor(Color.WHITE);
		_paint.setStrokeWidth(0);
		_paint.setStyle(Paint.Style.STROKE);
		//_paint.setAntiAlias(true);
		ButtonOK();
		SoundBox();
		// 
	}
	
	


	private int SoundBox() {
		float ramka_sz=(float) (r.right-r.left) ;
		float size50;
		if (widthx>heighty){
			size50=( ramka_sz/100)*40;
		} else {
		    size50=( ramka_sz/100)*80;
		}
		
		int left = r.left +(int) ( ( ramka_sz - size50)/2);
		
		Bitmap newgameBmpDown= BitmapFactory.decodeResource(MainGameVariable.myContext.getResources(), R.drawable.check_box_down);
		Bitmap newgameBmpUP= BitmapFactory.decodeResource(MainGameVariable.myContext.getResources(), R.drawable.check_box_up);
		Bitmap newgameBmpNapis= BitmapFactory.decodeResource(MainGameVariable.myContext.getResources(), R.drawable.sound);
		//float size50top=(( (float) r.bottom-r.top)/100)*40;
		int top = r.top +(int) ( ( (float) (r.bottom-r.top))/2);
		
		int size_text_org=newgameBmpDown.getWidth()+newgameBmpNapis.getWidth();
		float proc;
		proc=size50/(float) size_text_org;
		int wxB=(int) (newgameBmpDown.getWidth()*proc);
		int hsp=(int) (newgameBmpDown.getHeight()*proc);
		Bitmap newgameBmpDown2= Kontener.getResizedBitmap(newgameBmpDown,wxB,hsp);
		wxB=(int) (newgameBmpUP.getWidth()*proc);
		hsp=(int) (newgameBmpUP.getHeight()*proc);
		Bitmap newgameBmpUP2= Kontener.getResizedBitmap(newgameBmpUP,wxB,hsp);		
		wxB=(int) (newgameBmpNapis.getWidth()*proc);
		hsp=(int) (newgameBmpNapis.getHeight()*proc);
		top=top-(int) ( (float)hsp/2 );
		Bitmap newgameBmpNapis2= Kontener.getResizedBitmap(newgameBmpNapis,wxB,hsp);
		
		TCheckBox new_game = new TCheckBox(left,top,newgameBmpDown2, newgameBmpUP2,newgameBmpNapis2,MainGameVariable);		
		add((TWinPanelBase) new_game);
		new_game.SetVisible(true);
		return top;
	}




	private int ButtonOK() {
		try {
			int wsp_x;
			int wsp_y;
			if (widthx>heighty){
				wsp_x=30;
				wsp_y=25;
			} else {
				wsp_x=15;
				wsp_y=15;
			}
			float wx= (( (float) r.right-r.left)/100)*wsp_x;
			int wxB=  r.right-r.left - (int) wx*2; // szerokosc buttona
		
			Bitmap newgameBmp1= BitmapFactory.decodeResource(MainGameVariable.myContext.getResources(), R.drawable.ok_black);
			Bitmap newgameBmp11= BitmapFactory.decodeResource(MainGameVariable.myContext.getResources(), R.drawable.ok_white);		
			
			int hb=newgameBmp1.getHeight();
			int wb=newgameBmp1.getWidth();
			int hsp=(int)( ( (float) hb/(float )wb) *(float)wxB);  // wyliczam proporcje
			float  precentH=(( (float) hsp)/100)*wsp_y;
		
			int top=   r.bottom-r.top-(int)hsp- (int)precentH;
		
			Bitmap newgameBmp2= Kontener.getResizedBitmap(newgameBmp1,wxB,hsp);	
			Bitmap newgameBmp22= Kontener.getResizedBitmap(newgameBmp11,wxB,hsp);		
			TButton new_game = new TButton((int) r.left+(int) wx,r.top+top,newgameBmp2, newgameBmp22);
			
			new_game.onClickAbout= new InOnClick(){

				@Override
				public void OnClick(TWinPanelBase new_game) {
					doOnClickSettingsOK(new_game);
				}
				
			} ;		
			add((TWinPanelBase) new_game);
			new_game.SetVisible(true);	
			return r.top+top;
		} catch (Exception e) {
			Log.v("m_game2", "error");
			return 0;
		}
	}

	
	public void doOnClickSettingsOK(TWinPanelBase new_game) {
		SetActive(false);
		SetVisible(false);
		MainGameVariable.WinPanel.ListWinPanel.get(0).SetActive(true);
		MainGameVariable.WinPanel.ListWinPanel.get(0).SetVisible(true);
	}

	
	
	@Override
	public void drawMainPanel(Canvas ca){
		try {
			if(GetVisible()){
				ca.drawColor(color);
				ca.drawRect(r, _paint);
				if (ListWinPanel!=null){
					for(int i=0;i<ListWinPanel.size();i++){
						if(ListWinPanel.get(i).GetVisible()){
							ListWinPanel.get(i).drawMainPanel(ca);
						}
					}				
				}
				paint.setColor(Color.WHITE);
				ca.drawText("gecko71 (c) 2013",10,heighty-10,paint);

			}
		} catch (Exception e) {	}		;
	}
	

	@Override
	public boolean OnEvantGame(Game_Evant game_evant) {
		boolean ok=false; 
		try {
			if (ListWinPanel!=null){
				for(int i=0;i<ListWinPanel.size();i++){
					if (ListWinPanel.get(i).OnEvantGame(game_evant)){
						ok=true;
						break;
					}
				}
			}
		} catch (Exception e) {Log.v("m_game2", "error");	ok=false;}
		return ok;
	}


	@Override
	public void finalize(){
		try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
