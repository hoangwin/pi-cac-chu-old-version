package caogia.android.pikachu.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import caogia.android.pikachu.BitmapFont;
import caogia.android.pikachu.DEF;
import caogia.android.pikachu.Dialog;
import caogia.android.pikachu.GameLayer;
import caogia.android.pikachu.GameLib;
import caogia.android.pikachu.IConstant;
import caogia.android.pikachu.Map;
import caogia.android.pikachu.SoundManager;
import caogia.android.pikachu.Sprite;
import caogia.android.pikachu.Pikachu;


import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.view.KeyEvent;


public class StateLogo extends Pikachu implements IConstant
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
				if (SCREEN_HEIGHT > 700) {
					if (Pikachu.fontsmall_White == null)
						Pikachu.fontsmall_White = new BitmapFont("font/white_24.spr", true);
					if (Pikachu.fontsmall_Yellow == null)
						Pikachu.fontsmall_Yellow = new BitmapFont("font/yellow_24.spr", true);
					if (Pikachu.fontbig_White == null)
						Pikachu.fontbig_White = new BitmapFont("font/white_36.spr", true);
					if (Pikachu.fontbig_Yellow == null)
						Pikachu.fontbig_Yellow = new BitmapFont("font/yellow_36.spr", true);
				} else if (SCREEN_HEIGHT > 300) {
					if (Pikachu.fontsmall_White == null)
						Pikachu.fontsmall_White = new BitmapFont("font/white_big_400x240.spr", true);
					if (Pikachu.fontsmall_Yellow == null)
						Pikachu.fontsmall_Yellow = new BitmapFont("font/yellow_big_400x240.spr", true);
					if (Pikachu.fontbig_White == null)
						Pikachu.fontbig_White = new BitmapFont("font/white_big_400x240.spr", true);
					if (Pikachu.fontbig_Yellow == null)
						Pikachu.fontbig_Yellow = new BitmapFont("font/yellow_big_400x240.spr", true);
				}
				break;
			case 2:
				SoundManager.getInstance();
				SoundManager.initSounds(Pikachu.context);
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
					StateHowToPlay.bitmapHowToplay = Pikachu.loadImageFromAsset("image/howtoplay.png");
				}
				break;

			case 6:
				if (StateGameplay.spriteDPad == null)
					StateGameplay.spriteDPad = new Sprite("sprite/ui/dpad_" + sizePreFix + ".sprite", true);
				if (StateGameplay.spriteTileBoard == null)
					StateGameplay.spriteTileBoard = new Sprite("sprite/ui/animal_" + sizePreFix + ".sprite", true);

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
				Map.CELL_WIDTH = StateGameplay.spriteTileBoard.getFrameWidth(0);
				Map.CELL_HEIGHT = StateGameplay.spriteTileBoard.getFrameHeight(0);
				DEF.BUTTON_IGM_X = SCREEN_WIDTH - DEF.BUTTON_IGM_W - 4;
				DEF.BUTTON_IGM_Y = 4;
				Map.BEGIN_X = (SCREEN_WIDTH- Map.COL*Map.CELL_WIDTH)/2 - DEF.BUTTON_IGM_W ;
				Map.BEGIN_Y = DEF.BUTTON_IGM_H ;//2 * StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_TIMER_BAR_0);\
				
				DEF.BUTTON_TIMER_BAR_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_0);
				DEF.BUTTON_TIMER_BAR_H = DEF.BUTTON_IGM_W + 2;// StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_TIMER_BAR_0);
				DEF.BUTTON_TIMER_BAR_X = fontsmall_Yellow.stringWidth("Level : 60") + DEF.DIALOG_BUTTON_CONFIRM_W / 2;
				DEF.LABEL_SCORE_X = SCREEN_WIDTH  - fontsmall_Yellow.stringWidth("SCORE: 999") ;
				
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

			Pikachu.mainPaint.setColor(Color.BLACK);
			Pikachu.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, Pikachu.mainPaint);
			Pikachu.mainPaint.setColor(Color.WHITE);
			Pikachu.mainPaint.setTextSize(20);
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
