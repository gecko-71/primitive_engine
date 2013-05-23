package com.gecko71.m_game2;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

public class TScorePannel extends TWinPanelBase {

    Rect r;
    Paint _paint;
	public TMainGameVariable MainGameVariable;
	int topTitleText;
	int leftTitleText;
	private Paint text_paint;
	private Paint score_paint;
	int score_top;
	private int odstep;
	private int hh_Score;
	private int score_left;
	
	
	public TScorePannel(TMainGameVariable mainGameVariable,int w,int h){
		super();
		active=false;
		widthx=w;
		heighty=h;
		MainGameVariable=mainGameVariable;
		
		float wx= (( (float) widthx)/100)*2; // wyliczam 15 % 
		int wxB=  widthx- (int) wx;
		float hx= (( (float) heighty)/100)*2; // wyliczam 15 % 
		int hxB=  heighty- (int) hx;
		r = new Rect((int) wx,(int) hx,wxB,hxB);
		
		_paint=new Paint();
		_paint.setColor(Color.WHITE);
		_paint.setStrokeWidth(0);
		_paint.setStyle(Paint.Style.STROKE);
		//_paint.setAntiAlias(true);
		int top =ButtonOK();
		// dostepney obszar
		top=top-r.top;
		int ww= (int)(((float) widthx/100f)*45f);
		int hh= (int) ( (( float)top/100) *15);
				
		topTitleText= hh+r.top;
		leftTitleText=(int)(((float)widthx -  (float)ww )  /2f);
		text_paint=new Paint();
		Typeface customfont = Typeface.createFromAsset(MainGameVariable.myContext.getAssets(), "Roboto-Thin.ttf");
		paint.setTypeface(customfont);
		Kontener.adjustTextSize(text_paint,"High Score",hh);
		Kontener.adjustTextScale(text_paint,"High Score",hh,ww);
		//-- 
		//int startS=topTitleText;
		hh_Score= (int) ( (float)(top-topTitleText)/9f);
		odstep= (int) ((    (top-topTitleText)-(hh_Score*5) )/6f);
		//odstep=(int)(      ((float)hh*2f)/4f          );
		score_top=topTitleText;
		score_paint=new Paint();
		ww= (int)(((float) widthx/100f)*85f);
		score_left= (int) ((float) (widthx-ww)/2f);
		String hsText="1   2013-01-03   67856811"; 
		if(MainGameVariable.HighScore.size()>0){
			hsText=GetHighScoreText(1,MainGameVariable.HighScore.get(0));
		}
		//-- 
		Kontener.adjustTextSize(score_paint,hsText,hh_Score);
		Kontener.adjustTextScale(score_paint,hsText,hh_Score,ww);
		
	}
	
	



	private String GetHighScoreText(int i,THighScore tHighScore) {
		// TODO Auto-generated method stub
		try {
			
			Date d = new Date(tHighScore.d);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//String s=Long.toString(tHighScore.score);
			String txt=Integer.toString(i)+"   ";
			txt=txt+sdf.format(d)+"   ";
			txt=txt+String.format("%08d", tHighScore.score);
			return txt;
		} catch (Exception e) {
			
		}
		return "  ";
	}





	private int  ButtonOK() {
		try {
			int wsp_x;
			int wsp_y;
			if (widthx>heighty){
				wsp_x=38;
				wsp_y=40;
			} else {
				wsp_x=20;
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
					doOnClickScoreOK(new_game);
				}
				
			} ;
			add((TWinPanelBase) new_game);
			new_game.SetVisible(true);	
			new_game.SetActive(true);	
			return r.top+top;
		} catch (Exception e) {
			Log.v("m_game2", "error");
			return 0;
		}
	}

	
	public void doOnClickScoreOK(TWinPanelBase new_game) {
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
				//ca.drawText("gecko71 (c) 2013",10,heighty-10,paint);
			}
		} catch (Exception e) {Log.v("m_game2", "error score");	}		
		//ca.drawText("3",10,heighty-10,paint);
		text_paint.setColor(Color.MAGENTA);
		ca.drawText("High Score",leftTitleText,topTitleText,text_paint);
		// ---
		score_paint.setColor(Color.WHITE);
		int hhh;
		//int hhh=score_top+hh_Score+odstep;  //odstep
		//ca.drawText("1   2013-01-03   67856811",score_left,hhh,score_paint);
		//hhh=hhh+hh_Score+odstep;		
		//ca.drawText("2   2013-02-14   67856811",score_left,hhh,score_paint);
		//hhh=hhh+hh_Score+odstep;
		//ca.drawText("3   2013-03-23   67856811",score_left,hhh,score_paint);
		//hhh=hhh+hh_Score+odstep;		
		//ca.drawText("4   2013-04-21   67856811",score_left,hhh,score_paint);
		//hhh=hhh+hh_Score+odstep;
		//ca.drawText("5   2013-12-04   67856811",score_left,hhh,score_paint);
		String hsText;
		hhh=score_top;
		for(int i=0;i<MainGameVariable.HighScore.size();i++){
			if(i==5){
				break;
			}
			hsText=GetHighScoreText(i+1,MainGameVariable.HighScore.get(i));
			hhh=hhh+hh_Score+odstep;  //odstep
			ca.drawText(hsText,score_left,hhh,score_paint);			
		}
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

	
}
