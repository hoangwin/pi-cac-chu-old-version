package com.mobilepuzzle.candypopHD.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.mobilepuzzle.candypopHD.BitmapFont;
import com.mobilepuzzle.candypopHD.CandyPop;
import com.mobilepuzzle.candypopHD.DEF;
import com.mobilepuzzle.candypopHD.Dialog;
import com.mobilepuzzle.candypopHD.GameLayer;
import com.mobilepuzzle.candypopHD.GameLib;
import com.mobilepuzzle.candypopHD.IConstant;
import com.mobilepuzzle.candypopHD.Map;
import com.mobilepuzzle.candypopHD.SoundManager;



import android.R.color;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;

public class StateWinLose extends CandyPop
{
	public static RectF rectF;
	public static int MENU_H = SCREEN_HEIGHT;
	public static int m_start = 0;
	public static boolean isWin = false;
	
	public static int RatingButtonX = SCREEN_WIDTH/2;
	public static int RatingButtonY = (int)(960*scaleY);

	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:
		

			CandyPop.mainActivity.saveGame();
			if(StateGameplay.gameMode ==1)
				if(CandyPop.mcurrentlevel >= CandyPop.mLevelUnlock)
					CandyPop.mLevelUnlock++;
			SoundManager.pausesoundLoop(0);
			//if (isWin)
				SoundManager.playSound(SoundManager.SOUND_WIN, 1);
			//else
			//	SoundManager.playSound(SoundManager.SOUND_LOSE, 1);

			DEF.WINLOSE_BUTTON_X2 = SCREEN_WIDTH / 2 - DEF.DIALOG_BUTTON_CONFIRM_H / 2;
			DEF.WINLOSE_BUTTON_X1 = DEF.WINLOSE_BUTTON_X2 - 2 * DEF.DIALOG_BUTTON_CONFIRM_W;
			DEF.WINLOSE_BUTTON_X3 = DEF.WINLOSE_BUTTON_X2 + 2 * DEF.DIALOG_BUTTON_CONFIRM_W;
			DEF.WINLOSE_BUTTON_Y1 = (int) (700 * SCREEN_HEIGHT * 1.0 / 1280);
			DEF.WINLOSE_BUTTON_Y2 = DEF.WINLOSE_BUTTON_Y3 = DEF.WINLOSE_BUTTON_Y1;
			break;
		case MESSAGE_UPDATE:
			if (frameCountCurrentState < 20)
				return;
			if (CandyPop.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_GAMEPLAY);

			if (isKeyReleased(KeyEvent.KEYCODE_BACK) || CandyPop.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_MAINMENU);
			if (CandyPop.mcurrentlevel <  (StateSelectLevel.NUM_MAX_LEVEL -1) && StateGameplay.gameMode ==1)
			{
				if (CandyPop.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H)) {
					CandyPop.mcurrentlevel++;
					changeState(STATE_HINT);
				}
			}
			else if (StateGameplay.gameMode == 2)
			{
				if (CandyPop.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					changeState(STATE_LEADERBOARD);
			}
			
			if (CandyPop.isTouchReleaseInRect(RatingButtonX - spriteUI.getFrameWidth(4)/2, RatingButtonY - spriteUI.getFrameHeight(4)/2,spriteUI.getFrameWidth(4), spriteUI.getFrameHeight(4)))
			{
				try {
					Uri uri = Uri.parse("market://details?id=com.mobilepuzzle.candypopHD");
					mainActivity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
				} catch (Exception e) {

				}
			}
			break;
		case MESSAGE_PAINT:
			StateGameplay.SendMessage(MESSAGE_PAINT);
			if (Dialog.isShowDialog) {
				Dialog.drawDialog(mainCanvas); 
				return;
			}

			CandyPop.mainPaint.setStyle(Style.FILL);
			CandyPop.mainPaint.setARGB(100, 0, 0, 0);
			CandyPop.mainCanvas.drawRect(0, 0, CandyPop.SCREEN_WIDTH, CandyPop.SCREEN_HEIGHT, CandyPop.mainPaint);
			//FishRescue.mainPaint.setStyle(Style.FILL_AND_STROKE);
			//FishRescue.mainPaint.setColor(Color.rgb(67, 120, 167));
			//FishRescue.mainCanvas.drawRoundRect(rectF, 25, 25, FishRescue.mainPaint);//WINLOSE_TITLE_CONTENT_SPACE_H
			matrix.reset();
			
			float scale = (1.0f * frameCountCurrentState) / 20;
		
			if (scale > 1)
				scale = 1; 
			
			if(StateGameplay.gameMode ==0)
			{
				CandyPop.spriteUI.drawAFrame(mainCanvas, 2, 0, (int)(scaleY*300));
				
			}
			else
				CandyPop.spriteUI.drawAFrame(mainCanvas, 1, 0, (int)(scaleY*300));
			//draw text
			if (scale >= 1) {
				int tempHeight = CandyPop.fontbig_White.getFontHeight();
				Rect textBounds =  new  Rect();
				android_FontBig.getTextBounds("Maig", 0, "Maig".length(), textBounds);
				mainCanvas.drawText( "YOUR SCORE :",  SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 -  textBounds.height(), android_FontBigBoder);
				mainCanvas.drawText( "YOUR SCORE :",  SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 -  textBounds.height(), android_FontBig);
				if(StateGameplay.gameMode == 2)	
				{
					mainCanvas.drawText(  ""+ Map.mTopScore,  SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2, android_FontBigBoder);
					mainCanvas.drawText(  ""+ Map.mTopScore,  SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2, android_FontBig);
				}
				else
				{
					mainCanvas.drawText(  ""+ Map.score,  SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2, android_FontBigBoder);
					mainCanvas.drawText(  ""+ Map.score,  SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2, android_FontBig);
				}
				//Monster.fontbig_White.drawString(Monster.mainCanvas, "YOUR SCORE :", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 - tempHeight / 2, BitmapFont.ALIGN_CENTER);
				//Monster.fontbig_White.drawString(Monster.mainCanvas, ""+ Map.score, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 + tempHeight / 2, BitmapFont.ALIGN_CENTER);

				if (!CandyPop.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(CandyPop.mainCanvas, DEF.FRAME_RETRY_NORMAL, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);
				else
					StateGameplay.spriteDPad.drawAFrame(CandyPop.mainCanvas, DEF.FRAME_RETRY_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);

				if (!CandyPop.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(CandyPop.mainCanvas, DEF.FRAME_MAINMENU_NORMAL, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
				else
					StateGameplay.spriteDPad.drawAFrame(CandyPop.mainCanvas, DEF.FRAME_MAINMENU_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
				if (CandyPop.mcurrentlevel <  (StateSelectLevel.NUM_MAX_LEVEL -1) && StateGameplay.gameMode ==1)
				{
					if (!CandyPop.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
						StateGameplay.spriteDPad.drawAFrame(CandyPop.mainCanvas, DEF.FRAME_NEXT_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
					else
						StateGameplay.spriteDPad.drawAFrame(CandyPop.mainCanvas, DEF.FRAME_NEXT_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
				}
				else if (StateGameplay.gameMode == 2)// mode dua diem
				{
					if (!CandyPop.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
						StateGameplay.spriteDPad.drawAFrame(CandyPop.mainCanvas, DEF.FRAME_LEADERBOARD_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
					else
						StateGameplay.spriteDPad.drawAFrame(CandyPop.mainCanvas, DEF.FRAME_LEADERBOARD_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
				}
				
				if (!CandyPop.isTouchDrapInRect(RatingButtonX - spriteUI.getFrameWidth(4)/2, RatingButtonY - spriteUI.getFrameHeight(4)/2,spriteUI.getFrameWidth(4), spriteUI.getFrameHeight(4)))
					spriteUI.drawAFrame(CandyPop.mainCanvas, 4,RatingButtonX, RatingButtonY);
				else
					spriteUI.drawAFrame(CandyPop.mainCanvas, 5, RatingButtonX,RatingButtonY);
				//rating button
				
				
			}
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
