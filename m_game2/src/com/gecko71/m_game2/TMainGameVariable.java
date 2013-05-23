package com.gecko71.m_game2;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.SurfaceHolder;

public class TMainGameVariable {

	Context myContext; 
	SurfaceHolder mySurfaceHolder;
	Bitmap backgroundImg;
	int screenW = 1;
	int screenH = 1;
	boolean running = false;
	boolean onTitle = true;
	MainGameThread thread; // centralny w¹tek
	TWinPanelBase WinPanel;  // centralne okno
	TPanel AboutPane=null;
	TSettingsPannel settingsPannel=null;
	//Context contextT;
	boolean sound_check=true;
	TScorePannel ScorePannel=null;
	ArrayList<THighScore> HighScore = null;
	TNewGamePannelPannel NewGamePannel= null;
}
