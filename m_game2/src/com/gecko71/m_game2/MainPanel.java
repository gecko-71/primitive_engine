package com.gecko71.m_game2;


import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Handler;
import android.os.Message;

public class MainPanel extends SurfaceView implements SurfaceHolder.Callback {

	MainGameThread thread ;
	TMainGameVariable MainGameVariable;
	int widthx,heighty;
	float precentH;
	Context contextT;	 
	
	public MainPanel(Context context,int w, int h, SharedPreferences sharedPreferences) {
		super(context);
		contextT=context;
		MainGameVariable = new TMainGameVariable();
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		widthx=w;
		heighty=h;
		MainGameVariable.HighScore = new ArrayList<THighScore>(); 
		THighScore hg = new THighScore();
		hg.score=827237;
		Date d=new Date();
		hg.d=d.getTime();
		MainGameVariable.HighScore.add(hg);
		
		AddWinPanel(); // ekrany gry , definicje
		
		thread = new MainGameThread(holder, context,sharedPreferences,MainGameVariable,new Handler() {
				@Override
				public void handleMessage(Message m) {
					
					}
				});		
		setFocusable(true);
	}

	
	// ekrany gry , definicje , scena itp
	private void AddWinPanel() {
		
		try {
			// pierwsza plansza
			TWinPanelStart WinPanelStart = new TWinPanelStart(widthx,heighty);
			WinPanelStart.SetVisible(true);
			WinPanelStart.SetActive(true);
			MainGameVariable.WinPanel=new TWinPanelBase(); // kontener z oknami
			MainGameVariable.WinPanel.widthx=widthx;
			MainGameVariable.WinPanel.heighty=heighty;
			MainGameVariable.WinPanel.add( (TWinPanelBase) WinPanelStart );
			//MainGameVariable.contextT=contextT;
			// load bitmap
			LoadBmpWinPanel(WinPanelStart); //Bitmap `;
		} catch (Exception e) {	}	
	};


	private void LoadBmpWinPanel(TWinPanelStart winPanelStart) {
		// TODO Auto-generated method stub
		
		
		// wysokoœc buttona 135 , przeliczenie na wys ekranu
		// button new game ,zwracam TButton bo dwrazy nie bede licy³ wysokoœci
		//int hy=(135+10)*4;
		TButton hb=ButtonNewGame(winPanelStart);
		int xaboutpanel=hb.x;
		int yaboutpanel=hb.y;
		// button continue
		hb=ButtonContiue(hb,winPanelStart);
		hb=ButtonScore(hb,winPanelStart);
		hb=ButtonSettings(hb,winPanelStart);		
		hb=ButtonAbout(hb,winPanelStart);
		int xaboutpanel_down=hb.x+hb.widthx;
		int yaboutpanel_down=hb.y+hb.heighty;
		PanelAbout(xaboutpanel,yaboutpanel,xaboutpanel_down,yaboutpanel_down,winPanelStart);
	}





	private void PanelAbout(int xaboutpanel, int yaboutpanel,
			int xaboutpanel_down, int yaboutpanel_down,
			TWinPanelStart winPanelStart) {

		
		TPanel new_game = new TPanel(xaboutpanel,yaboutpanel,xaboutpanel_down, yaboutpanel_down);
		new_game.SetVisible(false);
		MainGameVariable.AboutPane=new_game;
		winPanelStart.add((TWinPanelBase) new_game);
		new_game.parent=winPanelStart;
		new_game.onClickPanel= new InOnClick(){

			@Override
			public void OnClick(TWinPanelBase new_game) {
				thread.doOnClickPanel(new_game);
			}
			
		} ;	
	}

	private TButton ButtonSettings(TButton hb, TWinPanelStart winPanelStart) {
		Bitmap newgameBmp1= BitmapFactory.decodeResource(getResources(), R.drawable.settings_black);
		Bitmap newgameBmp11= BitmapFactory.decodeResource(getResources(), R.drawable.settings_white);		
		
		Bitmap newgameBmp2= Kontener.getResizedBitmap(newgameBmp1,hb.widthx,hb.heighty);	
		Bitmap newgameBmp22= Kontener.getResizedBitmap(newgameBmp11,hb.widthx,hb.heighty);
		TButton new_game = new TButton(hb.x,hb.y+(int)precentH+hb.heighty,newgameBmp2, newgameBmp22);

		new_game.SetVisible(true);
		winPanelStart.add((TWinPanelBase) new_game);
		new_game.parent=winPanelStart;
		new_game.onClickAbout= new InOnClick(){

			@Override
			public void OnClick(TWinPanelBase new_game) {
				thread.doOnClickSettings(new_game);
			}
			
		} ;		
		return new_game;	
	}

	private TButton ButtonAbout(TButton hb, TWinPanelStart winPanelStart) {
		Bitmap newgameBmp1= BitmapFactory.decodeResource(getResources(), R.drawable.aboutblack);
		Bitmap newgameBmp11= BitmapFactory.decodeResource(getResources(), R.drawable.aboutwhite);		
		
		Bitmap newgameBmp2= Kontener.getResizedBitmap(newgameBmp1,hb.widthx,hb.heighty);	
		Bitmap newgameBmp22= Kontener.getResizedBitmap(newgameBmp11,hb.widthx,hb.heighty);
		TButton new_game = new TButton(hb.x,hb.y+(int)precentH+hb.heighty,newgameBmp2, newgameBmp22);

		new_game.SetVisible(true);
		winPanelStart.add((TWinPanelBase) new_game);
		new_game.parent=winPanelStart;
		new_game.onClickAbout= new InOnClick(){

			@Override
			public void OnClick(TWinPanelBase new_game) {
				thread.doOnClickAbout(new_game);
			}
			
		} ;		
		return new_game;	
	}

	//public interface InOnClickAbout{
//		void OnClickAbout();
//	}
	
	
	

	private TButton ButtonScore(TButton hb, TWinPanelStart winPanelStart) {
		
		Bitmap newgameBmp1= BitmapFactory.decodeResource(getResources(), R.drawable.score_black);
		Bitmap newgameBmp11= BitmapFactory.decodeResource(getResources(), R.drawable.score_white);
			
		Bitmap newgameBmp2= Kontener.getResizedBitmap(newgameBmp1,hb.widthx,hb.heighty);	
		Bitmap newgameBmp22= Kontener.getResizedBitmap(newgameBmp11,hb.widthx,hb.heighty);		
		TButton new_game = new TButton(hb.x,hb.y+(int)precentH+hb.heighty,newgameBmp2, newgameBmp22);
		new_game.SetVisible(true);		
		winPanelStart.add((TWinPanelBase) new_game);
		new_game.onClickAbout= new InOnClick(){

			@Override
			public void OnClick(TWinPanelBase new_game) {
				thread.doOnClickScore(new_game);
			}
			
		} ;		
		
		return new_game;	
	}


	private TButton  ButtonContiue(TButton hb, TWinPanelStart winPanelStart) {
		// TODO Auto-generated method stub
	
		Bitmap newgameBmp1= BitmapFactory.decodeResource(getResources(), R.drawable.continue_black);
		Bitmap newgameBmp11= BitmapFactory.decodeResource(getResources(), R.drawable.continue_white);		
			
		Bitmap newgameBmp2= Kontener.getResizedBitmap(newgameBmp1,hb.widthx,hb.heighty);	
		Bitmap newgameBmp22= Kontener.getResizedBitmap(newgameBmp11,hb.widthx,hb.heighty);		
		TButton new_game = new TButton(hb.x,hb.y+(int)precentH+hb.heighty,newgameBmp2, newgameBmp22);
		winPanelStart.add((TWinPanelBase) new_game);
		new_game.SetVisible(true);		
		return new_game;		
	}

	

	private TButton ButtonNewGame(TWinPanelStart winPanelStart) {
		
		
		float wx;
		int wxB;
		
		Bitmap newgameBmp1;
		Bitmap newgameBmp11;		
		
		int hb;
		int wb;
		float hsp;
		//float precentH;
		
		float top;
		float h;
		float p_size;
		
		if (winPanelStart.widthx<winPanelStart.heighty){
			wx= (( (float) winPanelStart.widthx)/100)*15;
			wxB=  winPanelStart.widthx- (int) wx*2;
			newgameBmp1= BitmapFactory.decodeResource(getResources(), R.drawable.new_game_black);
			newgameBmp11= BitmapFactory.decodeResource(getResources(), R.drawable.new_game_white);		
			hb=newgameBmp1.getHeight();
			wb=newgameBmp1.getWidth();
			hsp=(int)( ( (float) hb/(float )wb) *(float)wxB);
			precentH=(( (float) hsp)/100)*25;
			top=  ( winPanelStart.heighty-(int)((hsp+precentH)*5))/2;
			
		} else {
			
			top =(( (float) winPanelStart.heighty)/100)*10;
			h = winPanelStart.heighty- (int) top*2; 
			hsp=h/6; // wys bautona
			precentH=(hsp)/4; // wys odstepu miedzy buttonami
			
			newgameBmp1= BitmapFactory.decodeResource(getResources(), R.drawable.new_game_black);
			newgameBmp11= BitmapFactory.decodeResource(getResources(), R.drawable.new_game_white);		
			hb=newgameBmp1.getHeight();
			wb=newgameBmp1.getWidth();		
			
			wxB= (int)( ( (float) wb/(float )hb) *(float)hsp);
			wx= (winPanelStart.widthx-wxB)/2;
		}
			
		
		Bitmap newgameBmp2= Kontener.getResizedBitmap(newgameBmp1,wxB,(int)hsp);	
		Bitmap newgameBmp22= Kontener.getResizedBitmap(newgameBmp11,wxB,(int)hsp);		
		TButton new_game = new TButton((int) wx,(int) top,newgameBmp2, newgameBmp22);
		new_game.SetVisible(true);		
		winPanelStart.add((TWinPanelBase) new_game);
		new_game.onClickAbout= new InOnClick(){

			@Override
			public void OnClick(TWinPanelBase new_game) {
				thread.doOnClickNew(new_game);
			}
			
		} ;		
		return new_game;
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return thread.doTouchEvent(event);
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		thread.setSurfaceSize(width, height);
	}
	
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		if (thread.getState() == Thread.State.NEW) {
			thread.start();
		}
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//thread.SaveState();
		thread.setRunning(false);
	}	
	
	
	public MainGameThread getThread() { 
		return thread;
	}
	



}
