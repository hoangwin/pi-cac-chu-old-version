package com.xiaxio.christmas.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.christmas.BitmapFont;
import com.xiaxio.christmas.ChristmasActivity;
import com.xiaxio.christmas.Dialog;
import com.xiaxio.christmas.GameLayer;
import com.xiaxio.christmas.GameLib;
import com.xiaxio.christmas.IConstant;
import com.xiaxio.christmas.Map;
import com.xiaxio.christmas.SoundManager;
import com.xiaxio.christmas.Sprite;

import resolution.DEF;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.view.KeyEvent;



public class StateLogo extends ChristmasActivity implements IConstant
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
				if (SCREEN_WIDTH > 700) {
					if (ChristmasActivity.fontsmall_White == null)
						ChristmasActivity.fontsmall_White = new BitmapFont("font/white_24.spr", true);
					if (ChristmasActivity.fontsmall_Yellow == null)
						ChristmasActivity.fontsmall_Yellow = new BitmapFont("font/yellow_24.spr", true);
					if (ChristmasActivity.fontbig_White == null)
						ChristmasActivity.fontbig_White = new BitmapFont("font/white_36.spr", true);
					if (ChristmasActivity.fontbig_Yellow == null)
						ChristmasActivity.fontbig_Yellow = new BitmapFont("font/yellow_36.spr", true);
				} else if (SCREEN_WIDTH > 300) { 
					if (ChristmasActivity.fontsmall_White == null)
						ChristmasActivity.fontsmall_White = new BitmapFont("font/white_big_400x240.spr", true);
					if (ChristmasActivity.fontsmall_Yellow == null)
						ChristmasActivity.fontsmall_Yellow = new BitmapFont("font/yellow_big_400x240.spr", true);
					if (ChristmasActivity.fontbig_White == null)
						ChristmasActivity.fontbig_White = new BitmapFont("font/white_big_400x240.spr", true);
					if (ChristmasActivity.fontbig_Yellow == null)
						ChristmasActivity.fontbig_Yellow = new BitmapFont("font/yellow_big_400x240.spr", true);
				}
				break;
			case 2:
				SoundManager.getInstance();
				SoundManager.initSounds(ChristmasActivity.context);
				SoundManager.loadSounds();
				break;
			case 3:

				break;
			case 4:
				if (StateGameplay.backgroundImageSelectLevel == null)
					StateGameplay.backgroundImageSelectLevel = loadImageFromAsset("image/screen_1280x800.jpg");
				break;
			case 5:
				if (StateMainMenu.splashBitmap == null)
					StateMainMenu.splashBitmap = loadImageFromAsset("image/splash_1280x800.jpg");
				if (StateMainMenu.gameTitleBitmap == null)
					StateMainMenu.gameTitleBitmap = loadImageFromAsset("image/gametitle.png");
				
				
				if (StateHowToPlay.bitmapHowToplay == null) {
					StateHowToPlay.bitmapHowToplay = ChristmasActivity.loadImageFromAsset("image/howtoplay.png");
				}
				break;

			case 6:
				if (StateGameplay.spriteDPad == null)
					StateGameplay.spriteDPad = new Sprite("sprite/ui/dpad_" + sizePreFix + ".sprite", true);
				if (StateGameplay.spriteTileBoard == null)
					StateGameplay.spriteTileBoard = new Sprite("sprite/ui/animal_" + sizePreFix + ".sprite", true);

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

			ChristmasActivity.mainPaint.setColor(Color.BLACK);
			ChristmasActivity.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, ChristmasActivity.mainPaint);
			ChristmasActivity.mainPaint.setColor(Color.WHITE);
			ChristmasActivity.mainPaint.setTextSize(20);
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
