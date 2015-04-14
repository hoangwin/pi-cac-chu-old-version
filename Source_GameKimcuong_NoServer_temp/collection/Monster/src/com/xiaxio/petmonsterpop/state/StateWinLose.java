package com.xiaxio.petmonsterpop.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.petmonsterpop.BitmapFont;
import com.xiaxio.petmonsterpop.DEF;
import com.xiaxio.petmonsterpop.Dialog;
import com.xiaxio.petmonsterpop.GameLayer;
import com.xiaxio.petmonsterpop.GameLib;
import com.xiaxio.petmonsterpop.GameThread;
import com.xiaxio.petmonsterpop.IConstant;
import com.xiaxio.petmonsterpop.Map;
import com.xiaxio.petmonsterpop.Monster;
import com.xiaxio.petmonsterpop.SoundManager;


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

public class StateWinLose extends Monster
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
		

			Monster.mainActivity.saveGame();
			if(StateGameplay.gameMode ==1)
				if(Monster.mcurrentlevel >= Monster.mLevelUnlock)
					Monster.mLevelUnlock++;
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
			if (Monster.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_GAMEPLAY);

			if (isKeyReleased(KeyEvent.KEYCODE_BACK) || Monster.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_MAINMENU);
			if (Monster.mcurrentlevel < (StateSelectLevel.NUM_MAX_LEVEL -1)&& StateGameplay.gameMode ==1)
			{
				if (Monster.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H)) {
					Monster.mcurrentlevel++;
					changeState(STATE_HINT);
				}
			}
			else if (StateGameplay.gameMode == 2)
			{
				if (Monster.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					changeState(STATE_LEADERBOARD);
			}
			
			if (Monster.isTouchReleaseInRect(RatingButtonX - spriteUI.getFrameWidth(4)/2, RatingButtonY - spriteUI.getFrameHeight(4)/2,spriteUI.getFrameWidth(4), spriteUI.getFrameHeight(4)))
			{
				try {
					Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.xiaxio.petmonsterpop");
					mainActivity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
				} catch (Exception e) {

				}
			}
			
			if (Monster.isTouchReleaseInRect(RatingButtonX - spriteUI.getFrameWidth(6)/2, (int)(scaleY*150) - spriteUI.getFrameHeight(6)/2,spriteUI.getFrameWidth(6), spriteUI.getFrameHeight(6)))
			{
				try {
					Uri uri = Uri.parse("market://details?id=com.xiaxio.fruitslide");
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

			Monster.mainPaint.setStyle(Style.FILL);
			Monster.mainPaint.setARGB(100, 0, 0, 0);
			Monster.mainCanvas.drawRect(0, 0, Monster.SCREEN_WIDTH, Monster.SCREEN_HEIGHT, Monster.mainPaint);
			//FishRescue.mainPaint.setStyle(Style.FILL_AND_STROKE);
			//FishRescue.mainPaint.setColor(Color.rgb(67, 120, 167));
			//FishRescue.mainCanvas.drawRoundRect(rectF, 25, 25, FishRescue.mainPaint);//WINLOSE_TITLE_CONTENT_SPACE_H
			matrix.reset();
			
			float scale = (1.0f * frameCountCurrentState) / 20;
		
			if (scale > 1)
				scale = 1;
			
			if (!Monster.isTouchDrapInRect( SCREEN_WIDTH / 2 - spriteUI.getFrameWidth(6)/2, (int)(scaleY*150) - spriteUI.getFrameHeight(6)/2,spriteUI.getFrameWidth(6), spriteUI.getFrameHeight(6)))
			{
				Monster.spriteUI.drawAFrame(mainCanvas, 6, SCREEN_WIDTH / 2, (int)(scaleY*150));
			}
			else
			{
				Monster.spriteUI.paint.setAlpha(150);
				Monster.spriteUI.drawAFrame(mainCanvas, 6, SCREEN_WIDTH / 2, (int)(scaleY*150));
				Monster.spriteUI.paint.setAlpha(255);
			}
			
			if(StateGameplay.gameMode ==0)
			{
				Monster.spriteUI.drawAFrame(mainCanvas, 2, 0, (int)(scaleY*300));
				
			}
			else
				Monster.spriteUI.drawAFrame(mainCanvas, 1, 0, (int)(scaleY*300));
			//draw text
			if (scale >= 1) {
				int tempHeight = Monster.fontbig_White.getFontHeight();
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

				if (!Monster.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_RETRY_NORMAL, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);
				else
					StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_RETRY_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);

				if (!Monster.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_MAINMENU_NORMAL, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
				else
					StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_MAINMENU_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
				if (Monster.mcurrentlevel <  (StateSelectLevel.NUM_MAX_LEVEL -1) && StateGameplay.gameMode ==1)
				{
					if (!Monster.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
						StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_NEXT_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
					else
						StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_NEXT_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
				}
				else if (StateGameplay.gameMode == 2)// mode dua diem
				{
					if (!Monster.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
						StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_LEADERBOARD_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
					else
						StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_LEADERBOARD_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
				}
				
				if (!Monster.isTouchDrapInRect(RatingButtonX - spriteUI.getFrameWidth(4)/2, RatingButtonY - spriteUI.getFrameHeight(4)/2,spriteUI.getFrameWidth(4), spriteUI.getFrameHeight(4)))
					spriteUI.drawAFrame(Monster.mainCanvas, 4,RatingButtonX, RatingButtonY);
				else
					spriteUI.drawAFrame(Monster.mainCanvas, 5, RatingButtonX,RatingButtonY);
				//rating button
				
				
			}
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
