package com.megagame.animal.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.megagame.animal.BitmapFont;
import com.megagame.animal.Dialog;
import com.megagame.animal.GameLayer;
import com.megagame.animal.GameLib;
import com.megagame.animal.IConstant;
import com.megagame.animal.Map;
import com.megagame.animal.AnimalActivity;
import com.megagame.animal.SoundManager;
import com.megagame.animal.Sprite;

import resolution.DEF;

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



public class StateLogo extends AnimalActivity implements IConstant
{
	public static Bitmap logoBitmap = null;
	public static long timer = 0;
	public static int loadingStep = 0;
	public static String sizePreFix = "";

	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:
			if (SCREEN_WIDTH >= 1200) {
				sizePreFix = "1280x800";
			}else if (SCREEN_WIDTH >= 1000) {
				sizePreFix = "1024x600";
			} else if (SCREEN_WIDTH > 700) {
				sizePreFix = "800x480";
			} else if (SCREEN_WIDTH > 440) {
				sizePreFix = "480x320";
			} else if (SCREEN_WIDTH > 360) {
				sizePreFix = "400x240";
			} else {
				sizePreFix = "320x240";
			}
			logoBitmap = loadImageFromAsset("image/logo.png");
			loadingStep = 0;
			timer = System.currentTimeMillis();
			//check pre fix

			break;
		case MESSAGE_UPDATE:
			switch (loadingStep)
			{
			case 1:
			Typeface face;
				face = Typeface.createFromAsset(mainGameLib.getAssets(), "font/font.ttf");
				android_FontSmall.setTypeface(face);
				android_FontSmall.setAntiAlias(true);
				android_FontSmall.setDither(true);
				android_FontSmall.setTextSize((int)(45*scaleY));
				android_FontSmall.setColor(Color.WHITE);
				android_FontSmall.setTextAlign(Align.CENTER);
				
				android_FontNormal.setTypeface(face);
				android_FontNormal.setAntiAlias(true);
				android_FontNormal.setDither(true);
				android_FontNormal.setTextSize((int)(75*scaleY));
				android_FontNormal.setColor(Color.rgb(68,153,238));
				android_FontNormal.setTextAlign(Align.CENTER);
				
				
				android_FontBig.setTypeface(face);
				android_FontBig.setAntiAlias(true);
				android_FontBig.setDither(true);
				android_FontBig.setTextSize((int)(90*scaleY));				
				android_FontBig.setColor(Color.RED);
				android_FontBig.setTextAlign(Align.CENTER);
				if (SCREEN_WIDTH > 700) {
					if (AnimalActivity.fontsmall_White == null)
						AnimalActivity.fontsmall_White = new BitmapFont("font/white_24.spr", true);
					if (AnimalActivity.fontsmall_Yellow == null)
						AnimalActivity.fontsmall_Yellow = new BitmapFont("font/yellow_24.spr", true);
					if (AnimalActivity.fontbig_White == null)
						AnimalActivity.fontbig_White = new BitmapFont("font/white_36.spr", true);
					if (AnimalActivity.fontbig_Yellow == null)
						AnimalActivity.fontbig_Yellow = new BitmapFont("font/yellow_36.spr", true);
				} else if (SCREEN_WIDTH > 300) { 
					if (AnimalActivity.fontsmall_White == null)
						AnimalActivity.fontsmall_White = new BitmapFont("font/white_big_400x240.spr", true);
					if (AnimalActivity.fontsmall_Yellow == null)
						AnimalActivity.fontsmall_Yellow = new BitmapFont("font/yellow_big_400x240.spr", true);
					if (AnimalActivity.fontbig_White == null)
						AnimalActivity.fontbig_White = new BitmapFont("font/white_big_400x240.spr", true);
					if (AnimalActivity.fontbig_Yellow == null)
						AnimalActivity.fontbig_Yellow = new BitmapFont("font/yellow_big_400x240.spr", true);
				}
				break;
			case 2:
				SoundManager.getInstance();
				SoundManager.initSounds(AnimalActivity.context);
				SoundManager.loadSounds();
				break;
			case 3:

				break;
			case 4:
				if (StateGameplay.backgroundImage == null)
				{
					StateGameplay.backgroundImage = loadImageFromAsset("image/screen_1280x800.jpg");
					StateGameplay.backgroundImage = Bitmap.createScaledBitmap(StateGameplay.backgroundImage, SCREEN_WIDTH, SCREEN_HEIGHT, true);
				}
				break;
			case 5:
				if (StateMainMenu.splashBitmap == null)
				{
					StateMainMenu.splashBitmap = loadImageFromAsset("image/splash_1280x800.jpg");
					StateMainMenu.splashBitmap = Bitmap.createScaledBitmap(StateMainMenu.splashBitmap, SCREEN_WIDTH, SCREEN_HEIGHT, true);
				}
				if (StateMainMenu.gameTitleBitmap == null)
				{
					StateMainMenu.gameTitleBitmap = loadImageFromAsset("image/gametitle.png");
					StateMainMenu.gameTitleBitmap = Bitmap.createScaledBitmap(StateMainMenu.gameTitleBitmap, (int)(StateMainMenu.gameTitleBitmap.getWidth()*scaleX), (int)(StateMainMenu.gameTitleBitmap.getHeight()*scaleX), true);
				}
				if (StateHowToPlay.bitmapHowToplay == null) {
					StateHowToPlay.bitmapHowToplay = loadImageFromAsset("image/howtoplay.png");
					StateHowToPlay.bitmapHowToplay = Bitmap.createScaledBitmap(StateHowToPlay.bitmapHowToplay, SCREEN_WIDTH, SCREEN_HEIGHT, true);
				}
				break;

			case 6:
				if (StateGameplay.spriteDPad == null)
					StateGameplay.spriteDPad = new Sprite("sprite/ui/dpad_1280x800.sprite", true,scaleX,scaleY);
				if (StateGameplay.spriteTileBoard == null)
					StateGameplay.spriteTileBoard = new Sprite("sprite/ui/animal_1280x800.sprite", true,scaleX,scaleY);

				break;
			case 7:
				bitmapScreenBuffer = Bitmap.createBitmap(1280, 800, Config.ARGB_4444);
				canvasScreenBuffer = new Canvas(bitmapScreenBuffer);
				break;
			case 8://init all variable 
				DEF.DIALOG_BUTTON_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_OK_NORMAL);
				DEF.DIALOG_BUTTON_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_OK_NORMAL);
				DEF.DIALOG_ARROW_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_RIGHT_NORMAL);
				DEF.DIALOG_ARROW_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_RIGHT_NORMAL);

				

				DEF.BUTTON_IGM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_PAUSE_NORMAL);
				DEF.BUTTON_IGM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_PAUSE_NORMAL);
				
				
				DEF.BUTTON_TIMER_BAR_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_0);
				DEF.BUTTON_TIMER_BAR_H = DEF.BUTTON_IGM_W +2;// StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_TIMER_BAR_0);
				DEF.BUTTON_TIMER_BAR_X = fontsmall_Yellow.stringWidth("Level : 60") + DEF.DIALOG_BUTTON_CONFIRM_W / 2;
				DEF.BUTTON_TIMER_BAR_Y = (DEF.BUTTON_IGM_W -StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_TIMER_BAR_0))/2;
				
				DEF.BUTTON_IGM_X = 2;
				DEF.BUTTON_IGM_Y =  DEF.BUTTON_TIMER_BAR_H  + Map.CELL_HEIGHT / 2;
				DEF.BUTTON_HINT_X = 2;
				DEF.BUTTON_HINT_Y = DEF.BUTTON_IGM_Y + DEF.BUTTON_IGM_W  +  StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_PAUSE_NORMAL) / 4 ;
				 DEF.BUTTON_CHANGE_TILE_X  =2;
				DEF.BUTTON_CHANGE_TILE_Y = DEF.BUTTON_HINT_Y + DEF.BUTTON_IGM_W  +  StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_PAUSE_NORMAL) / 4 ;
				DEF.BUTTON_HINT_W = DEF.BUTTON_CHANGE_TILE_W = 40;
				DEF.BUTTON_HINT_H = DEF.BUTTON_CHANGE_TILE_H = 40;
				//StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_0)
				
				//for map
				
				Map.CELL_WIDTH = StateGameplay.spriteTileBoard.getFrameWidth(0);
				Map.CELL_HEIGHT = StateGameplay.spriteTileBoard.getFrameHeight(0);
				Map.BEGIN_X = DEF.BUTTON_IGM_W;
				Map.BEGIN_Y = DEF.BUTTON_TIMER_BAR_H / 2 - Map.CELL_HEIGHT / 4;//2 * StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_TIMER_BAR_0);

				DEF.BUTTON_CANCEL_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_CANCEL_NORMAL);
				DEF.BUTTON_CANCEL_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_CANCEL_NORMAL);
				DEF.BUTTON_HINT_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_CUSTOM_HIGHTLIGHT);

				DEF.BUTTON_HINT_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_CUSTOM_HIGHTLIGHT);

				DEF.BUTTON_CHANGE_TILE_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_CUSTOM_HIGHTLIGHT);

				DEF.BUTTON_CHANGE_TILE_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_CUSTOM_HIGHTLIGHT);

				break;
			case 9:

			default:
				break;
			}
			loadingStep++;

			if ((System.currentTimeMillis() - timer > 3500) && loadingStep > 15) {
				changeState(STATE_MAINMENU);

			}
			break;
		case MESSAGE_PAINT:

			AnimalActivity.mainPaint.setColor(Color.BLACK);
			AnimalActivity.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, AnimalActivity.mainPaint);
			AnimalActivity.mainPaint.setColor(Color.WHITE);
			AnimalActivity.mainPaint.setTextSize(20);
			matrix.reset();

			matrix.setScale(SCREEN_WIDTH * 1f / 1280, SCREEN_HEIGHT * 1f / 800);
			matrix.postTranslate((SCREEN_WIDTH - logoBitmap.getWidth() * SCREEN_WIDTH / 1280) / 2, (SCREEN_HEIGHT - logoBitmap.getHeight() * SCREEN_HEIGHT / 800) / 2);
			if (logoBitmap != null)
				mainCanvas.drawBitmap(logoBitmap, matrix, mainPaint);

			//matrix.reset();
			//matrix.setScale(SCREEN_WIDTH*1f/1280, SCREEN_HEIGHT*1f/800);
			//	mainCanvas.drawBitmap(logoBitmap, (SCREEN_WIDTH - logoBitmap.getWidth()) / 2, (SCREEN_HEIGHT - logoBitmap.getHeight()) / 2 - 20, mainPaint);
			//		mainCanvas.drawText(strString, 000, 100, mainPaint);

			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
