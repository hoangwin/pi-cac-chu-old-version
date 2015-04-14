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
import com.lavagame.bubblemonster.SoundManager;


import android.graphics.Color;
import android.graphics.Rect;

import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;

public class StateSelectLevel extends MainActivity
{
	public static RectF rectF;
	public static int NUM_ROW = 5;
	public static int NUM_COL = 4;
	public static int MAX_PAG = 1;
	public static int MAX_LEVEL = 20;
	public static int mcurrentPage = 0;
	public static int SELECTLEVEL_BUTTON_W = 0;
	public static int SELECTLEVEL_BUTTON_H = 0;
	public static int SELECTLEVEL_BEGIN_Y = 0;
	public static int SELECTLEVEL_BEGIN_X = 0;
	public static int BUTTON_ARROW_CONFIRM_W = 60;
	public static int BUTTON_ARROW_CONFIRM_H = 60;
	static int BUTTON_LEFT_X = (int)(200*scaleX);
	static int BUTTON_RIGHT_X = (int)(500*scaleX);
	static int BUTTON_LEFT_RIGHT_Y = (int)(1050*scaleY);
	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:
			MAX_LEVEL = LevelManager.getMaxLevel(); 
			MAX_PAG = MAX_LEVEL/(NUM_ROW*NUM_COL);
			rectF = new RectF(DEF.SELECTLEVEL_BACKGROUND_X, DEF.SELECTLEVEL_BACKGROUND_Y, DEF.SELECTLEVEL_BACKGROUND_X + DEF.SELECTLEVEL_BACKGROUND_W, DEF.SELECTLEVEL_BACKGROUND_Y + DEF.SELECTLEVEL_BACKGROUND_H);
			mcurrentPage = MainActivity.mLevelUnlock / (NUM_ROW * NUM_COL);
			if(mcurrentPage >= MAX_PAG)
				mcurrentPage = 0;

			SELECTLEVEL_BUTTON_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_SELECTLEVEL_NORMAL);
			SELECTLEVEL_BUTTON_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_SELECTLEVEL_NORMAL);
			SELECTLEVEL_BEGIN_Y = (int)(220*scaleY);//(SCREEN_HEIGHT - ((SELECTLEVEL_BUTTON_H + DEF.SELECTLEVEL_CONTENT_SPACE_H) * (NUM_ROW - 1))) / 2 - 20;
			SELECTLEVEL_BEGIN_X = (SCREEN_WIDTH - ((SELECTLEVEL_BUTTON_W + DEF.SELECTLEVEL_CONTENT_SPACE_W) * (NUM_COL - 1))) / 2;

			DEF.SELECTLEVEL_CONTENT_SPACE_H =(int)(40*scaleY);// ((SCREEN_HEIGHT - SELECTLEVEL_BEGIN_Y) - NUM_ROW * SELECTLEVEL_BUTTON_H - SCREEN_HEIGHT / 10) / (NUM_ROW - 1);
			BUTTON_ARROW_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_LEFT_NORMAL);
			BUTTON_ARROW_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_LEFT_NORMAL);
			//SoundManager.pausesoundLoop(0);
			break;
		case MESSAGE_UPDATE:
			Rect textBounds =  new  Rect();
			android_FontNormal.getTextBounds("Maig", 0, "Maig".length(), textBounds);
			for (int i = 0; i < NUM_ROW; i++) {
				int y = SELECTLEVEL_BEGIN_Y + i * (SELECTLEVEL_BUTTON_H + DEF.SELECTLEVEL_CONTENT_SPACE_H) + textBounds.height();
				int x = 0;
				for (int j = 0; j < NUM_COL; j++) {
					x = SELECTLEVEL_BEGIN_X + j * (SELECTLEVEL_BUTTON_W + DEF.SELECTLEVEL_CONTENT_SPACE_W);

					if (isTouchReleaseInRect(x - SELECTLEVEL_BUTTON_W / 2, y - SELECTLEVEL_BUTTON_H / 2, SELECTLEVEL_BUTTON_W, SELECTLEVEL_BUTTON_H)) {

						MainActivity.mcurrentlevel = mcurrentPage * NUM_COL * NUM_ROW + i * NUM_ROW + j;
						if (MainActivity.mcurrentlevel <= MainActivity.mLevelUnlock) {
							SoundManager.playSound(SoundManager.SOUND_SELECT, 1);
							changeState(STATE_HINT);
						}
						break;
					}

				}
			}

			//left right button
			if (MAX_PAG > 1)
				if (MainActivity.isTouchReleaseInRect(BUTTON_LEFT_X, BUTTON_LEFT_RIGHT_Y, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H)) {
					mcurrentPage--;
					if (mcurrentPage < 0)
						mcurrentPage = MAX_PAG - 1;
					SoundManager.playSound(SoundManager.SOUND_SELECT, 1);
				}

			//right	
			if (MAX_PAG > 1)
				if (MainActivity.isTouchReleaseInRect(BUTTON_RIGHT_X,BUTTON_LEFT_RIGHT_Y, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H)) {
					mcurrentPage++;
					if (mcurrentPage >= MAX_PAG)
						mcurrentPage = 0;
					SoundManager.playSound(SoundManager.SOUND_SELECT, 1);
				}

			if (isKeyReleased(KeyEvent.KEYCODE_BACK) || MainActivity.isTouchReleaseInRect(SCREEN_WIDTH-  DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.SELECTLEVEL_BACKGROUND_Y + 8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H)) {

				SoundManager.playSound(SoundManager.SOUND_BACK, 1);
				changeState(STATE_MAINMENU, true, true);
				break;
			}
			//if (GameLib.isTouchReleaseScreen()) {
			//	changeState(STATE_GAMEPLAY);
			//}

			break;
		case MESSAGE_PAINT:
			if (!StateGameplay.isIngame) {
				MainActivity.mainPaint.setColor(Color.BLACK);
				MainActivity.mainPaint.setStyle(Style.FILL);				
				mainCanvas.drawBitmap(StateMainMenu.splashBitmap, 0,0, mainPaint);
			} else {
				StateGameplay.SendMessage(MESSAGE_PAINT);
			}
			textBounds =  new  Rect();
			android_FontNormal.getTextBounds("Maig", 0, "Maig".length(), textBounds);
			if (MAX_PAG > 1)
				mainCanvas.drawText("" + (mcurrentPage + 1) + "/" + MAX_PAG, SCREEN_WIDTH/2, (int) (1100*scaleY) , android_FontNormal);
				//TapTapZooCrush.fontsmall_Yellow.drawString(TapTapZooCrush.mainCanvas, "Page : " + (mcurrentPage + 1) + "/" + MAX_PAG, DEF.SELECTLEVEL_BACKGROUND_X + SELECTLEVEL_BUTTON_W + 2, DEF.SELECTLEVEL_BACKGROUND_Y - 2, BitmapFont.ALIGN_LEFT);
			
			
			for (int i = 0; i < NUM_ROW; i++) {
				int y = SELECTLEVEL_BEGIN_Y + i * (SELECTLEVEL_BUTTON_H + DEF.SELECTLEVEL_CONTENT_SPACE_H) + textBounds.height();
				int x = 0;
				for (int j = 0; j < NUM_COL; j++) {
					x = SELECTLEVEL_BEGIN_X + j * (SELECTLEVEL_BUTTON_W + DEF.SELECTLEVEL_CONTENT_SPACE_W);
					MainActivity.mcurrentlevel = mcurrentPage * NUM_COL * NUM_ROW + i * NUM_ROW + j;
					if (MainActivity.mcurrentlevel <= MainActivity.mLevelUnlock) {
						if (isTouchDrapInRect(x - SELECTLEVEL_BUTTON_W / 2, y - SELECTLEVEL_BUTTON_H / 2, SELECTLEVEL_BUTTON_W, SELECTLEVEL_BUTTON_H))
							StateGameplay.spriteDPad.drawAFrame(mainCanvas, DEF.FRAME_SELECTLEVEL_HIGHTLIGHT, x, y);
						else
							StateGameplay.spriteDPad.drawAFrame(mainCanvas, DEF.FRAME_SELECTLEVEL_NORMAL, x, y);
						mainCanvas.drawText(" " + (mcurrentPage * NUM_COL * NUM_ROW + i * NUM_ROW + j + 1) + " ", x, y + textBounds.height()/2 , android_FontNormal);
						//fontsmall_White.drawString(mainCanvas, " " + (mcurrentPage * NUM_COL * NUM_ROW + i * NUM_ROW + j + 1) + " ", x, y - 3 * fontbig_Yellow.getFontHeight() / 4, BitmapFont.ALIGN_CENTER);
						if (MainActivity.mcurrentlevel != MainActivity.mLevelUnlock) {
							//	fontbig_Yellow.drawString(mainCanvas, "Pass", x, y - fontbig_Yellow.getFontHeight()/2  , BitmapFont.ALIGN_CENTER);
						} else
							mainCanvas.drawText( "New", x, y+ 3 * textBounds.height()/2 , android_FontNormal);
							//fontsmall_White.drawString(mainCanvas, "New", x, y, BitmapFont.ALIGN_CENTER);
					} else {
						StateGameplay.spriteDPad.drawAFrame(mainCanvas, DEF.FRAME_SELECTLEVEL_LOCK, x, y);
					}

				}
			}
			//select left and right button
			
			mainCanvas.drawText( "SELECT LEVEL", SCREEN_WIDTH / 2, textBounds.height(), android_FontNormal);

			//Monster.fontbig_Yellow.drawString(Monster.mainCanvas, "SELECT LEVEL", SCREEN_WIDTH / 2, fontbig_Yellow.getFontHeight(), BitmapFont.ALIGN_CENTER);
			if (MAX_PAG > 1)
				if (MainActivity.isTouchDrapInRect(BUTTON_LEFT_X, BUTTON_LEFT_RIGHT_Y, BUTTON_ARROW_CONFIRM_W, BUTTON_ARROW_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_BUTTON_LEFT_HIGHTLIGHT,BUTTON_LEFT_X, BUTTON_LEFT_RIGHT_Y);
				else
					StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_BUTTON_LEFT_NORMAL, BUTTON_LEFT_X,BUTTON_LEFT_RIGHT_Y);
			//right	
			if (MAX_PAG > 1)
				if (MainActivity.isTouchDrapInRect(BUTTON_RIGHT_X, BUTTON_LEFT_RIGHT_Y, BUTTON_ARROW_CONFIRM_W, BUTTON_ARROW_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_BUTTON_RIGHT_HIGHTLIGHT, BUTTON_RIGHT_X,BUTTON_LEFT_RIGHT_Y);
				else
					StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_BUTTON_RIGHT_NORMAL, BUTTON_RIGHT_X, BUTTON_LEFT_RIGHT_Y);

			//draw cancel
			if (MainActivity.isTouchDrapInRect(SCREEN_WIDTH-  DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.SELECTLEVEL_BACKGROUND_Y + 8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, SCREEN_WIDTH-  DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.SELECTLEVEL_BACKGROUND_Y + 8);
			else
				StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_CANCEL_NORMAL, SCREEN_WIDTH-  DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.SELECTLEVEL_BACKGROUND_Y + 8);

			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
