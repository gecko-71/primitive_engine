package com.gecko71.m_game2;

//import com.gecko71.puzzle.PuzleDraw.TBmpRecord;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;



public class MainGameThread extends Thread{

	TMainGameVariable MainGameVariable;
	Paint paint=null;
	TWinPanelBase WinPanel=null;
	Game_Evant game_evant;
	SharedPreferences sPreferences;
	
	public MainGameThread(SurfaceHolder	surfaceHolder, Context context,
			SharedPreferences sharedPreferences, TMainGameVariable MainGameV,Handler handler) {
		MainGameVariable=MainGameV;
		MainGameVariable.mySurfaceHolder = surfaceHolder;
		MainGameVariable.myContext = context;
		MainGameVariable.backgroundImg =	null;
		game_evant=new Game_Evant();
		paint = new Paint();
		sPreferences=sharedPreferences;
	}

	@Override
	public void run() {
		OpenState();
		//THighScore hg = new THighScore();
		//hg.score=827237;
		//Date d=new Date();
		//hg.d=d.getTime();
		//MainGameVariable.HighScore.add(hg);
		//hg = new THighScore();
		//hg.score=72347;
		//d=new Date();
		//hg.d=d.getTime();		
		//MainGameVariable.HighScore.add(hg);
		
		while (MainGameVariable.running) {
			// onEvent
			//synchronized (MainGameVariable.mySurfaceHolder) {
			//	if (game_evant.ok==false){ // jesli zdarzenie nie obsluzone
			//		MainGameVariable.WinPanel.OnEvantGame(game_evant); // szukam gdzie zdarzenie wyslac				
			//	}
			//}
			
			// rysowanie
			Canvas c = null;
			try {
				c = MainGameVariable.mySurfaceHolder.lockCanvas(null);
				synchronized (MainGameVariable.mySurfaceHolder) {
					MainGameVariable.WinPanel.drawMainPanel(c);
				}
			} finally {
				if (c != null) {
					MainGameVariable.mySurfaceHolder.unlockCanvasAndPost(c);
				}
			}
			// -------
		}
		SaveState();
	}

	private void  OpenState() {
		try{
				MainGameVariable.sound_check=sPreferences.getBoolean("sound_check", true);
				MainGameVariable.HighScore.clear();
			//	---
				THighScore B ;
				int lp;
				int HighScoreCount=sPreferences.getInt("HighScoreCount",-1);
				if(HighScoreCount>-1){
				for(int i=0;i<HighScoreCount;i++){
					B = new THighScore();
					lp=sPreferences.getInt("HighScore_"+i+"lp",-1);
					if(lp==-1) {
					   break;	
					}
					B.score=sPreferences.getLong("HighScore_"+i+"Score",0);
					B.d=sPreferences.getLong("HighScore_"+i+"data",0);
					MainGameVariable.HighScore.add(0,B);
				}
			}			
		} catch (Exception e) { }
	}

	
	
	private void SaveState() {
		synchronized (MainGameVariable.mySurfaceHolder) {
			try {
				SharedPreferences.Editor editor = sPreferences.edit();  // Put the values from the
				
				for(int i=0;i<MainGameVariable.WinPanel.ListWinPanel.size();i++){
					MainGameVariable.WinPanel.ListWinPanel.get(i).SaveState(editor);
				}
				editor.putBoolean("sound_check", MainGameVariable.sound_check);
				//
				//HighScore
				if(MainGameVariable.HighScore.size()>0){
					editor.putInt("HighScoreCount",MainGameVariable.HighScore.size());	
				} else {
					editor.putInt("HighScoreCount",-1);	
				}
				for(int i=0;i<MainGameVariable.HighScore.size();i++){
					editor.putInt("HighScore_"+i+"lp",i);
					editor.putLong("HighScore_"+i+"Score",MainGameVariable.HighScore.get(i).score);
					editor.putLong("HighScore_"+i+"data",MainGameVariable.HighScore.get(i).d);					
				}
				
				//-----
				editor.commit();		
			} catch (Exception e) {	}
		}
	}

	boolean doTouchEvent(MotionEvent event) { 
		synchronized (MainGameVariable.mySurfaceHolder) {
			int eventaction = event.getAction();
			int X = (int)event.getX();
			int Y = (int)event.getY();
			switch (eventaction ) {
				case MotionEvent.ACTION_DOWN:
					    game_evant.MotionEvent_event=MotionEvent.ACTION_DOWN;
					    game_evant.x=X;
					    game_evant.y=Y;
					    game_evant.ok=false;
						if (game_evant.ok==false){ // jesli zdarzenie nie obsluzone
							MainGameVariable.WinPanel.OnEvantGame(game_evant); // szukam gdzie zdarzenie wyslac				
						}
						break;
				case MotionEvent.ACTION_MOVE:
						game_evant.MotionEvent_event=MotionEvent.ACTION_MOVE;
						game_evant.x=X;
						game_evant.y=Y;
						game_evant.ok=false;
						if (game_evant.ok==false){ // jesli zdarzenie nie obsluzone
							MainGameVariable.WinPanel.OnEvantGame(game_evant); // szukam gdzie zdarzenie wyslac				
						}
						break;
				case MotionEvent.ACTION_UP:
					game_evant.MotionEvent_event=MotionEvent.ACTION_UP;
					game_evant.x=X;
					game_evant.y=Y;
					game_evant.ok=false;
					if (game_evant.ok==false){ // jesli zdarzenie nie obsluzone
						MainGameVariable.WinPanel.OnEvantGame(game_evant); // szukam gdzie zdarzenie wyslac				
					}
					break;
			}
		}
		return true;
	}	

	

	public void setSurfaceSize(int width, int height) {
			synchronized (MainGameVariable.mySurfaceHolder) {
				MainGameVariable.screenW = width;
				MainGameVariable.screenH = height;
			}
	}
	
	public void setRunning(boolean b) {
		MainGameVariable.running = b;
	}

	public void doOnClickAbout(TWinPanelBase B) {
		// TODO Auto-generated method stub
		TButton BB=(TButton) B;
		synchronized (MainGameVariable.mySurfaceHolder) {
			try {
				if(BB.parent!=null){
					if (BB.parent.ListWinPanel!=null){
						for(int i=0;i<BB.parent.ListWinPanel.size();i++){
							BB.parent.ListWinPanel.get(i).SetVisible(false);
						}				
					}
					// wyswietl panel
					if(MainGameVariable.AboutPane!=null) {
					  MainGameVariable.AboutPane.SetVisible(true);
					}
				}
			} catch (Exception e) {	}
		}
	}

	public void doOnClickPanel(TWinPanelBase B) {
		TPanel BB=(TPanel) B;
		synchronized (MainGameVariable.mySurfaceHolder) {
			try {
				if(BB.parent!=null){
					
					if (BB.parent.ListWinPanel!=null){
						for(int i=0;i<BB.parent.ListWinPanel.size();i++){
							BB.parent.ListWinPanel.get(i).SetVisible(true);
						}				
					}
					// gas panel panel
					if(MainGameVariable.AboutPane!=null) {
					  MainGameVariable.AboutPane.SetVisible(false);
					}
				}
			} catch (Exception e) {	}
		}
	}

	public void doOnClickSettings(TWinPanelBase B) {
		synchronized (MainGameVariable.mySurfaceHolder) {
			try {
				for(int i=0;i<MainGameVariable.WinPanel.ListWinPanel.size();i++){
					MainGameVariable.WinPanel.ListWinPanel.get(i).SetActive(false);
				}				
				SettingsPannel();
			} catch (Exception e) {	}
		}
		
	}


	
	
	private void SettingsPannel() {
		if(MainGameVariable.settingsPannel==null){
			MainGameVariable.settingsPannel = new TSettingsPannel(MainGameVariable,MainGameVariable.WinPanel.widthx,MainGameVariable.WinPanel.heighty);
			MainGameVariable.WinPanel.add(MainGameVariable.settingsPannel);
		} 
		if(MainGameVariable.settingsPannel!=null){
			MainGameVariable.settingsPannel.SetActive(true);
			MainGameVariable.settingsPannel.SetVisible(true);	
			
		}
	}

	
	
	public void doOnClickScore(TWinPanelBase B) {
		synchronized (MainGameVariable.mySurfaceHolder) {
			try {
				for(int i=0;i<MainGameVariable.WinPanel.ListWinPanel.size();i++){
					MainGameVariable.WinPanel.ListWinPanel.get(i).SetActive(false);
				}				
				ScorePannel();
			} catch (Exception e) {	}
		}
		
	}

	private void ScorePannel() {
		
		if(MainGameVariable.ScorePannel==null){
			MainGameVariable.ScorePannel = new TScorePannel(MainGameVariable,MainGameVariable.WinPanel.widthx,MainGameVariable.WinPanel.heighty);
			MainGameVariable.WinPanel.add(MainGameVariable.ScorePannel);
		} 
		if(MainGameVariable.ScorePannel!=null){
			MainGameVariable.ScorePannel.SetActive(true);
			MainGameVariable.ScorePannel.SetVisible(true);	
			
		}
		
	}

	public void doOnClickNew(TWinPanelBase new_game) {
		synchronized (MainGameVariable.mySurfaceHolder) {
			try {
				for(int i=0;i<MainGameVariable.WinPanel.ListWinPanel.size();i++){
					MainGameVariable.WinPanel.ListWinPanel.get(i).SetActive(false);
				}				
				NewGamePannel();
			} catch (Exception e) {	}
		}
	}

	private void NewGamePannel() {
		if(MainGameVariable.NewGamePannel==null){
			MainGameVariable.NewGamePannel = new TNewGamePannelPannel(MainGameVariable,MainGameVariable.WinPanel.widthx,MainGameVariable.WinPanel.heighty);
			MainGameVariable.WinPanel.add(MainGameVariable.NewGamePannel);
		} 
		if(MainGameVariable.NewGamePannel!=null){
			MainGameVariable.NewGamePannel.SetActive(true);
			MainGameVariable.NewGamePannel.SetVisible(true);	
			
		}
	}		

//	private void drawMaiPanel(Canvas ca) {
		// //TODO Auto-generated method stub
		//try {
				////ca.drawBitmap(MainGameVariable.backgroundImg, 0, 0,null);
				//paint.setColor(Color.WHITE);
				//ca.drawText("Piotr S (c) 2013",2,12,paint);
			//} catch (Exception e) {	}		
	//}	

}
