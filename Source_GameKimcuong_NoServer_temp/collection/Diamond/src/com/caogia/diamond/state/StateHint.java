package com.caogia.diamond.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.caogia.diamond.BitmapFont;
import com.caogia.diamond.Dialog;
import com.caogia.diamond.Diamond;
import com.caogia.diamond.GameLayer;
import com.caogia.diamond.GameLib;
import com.caogia.diamond.GameThread;
import com.caogia.diamond.IConstant;
import com.caogia.diamond.Map;
import com.caogia.diamond.SoundManager;

import resolution.DEF;

import android.graphics.Color;

import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;

public class StateHint extends Diamond
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
	public static int HINT_H = SCREEN_HEIGHT / 2; 

	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:
			Map.targetScore = (3000 + StateGameplay.mcurrentlevel*500);
			rectF = new RectF(HINT_X, HINT_Y, HINT_X + HINT_W, HINT_Y + HINT_H);
			mcurrentPage = Diamond.mLevelUnlock / (NUM_ROW * NUM_COL);

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

			Diamond.mainPaint.setColor(Color.BLACK);
			Diamond.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, Diamond.mainPaint);
			matrix.reset();
			matrix.setScale(SCREEN_WIDTH * 1f / 800, SCREEN_HEIGHT * 1f / 1280);
			mainPaint.setAntiAlias(true);
			mainPaint.setFilterBitmap(true);
			mainPaint.setDither(true);
			mainCanvas.drawBitmap(StateMainMenu.splashBitmap, matrix, mainPaint);

			Diamond.mainPaint.setStyle(Style.FILL);
			Diamond.mainPaint.setARGB(150, 0, 0, 0);
			Diamond.mainCanvas.drawRect(0, 0, Diamond.SCREEN_WIDTH, Diamond.SCREEN_HEIGHT, Diamond.mainPaint);
			Diamond.mainPaint.setStyle(Style.FILL_AND_STROKE);
			Diamond.mainPaint.setColor(Color.argb(180, 0, 0, 0));
			Diamond.mainCanvas.drawRoundRect(rectF, 25, 25, Diamond.mainPaint);
			String str = "";
			if (StateGameplay.gameMode == 0) {
				str = "In QuickPlay mode you will Play game in 60 second.";

			} else {
				str = "Level : " + (StateGameplay.mcurrentlevel +1)+ "\nIn This level you must get " +Map.targetScore +" score to complete level";
			}
			fontsmall_White.drawString(mainCanvas, str, SCREEN_WIDTH / 2, HINT_Y + 3*fontsmall_White.getFontHeight(), SCREEN_WIDTH - 40, BitmapFont.ALIGN_CENTER);
			if (GameThread.timeCurrent % 1000 < 500)
				fontbig_White.drawString(mainCanvas, "TOUCH SCREEN TO PLAY GAME", SCREEN_WIDTH / 2, HINT_Y + HINT_H - fontbig_White.getFontHeight(), SCREEN_WIDTH - 20, BitmapFont.ALIGN_CENTER);
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
