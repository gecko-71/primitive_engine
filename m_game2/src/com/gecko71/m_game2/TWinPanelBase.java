package com.gecko71.m_game2;

import java.util.ArrayList;



import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;




// dewinicje rysowania sceny itp

public class TWinPanelBase {
	public int widthx=0;
	public int heighty=0;
	public int x=0;
	public int y=0;
	boolean active=false;
	boolean _visible=false;
	public ArrayList<TWinPanelBase> ListWinPanel = null;
	Paint paint;
	int color ; 
	int LineColor=Color.WHITE;
	
	
	public TWinPanelBase (){
		paint=new Paint();
		color=Color.BLACK;
		ListWinPanel = new ArrayList<TWinPanelBase >();
		//paint.setAntiAlias(true);
	}
	
	public void drawMainPanel(Canvas ca){
		TWinPanelBase dr=null;
		try {
			if (ListWinPanel!=null){
				for(int i=0;i<ListWinPanel.size();i++){
					if(ListWinPanel.get(i).active==true){
						dr=ListWinPanel.get(i);
						break;
					}
				}				
				if(dr!=null){
					// rysuje wszystko na czarno
					//ca.save();
					dr.drawMainPanel(ca);
					//ca.restore();
				}
			}
		} catch (Exception e) {	}
	}
	
	public boolean add(TWinPanelBase W){
		try {
			ListWinPanel.add(W);
			return true;
		} catch (Exception e) {return false;	}		
	}

	
	
	public boolean OnEvantGame(Game_Evant game_evant) {
		TWinPanelBase dr=null;
		boolean ok=false;
		try {
			if (ListWinPanel!=null){
				for(int i=0;i<ListWinPanel.size();i++){
					if(ListWinPanel.get(i).active==true){
						dr=ListWinPanel.get(i);
						break;
					}
				}				
				if(dr!=null){
					dr.OnEvantGame(game_evant);
					//Log.v("m_game2", "true");
					ok=true;
				} else {
					//Log.v("m_game2", "false");
					ok=false;					
				}
			}
		} catch (Exception e) {Log.v("m_game2", "error");	ok=false;}
		return ok;	
	}

	
	public void SetVisible(boolean v){
		_visible=v;
		
	}

	
	public boolean GetVisible(){
		return _visible;
	}
	
	public void SetActive(boolean b) {
		active=b;
	}	

	public boolean GetActive() {
		return active;
	}

	public void SaveState(Editor editor) {
		TWinPanelBase dr=null;
		try {
			if (ListWinPanel!=null){
				for(int i=0;i<ListWinPanel.size();i++){
					if(ListWinPanel.get(i).active==true){
						dr=ListWinPanel.get(i);
						dr.SaveState(editor);
					}
				}				
			}
		} catch (Exception e) {}
	}	
	

}
