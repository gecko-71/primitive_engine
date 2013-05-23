package com.gecko71.m_game2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class TWinPanelStart extends  TWinPanelBase {

	
	public TWinPanelStart(int w,int h){
		super();
		active=false;
		widthx=w;
		heighty=h;
	}
	
	
	@Override
	public void drawMainPanel(Canvas ca){
		try {
			if(GetVisible()){
				ca.drawColor(color);
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
		} catch (Exception e) {	}		
	}
	
	
	
	@Override
	public boolean OnEvantGame(Game_Evant game_evant) {
		boolean ok=false; 
		try {
			if (ListWinPanel!=null){
				for(int i=0;i<ListWinPanel.size();i++){
					if (ListWinPanel.get(i).OnEvantGame(game_evant)){
						ok=true;
						//Log.v("m_game2", "true");
						break;
					}
				}
			}
		} catch (Exception e) {Log.v("m_game2", "error");	ok=false;}
		return ok;
	}

	
}
