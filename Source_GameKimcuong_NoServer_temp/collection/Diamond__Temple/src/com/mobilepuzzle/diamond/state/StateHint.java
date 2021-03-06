package com.mobilepuzzle.diamond.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.mobilepuzzle.diamond.BitmapFont;
import com.mobilepuzzle.diamond.DEF;
import com.mobilepuzzle.diamond.Dialog;
import com.mobilepuzzle.diamond.GameLayer;
import com.mobilepuzzle.diamond.GameLib;
import com.mobilepuzzle.diamond.GameThread;
import com.mobilepuzzle.diamond.IConstant;
import com.mobilepuzzle.diamond.Map;
import com.mobilepuzzle.diamond.DiamondTemple;
import com.mobilepuzzle.diamond.SoundManager;


import android.graphics.Color;
import android.graphics.Rect;

import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;

public class StateHint extends DiamondTemple
{
	public static RectF rectF;
	public static int NUM_ROW = 5;
	public static int NUM_COL = 4;
	public static int MAX_PAG = 1;
	public static int mcurrentPage = 0;
	public static int SELECTLEVEL_BUTTON_W = 0;
	public static int SELECTLEVEL_BUTTON_H = 0;
	public static int SELECTLEVEL_BEGIN_Y = 0;
	public static int SELECTLEVEL_BEGIN_X = 0;
	public static int BUTTON_ARROW_CONFIRM_W = 60;
	public static int BUTTON_ARROW_CONFIRM_H = 60;
	public static int HINT_X = 5;
	public static int HINT_Y = SCREEN_HEIGHT / 4;
	public static int HINT_W = SCREEN_WIDTH - 10;
	public static int HINT_H = (int)(653*scaleY); 

	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:
			Map.targetScore = (3000 + StateGameplay.mcurrentlevel*1000);
			rectF = new RectF(HINT_X, HINT_Y, HINT_X + HINT_W, HINT_Y + HINT_H);
			mcurrentPage = DiamondTemple.mLevelUnlock / (NUM_ROW * NUM_COL);

			SELECTLEVEL_BUTTON_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_SELECTLEVEL_NORMAL);
			SELECTLEVEL_BUTTON_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_SELECTLEVEL_NORMAL);

			SELECTLEVEL_BEGIN_Y = (SCREEN_HEIGHT - ((SELECTLEVEL_BUTTON_H + DEF.SELECTLEVEL_CONTENT_SPACE_H) * (NUM_ROW - 1))) / 2 + 20;
			SELECTLEVEL_BEGIN_X = (SCREEN_WIDTH - ((SELECTLEVEL_BUTTON_W + DEF.SELECTLEVEL_CONTENT_SPACE_W) * (NUM_COL - 1))) / 2;

			DEF.SELECTLEVEL_CONTENT_SPACE_H = ((SCREEN_HEIGHT - SELECTLEVEL_BEGIN_Y) - NUM_ROW * SELECTLEVEL_BUTTON_H - SCREEN_HEIGHT / 10) / (NUM_ROW - 1);
			BUTTON_ARROW_CONFIRM_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_LEFT_NORMAL);
			BUTTON_ARROW_CONFIRM_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_LEFT_NORMAL);
			//SoundManager.pausesoundLoop(0);
			break;
		case MESSAGE_UPDATE:
			if (isTouchReleaseScreen()) {
				changeState(STATE_GAMEPLAY);
			}
			break;
		case MESSAGE_PAINT:

			DiamondTemple.mainPaint.setColor(Color.BLACK);
			DiamondTemple.mainPaint.setStyle(Style.FILL);			
			mainCanvas.drawBitmap(StateMainMenu.splashBitmap, 0,0, mainPaint);

			//Monster.mainPaint.setStyle(Style.FILL);
			//Monster.mainPaint.setARGB(150, 0, 0, 0);
			//Monster.mainCanvas.drawRect(0, 0, Monster.SCREEN_WIDTH, Monster.SCREEN_HEIGHT, Monster.mainPaint);
			//DiamondTemple.mainPaint.setStyle(Style.FILL_AND_STROKE);
			//DiamondTemple.mainPaint.setColor(Color.argb(170,226, 204, 104));
			//DiamondTemple.mainCanvas.drawRoundRect(rectF, 25, 25, DiamondTemple.mainPaint);
			Rect textBounds =  new  Rect();
			DiamondTemple.spriteUI.drawAFrame(mainCanvas, 0,HINT_X, HINT_Y);
			android_FontNormal.getTextBounds("Maig", 0, "Maig".length(), textBounds);
			
			String str = "";
			if (StateGameplay.gameMode == 0) {
				mainCanvas.drawText( "In QuickPlay mode" , SCREEN_WIDTH / 2,  HINT_Y  +  4*textBounds.height() , android_FontNormal);
				mainCanvas.drawText( "you will Play game" , SCREEN_WIDTH / 2,  HINT_Y  + 5* textBounds.height(), android_FontNormal);
				mainCanvas.drawText( "in 60 second." , SCREEN_WIDTH / 2,  HINT_Y +  6*textBounds.height(), android_FontNormal);
				//str = "In QuickPlay mode you will Play game in 60 second.";

			} else if (StateGameplay.gameMode == 1) {
				mainCanvas.drawText("Level : " + (StateGameplay.mcurrentlevel +1), SCREEN_WIDTH / 2,  HINT_Y +  3*textBounds.height(), android_FontNormal);
				mainCanvas.drawText("In This level you", SCREEN_WIDTH / 2,  HINT_Y +  4*textBounds.height(), android_FontNormal);
				mainCanvas.drawText("must get " +Map.targetScore +" score ", SCREEN_WIDTH / 2,  HINT_Y +  5*textBounds.height(), android_FontNormal);
				mainCanvas.drawText("to complete level", SCREEN_WIDTH / 2,  HINT_Y + 6*textBounds.height(), android_FontNormal);
				//str = "Level : " + (StateGameplay.mcurrentlevel +1)+ "\nIn This level you must get " +Map.targetScore +" score to complete level";
			} else 				
			{
				mainCanvas.drawText("CHALLENGE MODE : " , SCREEN_WIDTH / 2,  HINT_Y +  3*textBounds.height(), android_FontNormal);
				mainCanvas.drawText("In This mode your", SCREEN_WIDTH / 2,  HINT_Y +  4*textBounds.height(), android_FontNormal);
				mainCanvas.drawText("score is unlimitd. Get", SCREEN_WIDTH / 2,  HINT_Y +  5*textBounds.height(), android_FontNormal);
				mainCanvas.drawText("more score and compared", SCREEN_WIDTH / 2,  HINT_Y + 6*textBounds.height(), android_FontNormal);
				mainCanvas.drawText("with orther player", SCREEN_WIDTH / 2,  HINT_Y + 7*textBounds.height(), android_FontNormal);
			}
			//fontsmall_White.drawString(mainCanvas, str, SCREEN_WIDTH / 2, HINT_Y + 3*fontsmall_White.getFontHeight(), SCREEN_WIDTH - 40, BitmapFont.ALIGN_CENTER);
			if (GameThread.timeCurrent % 1000 < 500)
				mainCanvas.drawText( "TOUCH SCREEN TO PLAY", SCREEN_WIDTH / 2,  HINT_Y + HINT_H, android_FontNormal);
				//fontbig_White.drawString(mainCanvas, "TOUCH SCREEN TO PLAY GAME", SCREEN_WIDTH / 2, HINT_Y + HINT_H - fontbig_White.getFontHeight(), SCREEN_WIDTH - 20, BitmapFont.ALIGN_CENTER);
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
