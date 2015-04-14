package com.xiaxio.halloween.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.halloween.BitmapFont;
import com.xiaxio.halloween.Dialog;
import com.xiaxio.halloween.GameLayer;
import com.xiaxio.halloween.GameLib;
import com.xiaxio.halloween.HalloweenActivity;
import com.xiaxio.halloween.IConstant;
import com.xiaxio.halloween.Map;

import resolution.DEF;

import android.R.color;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF; 
import android.graphics.Paint.Style;
import android.view.KeyEvent;


public class StateWinLose extends HalloweenActivity
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
			DEF.WINLOSE_CONTENT_SPACE_H = fontsmall_White.getFontHeight();
			if(isWin)
			{
				totalScore =  (int) (Map.mcountAllPaired*10 +Map.mAllScoreBonus +(Map.mTimerDecrease - Map.mTimerCount)/200) ;
				HalloweenActivity.mLevelUnlock++;
				HalloweenActivity.mainActivity.saveGame();
			}
			DEF.WINLOSE_BACKGROUND_W = SCREEN_WIDTH / 4 * 3;
			DEF.WINLOSE_BACKGROUND_H = SCREEN_HEIGHT / 6 * 5;
			 DEF.WINLOSE_BACKGROUND_X =(SCREEN_WIDTH - DEF.WINLOSE_BACKGROUND_W) / 2;
			 DEF.WINLOSE_BACKGROUND_Y = (SCREEN_HEIGHT - DEF.WINLOSE_BACKGROUND_H) / 2;
			 
			rectF = new RectF( DEF.WINLOSE_BACKGROUND_X, DEF.WINLOSE_BACKGROUND_Y,  DEF.WINLOSE_BACKGROUND_X+ DEF.WINLOSE_BACKGROUND_W,  DEF.WINLOSE_BACKGROUND_Y + DEF.WINLOSE_BACKGROUND_H);
			DEF.WINLOSE_TITLE_Y = DEF.WINLOSE_BACKGROUND_Y + 1;
			DEF.WINLOSE_CONTENT_Y = DEF.WINLOSE_TITLE_Y + 2*HalloweenActivity.fontbig_Yellow.getFontHeight();
			
			DEF.WINLOSE_BUTTON_X2 = SCREEN_WIDTH/2 - DEF.DIALOG_BUTTON_CONFIRM_H/2;
			DEF.WINLOSE_BUTTON_X1 =DEF.WINLOSE_BUTTON_X2 - 2* DEF.DIALOG_BUTTON_CONFIRM_W;
			
			DEF.WINLOSE_BUTTON_X3 =DEF.WINLOSE_BUTTON_X2 + 2* DEF.DIALOG_BUTTON_CONFIRM_W;
					
			DEF.WINLOSE_BUTTON_Y1 = DEF.WINLOSE_BACKGROUND_Y + DEF.WINLOSE_BACKGROUND_H -  DEF.DIALOG_BUTTON_CONFIRM_H -2;
			DEF.WINLOSE_BUTTON_Y2 = DEF.WINLOSE_BUTTON_Y3 = DEF.WINLOSE_BUTTON_Y1;
			break;
		case MESSAGE_UPDATE:

			if (HalloweenActivity.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_GAMEPLAY);
			
			if (isKeyReleased(KeyEvent.KEYCODE_BACK)||HalloweenActivity.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_MAINMENU);
			if(isWin) 
				if (HalloweenActivity.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				{
					HalloweenActivity.mcurrentlevel++;
					changeState(STATE_GAMEPLAY);
				}
			

			break;
		case MESSAGE_PAINT:
			StateGameplay.SendMessage(MESSAGE_PAINT);
			if (Dialog.isShowDialog) {
				Dialog.drawDialog(mainCanvas);
				return;
			}
			HalloweenActivity.mainPaint.setStyle(Style.FILL);
			HalloweenActivity.mainPaint.setARGB(150, 0, 0, 0);
			HalloweenActivity.mainCanvas.drawRect(0, 0, HalloweenActivity.SCREEN_WIDTH, HalloweenActivity.SCREEN_HEIGHT, HalloweenActivity.mainPaint);

			HalloweenActivity.mainPaint.setStyle(Style.FILL);
			HalloweenActivity.mainPaint.setARGB(200, 0, 0, 0);
			HalloweenActivity.mainCanvas.drawRect(0, 0, HalloweenActivity.SCREEN_WIDTH, HalloweenActivity.SCREEN_HEIGHT, HalloweenActivity.mainPaint);
			HalloweenActivity.mainPaint.setStyle(Style.FILL_AND_STROKE);
			HalloweenActivity.mainPaint.setColor(Color.rgb(67, 120, 167));
			HalloweenActivity.mainCanvas.drawRoundRect(rectF, 25, 25, HalloweenActivity.mainPaint);//WINLOSE_TITLE_CONTENT_SPACE_H

			//draw text
			if(!isWin)
			{
				HalloweenActivity.fontbig_Yellow.drawString(HalloweenActivity.mainCanvas, "YOU LOSE", DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y, BitmapFont.ALIGN_CENTER);
				
			}	
			else
			{
				HalloweenActivity.fontbig_Yellow.drawString(HalloweenActivity.mainCanvas, "YOU WIN", DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y , BitmapFont.ALIGN_CENTER);
				if(totalScore < 450)
					StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_START_1, DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y+ HalloweenActivity.fontbig_Yellow.getFontHeight());
				else if(totalScore < 550)
					StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_START_2, DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y+ HalloweenActivity.fontbig_Yellow.getFontHeight());
				else
					StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_START_3, DEF.WINLOSE_TITLE_X, DEF.WINLOSE_TITLE_Y+ HalloweenActivity.fontbig_Yellow.getFontHeight());
			}	
			
			
			
			HalloweenActivity.fontsmall_Yellow.drawString(HalloweenActivity.mainCanvas, "Score :", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y, BitmapFont.ALIGN_LEFT);
			
			HalloweenActivity.fontsmall_Yellow.drawString(HalloweenActivity.mainCanvas, "==================", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y+ DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_LEFT);
			
			HalloweenActivity.fontsmall_Yellow.drawString(HalloweenActivity.mainCanvas, "Paired :", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 2*DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_LEFT);
			HalloweenActivity.fontsmall_White.drawString(HalloweenActivity.mainCanvas, " " + (Map.mcountAllPaired*10), DEF.WINLOSE_CONTENT_X + DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 2*DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_RIGHT);

			HalloweenActivity.fontsmall_Yellow.drawString(HalloweenActivity.mainCanvas, "Bonus", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 3 * DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_LEFT);
			HalloweenActivity.fontsmall_White.drawString(HalloweenActivity.mainCanvas, " " + Map.mAllScoreBonus, DEF.WINLOSE_CONTENT_X + DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 3 * DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_RIGHT);

			HalloweenActivity.fontsmall_Yellow.drawString(HalloweenActivity.mainCanvas, "Timer remain", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 4 * DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_LEFT);
			HalloweenActivity.fontsmall_White.drawString(HalloweenActivity.mainCanvas, " " +(Map.mTimerDecrease - Map.mTimerCount)/200, DEF.WINLOSE_CONTENT_X + DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 4 * DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_RIGHT);
			
			HalloweenActivity.fontsmall_Yellow.drawString(HalloweenActivity.mainCanvas, "==================", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y+ 5 * DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_LEFT);
			
			HalloweenActivity.fontsmall_Yellow.drawString(HalloweenActivity.mainCanvas, "Total", DEF.WINLOSE_CONTENT_X - DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 6 * DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_LEFT);
			HalloweenActivity.fontsmall_White.drawString(HalloweenActivity.mainCanvas, " " + (Map.mcountAllPaired*10 +Map.mAllScoreBonus +(Map.mTimerDecrease - Map.mTimerCount)/200 ), DEF.WINLOSE_CONTENT_X + DEF.WINLOSE_CONTENT_SPACE_W, DEF.WINLOSE_CONTENT_Y + 6 * DEF.WINLOSE_CONTENT_SPACE_H, BitmapFont.ALIGN_RIGHT);
			
			if (HalloweenActivity.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_RETRY_NORMAL, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);
			else
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_RETRY_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);
			
			
			if (HalloweenActivity.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_MAINMENU_NORMAL, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
			else
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_MAINMENU_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
			
			if(isWin)
			{
				if (HalloweenActivity.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_NEXT_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
				else
					StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_NEXT_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
			}
			

			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
