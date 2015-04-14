// author: hoang.nguyenmau
//this file is template for activity same as cGame
//GameLib same GLIb
package com.xiaxio.fruit;



import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.banner.Banner;
import com.xiaxio.fruit.state.StateGameplay;
import com.xiaxio.fruit.state.StateHowToPlay;
import com.xiaxio.fruit.state.StateIngameMenu;
import com.xiaxio.fruit.state.StateLogo;
import com.xiaxio.fruit.state.StateMainMenu;
import com.xiaxio.fruit.state.StateSelectLevel;
import com.xiaxio.fruit.state.StateWinLose;

import resolution.DEF;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.content.*;

public class FruitLink extends GameLib
{

	public static boolean start = true;
	public static boolean running = true;

	public static BitmapFont fontbig_White = null;
	public static Bitmap logoimage;
	public static Bitmap bitmapBgMenu;
	public static Bitmap bitmapBgGamePlay;
	//public static Sprite spriteMenu = null;
	// protected static Sprite spriteMenuButton;

	protected static boolean isGamePause = false;
	public static int mcurrentlevel = 0;
	public static int mLevelUnlock = 0;
	public static Context context;
	public static String SAVE_REF = "level_ref";
	public static String LEVEL_UNLOCK = "level";
	public static boolean isEnableSound = true;
	public static FruitLink mainActivity;
	public static Paint android_FontSmall = new Paint();
	public static Paint android_FontNormal= new Paint();
	public static Paint android_FontBig= new Paint();
	// SCALE
	public static float scaleX = 1.0f;
	public static float scaleY = 1.0f;
	private StartAppAd startAppAd;
	public static Bitmap bitmapScreenBuffer;
	public static Canvas canvasScreenBuffer;
	public static float density = 1.0f;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mainView = new PanelView(this); 
		DisplayMetrics metrics = mainView.getResources().getDisplayMetrics();
		density = metrics.density;
		GameLib.SCREEN_WIDTH = metrics.widthPixels;
		GameLib.SCREEN_HEIGHT = metrics.heightPixels;
		context = getApplicationContext(); 
		loadGame();
	//	10-14 01:11:41.250: I/Ads(1001): To get test ads on this device, call adRequest.addTestDevice("A5E4D10B9DD43396048C1F79524989DB");

		FrameLayout layout = new FrameLayout(this);
		layout.setPadding(0, 0, 0, 0);			
		FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.TOP | android.view.Gravity.RIGHT);
	
		
		layout.addView(mainView);

		
		showStartApp(layout,this); 
		//AdsManager.showInnerActive(layout, this);

		startAppAd = new StartAppAd(this);
		//startAppAd.showAd(); // show the ad
		//startAppAd.loadAd(); // load the next ad
		StartAppAd.showSplash(this, savedInstanceState);
		setContentView(layout);
		//setContentView(mainView, new ViewGroup.LayoutParams(SCREEN_WIDTH,
		//		SCREEN_HEIGHT));

		FruitLink.running = true;

		mainGameLib = this;
		mainActivity = this;
		//	mainView.requestFocus();
		Sprite.initSprite(mainActivity, mainView);
		scaleX =  SCREEN_WIDTH*1.0f/1280;
		scaleY = SCREEN_HEIGHT*1.0f/800;
		changeState(STATE_LOGO);
		(new Thread(new GameThread())).start();

	}
	public static void showStartApp(FrameLayout mainLayout,FruitLink _gameLib)//not full
	{		
		//
		StartAppSDK.init(_gameLib, "106420618", "207681100", true);
		 //  RelativeLayout mainLayout = new RelativeLayout(_gameLib);// (RelativeLayout)findViewById(R.id.mainLayout);   
		   // Define StartApp Banner
		FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);
		   Banner startAppBanner = new Banner(_gameLib);			  
		   // Add to main Layout
		   mainLayout.addView(startAppBanner, adsParams);	
		
	}
	@Override
	public void onPause()
	{
		super.onPause(); // Always call the superclass method first
		if(startAppAd!= null)
			startAppAd.onPause();
		SoundManager.pausesoundLoop(0);
		SoundManager.pausesoundLoop(1);
		// Release the Camera because we don't need it when paused
		// and other activities might need to use it.
		Log.d("       ssss", "ssssssssssssssssssssssssssssssssssssssssssssssssssss");
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if(startAppAd!= null)		
			startAppAd.onResume();
		if (mCurrentState == STATE_MAINMENU)
			SoundManager.playsoundLoop(0, true);
		else if (mCurrentState == STATE_GAMEPLAY)
			SoundManager.playsoundLoop(1, true);

	}

	public void saveGame()
	{
		SharedPreferences settings = getSharedPreferences(SAVE_REF, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(LEVEL_UNLOCK, mLevelUnlock);
		editor.commit();
	}

	public void loadGame()
	{
		SharedPreferences settings = getSharedPreferences(SAVE_REF, 0);
		mLevelUnlock = settings.getInt(LEVEL_UNLOCK, 0);
	}

	public static void changeState(int nextstate)
	{
		// SendMessage
		resetTouch();
		SendMessage(mCurrentState, MESSAGE_DTOR);
		mPreState = mCurrentState;
		mCurrentState = nextstate;
		SendMessage(nextstate, MESSAGE_CTOR);
		// reset variable
		timeBeginCurrentState = System.currentTimeMillis();// L not 1
		frameCountCurrentState = 0;// L not 1

	}

	public static void changeState(int nextstate, boolean isCallConstruct, boolean isCallDetructor)
	{
		// SendMessage
		resetTouch();
		if (isCallDetructor)
			SendMessage(mCurrentState, MESSAGE_DTOR);
		mPreState = mCurrentState;
		mCurrentState = nextstate;
		if (isCallConstruct)
			SendMessage(nextstate, MESSAGE_CTOR);
		// reset variable
		timeBeginCurrentState = System.currentTimeMillis();// L not 1
		frameCountCurrentState = 0;// L not 1

	}

	public static synchronized void SendMessage(int state, int type)
	{
		switch (state)
		{
		case STATE_LOGO:
			StateLogo.SendMessage(type);
			break;
		case STATE_MAINMENU:
			StateMainMenu.SendMessage(type);
			break;
		case STATE_SELECT_LEVEL:
			StateSelectLevel.SendMessage(type);

			break;
		
		case STATE_HOW_TO_PLAY:
			StateHowToPlay.SendMessage(type);
			break;
		case STATE_GAMEPLAY:
			StateGameplay.SendMessage(type);
			break;
		case STATE_IGM:
			StateIngameMenu.SendMessage(type);
			break;
		case STATE_LOADING:
			//	StateLoading.SendMessage(type);
			break;
		case STATE_WINLOSE:
			StateWinLose.SendMessage(type);
			break;
		}

	}

	public static Matrix matrix = new Matrix();

	@Override
	public synchronized void DrawAll(Canvas canvas, Paint paint)
	{
		mainCanvas = canvas;
		mainPaint = paint;
		SendMessage(mCurrentState, MESSAGE_PAINT);

	}

	@Override
	protected void onDestroy()
	{
		SoundManager.pausesoundLoop(0);
		SoundManager.pausesoundLoop(1);
		super.onDestroy();
		//	sound.stopAllSound();
		SoundManager.pausesoundLoop(0);
		SoundManager.pausesoundLoop(1);
		
		
	}

}