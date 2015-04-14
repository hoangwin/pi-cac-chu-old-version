package com.megagame.animal.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.megagame.animal.BitmapFont;
import com.megagame.animal.Dialog;
import com.megagame.animal.GameLayer;
import com.megagame.animal.GameLib;
import com.megagame.animal.IConstant;
import com.megagame.animal.AnimalActivity;
import com.megagame.animal.SoundManager;

import resolution.DEF;

import android.graphics.Color;
import android.graphics.Rect;

import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;




public class StateSelectLevel extends AnimalActivity
{
	public static RectF rectF;
	public static int NUM_ROW = 4;
	public static int NUM_COL = 5;
	public static int MAX_PAG = 3;
	public static int mcurrentPage = 0;
	public static int SELECTLEVEL_BUTTON_W = 0;
	public static int SELECTLEVEL_BUTTON_H = 0;	
	public static int SELECTLEVEL_BEGIN_Y = 0;
	public static int SELECTLEVEL_BEGIN_X = 0;
	public static int BUTTON_ARROW_CONFIRM_W = 60;
	public static int BUTTON_ARROW_CONFIRM_H = 60;	 
	public static synchronized void SendMessage(int type)
	{

		switch (type) 
		{
		case MESSAGE_CTOR: 
			rectF = new RectF(DEF.SELECTLEVEL_BACKGROUND_X, DEF.SELECTLEVEL_BACKGROUND_Y, DEF.SELECTLEVEL_BACKGROUND_X + DEF.SELECTLEVEL_BACKGROUND_W, DEF.SELECTLEVEL_BACKGROUND_Y + DEF.SELECTLEVEL_BACKGROUND_H);
			mcurrentPage = AnimalActivity.mLevelUnlock / (NUM_ROW * NUM_COL);
			
			SELECTLEVEL_BUTTON_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_SELECTLEVEL_NORMAL);
			SELECTLEVEL_BUTTON_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_SELECTLEVEL_NORMAL);
			
			SELECTLEVEL_BEGIN_Y = (SCREEN_HEIGHT - ((SELECTLEVEL_BUTTON_H + DEF.SELECTLEVEL_CONTENT_SPACE_H)* (NUM_ROW -1)))/2+ 10;
			SELECTLEVEL_BEGIN_X = (SCREEN_WIDTH - ((SELECTLEVEL_BUTTON_W + DEF.SELECTLEVEL_CONTENT_SPACE_W)* (NUM_COL-1)))/2;
			DEF.SELECTLEVEL_CONTENT_SPACE_H = (SCREEN_HEIGHT - 2* DEF.SELECTLEVEL_BACKGROUND_Y-SELECTLEVEL_BUTTON_H/2)- 4 *SELECTLEVEL_BUTTON_H;
			DEF.SELECTLEVEL_CONTENT_SPACE_H = DEF.SELECTLEVEL_CONTENT_SPACE_H/4;
			BUTTON_ARROW_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_LEFT_NORMAL);
			BUTTON_ARROW_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_LEFT_NORMAL);			
			SoundManager.pausesoundLoop(0);
			break;
		case MESSAGE_UPDATE:
			for (int i = 0; i < NUM_ROW; i++) {
				int y = SELECTLEVEL_BEGIN_Y + i * (SELECTLEVEL_BUTTON_H + DEF.SELECTLEVEL_CONTENT_SPACE_H);
				int x = 0;
				for (int j = 0; j < NUM_COL; j++) {
					x = SELECTLEVEL_BEGIN_X + j * (SELECTLEVEL_BUTTON_W + DEF.SELECTLEVEL_CONTENT_SPACE_W);

					if (isTouchReleaseInRect(x - SELECTLEVEL_BUTTON_W / 2, y - SELECTLEVEL_BUTTON_H / 2, SELECTLEVEL_BUTTON_W, SELECTLEVEL_BUTTON_H)) {

						AnimalActivity.mcurrentlevel = mcurrentPage * NUM_COL * NUM_ROW + i * 5 + j;
						if (AnimalActivity.mcurrentlevel <= AnimalActivity.mLevelUnlock)
						{							
							
							changeState(STATE_GAMEPLAY);
						}break;
					}

				}
			}

			//left right button
			if (AnimalActivity.isTouchReleaseInRect(SELECTLEVEL_BEGIN_X-  3*BUTTON_ARROW_CONFIRM_W/2, SCREEN_HEIGHT / 2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H)) {
				mcurrentPage--;
				if (mcurrentPage < 0)
					mcurrentPage = MAX_PAG - 1;
				SoundManager.playSound(SoundManager.SOUND_SELECT, 1);
			}

			//right	
			if (AnimalActivity.isTouchReleaseInRect(SCREEN_WIDTH - (SELECTLEVEL_BEGIN_X-  BUTTON_ARROW_CONFIRM_W/2), SCREEN_HEIGHT / 2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H)) {
				mcurrentPage++;
				if (mcurrentPage >= MAX_PAG)
					mcurrentPage = 0;
				SoundManager.playSound(SoundManager.SOUND_SELECT, 1);
			}

			if (isKeyReleased(KeyEvent.KEYCODE_BACK) || AnimalActivity.isTouchReleaseInRect(DEF.SELECTLEVEL_BACKGROUND_X , DEF.SELECTLEVEL_BACKGROUND_Y + DEF.BUTTON_CANCEL_CONFIRM_H, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
			{
				
				
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
				mainCanvas.drawBitmap(StateMainMenu.splashBitmap, 0,0, mainPaint);
			} else {
				StateGameplay.SendMessage(MESSAGE_PAINT);
			}
			//PikachuActivity.mainPaint.setStyle(Style.FILL);
		//	PikachuActivity.mainPaint.setARGB(100, 0, 0, 0);
		//	PikachuActivity.mainCanvas.drawRect(0, 0, PikachuActivity.SCREEN_WIDTH, PikachuActivity.SCREEN_HEIGHT, PikachuActivity.mainPaint);
		//	PikachuActivity.mainPaint.setStyle(Style.FILL_AND_STROKE);
		//	PikachuActivity.mainPaint.setARGB(255, 67, 120, 167);
			//MainActivity.mainPaint.setColor(Color.rgb(67, 120, 167));

		//	PikachuActivity.mainCanvas.drawRoundRect(rectF, 25, 25, PikachuActivity.mainPaint);//DEF.SELECTLEVEL_TITLE_CONTENT_SPACE_H
			//draw text
			//PikachuActivity.fontbig_Yellow.drawString(PikachuActivity.mainCanvas, "SELECT LEVEL", DEF.SELECTLEVEL_TITLE_X, DEF.SELECTLEVEL_TITLE_Y - 5, BitmapFont.ALIGN_CENTER);
			
			Rect rect = new Rect();
			android_FontNormal.getTextBounds("agHa", 0,3, rect);
			android_FontNormal.setColor(Color.rgb(38, 210, 235));
			android_FontSmall.setColor(Color.rgb(38, 210, 235));
			//AnimalActivity.fontsmall_White.drawString(AnimalActivity.mainCanvas, "Page : " + (mcurrentPage+1)+"/" +MAX_PAG,DEF.SELECTLEVEL_BACKGROUND_X +SELECTLEVEL_BUTTON_W+2 , DEF.SELECTLEVEL_BACKGROUND_Y-2, BitmapFont.ALIGN_LEFT);
			mainCanvas.drawText( "Page : " + (mcurrentPage+1)+"/" +MAX_PAG, DEF.SELECTLEVEL_BACKGROUND_X +SELECTLEVEL_BUTTON_W+2 , rect.height(), android_FontSmall);
			

			
			for (int i = 0; i < NUM_ROW; i++) {
				int y = SELECTLEVEL_BEGIN_Y + i * (SELECTLEVEL_BUTTON_H + DEF.SELECTLEVEL_CONTENT_SPACE_H);
				int x = 0;
				for (int j = 0; j < NUM_COL; j++) {
					x = SELECTLEVEL_BEGIN_X + j * (SELECTLEVEL_BUTTON_W + DEF.SELECTLEVEL_CONTENT_SPACE_W);
					AnimalActivity.mcurrentlevel = mcurrentPage * NUM_COL * NUM_ROW + i * 5 + j;
					if (AnimalActivity.mcurrentlevel <= AnimalActivity.mLevelUnlock) {
						if (isTouchDrapInRect(x - SELECTLEVEL_BUTTON_W / 2, y - SELECTLEVEL_BUTTON_H / 2, SELECTLEVEL_BUTTON_W, SELECTLEVEL_BUTTON_H))
							StateGameplay.spriteDPad.drawAFrame(mainCanvas, DEF.FRAME_SELECTLEVEL_HIGHTLIGHT, x, y);
						else
							StateGameplay.spriteDPad.drawAFrame(mainCanvas, DEF.FRAME_SELECTLEVEL_NORMAL, x, y);
						//mainCanvas.drawText(  " " + (mcurrentPage * NUM_COL * NUM_ROW + i * NUM_ROW + j + 1) + " ", x, y + rect.height()/2, android_FontNormal);
						mainCanvas.drawText( " " + (mcurrentPage * NUM_COL * NUM_ROW + i * NUM_ROW + j + 1) + " ", x, y + rect.height()/2, android_FontNormal);
						//fontbig_White.drawString(mainCanvas, " " + (mcurrentPage * NUM_COL * NUM_ROW + i * NUM_ROW + j + 1) + " ", x, y - 3*fontbig_Yellow.getFontHeight()/4, BitmapFont.ALIGN_CENTER);
						if (AnimalActivity.mcurrentlevel !=AnimalActivity.mLevelUnlock)
						{	
						//	fontbig_Yellow.drawString(mainCanvas, "Pass", x, y - fontbig_Yellow.getFontHeight()/2  , BitmapFont.ALIGN_CENTER);
						}
						else
							mainCanvas.drawText(  "NEW", x, y + 3*rect.height()/2, android_FontNormal);
						//	fontbig_White.drawString(mainCanvas, "New", x, y   , BitmapFont.ALIGN_CENTER);
					} else {
						StateGameplay.spriteDPad.drawAFrame(mainCanvas, DEF.FRAME_SELECTLEVEL_LOCK, x, y);
					}
				}
			}
			//select left and right button
			mainCanvas.drawText(  "SELECT LEVEL", SCREEN_WIDTH / 2, 3*rect.height()/2, android_FontNormal);
		//	StateGameplay.fontbig_White.drawString(TwinsCandy.mainCanvas, "SELECT LEVEL", SCREEN_WIDTH / 2, fontbig_Yellow.getFontHeight()/2, BitmapFont.ALIGN_CENTER);
			if (!AnimalActivity.isTouchDrapInRect(SELECTLEVEL_BEGIN_X -  3*BUTTON_ARROW_CONFIRM_W/2 , SCREEN_HEIGHT / 2, BUTTON_ARROW_CONFIRM_W, BUTTON_ARROW_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(AnimalActivity.mainCanvas, DEF.FRAME_BUTTON_LEFT_NORMAL,SELECTLEVEL_BEGIN_X-  3*BUTTON_ARROW_CONFIRM_W/2 , SCREEN_HEIGHT / 2);
			else
				StateGameplay.spriteDPad.drawAFrame(AnimalActivity.mainCanvas, DEF.FRAME_BUTTON_LEFT_HIGHTLIGHT,SELECTLEVEL_BEGIN_X -  3*BUTTON_ARROW_CONFIRM_W/2 , SCREEN_HEIGHT / 2);
			//right	
			if (!AnimalActivity.isTouchDrapInRect(SCREEN_WIDTH - (SELECTLEVEL_BEGIN_X-  BUTTON_ARROW_CONFIRM_W/2), SCREEN_HEIGHT / 2, BUTTON_ARROW_CONFIRM_W, BUTTON_ARROW_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(AnimalActivity.mainCanvas, DEF.FRAME_BUTTON_RIGHT_NORMAL, SCREEN_WIDTH - (SELECTLEVEL_BEGIN_X-  BUTTON_ARROW_CONFIRM_W/2), SCREEN_HEIGHT / 2);
			else
				StateGameplay.spriteDPad.drawAFrame(AnimalActivity.mainCanvas, DEF.FRAME_BUTTON_RIGHT_HIGHTLIGHT, SCREEN_WIDTH - (SELECTLEVEL_BEGIN_X-  BUTTON_ARROW_CONFIRM_W/2), SCREEN_HEIGHT / 2);

			//draw cancel
			if (AnimalActivity.isTouchDrapInRect(DEF.SELECTLEVEL_BACKGROUND_X , DEF.SELECTLEVEL_BACKGROUND_Y  + DEF.BUTTON_CANCEL_CONFIRM_H, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(AnimalActivity.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, DEF.SELECTLEVEL_BACKGROUND_X , DEF.SELECTLEVEL_BACKGROUND_Y   + DEF.BUTTON_CANCEL_CONFIRM_H);
			else
				StateGameplay.spriteDPad.drawAFrame(AnimalActivity.mainCanvas, DEF.FRAME_CANCEL_NORMAL, DEF.SELECTLEVEL_BACKGROUND_X , DEF.SELECTLEVEL_BACKGROUND_Y  + DEF.BUTTON_CANCEL_CONFIRM_H);

			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
