// author: hoang.nguyenmau
//this file is template for activity same as cGame
//GameLib same GLIb
package caogia.android.pikachu;

import caogia.android.pikachu.state.StateCreadit;
import caogia.android.pikachu.state.StateGameplay;
import caogia.android.pikachu.state.StateHowToPlay;
import caogia.android.pikachu.state.StateIngameMenu;
import caogia.android.pikachu.state.StateLogo;
import caogia.android.pikachu.state.StateMainMenu;
import caogia.android.pikachu.state.StateSelectLevel;
import caogia.android.pikachu.state.StateWinLose;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

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
import android.graphics.Paint.Style;
import android.content.*;

public class Pikachu extends GameLib
{

	public static boolean start = true;
	public static boolean running = true;

	public static BitmapFont fontsmall_White = null;
	public static BitmapFont fontbig_White = null;
	public static BitmapFont fontsmall_Yellow = null;
	public static BitmapFont fontbig_Yellow = null;
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
	public static Pikachu mainActivity;

	// SCALE
	public static float nScaleX = 1.0f;
	public static float nScaleY = 1.0f;
	private static AdView adView;
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

		String UID = "a150e542ec7e8d2"; //Pikachu Portrait

		if (SCREEN_WIDTH > 960)
			adView = new AdView(this, AdSize.IAB_BANNER, UID);
		else
			adView = new AdView(this, AdSize.BANNER, UID);
		FrameLayout layout = new FrameLayout(this);
		layout.setPadding(0, 0, 0, 0);
		FrameLayout.LayoutParams adsParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM | android.view.Gravity.CENTER);

		if (SCREEN_WIDTH < 380) {
			adsParams.setMargins(-70, 0, -70, 0);
		}
		//adsParams.setMargins(-(int)((density -1 +0.5)*320), -(int)((0.5)*50), 0, 0);
		//	RotateAnimation rotate = new RotateAnimation(0,270,25*density,25*density);
		//Transformation tran = new Transformation();
		//	rotate.setDuration(500);
		//	rotate.setRepeatCount(0);
		//	rotate.setFillAfter(true);
		//	layout.startAnimation(rotate);

		layout.addView(mainView);
		layout.addView(adView, adsParams);

		AdRequest request = new AdRequest();
		//request.addTestDevice("A5E4D10B9DD43396048C1F79524989DB");
		//request.addTestDevice(AdRequest.TEST_EMULATOR);               // Emulator
		//request.addTestDevice("TEST_DEVICE_ID");   
		//	request.addTestDevice(AdRequest.TEST_EMULATOR);
		//	request.setTesting(true);
		adView.loadAd(request);
		setContentView(layout);
		//setContentView(mainView, new ViewGroup.LayoutParams(SCREEN_WIDTH,
		//		SCREEN_HEIGHT));

		Pikachu.running = true;
		// Log.d("SCREEN_WIDTH : ", " " + SCREEN_WIDTH);
		// Log.d("SCREEN_HEIGHT : ", " " + SCREEN_HEIGHT);

		mainGameLib = this;
		mainActivity = this;
		//	mainView.requestFocus();
		Sprite.initSprite(mainActivity, mainView);
		changeState(STATE_LOGO);
		(new Thread(new GameThread())).start();

	}

	@Override
	public void onPause()
	{
		super.onPause(); // Always call the superclass method first
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
		case STATE_CREADIT:
			StateCreadit.SendMessage(type);
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
		if (type == MESSAGE_PAINT) {

			mainPaint.setColor(Color.BLACK);
			mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, SCREEN_HEIGHT - adView.getHeight(), SCREEN_WIDTH, SCREEN_HEIGHT, mainPaint);
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
		adView.destroy();

	}

}