package com.lavagame.bubblemonster.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.lavagame.bubblemonster.BitmapFont;
import com.lavagame.bubblemonster.DEF;
import com.lavagame.bubblemonster.Dialog;
import com.lavagame.bubblemonster.GameLayer;
import com.lavagame.bubblemonster.GameLib;
import com.lavagame.bubblemonster.IConstant;
import com.lavagame.bubblemonster.LevelManager;
import com.lavagame.bubblemonster.MainActivity;
import com.lavagame.bubblemonster.Map;
import com.lavagame.bubblemonster.SoundManager;
import com.lavagame.bubblemonster.Sprite;


import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.view.KeyEvent;

public class StateLogo extends MainActivity implements IConstant
{
	public static Bitmap logoBitmap = null;
	public static long timer = 0;
	public static int loadingStep = 0;
	public static String sizePreFix = "";
	public static float scalex = SCREEN_WIDTH*1.0f/800;
	public static float scaley = SCREEN_HEIGHT*1.0f/1280;
	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:
			if (SCREEN_HEIGHT >= 1200) {
				sizePreFix = "1280x800";
			} else if (SCREEN_HEIGHT >= 1000) {
				sizePreFix = "1024x600";
			} else if (SCREEN_HEIGHT > 700) {
				sizePreFix = "800x480";
			} else if (SCREEN_HEIGHT > 440) {
				sizePreFix = "480x320";
			} else if (SCREEN_HEIGHT > 360) {
				sizePreFix = "400x240";
			} else {
				sizePreFix = "320x240";
			}
			timer = System.currentTimeMillis();
			//check pre fix
			scalex = SCREEN_WIDTH*1.0f/800;
			scaley = SCREEN_HEIGHT*1.0f/1280;
			loadingStep = 0;
			break;
		case MESSAGE_UPDATE:
			switch (loadingStep)
			{
			case 1:
				
				if (StateMainMenu.splashBitmap == null)
				{
					logoBitmap = loadImageFromAsset("image/logo.png");		
					logoBitmap = Bitmap.createScaledBitmap(logoBitmap,(int)( logoBitmap.getWidth()*scaleX),(int)( logoBitmap.getHeight()*scaleY), true);
				}
				
				break;
			case 2:
				if (MainActivity.fontbig_White == null)
					MainActivity.fontbig_White = new BitmapFont("font/white_36.spr", true);				
				
				Typeface face;
				face = Typeface.createFromAsset(mainGameLib.getAssets(), "font/font.ttf");
				android_FontSmall.setTypeface(face);
				android_FontSmall.setAntiAlias(true);
				android_FontSmall.setDither(true);
				android_FontSmall.setTextSize((int)(40*scaleY));
				android_FontSmall.setColor(Color.WHITE);
				android_FontSmall.setTextAlign(Align.CENTER);
				
				android_FontSmallBoder = new  Paint(android_FontSmall);
				android_FontSmallBoder.setStrokeWidth((int)(9*scaleY));
				android_FontSmallBoder.setColor(Color.YELLOW);
				android_FontSmallBoder.setStyle(Style.FILL_AND_STROKE);
				
				android_FontNormal.setTypeface(face);
				android_FontNormal.setAntiAlias(true);
				android_FontNormal.setDither(true);
				android_FontNormal.setTextSize((int)(55*scaleY));
				android_FontNormal.setColor(Color.WHITE);
				android_FontNormal.setTextAlign(Align.CENTER);
				
				
				android_FontBig.setTypeface(face);
				android_FontBig.setAntiAlias(true);
				android_FontBig.setDither(true);
				android_FontBig.setTextSize((int)(90*scaleY));				
				android_FontBig.setColor(Color.rgb(244,235,100));
				android_FontBig.setTextAlign(Align.CENTER);
				
				android_FontBigBoder = new  Paint(android_FontBig);
				android_FontBigBoder.setStrokeWidth((int)(9*scaleY));
				android_FontBigBoder.setColor(Color.WHITE);
				android_FontBigBoder.setStyle(Style.FILL_AND_STROKE);
				break;
			case 4:
				SoundManager.getInstance();
				SoundManager.initSounds(MainActivity.context);
				SoundManager.loadSounds();
				break;
			case 6:
				if (StateMainMenu.splashBitmap == null)
				{
					StateMainMenu.splashBitmap = loadImageFromAsset("image/splash.jpg");
					StateMainMenu.splashBitmap = Bitmap.createScaledBitmap(StateMainMenu.splashBitmap, SCREEN_WIDTH, SCREEN_HEIGHT, true);
				}
				break;
			case 8:
				if (StateMainMenu.gameTitleBitmap == null){
					StateMainMenu.gameTitleBitmap = loadImageFromAsset("image/gametitle.png");
					StateMainMenu.gameTitleBitmap = Bitmap.createScaledBitmap(StateMainMenu.gameTitleBitmap, (int)(StateMainMenu.gameTitleBitmap.getWidth()*scaleX), (int)(StateMainMenu.gameTitleBitmap.getHeight()*scaleY), true);
				}
				break;
			case 10:
				
				if (spriteUI == null)
				{					
					spriteUI = new Sprite("sprite/ui/ui.sprite", true,scalex, scaley);
				}
			//	if (StateHowToPlay.bitmapHowToplay == null) {
			//		StateHowToPlay.bitmapHowToplay = Monster.loadImageFromAsset("image/howtoplay.png");
			//		StateHowToPlay.bitmapHowToplay = Bitmap.createScaledBitmap(StateHowToPlay.bitmapHowToplay, (int)(StateHowToPlay.bitmapHowToplay.getWidth()*scaleX), (int)(StateHowToPlay.bitmapHowToplay.getHeight()*scaleY), true);
			//	}
			//	break;
 
			case 12:
				LevelManager.loadAllLevel();

				if (StateGameplay.spriteDPad == null)
				{
					//StateGameplay.spriteDPad = new Sprite("sprite/ui/dpad_" + sizePreFix + ".sprite", true);
					StateGameplay.spriteDPad = new Sprite("sprite/ui/dpad_" + "1280x800"+ ".sprite", true,scalex, scaley);
				}

				break;
			case 14:
				//float scalex = SCREEN_WIDTH*1.0f/800;
				//float scaley = SCREEN_HEIGHT*1.0f/1280;
				if (StateGameplay.spriteFruit == null) {
					//StateGameplay.spriteFruit = new Sprite("sprite/ui/animal_" + sizePreFix + ".sprite", true);
				
					StateGameplay.spriteFruit = new Sprite("sprite/ui/animal_" + "1280x800" + ".sprite", true,scalex, scaley);

				}
				break;
			case 16:
			bitmapScreenBuffer = Bitmap.createBitmap(SCREEN_HEIGHT, SCREEN_HEIGHT, Config.ARGB_4444);
			canvasScreenBuffer = new Canvas(bitmapScreenBuffer);
				break;
			case 18://init all variable 
				DEF.DIALOG_BUTTON_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_OK_NORMAL);
				DEF.DIALOG_BUTTON_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_OK_NORMAL);
				DEF.DIALOG_ARROW_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_RIGHT_NORMAL);
				DEF.DIALOG_ARROW_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_RIGHT_NORMAL);

				DEF.BUTTON_IGM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_PAUSE_NORMAL);
				DEF.BUTTON_IGM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_PAUSE_NORMAL);

				
				DEF.BUTTON_IGM_X = SCREEN_WIDTH - DEF.BUTTON_IGM_W - 5;
				DEF.BUTTON_IGM_Y =(int)(950*scaleY);
				DEF.BUTTON_HINT_X = 2;
				DEF.BUTTON_HINT_Y = DEF.BUTTON_IGM_Y + DEF.BUTTON_IGM_W + StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_PAUSE_NORMAL) / 4;
				DEF.BUTTON_CHANGE_TILE_X = 2;
				DEF.BUTTON_CHANGE_TILE_Y = DEF.BUTTON_HINT_Y + DEF.BUTTON_IGM_W + StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_PAUSE_NORMAL) / 4;
				DEF.BUTTON_HINT_W = DEF.BUTTON_CHANGE_TILE_W = 40;
				DEF.BUTTON_HINT_H = DEF.BUTTON_CHANGE_TILE_H = 40;
				//StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_0)

				//for map

				DEF.BUTTON_CANCEL_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_CANCEL_NORMAL);
				DEF.BUTTON_CANCEL_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_CANCEL_NORMAL);
				DEF.BUTTON_HINT_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_CUSTOM_HIGHTLIGHT);

				DEF.BUTTON_HINT_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_CUSTOM_HIGHTLIGHT);

				DEF.BUTTON_CHANGE_TILE_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_CUSTOM_HIGHTLIGHT);

				DEF.BUTTON_CHANGE_TILE_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_CUSTOM_HIGHTLIGHT);
				break;
			case 20:

			default:
				break;
			}
			loadingStep++;
			System.gc();
			if ((System.currentTimeMillis() - timer > 3500) && loadingStep > 25) {
				changeState(STATE_MAINMENU);

			}
			break;
		case MESSAGE_PAINT:
			MainActivity.mainPaint.setColor(Color.WHITE);
			MainActivity.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, MainActivity.mainPaint);
			MainActivity.mainPaint.setColor(Color.WHITE);
			MainActivity.mainPaint.setTextSize(20);
			if (logoBitmap != null) {
				mainCanvas.drawBitmap(logoBitmap,(SCREEN_WIDTH - logoBitmap.getWidth())/2, (SCREEN_HEIGHT - logoBitmap.getHeight())/2, mainPaint);
			}
			//matrix.reset();
			//matrix.setScale(SCREEN_WIDTH*1f/1280, SCREEN_HEIGHT*1f/800);
			//	mainCanvas.drawBitmap(logoBitmap, (SCREEN_WIDTH - logoBitmap.getWidth()) / 2, (SCREEN_HEIGHT - logoBitmap.getHeight()) / 2 - 20, mainPaint);
			//		mainCanvas.drawText(strString, 000, 100, mainPaint);

			break;
		case MESSAGE_DTOR:
			if(logoBitmap != null)
			{
				logoBitmap.recycle();
				logoBitmap = null;
			}
			break;
		}
	}
}
