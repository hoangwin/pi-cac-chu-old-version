package com.xiaxio.monster.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.monster.BitmapFont;
import com.xiaxio.monster.Dialog;
import com.xiaxio.monster.GameLayer;
import com.xiaxio.monster.GameLib;
import com.xiaxio.monster.IConstant;
import com.xiaxio.monster.Map;
import com.xiaxio.monster.MonsterActivity;

import resolution.DEF;

import android.R.color;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;


public class StateWinLose extends MonsterActivity
{
	public static RectF rectF;
	public static int MENU_H = SCREEN_HEIGHT;
	public static int m_start = 0;
	public static int totalScore = 0;
	public static boolean isWin = false;

	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:
			Rect rect1 = new Rect();
			android_FontNormal.getTextBounds("agHa", 0,3, rect1);
			DEF.WINLOSE_CONTENT_SPACE_H = (int)(1.5*rect1.height());
			if (isWin) {
				totalScore = (int) (Map.mcountAllPaired * 10 + Map.mAllScoreBonus + (Map.mTimerDecrease - Map.mTimerCount) / 200);
				MonsterActivity.mLevelUnlock++;
				MonsterActivity.mainActivity.saveGame();
			}
			DEF.WINLOSE_BACKGROUND_W = SCREEN_WIDTH;
			DEF.WINLOSE_BACKGROUND_H = SCREEN_HEIGHT / 6 * 5;
			DEF.WINLOSE_BACKGROUND_X = 0;
			DEF.WINLOSE_BACKGROUND_Y = (SCREEN_HEIGHT - DEF.WINLOSE_BACKGROUND_H) / 2;

			rectF = new RectF(DEF.WINLOSE_BACKGROUND_X, DEF.WINLOSE_BACKGROUND_Y, DEF.WINLOSE_BACKGROUND_X + DEF.WINLOSE_BACKGROUND_W, DEF.WINLOSE_BACKGROUND_Y + DEF.WINLOSE_BACKGROUND_H);
			DEF.WINLOSE_TITLE_Y = DEF.WINLOSE_BACKGROUND_Y + 1;
			DEF.WINLOSE_CONTENT_Y = DEF.WINLOSE_TITLE_Y + 2 * (int)(60*scaleY);//MonsterActivity.fontbig_Yellow.getFontHeight();

			DEF.WINLOSE_BUTTON_X2 = SCREEN_WIDTH / 2 - DEF.DIALOG_BUTTON_CONFIRM_H / 2;
			DEF.WINLOSE_BUTTON_X1 = DEF.WINLOSE_BUTTON_X2 - 2 * DEF.DIALOG_BUTTON_CONFIRM_W;

			DEF.WINLOSE_BUTTON_X3 = DEF.WINLOSE_BUTTON_X2 + 2 * DEF.DIALOG_BUTTON_CONFIRM_W;

			DEF.WINLOSE_BUTTON_Y1 = DEF.WINLOSE_BACKGROUND_Y + DEF.WINLOSE_BACKGROUND_H - DEF.DIALOG_BUTTON_CONFIRM_H - 2;
			DEF.WINLOSE_BUTTON_Y2 = DEF.WINLOSE_BUTTON_Y3 = DEF.WINLOSE_BUTTON_Y1;
			break;
		case MESSAGE_UPDATE:
			if (frameCountCurrentState < 20)
				return;
			if (MonsterActivity.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_GAMEPLAY);

			if (isKeyReleased(KeyEvent.KEYCODE_BACK) || MonsterActivity.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_MAINMENU);
			if (isWin)
				if (MonsterActivity.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H)) {
					MonsterActivity.mcurrentlevel++;
					changeState(STATE_GAMEPLAY);
				}

			break;
		case MESSAGE_PAINT:
			StateGameplay.SendMessage(MESSAGE_PAINT);
			if (Dialog.isShowDialog) {
				Dialog.drawDialog(mainCanvas);
				return;
			}

			MonsterActivity.mainPaint.setStyle(Style.FILL);
			MonsterActivity.mainPaint.setARGB(220, 0, 0, 0);
			MonsterActivity.mainCanvas.drawRect(0, 0, MonsterActivity.SCREEN_WIDTH, MonsterActivity.SCREEN_HEIGHT, MonsterActivity.mainPaint);
			//	FishRescue.mainPaint.setStyle(Style.FILL_AND_STROKE);
			//	FishRescue.mainPaint.setColor(Color.rgb(67, 120, 167));
			//	FishRescue.mainCanvas.drawRoundRect(rectF, 25, 25, FishRescue.mainPaint);//WINLOSE_TITLE_CONTENT_SPACE_H

			//draw text
			Rect rectBig = new Rect();
			android_FontBig.getTextBounds("agHa", 0,3, rectBig);
			Rect rect = new Rect();
			android_FontNormal.getTextBounds("agHa", 0,3, rect);
			android_FontBig.setTextAlign(Align.CENTER);
			if (!isWin) {
				mainCanvas.drawText(  "YOU LOSE",  DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y, android_FontBig);
				//TwinsCandy.fontbig_Yellow.drawString(TwinsCandy.mainCanvas, "YOU LOSE", DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y, BitmapFont.ALIGN_CENTER);

			} else {
				mainCanvas.drawText(  "YOU WIN",  DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y, android_FontBig);
				//TwinsCandy.fontbig_Yellow.drawString(TwinsCandy.mainCanvas, "YOU WIN", DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y, BitmapFont.ALIGN_CENTER);
				if (totalScore < 450)
					StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_START_1, DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y + (int)(70*scaleY));
				else if (totalScore < 550)
					StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_START_2, DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y + (int)(70*scaleY));
				else
					StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_START_3, DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y + (int)(70*scaleY));
			}
			android_FontNormal.setTextAlign(Align.LEFT);
			mainCanvas.drawText(  "Score :",   DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y, android_FontNormal);
			//TwinsCandy.fontsmall_Yellow.drawString(TwinsCandy.mainCanvas, "Score :", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y, BitmapFont.ALIGN_LEFT);
			mainCanvas.drawText(  "==================",   DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + DEF.WINLOSE_CONTENT_SPACE_H, android_FontNormal);
			//TwinsCandy.fontsmall_Yellow.drawString(TwinsCandy.mainCanvas, "==================", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_LEFT);

			mainCanvas.drawText( "Paired :", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 2 * DEF.WINLOSE_CONTENT_SPACE_H, android_FontNormal);
			android_FontNormal.setTextAlign(Align.RIGHT);
			mainCanvas.drawText( " " + (Map.mcountAllPaired * 10), DEF.WINLOSE_CONTENT_X + DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 2 * DEF.WINLOSE_CONTENT_SPACE_H, android_FontNormal);
			android_FontNormal.setTextAlign(Align.LEFT);
			mainCanvas.drawText( "Bonus", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 3 * DEF.WINLOSE_CONTENT_SPACE_H,android_FontNormal);
			android_FontNormal.setTextAlign(Align.RIGHT);
			mainCanvas.drawText( " " + Map.mAllScoreBonus, DEF.WINLOSE_CONTENT_X + DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 3 * DEF.WINLOSE_CONTENT_SPACE_H, android_FontNormal);
			android_FontNormal.setTextAlign(Align.LEFT);
			mainCanvas.drawText( "Timer remain", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 4 * DEF.WINLOSE_CONTENT_SPACE_H, android_FontNormal);
			android_FontNormal.setTextAlign(Align.RIGHT);
			mainCanvas.drawText(" " + (Map.mTimerDecrease - Map.mTimerCount) / 200, DEF.WINLOSE_CONTENT_X + DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 4 * DEF.WINLOSE_CONTENT_SPACE_H,android_FontNormal);
			android_FontNormal.setTextAlign(Align.LEFT);
			mainCanvas.drawText( "==================", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 5 * DEF.WINLOSE_CONTENT_SPACE_H, android_FontNormal);
			android_FontNormal.setTextAlign(Align.LEFT);
			mainCanvas.drawText("Total", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 6 * DEF.WINLOSE_CONTENT_SPACE_H, android_FontNormal);
			android_FontNormal.setTextAlign(Align.RIGHT);
			mainCanvas.drawText(" " + (Map.mcountAllPaired * 10 + Map.mAllScoreBonus + (Map.mTimerDecrease - Map.mTimerCount) / 200), DEF.WINLOSE_CONTENT_X + DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 6 * DEF.WINLOSE_CONTENT_SPACE_H,android_FontNormal);
			android_FontNormal.setTextAlign(Align.CENTER);
			if (!MonsterActivity.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_RETRY_NORMAL, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);
			else
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_RETRY_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);

			if (MonsterActivity.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_MAINMENU_NORMAL, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
			else
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_MAINMENU_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);

			if (isWin) {
				if (MonsterActivity.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_NEXT_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
				else
					StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_NEXT_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
			}

			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
