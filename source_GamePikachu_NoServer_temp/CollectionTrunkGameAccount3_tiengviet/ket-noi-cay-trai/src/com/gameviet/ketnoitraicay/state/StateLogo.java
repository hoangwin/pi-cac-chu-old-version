package com.gameviet.ketnoitraicay.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.gameviet.ketnoitraicay.BitmapFont;
import com.gameviet.ketnoitraicay.NoiTraiCay;
import com.gameviet.ketnoitraicay.DEF;
import com.gameviet.ketnoitraicay.Dialog;
import com.gameviet.ketnoitraicay.GameLayer;
import com.gameviet.ketnoitraicay.GameLib;
import com.gameviet.ketnoitraicay.IConstant;
import com.gameviet.ketnoitraicay.Map;
import com.gameviet.ketnoitraicay.SoundManager;
import com.gameviet.ketnoitraicay.Sprite;



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


public class StateLogo extends NoiTraiCay implements IConstant
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
				android_FontWhite.setTypeface(face);
				android_FontWhite.setAntiAlias(true);
				android_FontWhite.setDither(true);
				android_FontWhite.setTextSize((int)(55*scaleY));
				android_FontWhite.setColor(Color.WHITE);
				android_FontWhite.setTextAlign(Align.CENTER);
				
				android_FontYellow.setTypeface(face);
				android_FontYellow.setAntiAlias(true);
				android_FontYellow.setDither(true);
				android_FontYellow.setTextSize((int)(55*scaleY));
				android_FontYellow.setColor(Color.rgb(161,107,77));
				android_FontYellow.setTextAlign(Align.CENTER);
								
				/*		
				if (SCREEN_HEIGHT > 700) {
					if (ConnectCute.fontsmall_White == null)
						ConnectCute.fontsmall_White = new BitmapFont("font/white_24.spr", true);
					if (ConnectCute.fontsmall_Yellow == null)
						ConnectCute.fontsmall_Yellow = new BitmapFont("font/yellow_24.spr", true);
					if (ConnectCute.fontbig_White == null)
						ConnectCute.fontbig_White = new BitmapFont("font/white_36.spr", true);
					if (ConnectCute.fontbig_Yellow == null)
						ConnectCute.fontbig_Yellow = new BitmapFont("font/yellow_36.spr", true);
				} else if (SCREEN_HEIGHT > 300) {
					if (ConnectCute.fontsmall_White == null)
						ConnectCute.fontsmall_White = new BitmapFont("font/white_big_400x240.spr", true);
					if (ConnectCute.fontsmall_Yellow == null)
						ConnectCute.fontsmall_Yellow = new BitmapFont("font/yellow_big_400x240.spr", true);
					if (ConnectCute.fontbig_White == null)
						ConnectCute.fontbig_White = new BitmapFont("font/white_big_400x240.spr", true);
					if (ConnectCute.fontbig_Yellow == null)
						ConnectCute.fontbig_Yellow = new BitmapFont("font/yellow_big_400x240.spr", true);
				}
				*/
				break;
			case 2:
				SoundManager.getInstance();
				SoundManager.initSounds(NoiTraiCay.context);
				SoundManager.loadSounds();
				break;
			case 3:

				break;
			case 4:				
				break;
			case 5:
				if (StateMainMenu.splashBitmap == null)
				{
					StateMainMenu.splashBitmap = loadImageFromAsset("image/splash_1280x800.jpg");
					StateMainMenu.splashBitmap = Bitmap.createScaledBitmap(StateMainMenu.splashBitmap, SCREEN_WIDTH, SCREEN_HEIGHT, true);
				}
				if (StateMainMenu.gameTitleBitmap == null)
					StateMainMenu.gameTitleBitmap = loadImageFromAsset("image/gametitle.png");

				if (StateHowToPlay.bitmapHowToplay == null) {
					StateHowToPlay.bitmapHowToplay = NoiTraiCay.loadImageFromAsset("image/howtoplay.png");
					StateHowToPlay.bitmapHowToplay = Bitmap.createScaledBitmap(StateHowToPlay.bitmapHowToplay, SCREEN_WIDTH, SCREEN_HEIGHT, true);
				}
				break;

			case 6:
				if (StateGameplay.spriteDPad == null)
					StateGameplay.spriteDPad = new Sprite("sprite/ui/dpad_1280x800.sprite", true,scaleX,scaleY);
				if (StateGameplay.spriteTileBoard == null)
					StateGameplay.spriteTileBoard = new Sprite("sprite/ui/animal_1280x800.sprite",true,NoiTraiCay.scaleX,NoiTraiCay.scaleY);
					//StateGameplay.spriteTileBoard = new Sprite("sprite/ui/animal_" + sizePreFix + ".sprite", true);

				break;
			case 7:
				bitmapScreenBuffer = Bitmap.createBitmap(800, 1280, Config.ARGB_4444);
				canvasScreenBuffer = new Canvas(bitmapScreenBuffer);
				break;
			case 8://init all variable 
				DEF.DIALOG_BUTTON_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_OK_NORMAL);
				DEF.DIALOG_BUTTON_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_OK_NORMAL);
				DEF.DIALOG_ARROW_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_RIGHT_NORMAL);
				DEF.DIALOG_ARROW_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_RIGHT_NORMAL);

				DEF.BUTTON_IGM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_PAUSE_NORMAL);
				DEF.BUTTON_IGM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_PAUSE_NORMAL);
				Map.CELL_WIDTH = StateGameplay.spriteTileBoard.getFrameWidth(20);
				Map.CELL_HEIGHT = StateGameplay.spriteTileBoard.getFrameHeight(20);
				DEF.BUTTON_IGM_X = SCREEN_WIDTH - DEF.BUTTON_IGM_W - 4;
				DEF.BUTTON_IGM_Y = 4;
				Map.BEGIN_X = (SCREEN_WIDTH - (Map.COL+2)*Map.CELL_WIDTH)/2 ;
				Map.BEGIN_Y = DEF.BUTTON_IGM_H ;//2 * StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_TIMER_BAR_0);\
				
				DEF.BUTTON_TIMER_BAR_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_0);
				DEF.BUTTON_TIMER_BAR_H = DEF.BUTTON_IGM_W + 2;// StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_TIMER_BAR_0);
				float widths[] = new float["Level : 60".length()];
				int w = android_FontYellow.getTextWidths("Level : 60",  widths);
				//DEF.BUTTON_TIMER_BAR_X = fontsmall_Yellow.stringWidth("Level : 60") + DEF.DIALOG_BUTTON_CONFIRM_W / 2;				
				DEF.BUTTON_TIMER_BAR_X =  w + DEF.DIALOG_BUTTON_CONFIRM_W / 6;
				//DEF.LABEL_SCORE_X = SCREEN_WIDTH  - fontsmall_Yellow.stringWidth("SCORE: 999") ;
				w = android_FontYellow.getTextWidths("SCORE: 999",  widths);
				DEF.LABEL_SCORE_X = SCREEN_WIDTH  - w;
				DEF.BUTTON_HINT_X = SCREEN_WIDTH/2 - 3*DEF.BUTTON_HINT_W/2;//DEF.BUTTON_IGM_W;
				DEF.BUTTON_HINT_Y = Map.BEGIN_Y + (Map.ROW + 1)*Map.CELL_HEIGHT+Map.CELL_HEIGHT/2;
				DEF.BUTTON_CHANGE_TILE_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_INFO_HIGHTLIGHT);
				DEF.BUTTON_CHANGE_TILE_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_INFO_HIGHTLIGHT);
				DEF.BUTTON_CHANGE_TILE_X =SCREEN_WIDTH/2 + DEF.BUTTON_HINT_W/2;
				DEF.BUTTON_CHANGE_TILE_Y = DEF.BUTTON_HINT_Y;
				//for map				
				
				DEF.BUTTON_CANCEL_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_CANCEL_NORMAL);
				DEF.BUTTON_CANCEL_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_CANCEL_NORMAL);
				DEF.BUTTON_HINT_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_INFO_HIGHTLIGHT);

				DEF.BUTTON_HINT_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_INFO_HIGHTLIGHT);

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

			NoiTraiCay.mainPaint.setColor(Color.BLACK);
			NoiTraiCay.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, NoiTraiCay.mainPaint);
			NoiTraiCay.mainPaint.setColor(Color.WHITE);
			NoiTraiCay.mainPaint.setTextSize(20);
			matrix.reset();

			matrix.setScale(SCREEN_WIDTH * 1f / 800, SCREEN_HEIGHT * 1f / 1280);
			matrix.postTranslate((SCREEN_WIDTH - logoBitmap.getWidth() * SCREEN_WIDTH / 800) / 2, (SCREEN_HEIGHT - logoBitmap.getHeight() * SCREEN_HEIGHT / 1280) / 2);
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
